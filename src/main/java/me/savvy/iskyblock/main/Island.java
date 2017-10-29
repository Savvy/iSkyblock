package me.savvy.iskyblock.main;

import lombok.Getter;
import lombok.Setter;
import me.savvy.iskyblock.ISkyblock;
import me.savvy.iskyblock.facades.IslandFacade;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Island implements IslandFacade {

    @Getter private UUID owner;
    @Getter private List<UUID> members;
    @Getter private int islandX, islandZ, size;
    @Getter private Location center, min, max;
    @Getter @Setter private Location spawnPadMin, spawnPadMax;
    public Island(UUID uuid, int x, int z, int size) {
        this.owner = uuid;
        this.members = new ArrayList<>();
        this.islandX = x;
        this.islandZ = z;
        this.size = size;
        this.center = new Location(Bukkit.getWorld(ISkyblock.getInstance().getServerSettings().getWorldName()), x, 115, z, (float) 90, (float) 2);
        this.min = new Location(center.getWorld(), center.getX() - size, 0, center.getZ() - size);
        this.max = new Location(center.getWorld(), center.getX() + size, 300, center.getZ() + size);
    }

    @Override
    public boolean isInIsland(Location location) {
        return location.toVector().isInAABB(min.toVector(), max.toVector());
    }

    @Override
    public boolean isInSpawnProtection(Location location) {
        return location.toVector().isInAABB(spawnPadMin.toVector(), spawnPadMax.toVector());
    }
}
