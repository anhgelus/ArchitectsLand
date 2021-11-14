package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.AnnouncementCommand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
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
    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "delete";

    public FactionDelete (String[] strings, CommandSender commandSender, ArchitectsLand main) {
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
        if (strings.length > 1) {
            File basesFile = new FactionCommand(main).getFactionsData();
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            File playersFile = new FactionCommand(main).getPlayersData();
            final YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playersFile);

            File listFile = new FactionCommand(main).getListData();
            final YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(listFile);

            final String playerUUID = String.valueOf(((Player) commandSender).getUniqueId());

            final String key = strings[1].toLowerCase();

            // Check if the faction exist
            if (!FactionCommand.doubleFaction(config, key)) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            // Check if he's the owner
            if (!Objects.equals(playerUUID, config.getString(key + ".owner"))) {
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
