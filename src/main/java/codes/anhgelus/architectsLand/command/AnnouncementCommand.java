package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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

                String message = Static.SEPARATOR + Static.EOL + Static.SUCCESS + Static.arrayToString(strings) + Static.EOL + Static.SEPARATOR;
                for (Player i : players) {
                    i.sendMessage(message);
                }
                message = "**`[ANNOUNCEMENT]`** " + Static.arrayToString(strings);
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "discord broadcast " + message);
            } else {
                commandSender.sendMessage(Static.ERROR + "You need to specify the message to send it!");
            }
            return true;
        }
        return false;
    }
}
