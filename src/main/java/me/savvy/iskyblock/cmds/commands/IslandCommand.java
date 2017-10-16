package me.savvy.iskyblock.cmds.commands;

import me.savvy.iskyblock.cmds.handler.Command;
import me.savvy.iskyblock.main.SkyAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IslandCommand extends Command {

    public IslandCommand(String name) {
        super(name);
        this.getAliases().add("is");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player;
        switch (args.length) {
            case 1:
                switch(args[0].toLowerCase()) {
                    case "create":
                        if (!(checkSender(sender))) {
                            // Must be player
                            break;
                        }
                        player = (Player) sender;
                        if (SkyAPI.getInstance().hasIsland(player.getUniqueId())) {
                            // Already has island.
                            break;
                        }
                        break;
                    case "delete":
                        if (!(checkSender(sender))) {
                            // Must be player
                            break;
                        }
                        player = (Player) sender;
                        if (!(SkyAPI.getInstance().hasIsland(player.getUniqueId()))) {
                        // Doesn't have an island.
                        break;
                    }
                    player = (Player) sender;
                    break;
                    case "reset":
                        if (!(checkSender(sender))) {
                            // Must be player
                            break;
                        }
                        player = (Player) sender;
                        if (!(SkyAPI.getInstance().hasIsland(player.getUniqueId()))) {
                        // Doesn't have an island.
                        break;
                    }
                    break;
                    default:
                        break;
                }
                break;
        }
        return false;
    }
}
