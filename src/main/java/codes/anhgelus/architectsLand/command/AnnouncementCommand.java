package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnnouncementCommand implements CommandExecutor {
    public static final String PERMISSION = ArchitectsLand.PERMISSION + "chat.announcement";

    /**
     * Create the /broadcast
     *
     * @param commandSender Sender of the command
     * @param command Command
     * @param s Command string
     * @param strings Args
     * @return true
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Check if the commandSend is a player or not
        if (s.equals("announcement") || s.equals("ann") && commandSender instanceof Player) {
            final Player player = (Player) commandSender;

            // Check if he has the permission
            if (!player.hasPermission(PERMISSION)) {
                player.sendMessage(Static.ERROR + "You don't have the permission to do this!");
                return true;
            }

            // Check if the command has args
            if (strings.length != 0) {
                // Get every players
                final Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);

                for (Player i : players) {
                    final String message = Static.arrayToString(strings);
                    i.sendMessage( Static.SEPARATOR + Static.EOL +
                            Static.SUCCESS + message + Static.EOL +
                            Static.SEPARATOR);
                }
                return true;
            }
        }
        return false;
    }
}