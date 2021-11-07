package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class FactionJoin {

    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public FactionJoin (String[] strings, CommandSender commandSender, ArchitectsLand main) {
        this.strings = strings;
        this.commandSender = commandSender;
        this.main = main;
    }
    public boolean command() {
        /*
        * N'arrive pas à détecter la faction (à fix)
        */

        if (strings.length > 1) {
            final File basesFile = new FactionCommand(main).getFactionsData();
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);
            commandSender.sendMessage(String.valueOf(strings.length));
            if (config.getString(strings[1]) != null) {
                final String[] players = config.getString(strings[1] + ".members").split(",");
                for (String i : players) {
                    if (Objects.equals(i, String.valueOf(((Player) commandSender).getUniqueId()))) {
                        commandSender.sendMessage(Static.ERROR + "You're already in this faction.");
                        return true;
                    }
                }
                config.set(strings[1] + ".members", config.getString(strings[1]) + "," + ((Player) commandSender).getUniqueId());
                commandSender.sendMessage(Static.SUCCESS + "You joined the " + strings[1] + " Faction!");
            } else {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist.");
            }
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to join it!");
        }
        return true;
    }
}
