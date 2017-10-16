package me.savvy.iskyblock.cmds.handler;

import me.savvy.iskyblock.ISkyblock;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Command extends BukkitCommand {

    public Command(String name) {
        super(name);
        this.getAliases().clear();
    }

    public Command(String name, String description, String usage, List<String> aliases) {
        super(name, description, usage, aliases);
    }

    public ISkyblock getInstance() {
        return ISkyblock.getInstance();
    }

    public boolean checkSender(CommandSender sender) {
        return sender instanceof Player;
    }
}
