package me.savvy.iskyblock.main;

import lombok.Getter;
import me.savvy.iskyblock.facades.ServerFacade;

public class ServerSettings implements ServerFacade {

    @Getter private int id;
    @Getter private String worldName;

    public ServerSettings() {}

}