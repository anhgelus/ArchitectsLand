package main.java.codes.anhgelus.architectsLand.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Check if the commandSend is a player or not
        if (s.equals("broadcast") || s.equals("bc") && commandSender instanceof Player) {
            // Check if the command has args
            if (strings.length != 0) {
                // Get every players
                final Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);

                for (Player i : players) {
                    String message = new String();
                    for (String n : strings) {
                        // Check if the n != command
                        if (!Objects.equals(n, "bc") || !Objects.equals(n, "broadcast")) {
                            message = message + " " + n;
                        }
                    }
                    i.sendMessage(message);
                }
            }
        }
        return false;
    }
}
