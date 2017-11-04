package me.savvy.iskyblock.facades;

import me.savvy.iskyblock.main.Island;
import org.bukkit.Location;

import java.sql.Array;
import java.util.UUID;

public interface GridFacade {

    Island getIsland(int x, int z);
    Island[][] getIslandGrid();

    Island getIsland(UUID uuid);

    int[] getFreeSpace();
    void claimSpace(Island island);
    boolean isFreeSpace(int x, int z);
    Island getIsland(Location location);
    boolean isWithinIsland(Location location);

    void load();
}
