package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.AnnouncementCommand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.manager.DataManager;
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

public class FactionAcceptAlliance implements SubCommandBase {

    public static String PERMISSION = FactionCommand.PERMISSION_FACTION + "alliance-accept";

    @Override
    public boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main) {
        if (strings.length > 1) {
            final File basesFile = FileManager.getFactionsData(main);
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            final File playerFile = FileManager.getPlayersData(main);
            final YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

            final Player sender = (Player) commandSender;
            final String senderFaction = playerConfig.getString(sender.getUniqueId() + ".faction");

            //Check if the faction exist
            if (!FactionManager.doubleFaction(config, strings[1])) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

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
            final String alliance = config.getString(senderFaction + status + "alliance");
            final String[] factionsA = alliance.split(FactionCommand.UUID_SEPARATOR);


            if (Arrays.asList(factionsA).contains(factionAgainst)) {
                commandSender.sendMessage(Static.ERROR + "You already declared the alliance with " + factionAgainst + "!");
                return true;
            }

            if (factionAgainst.equals(senderFaction)) {
                commandSender.sendMessage(Static.ERROR + "AHAHAHHAHAHAHAHAHAHHAHAHAH");
                return true;
            }

            final DataManager data = new DataManager("yml");

            //Check if they are in war
            if (config.getString(senderFaction + status + "war") != null) {
                final String[] factionsW = config.getString(senderFaction + status + "war").split(FactionCommand.UUID_SEPARATOR);
                if (Arrays.asList(factionsW).contains(factionAgainst)) {
                    commandSender.sendMessage(Static.ERROR + "Bro, please, you're on war with them!");
                    config.set(senderFaction + status + "alliance-invitation", Static.removeStringInArray(factionsW, factionAgainst));

                    data.save(config, basesFile);
                    return true;
                }
            }

            config.set(senderFaction + status + "alliance", alliance + factionAgainst + FactionCommand.UUID_SEPARATOR);
            config.set(factionAgainst + status + "alliance", alliance + senderFaction + FactionCommand.UUID_SEPARATOR);
            data.save(config, basesFile);

            commandSender.sendMessage(Static.SUCCESS + "You're in alliance with this faction!");
            AnnouncementCommand.announcement("alliance",
                    config.getString(senderFaction + status + "name") + " declared the alliance with " + config.getString(factionAgainst + status + "name") + "!",
                    Bukkit.getOnlinePlayers().toArray(new Player[0]));
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to join it!");
        }
        return true;
    }
}
