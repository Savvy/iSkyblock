package me.savvy.iskyblock.main;

import me.savvy.iskyblock.facades.IslandFacade;

import java.util.List;
import java.util.UUID;

public class Island implements IslandFacade {

    private int islandId;
    private UUID islandOwner;
    private List<UUID> islandMembers;

    public Island() {

    }

    @Override
    public int getIslandId() {
        return islandId;
    }

    @Override
    public UUID getOwner() {
        return islandOwner;
    }

    @Override
    public List<UUID> getMembers() {
        return islandMembers;
    }
}
