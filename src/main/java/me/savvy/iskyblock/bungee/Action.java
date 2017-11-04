package me.savvy.iskyblock.bungee;

import org.bukkit.entity.Player;

public interface Action {

    ActionType getType();
    default void execute(Player player) {}
    default void execute(Player player, Object... objects) {}
}
