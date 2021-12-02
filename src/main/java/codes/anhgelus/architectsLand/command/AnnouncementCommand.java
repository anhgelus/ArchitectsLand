package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
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

                announcement("ANNOUNCEMENT", Static.arrayToString(strings), players);
            } else {
                commandSender.sendMessage(Static.ERROR + "You need to specify the message to send it!");
            }
            return true;
        }
        return false;
    }

    /**
     * Send an announcement
     *
     * @param type Type of announcement (word(s) in [here])
     * @param discordContent Message content for discord
     * @param players Players of the server
     */
    public static void announcement(String type, String discordContent, Player[] players) {
        // Discord part
        String message = "**`["+ type.toUpperCase() +"]`** " + discordContent;
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, "discord broadcast " + message);

        // Minecraft part
        for (Player i : players) {
            String minecraftMessage = Static.SEPARATOR_COLOR + "[" + ChatColor.GREEN + type.toUpperCase() + Static.SEPARATOR_COLOR + "] " +
                    Static.SUCCESS + discordContent;
            i.sendMessage(minecraftMessage);
            if (type.equals("alliance")) {
                i.playSound(i.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 15, 1);
            } else if (type.equals("war")) {
                i.playSound(i.getLocation(), Sound.BLOCK_ANVIL_LAND, 2, 1);
            } else if (type.equals("end of war")) {
                i.playSound(i.getLocation(), Sound.BLOCK_ANVIL_LAND, 2, 1);
            } else if (type.equals("end of alliance")) {
                i.playSound(i.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 5, 1);
            }  else if (type.equals("faction")) {
                i.playSound(i.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 5, 1);
            }
            else {
                i.playSound(i.getLocation(), Sound.BLOCK_BELL_USE, 10, 1);
            }
        }
    }
}
