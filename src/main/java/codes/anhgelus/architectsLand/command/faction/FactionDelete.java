package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class FactionDelete {
    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "delete";

    public FactionDelete (String[] strings, CommandSender commandSender, ArchitectsLand main) {
        this.strings = strings;
        this.commandSender = commandSender;
        this.main = main;
    }
    public boolean command() {
        if (strings.length > 1) {
            File basesFile = new FactionCommand(main).getFactionsData();
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            final String playerUUID = String.valueOf(((Player) commandSender).getUniqueId());

            final String key = strings[1].toLowerCase();

            if (!FactionCommand.doubleFaction(config, key)) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            if (!Objects.equals(playerUUID, config.getString(key + ".owner"))) {
                commandSender.sendMessage(Static.ERROR + "You're not the owner!");
                return true;
            }

            config.set(key, null);
            FactionCommand.saveFile(config, basesFile);

            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, "team remove " + key);

            commandSender.sendMessage(Static.SUCCESS + "The faction was deleted!");
            ArchitectsLand.LOGGER.info("Faction " + strings[1] + " was deleted by " + ((Player) commandSender).getDisplayName());
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to delete a faction!");
        }
        return true;
    }
}
