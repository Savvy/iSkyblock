package me.savvy.iskyblock;

import org.bukkit.plugin.java.JavaPlugin;

public class ISkyblock extends JavaPlugin {

    private static ISkyblock instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ISkyblock getInstance() {
        return instance;
    }
}
