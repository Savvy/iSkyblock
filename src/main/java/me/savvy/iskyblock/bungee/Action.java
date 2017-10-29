package me.savvy.iskyblock.bungee;

import org.bukkit.entity.Player;

public interface Action {

    ActionType getType();
    void execute(Player player);
}
