package me.savvy.iskyblock.main;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.world.DataException;
import me.savvy.iskyblock.ISkyblock;
import me.savvy.iskyblock.facades.GridFacade;
import me.savvy.iskyblock.utils.ISchematic;
import me.savvy.iskyblock.utils.schematic.SchematicUtils;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SkyAPI {

    private static SkyAPI instance;
    private GridFacade gridFacade;

    public SkyAPI() {
        instance = this;
        gridFacade = new Grid();
    }

    public GridFacade getGridFacade() {
        return gridFacade;
    }

    public boolean hasIsland(UUID playerUUID) {
        return ISkyblock.getInstance().getSqlBuilder().exists("islands", "owner", playerUUID.toString());
    }

    public boolean hasIsland(Player player) {
        return hasIsland(player.getUniqueId());
    }

    public boolean hasIsland(OfflinePlayer offlinePlayer) {
        return hasIsland(offlinePlayer.getUniqueId());
    }

    public boolean isWithinIsland(Location location) {
        return gridFacade.isWithinIsland(location);
    }

    public Island getIsland(Location location) {
       return gridFacade.getIsland(location);
    }

    public Island getIsland(UUID uniqueId) {
        for (int row = 0; row < getGridFacade().getIslandGrid().length; row++) {
            for (int col = 0; col < getGridFacade().getIslandGrid()[row].length; col++) {
                Island island = getGridFacade().getIsland(row, col);
                if (island != null) {
                    if (island.getOwner() == uniqueId)
                    return island;
                }
            }
        }
        return null;
    }

    public static SkyAPI getInstance() {
        return (instance == null) ? new SkyAPI() : instance;
    }

    public boolean generateIsland(String schematicName, Island island) {
        int x = (island.getIslandX() == 0) ? island.getIslandX() : island.getIslandX() * 520;
        int z = (island.getIslandZ() == 0) ? island.getIslandZ() : island.getIslandZ() * 520;
        Location locationCenter = new Location
                (Bukkit.getWorld(ISkyblock.getInstance().getServerSettings().getWorldName())
                        , x, 115, z, (float) 90, (float) 2);
        try {
            ISchematic.paste(schematicName, locationCenter);
            sendWorldBorder(locationCenter, 200);
            Player player = Bukkit.getPlayer(island.getOwner());
            player.teleport(locationCenter);
            return true;
        } catch (MaxChangedBlocksException | DataException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendWorldBorder(Location location, int borderSize) {
        WorldBorder newBorder = new WorldBorder();
        newBorder.setCenter(location.getBlockX(), location.getBlockZ());
        newBorder.setWarningDistance(5);
        newBorder.setSize(borderSize);
        newBorder.setDamageAmount(10);
        newBorder.world = ((CraftWorld) location.getWorld()).getHandle();
        PacketPlayOutWorldBorder spawnBorderPacket3;
        spawnBorderPacket3 = new PacketPlayOutWorldBorder(newBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE);
        Location min = new Location(location.getWorld(), location.getX() - borderSize, location.getY() - 20, location.getZ() - borderSize);
        Location max = new Location(location.getWorld(), location.getX() + borderSize, location.getY() + 20, location.getZ() + borderSize);
        for (Player ps : Bukkit.getOnlinePlayers()) {
            // Check if player is at correct island
            if (ps.getLocation().toVector().isInAABB(min.toVector(), max.toVector()))
                ((CraftPlayer) ps).getHandle().playerConnection.sendPacket(spawnBorderPacket3);
        }

    }

    public Location getLocationFromString(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 6) {
            World w = Bukkit.getServer().getWorld(parts[0]);
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);
            return new Location(w, x, y, z, yaw, pitch);
        }
        return null;
    }

    public String getStringFromLocation(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
    }
}
