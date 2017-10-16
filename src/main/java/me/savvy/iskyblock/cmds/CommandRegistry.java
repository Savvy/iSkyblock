package me.savvy.iskyblock.cmds;

import me.savvy.iskyblock.ISkyblock;
import me.savvy.iskyblock.cmds.commands.IslandCommand;
import me.savvy.iskyblock.cmds.handler.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class CommandRegistry {

    public void register() {
        registerAll(new IslandCommand("island"));
    }


    public void registerAll(Command... cmds) {
        for (Command cmd : cmds) {
            register(cmd);
        }
    }

    public void register(Command cmd) {
        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            CommandMap map = (CommandMap) f.get(ISkyblock.getInstance().getServer());
            map.register(cmd.getName(), "iSkyBlock", cmd);
            ISkyblock.getInstance().getLogger().log
                    (Level.INFO, "Successfully registered command " + cmd.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
