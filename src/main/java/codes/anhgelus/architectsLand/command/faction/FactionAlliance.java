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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class FactionAlliance implements SubCommandBase {

    public static String PERMISSION = FactionCommand.PERMISSION_FACTION + "alliance";

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
            if (!Objects.equals(senderUUID, config.getString(senderFaction + ".owner"))) {
                commandSender.sendMessage(Static.ERROR + "You're not the owner!");
                return true;
            }

            final String status = ".status.";
            final String factionAgainst = strings[1].toLowerCase();

            //Check if the war is already declared
            if (Arrays.asList(config.getString(senderFaction + status + "alliance").split(FactionCommand.UUID_SEPARATOR)).contains(factionAgainst)) {
                commandSender.sendMessage(Static.ERROR + "You already declared the alliance with " + factionAgainst + "!");
                return true;
            }

            if (factionAgainst.equals(senderFaction)) {
                commandSender.sendMessage(Static.ERROR + "AHAHAHHAHAHAHAHAHAHHAHAHAH");
                return true;
            }

            //Check if they are in war
            if (config.getString(senderFaction + status + "war") != null) {
                if (Arrays.asList(config.getString(senderFaction + status + "war").split(FactionCommand.UUID_SEPARATOR)).contains(factionAgainst)) {
                    commandSender.sendMessage(Static.ERROR + "Bro, please, you're on war with them!");
                    return true;
                }
            }


            final String alliance = config.getString(senderFaction + status + "alliance");

            config.set(senderFaction + status + "alliance", alliance + factionAgainst + FactionCommand.UUID_SEPARATOR);
            config.set(factionAgainst + status + "alliance", alliance + senderFaction + FactionCommand.UUID_SEPARATOR);

            FactionCommand.saveFile(config, basesFile); //save factions.yml

            AnnouncementCommand.announcement("alliance",
                    config.getString(senderFaction + status + "name") + " declared the alliance with " + config.getString(factionAgainst + status + "name") + "!",
                    Bukkit.getOnlinePlayers().toArray(new Player[0]));
            commandSender.sendMessage(Static.SUCCESS + "The alliance was declared!");
            ArchitectsLand.LOGGER.info("Faction " + strings[1] + " declared the alliance with " + factionAgainst + " by " + ((Player) commandSender).getDisplayName());
        }
        return true;
    }
}
