package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.manager.FileManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class BroadcastCommand implements CommandExecutor {
    private final ArchitectsLand main;

    public static final String PERMISSION = ArchitectsLand.PERMISSION + "chat.broadcast";

    public BroadcastCommand (ArchitectsLand main) {
        this.main = main;
    }

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
        if (s.equals("broadcast") || s.equals("bc") && commandSender instanceof Player) {
            final File basesFile = FileManager.getFactionsData(this.main);
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            File playersFile = FileManager.getPlayersData(this.main);
            final YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playersFile);

            final String senderUUID = String.valueOf(((Player) commandSender).getUniqueId());

            final Player player = (Player) commandSender;

            // Check if he has the permission
            if (!player.hasPermission(PERMISSION)) {
                player.sendMessage(Static.ERROR + "You don't have the permission to do this!");
                return true;
            }

            final String senderFaction = playerConfig.getString(senderUUID + ".faction");

            //Check if the player has a faction
            if (senderFaction == null) {
                commandSender.sendMessage(Static.ERROR + "You don't have faction!");
                return true;
            }

            //Check if he is the faction's owner
            if (!Objects.equals(senderUUID, config.getString(senderFaction + ".owner"))) {
                commandSender.sendMessage(Static.ERROR + "You're not the owner!");
                return true;
            }

            // Check if the command has args
            if (strings.length != 0) {
                AnnouncementCommand.announcement("broadcast",
                        Static.arrayToString(strings) + " *- by " + player.getDisplayName() + "*",
                        Bukkit.getOnlinePlayers().toArray(new Player[0]));
            } else {
                commandSender.sendMessage(Static.ERROR + "You need to specify the message to send it!");
            }
            return true;
        }
        return false;
    }
}
