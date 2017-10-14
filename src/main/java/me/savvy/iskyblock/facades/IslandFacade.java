package me.savvy.iskyblock.facades;

import java.util.List;
import java.util.UUID;

public interface IslandFacade {

    public int getIslandId();
    public UUID getOwner();
    public List<UUID> getMembers();
}
