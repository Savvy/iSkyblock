package me.savvy.iskyblock.bungee.actions;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.savvy.iskyblock.ISkyblock;
import me.savvy.iskyblock.bungee.Action;
import me.savvy.iskyblock.bungee.ActionType;
import org.bukkit.entity.Player;

public class TeleportServerAction implements Action {
    @Override
    public ActionType getType() {
        return ActionType.TELEPORT_TO_SERVER;
    }

    @Override
    public void execute(Player player, Object...objects) {
        if (objects.length == 0 || objects[0] == null) {
            throw new IllegalArgumentException("Please include server name.");
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(String.valueOf(objects[0]));
        player.sendPluginMessage(ISkyblock.getInstance(), "BungeeCord", out.toByteArray());
    }
}
