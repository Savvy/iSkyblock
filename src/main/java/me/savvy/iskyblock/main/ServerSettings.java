package me.savvy.iskyblock.main;

import lombok.Getter;
import me.savvy.iskyblock.ISkyblock;
import me.savvy.iskyblock.facades.ServerFacade;
import org.bukkit.configuration.file.FileConfiguration;

public class ServerSettings implements ServerFacade {

    @Getter private String id;
    @Getter private String worldName;
    @Getter private FileConfiguration config;
    public ServerSettings() {
        this.config = ISkyblock.getInstance().getConfig();
        this.id = getConfig().getString("skyblock.serverId", "serverId");
        if (id.equalsIgnoreCase("serverId")) {
            ISkyblock.getInstance().getLogger().severe("Server Id seems to be default. Please update Server Id in `config.yml`!");
            ISkyblock.getInstance().getServer().shutdown();
            return;
        }
        this.worldName = getConfig().getString("skyblock.world", "iSkyblock");
    }

}