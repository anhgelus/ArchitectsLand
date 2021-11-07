package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class FactionCreate {

    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public FactionCreate (String[] strings, CommandSender commandSender, ArchitectsLand main) {
        this.strings = strings;
        this.commandSender = commandSender;
        this.main = main;
    }
    public boolean command() {
        if (strings.length > 1) {
            File basesFile = new FactionCommand(main).getFactionsData();
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            final String key = strings[1];
            final String status = ".status";
            config.set(key + ".owner", ((Player) commandSender).getUniqueId());
            config.set(key + ".members", ((Player) commandSender).getUniqueId());
            config.set(key + status + ".description", "No description set. Use /f modify description to set it.");

            try {
                config.save(basesFile);
                commandSender.sendMessage(Static.SUCCESS + "The faction was created!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to create a faction!");
        }
        return true;
    }
}
