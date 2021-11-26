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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class FactionLeave implements SubCommandBase {

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "leave";

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
            final String faction = strings[1].toLowerCase();

            // Check if the faction exist
            if (!FactionManager.doubleFaction(config, strings[1])) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            final String[] players = config.getString(faction + ".members").split(FactionCommand.UUID_SEPARATOR);

            for (String i : players) {
                // Check if the player is the owner or not
                if (!FactionManager.isFactionOwner((Player) commandSender, faction, main)) {
                    commandSender.sendMessage(Static.ERROR + "If you want to leave your faction, use /f delete <faction>");
                    return true;
                }

                // Check if the player is in the faction
                if (Objects.equals(i, String.valueOf(((Player) commandSender).getUniqueId()))) {
                    // Remove the player
                    config.set(faction + ".members", newPlayers(players, i));

                    FactionCommand.saveFile(config, basesFile);

                    AnnouncementCommand.announcement("faction",
                            ((Player) commandSender).getDisplayName() + "left " + strings[1] + "!",
                            Bukkit.getOnlinePlayers().toArray(new Player[0]));
                    commandSender.sendMessage(Static.SUCCESS + "You left this faction.");
                    ArchitectsLand.LOGGER.info(((Player) commandSender).getDisplayName() + " left " + strings[1]);
                    return true;
                }
            }

            commandSender.sendMessage(Static.ERROR + "You're not in " + strings[1] + "!");
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to leave it!");
        }
        return true;
    }

    private String newPlayers(String[] list, String remove) {
        String newPlayers = new String();
        for (String i : list) {
            if (!Objects.equals(i, remove)) {
                newPlayers = newPlayers + FactionCommand.UUID_SEPARATOR + i;
            }
        }
        return newPlayers;
    }
}
