package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class FactionModify {
    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "modify";

    public FactionModify (String[] strings, CommandSender commandSender, ArchitectsLand main) {
        this.strings = strings;
        this.commandSender = commandSender;
        this.main = main;
    }
    public boolean command() {
        if (strings.length == 4) {
            File basesFile = new FactionCommand(main).getFactionsData();
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            final String playerUUID = String.valueOf(((Player) commandSender).getUniqueId());

            final String key = strings[1].toLowerCase();
            final String status = ".status.";

            if (!FactionCommand.doubleFaction(config, key)) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            String modified = strings[3];

            if (!Objects.equals(playerUUID, config.getString(key + ".owner"))) {
                commandSender.sendMessage(Static.ERROR + "You're not the owner!");
                return true;
            }
            if (!Objects.equals(strings[2], "prefix") && !Objects.equals(strings[2], "name") && !Objects.equals(strings[2], "color")) {
                commandSender.sendMessage(Static.ERROR + "You can't modify this status!");
                return true;
            } else if (Objects.equals(strings[2], "prefix")) {

                final String color = config.getString(key + status + "color");
                final String prefix = Static.prefixCreatorJson(strings[3],color);

                modified = Static.prefixCreatorYml(strings[3],color);

                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "team modify " + key + " prefix " + prefix);

            } else if (Objects.equals(strings[2], "color")) {
                if (Static.colorExist(strings[3])) {

                    final String prefix = config.getString(key + status + "name");
                    final String prefixTeam = Static.prefixCreatorJson(prefix.substring(0, 3).toUpperCase(), strings[3]);

                    modified = strings[3];

                    config.set(key + status + "prefix", Static.prefixCreatorYml(prefix.replace("[", "").replace("]", ""),strings[3]));

                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "team modify " + key + " prefix " + prefixTeam);

                } else {
                    commandSender.sendMessage(Static.ERROR + "This color doesn't exist!");
                    return true;
                }
            }
            final String link = key + status + strings[2];
            config.set(link, modified);

            FactionCommand.saveFile(config, basesFile);
            commandSender.sendMessage(Static.SUCCESS + "The faction was modified!");
            ArchitectsLand.LOGGER.info("Faction " + strings[1] + " was modified by " + ((Player) commandSender).getDisplayName() + " (" + strings[2] + " turned into " + modified + ")");
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify 3 args to modify a faction! (see /f for more informations)");
        }
        return true;
    }
}
