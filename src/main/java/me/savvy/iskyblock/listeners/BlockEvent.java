package me.savvy.iskyblock.listeners;

import me.savvy.iskyblock.main.Island;
import me.savvy.iskyblock.main.SkyAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Bukkit.broadcastMessage("Debug - 1");
        if (!SkyAPI.getInstance().isWithinIsland(event.getBlock().getLocation())) return;
        Bukkit.broadcastMessage("Debug - 2");
        Island island = SkyAPI.getInstance().getIsland(event.getBlock().getLocation());
        if (island.isInSpawnProtection(event.getBlock().getLocation())) {
            Bukkit.broadcastMessage("Debug - 3");
            event.setCancelled(true);
        }
    }
}
