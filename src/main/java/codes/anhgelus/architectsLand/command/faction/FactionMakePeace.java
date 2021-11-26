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
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class FactionMakePeace implements SubCommandBase {

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "makepeace";

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

            final String senderUUID = String.valueOf(((Player) commandSender).getUniqueId());

            //Check if the faction exist
            if (!FactionManager.doubleFaction(config, strings[1])) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            final String senderFaction = playerConfig.getString(senderUUID + ".faction");

            //Check if the player has a faction
            if (senderFaction == null) {
                commandSender.sendMessage(Static.ERROR + "You don't have faction!");
                return true;
            }

            //Check if he is the faction's owner
            if (!FactionManager.isFactionOwner((Player) commandSender, senderFaction, main)) {
                commandSender.sendMessage(Static.ERROR + "You're not the owner!");
                return true;
            }

            final String status = ".status.";
            final String factionAgainst = strings[1].toLowerCase();
            final String[] wars = config.getString(senderFaction + status + "war").split(FactionCommand.UUID_SEPARATOR);

            //Check if they are in alliance
            if (!Arrays.asList(config.getString(senderFaction + status + "war").split(FactionCommand.UUID_SEPARATOR)).contains(factionAgainst)) {
                commandSender.sendMessage(Static.ERROR + "You're not in war with " + factionAgainst + "!");
                return true;
            }

            if (factionAgainst.equals(senderFaction)) {
                commandSender.sendMessage(Static.ERROR + "AHAHAHHAHAHAHAHAHAHHAHAHAH");
                return true;
            }

            final String newAllianceAgainst = Static.removeStringInArray(wars, senderFaction);
            final String newAllianceSender = Static.removeStringInArray(wars, factionAgainst);

            config.set(senderFaction + status + "war", newAllianceSender);
            config.set(factionAgainst + status + "war", newAllianceAgainst);

            FactionCommand.saveFile(config, basesFile); //save factions.yml

            AnnouncementCommand.announcement("end of war",
                    config.getString(senderFaction + status + "name") + " make peace with " + config.getString(factionAgainst + status + "name") + "!",
                    Bukkit.getOnlinePlayers().toArray(new Player[0]));
            commandSender.sendMessage(Static.SUCCESS + "You make peace with " + factionAgainst + "!");
            ArchitectsLand.LOGGER.info("Faction " + strings[1] + " broke the alliance with " + factionAgainst + " by " + ((Player) commandSender).getDisplayName());
        }
        return true;
    }
}
