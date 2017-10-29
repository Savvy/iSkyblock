package me.savvy.iskyblock.facades;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public interface IslandFacade {

    int getIslandX();
    int getIslandZ();
    int getSize();
    UUID getOwner();
    List<UUID> getMembers();
    boolean isInIsland(Location location);
    boolean isInSpawnProtection(Location location);
}
