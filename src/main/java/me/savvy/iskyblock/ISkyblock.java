package me.savvy.iskyblock;

import lombok.Getter;
import me.savvy.iskyblock.cmds.CommandRegistry;
import me.savvy.iskyblock.listeners.BlockEvent;
import me.savvy.iskyblock.main.ServerSettings;
import me.savvy.iskyblock.main.SkyAPI;
import me.savvy.iskyblock.storage.SQLBuilder;
import me.savvy.iskyblock.storage.enums.DatabaseTables;
import org.bukkit.plugin.java.JavaPlugin;

public class ISkyblock extends JavaPlugin {

    @Getter private static ISkyblock instance;
    @Getter private String schematicFolder;
    @Getter private SQLBuilder sqlBuilder;
    @Getter  private ServerSettings serverSettings;

    @Override
    public void onEnable() {
        instance = getPlugin(getClass());
        saveDefaultConfig();
        load();
        getServer().getPluginManager().registerEvents(new BlockEvent(), this);
    }

    private void load() {
        serverSettings = new ServerSettings();
        schematicFolder = getDataFolder() + "/schematics/";
        sqlBuilder = new SQLBuilder(
                getConfig().getString("SQL.userName"),
                getConfig().getString("SQL.password"),
                getConfig().getString("SQL.port"),
                getConfig().getString("SQL.databaseName"),
                getConfig().getString("SQL.hostName"))
                .executeUpdate(/*DatabaseTables.SERVER.getQuery(), */DatabaseTables.ISLANDS.getQuery());
        System.out.println("Should load Grid");
        SkyAPI.getInstance().getGridFacade().load();
        System.out.println("Grid done");
        new CommandRegistry().register();
    }

    @Override
    public void onDisable() {
        sqlBuilder.closeConnection();
    }
}
