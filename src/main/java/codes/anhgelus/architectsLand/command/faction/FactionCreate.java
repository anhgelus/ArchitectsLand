package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.AnnouncementCommand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.manager.FactionManager;
import main.java.codes.anhgelus.architectsLand.manager.FileManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class FactionCreate implements SubCommandBase {

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "create";

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

            File playersFile = FileManager.getPlayersData(main);
            final YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playersFile);

            File listFile = FileManager.getListData(main);
            final YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(listFile);

            final String playerUUID = String.valueOf(((Player) commandSender).getUniqueId());

            final String key = strings[1].toLowerCase();
            final String status = ".status";

            //Check if the faction exist
            if (FactionManager.doubleFaction(config, strings[1])) {
                commandSender.sendMessage(Static.ERROR + "This faction already exist!");
                return true;
            }

            final String prefixBrut = key.substring(0, 3).toUpperCase();
            final String prefixTeam = Static.prefixCreatorJson(prefixBrut, "white");
            final String prefix = ChatColor.YELLOW + "[" + ChatColor.WHITE + "" + prefixBrut + ChatColor.YELLOW + "]";

            // Set every args in factions.yml
            config.set(key + ".owner", playerUUID);
            config.set(key + ".members", playerUUID + FactionCommand.UUID_SEPARATOR);
            config.set(key + ".invitation", "");
            config.set(key + status + ".description", "No description set. Use /f modify description to set it.");
            config.set(key + status + ".prefix", prefix);
            config.set(key + status + ".name", strings[1]);
            config.set(key + status + ".color", "white");
            config.set(key + status + ".prefix-color", "white");
            config.set(key + status + ".war", "");
            config.set(key + status + ".alliance", "");

            // Set every args in players.yml
            playerConfig.set(playerUUID + ".faction", key);

            //Set every args in list.yml
            listConfig.set("factions", key + FactionCommand.UUID_SEPARATOR);

            FactionCommand.saveFile(config, basesFile); //save factions.yml
            FactionCommand.saveFile(playerConfig, playersFile); //save players.yml
            FactionCommand.saveFile(listConfig, listFile); //save lists.yml

            // Set every args in Minecraft team in game
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, "team add " + key);
            Bukkit.dispatchCommand(console, "team join " + key + " " + ((Player) commandSender).getDisplayName());
            Bukkit.dispatchCommand(console, "team modify " + key + " prefix " + prefixTeam);

            AnnouncementCommand.announcement("faction",
                    "The faction " + strings[1] + " was created by " + ((Player) commandSender).getDisplayName(),
                    Bukkit.getOnlinePlayers().toArray(new Player[0]));
            commandSender.sendMessage(Static.SUCCESS + "The faction was created!");
            ArchitectsLand.LOGGER.info("Faction " + strings[1] + " was created by " + ((Player) commandSender).getDisplayName());
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to create a faction!");
        }
        return true;
    }
}
