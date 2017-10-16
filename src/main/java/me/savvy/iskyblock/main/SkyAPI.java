package me.savvy.iskyblock.main;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SkyAPI {

    private static SkyAPI instance;

    public SkyAPI() {
        instance = this;
    }

    public boolean hasIsland(UUID playerUUID) {
        return true;
    }

    public boolean hasIsland(Player player) {
        return hasIsland(player.getUniqueId());
    }

    public boolean hasIsland(OfflinePlayer offlinePlayer) {
        return hasIsland(offlinePlayer.getUniqueId());
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
