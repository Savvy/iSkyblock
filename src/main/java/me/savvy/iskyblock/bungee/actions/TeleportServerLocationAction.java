package me.savvy.iskyblock.bungee.actions;

import me.savvy.iskyblock.bungee.Action;
import me.savvy.iskyblock.bungee.ActionType;
import org.bukkit.entity.Player;

public class TeleportServerLocationAction implements Action{
    @Override
    public ActionType getType() {
        return ActionType.TELEPORT_TO_SERVER_LOCATION;
    }

    @Override
    public void execute(Player player) {

    }
}
