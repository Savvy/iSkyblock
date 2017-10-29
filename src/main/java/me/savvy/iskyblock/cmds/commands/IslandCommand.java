package me.savvy.iskyblock.cmds.commands;

import me.savvy.iskyblock.ISkyblock;
import me.savvy.iskyblock.cmds.handler.Command;
import me.savvy.iskyblock.main.Island;
import me.savvy.iskyblock.main.SkyAPI;
import me.savvy.iskyblock.utils.ISchematic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class IslandCommand extends Command {

    public IslandCommand(String name) {
        super(name);
        this.getAliases().add("is");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player = (Player) sender;
        switch (args.length) {
            case 1:
                switch(args[0].toLowerCase()) {
                    case "create":
                        if (!(checkSender(sender))) {
                            sender.sendMessage("You must be a player for this command.");
                            // Must be player
                            break;
                        }
                        if (SkyAPI.getInstance().hasIsland(player.getUniqueId())) {
                            sender.sendMessage("You already have a(n) island!");
                            break;
                        }
                        int[] freeSpot = SkyAPI.getInstance().getGridFacade().getFreeSpace();
                    /*    Location spawnMin = new Location(Bukkit.getWorld(ISkyblock.getInstance().getServerSettings().getWorldName()), -7.0, 116, -10);
                        Location spawnMax = new Location(Bukkit.getWorld(ISkyblock.getInstance().getServerSettings().getWorldName()), 11, 91, 9);*/
                        Island island = new Island(player.getUniqueId(), freeSpot[0], freeSpot[1], 200);
                        SkyAPI.getInstance().generateIsland("temp.schematic", island);
                        Block b1 = null;
                        Block b2 = null;
                        for(Block block : ISchematic.getBlocksFromIsland(island, false)) {
                            if (block.getType() == Material.SLIME_BLOCK) {
                                if (b1 == null) {
                                    b1 = block;
                                    block.setType(Material.AIR);
                                } else {
                                    b2 = block;
                                    block.setType(Material.AIR);
                                    break;
                                }
                            }
                        }
                        if (b1 != null && b2 != null) {
                            island.setSpawnPadMin(b1.getLocation().getY() < b2.getLocation().getY() ? b1.getLocation() : b2.getLocation());
                            island.setSpawnPadMax(b1.getLocation().getY() > b2.getLocation().getY() ? b1.getLocation() : b2.getLocation());
                        }
                        SkyAPI.getInstance().getGridFacade().claimSpace(island);
                        break;
                    case "home":
                        if (!(checkSender(sender))) {
                            sender.sendMessage("You must be a player for this command.");
                            // Must be player
                            break;
                        }
                        if (!SkyAPI.getInstance().hasIsland(player.getUniqueId())) {
                            sender.sendMessage("You don't have a island!");
                            break;
                        }
                        island = SkyAPI.getInstance().getIsland(player.getUniqueId());
                        player.teleport(island.getCenter());
                        break;
                    case "reset":
                        // Doesn't have an island.
                        if (!(checkSender(sender))) {
                            sender.sendMessage("You must be a player for this command.");
                            // Must be player
                            break;
                        }
                        if (!SkyAPI.getInstance().hasIsland(player.getUniqueId())) {
                            sender.sendMessage("You don't have a island!");
                            break;
                        }
                        island = SkyAPI.getInstance().getIsland(player.getUniqueId());
                        Bukkit.broadcastMessage("Starting...");
                        List<Block> blocksList = ISchematic.getBlocksFromIsland(island, true);
                        if (blocksList.isEmpty()) {
                            SkyAPI.getInstance().generateIsland("temp.schematic", island);
                            break;
                        }
                        ISchematic.resetBlocks(blocksList, island);
                        break;
                    default:
                        break;
                }
                break;
        }
        return false;
    }
}