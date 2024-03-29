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

public class FactionDelete implements SubCommandBase {

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "delete";


    /**
     * Execute the command
     *
     * @return true
     */
    public boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main) {
        if (strings.length > 1) {
            final File basesFile = FileManager.getFactionsData(main);
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            File playersFile = FileManager.getPlayersData(main);
            final YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playersFile);

            File listFile = FileManager.getListData(main);
            final YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(listFile);

            final String playerUUID = String.valueOf(((Player) commandSender).getUniqueId());

            final String key = strings[1].toLowerCase();

            // Check if the faction exist
            if (!FactionManager.doubleFaction(config, key)) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            // Check if he's the owner
            if (!FactionManager.isFactionOwner((Player) commandSender, key, main)) {
                commandSender.sendMessage(Static.ERROR + "You're not the owner!");
                return true;
            }

            // Set every args in factions.yml
            config.set(key, null);

            // Set every args in players.yml
            playerConfig.set(playerUUID + ".faction", null);

            // Set every args in list.yml
            final String factionsString = listConfig.getString("factions");

            if (factionsString == null) {
                /* Set the factions in lists.yml */
                listConfig.set("factions", null);
            } else {
                final String newFactions = Static.removeStringInArray(factionsString.split(FactionCommand.UUID_SEPARATOR), key);

                /* Set the factions in lists.yml */
                listConfig.set("factions", newFactions);
            }

            FactionCommand.saveFile(config, basesFile); // save factions.yml
            FactionCommand.saveFile(playerConfig, playersFile); // save players.yml
            FactionCommand.saveFile(listConfig, listFile); // save lists.yml

            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, "team remove " + key);

            AnnouncementCommand.announcement("faction",
                    "The faction " + strings[1] + " was deleted by " + ((Player) commandSender).getDisplayName(),
                    Bukkit.getOnlinePlayers().toArray(new Player[0]));
            commandSender.sendMessage(Static.SUCCESS + "The faction was deleted!");
            ArchitectsLand.LOGGER.info("Faction " + strings[1] + " was deleted by " + ((Player) commandSender).getDisplayName());
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to delete a faction!");
        }
        return true;
    }
}
