package me.savvy.iskyblock.utils;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.session.ClipboardHolder;
import me.savvy.iskyblock.ISkyblock;
import me.savvy.iskyblock.main.Island;
import me.savvy.iskyblock.runnable.BlockRegenRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ISchematic {

    public static void save(Player player, String schematicName) throws EmptyClipboardException, IOException, DataException {
        File schematic = new File(ISkyblock.getInstance().getSchematicFolder() + schematicName);
        File dir = new File(ISkyblock.getInstance().getSchematicFolder());
        if (!dir.exists())
            dir.mkdirs();

        WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        WorldEdit we = wep.getWorldEdit();

        LocalPlayer localPlayer = wep.wrapPlayer(player);
        LocalSession localSession = we.getSession(localPlayer);
        ClipboardHolder selection = localSession.getClipboard();
        EditSession editSession = localSession.createEditSession(localPlayer);

        Vector min = selection.getClipboard().getMinimumPoint();
        Vector max = selection.getClipboard().getMaximumPoint();

        editSession.enableQueue();
        CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min);
        clipboard.copy(editSession);
        SchematicFormat.MCEDIT.save(clipboard, schematic);
        editSession.flushQueue();
    }

    public static void place(String schematicName, Location pasteLoc, Boolean ignoreAir) throws MaxChangedBlocksException, IOException, DataException {
        if (ignoreAir) {
            File dir = new File(ISkyblock.getInstance().getSchematicFolder() + schematicName);
            EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
            editSession.enableQueue();

            SchematicFormat schematic = SchematicFormat.getFormat(dir);
            CuboidClipboard clipboard = schematic.load(dir);

            clipboard.place(editSession, BukkitUtil.toVector(pasteLoc), true);
            editSession.flushQueue();
        } else {
            place(schematicName, pasteLoc);
        }
    }

    public static void place(String schematicName, Location pasteLoc) {
        try {
            File dir = new File(ISkyblock.getInstance().getSchematicFolder() + schematicName);
            EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
            editSession.enableQueue();

            SchematicFormat schematic = SchematicFormat.getFormat(dir);
            CuboidClipboard clipboard = schematic.load(dir);

            clipboard.place(editSession, BukkitUtil.toVector(pasteLoc), false);
            editSession.flushQueue();
        } catch (DataException | IOException | MaxChangedBlocksException ex) {
            ex.printStackTrace();
        }
    }

    public static void paste(String schematicName, Location pasteLoc, Boolean ignoreAir) throws MaxChangedBlocksException, IOException, DataException {
        if (ignoreAir) {
            File dir = new File(ISkyblock.getInstance().getSchematicFolder() + schematicName);
            EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
            editSession.enableQueue();

            SchematicFormat schematic = SchematicFormat.getFormat(dir);
            CuboidClipboard clipboard = schematic.load(dir);

            clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), true, true);
            editSession.flushQueue();
        } else {
            paste(schematicName, pasteLoc);
        }
    }

    public static void paste(String schematicName, Location pasteLoc) throws MaxChangedBlocksException, IOException, DataException {
        File dir = new File(ISkyblock.getInstance().getSchematicFolder() + schematicName);
        EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
        //editSession.enableQueue();
        SchematicFormat schematic = SchematicFormat.getFormat(dir);
        CuboidClipboard clipboard = schematic.load(dir);
        clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), false, true);
       //editSession.flushQueue();
    }

    public static List<Block> getBlocksFromIsland(Island island, boolean checkSpawnProt) {
        List<Block> blocks = new ArrayList<>();
        Location loc1 = island.getMin();
        Location loc2 = island.getMax();
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == Material.AIR) {
                        continue;
                    }
                    if(checkSpawnProt && island.isInSpawnProtection(block.getLocation())) {
                        continue;
                    }
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

    public static void resetBlocks(List<Block> blocks, Island island) {
        BlockRegenRunnable regenRunnable = new BlockRegenRunnable(blocks, island);
        regenRunnable.runTaskAsynchronously(ISkyblock.getInstance());
    }

    public static void setGround(Location center, int radius) {
        Location min = new Location(center.getWorld(), center.getX() - radius, center.getY() - 1, center.getZ() - radius);
        Location max = new Location(center.getWorld(), center.getX() + radius, center.getY() - 1, center.getZ() + radius);
        min.getBlock().setType(Material.OBSIDIAN);
        max.getBlock().setType(Material.OBSIDIAN);
    }
}