package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
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
            String modified = strings[3];

            if (!Objects.equals(playerUUID, config.getString(key + ".owner"))) {
                commandSender.sendMessage(Static.ERROR + "You're not the owner!");
                return true;
            }
            if (!Objects.equals(strings[2], "prefix") && !Objects.equals(strings[2], "name")) {
                commandSender.sendMessage(Static.ERROR + "You can't modify this status!");
                return true;
            } else if (Objects.equals(strings[2], "prefix")) {
                modified = Static.prefixCreator(strings[3]);
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
