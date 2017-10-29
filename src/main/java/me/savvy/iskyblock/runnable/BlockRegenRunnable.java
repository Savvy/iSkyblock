package me.savvy.iskyblock.runnable;

import me.savvy.iskyblock.ISkyblock;
import me.savvy.iskyblock.main.Island;
import me.savvy.iskyblock.main.SkyAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BlockRegenRunnable extends BukkitRunnable {
    private ThreadLocalRandom random;
    private List<Block> blocks;
    private Island island;

    public BlockRegenRunnable(List<Block> blocks, Island island){
        this.blocks = blocks;
        this.island = island;
    }

    public void run() {
        regen();
    }

    private void regen() {
        for(Block b: blocks) {
            Bukkit.getScheduler().runTaskLater(ISkyblock.getInstance(), () -> {
                        if (b.getType() != Material.AIR) {
                            b.setType(Material.AIR);
                        }
                        blocks.remove(b);
                        if(blocks.size() <= 1) {
                            SkyAPI.getInstance().generateIsland("temp.schematic", island);
                        }
                    },new Random().nextInt(10*20) + 10);
        }
    }

}