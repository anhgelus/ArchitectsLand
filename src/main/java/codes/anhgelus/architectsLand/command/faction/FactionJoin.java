package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.AnnouncementCommand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.manager.FactionManager;
import main.java.codes.anhgelus.architectsLand.manager.FileManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class FactionJoin implements SubCommandBase {

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "join";

    /**
     * Execute the command
     *
     * @return true
     */
    @Override
    public boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main) {
        if (strings.length > 1) {
            final File basesFile = FileManager.getFactionsData(main);
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            final File playersFile = FileManager.getPlayersData(main);
            final YamlConfiguration yml = YamlConfiguration.loadConfiguration(playersFile);

            final Player sender = (Player) commandSender;

            // Check if the faction exist
            if (!FactionManager.doubleFaction(config, strings[1])) {
                sender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            final String faction = strings[1].toLowerCase();
            final String playersString = config.getString(faction + ".members");
            final String invitation = config.getString(faction + ".invitation");
            final String[] invitations = invitation.split(FactionCommand.UUID_SEPARATOR);
            final String[] players = playersString.split(FactionCommand.UUID_SEPARATOR);

            // Check if the player is in the faction
            for (String i : players) {
                if (Objects.equals(i, String.valueOf(sender.getUniqueId()))) {
                    sender.sendMessage(Static.ERROR + "You're already in this faction.");
                    return true;
                }
            }

            for (String i : invitations) {
                if (Objects.equals(String.valueOf(sender.getUniqueId()), i)) {
                    config.set(faction + ".members", playersString + sender.getUniqueId() + FactionCommand.UUID_SEPARATOR);
                    /*
                     * Set the new invitations'
                     * config.set(faction + ".invitation", FactionManager.removePlayerFromInvitation(player));
                     */

                    yml.set(sender.getUniqueId() + ".faction", faction);

                    FactionCommand.saveFile(config, basesFile);
                    FactionCommand.saveFile(yml, playersFile);

                    AnnouncementCommand.announcement("faction",
                            ((Player) commandSender).getDisplayName() + " joined " + strings[1] + "!",
                            Bukkit.getOnlinePlayers().toArray(new Player[0]));

                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "team join " + strings[1] + " " +  sender.getDisplayName());
                    commandSender.sendMessage(Static.SUCCESS + "You joined " + strings[1] + "!");

                    ArchitectsLand.LOGGER.info(((Player) commandSender).getDisplayName() + " joined " + strings[1]);
                    return true;
                }
            }

            commandSender.sendMessage(Static.ERROR + "You can't join this faction!");
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to join it!");
        }
        return true;
    }
}
