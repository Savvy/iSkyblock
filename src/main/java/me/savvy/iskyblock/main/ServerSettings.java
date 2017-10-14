package me.savvy.iskyblock.main;

import me.savvy.iskyblock.facades.ServerFacade;

public class ServerSettings implements ServerFacade {

    private int id;

    public ServerSettings() {}

    @Override
    public int getId() {
        return id;
    }
}
