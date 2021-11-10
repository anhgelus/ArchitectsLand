package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class FactionLeave {
    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "leave";

    public FactionLeave (String[] strings, CommandSender commandSender, ArchitectsLand main) {
        this.strings = strings;
        this.commandSender = commandSender;
        this.main = main;
    }

    /**
     * Execute the command
     *
     * @return true
     */
    public boolean command() {
        /*
        * BUG = N'arrive pas à récupérer les joueurs (null)
         */
        if (strings.length > 1) {
            final File basesFile = new FactionCommand(main).getFactionsData();
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);
            final String faction = strings[1].toLowerCase();

            // Check if the faction exist
            if (!FactionCommand.doubleFaction(config, strings[1])) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            final String[] players = config.getString(faction + ".members").split(FactionCommand.UUID_SEPARATOR);

            for (String i : players) {
                // Check if the player is the owner or not
                if (Objects.equals(config.getString(faction + ".owner"), i)) {
                    commandSender.sendMessage(Static.ERROR + "If you want to leave your faction, use /f delete <faction>");
                    return true;
                }

                // Check if the player is in the faction
                if (Objects.equals(i, String.valueOf(((Player) commandSender).getUniqueId()))) {
                    // Remove the player
                    config.set(faction + ".members", newPlayers(players, i));

                    FactionCommand.saveFile(config, basesFile);
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
