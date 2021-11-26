package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FactionModify implements SubCommandBase {

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "modify";
    public static final List<String> MODIFY_TYPE = Arrays.asList("prefix", "name", "color", "description", "prefix-color");

    /**
     * Execute the command
     *
     * @return true
     */
    @Override
    public boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main) {
        if (strings.length == 4 || (strings.length >= 4 && Objects.equals(strings[2], "description"))) {
            final File basesFile = FileManager.getFactionsData(main);
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            final String playerUUID = String.valueOf(((Player) commandSender).getUniqueId());

            final String key = strings[1].toLowerCase();
            final String status = ".status.";

            // Check if the faction exist
            if (!FactionManager.doubleFaction(config, key)) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            String modified = strings[3];

            // Check if the player is the owner
            if (!FactionManager.isFactionOwner((Player) commandSender, key, main)) {
                commandSender.sendMessage(Static.ERROR + "You're not the owner!");
                return true;
            }

            // Check if it's the right args
            if (MODIFY_TYPE.contains(strings[3])) {
                commandSender.sendMessage(Static.ERROR + "This status doesn't exist!");
                return true;
            }

            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

            switch (strings[2]) {
                case "prefix":

                    final String color = config.getString(key + status + "prefix-color");
                    final String prefixJson = Static.prefixCreatorJson(strings[3], color);

                    modified = Static.prefixCreatorYml(strings[3], color);

                    Bukkit.dispatchCommand(console, "team modify " + key + " prefix " + prefixJson);

                    break;
                case "prefix-color":
                    // Check if the color exist
                    if (Static.colorExist(strings[3])) {

                        final String prefix = config.getString(key + status + "prefix").replace("[", "").replace("]", "");
                        final String prefixTeam = Static.prefixCreatorJson(prefix, strings[3]);

                        modified = strings[3];

                        config.set(key + status + "prefix", Static.prefixCreatorYml(prefix, prefixTeam));

                        Bukkit.dispatchCommand(console, "team modify " + key + " prefix " + prefixTeam);

                    } else {
                        commandSender.sendMessage(Static.ERROR + "This color doesn't exist!");
                        return true;
                    }
                    break;
                case "description":
                    modified = Static.arrayToString(strings).replace(strings[0], "").replace(strings[1], "").replace(strings[2], "").replace("    ", "");
                    break;
                case "color":
                    if (Static.colorExist(strings[3])) {
                        modified = Static.getChatColor(strings[3]) + strings[3];
                        Bukkit.dispatchCommand(console, "team modify " + key + " color " + strings[3]);
                    } else {
                        commandSender.sendMessage(Static.ERROR + "This color doesn't exist!");
                        return true;
                    }
                    break;
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
