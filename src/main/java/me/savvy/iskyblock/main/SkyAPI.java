package me.savvy.iskyblock.main;

import org.bukkit.Location;

public class SkyAPI {

    private static SkyAPI instance;

    public SkyAPI() {
        instance = this;
    }

    public boolean isWithinIsland(Location location) {
        return isWithinIsland(location.getX(), location.getY());
    }

    private boolean isWithinIsland(double x, double y) {
        return false;
    }

    public static SkyAPI getInstance() {
        return (instance == null) ? new SkyAPI() : instance;
    }
}
