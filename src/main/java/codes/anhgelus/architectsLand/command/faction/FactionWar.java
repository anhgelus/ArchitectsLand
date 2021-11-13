package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class FactionWar implements SubCommandBase {
    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "war";

    public FactionWar (String[] strings, CommandSender commandSender, ArchitectsLand main) {
        this.strings = strings;
        this.commandSender = commandSender;
        this.main = main;
    }
    @Override
    public boolean command() {
        if (strings.length > 1) {
            final File basesFile = new FactionCommand(main).getFactionsData();
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            File playersFile = new FactionCommand(main).getPlayersData();
            final YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playersFile);

            final String senderUUID = String.valueOf(((Player) commandSender).getUniqueId());

            //Check if the faction exist
            if (!FactionCommand.doubleFaction(config, strings[1])) {
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
            if (Arrays.asList(config.getString(senderFaction + status + "war").split(FactionCommand.UUID_SEPARATOR)).contains(factionAgainst)) {
                commandSender.sendMessage(Static.ERROR + "You already declared the war on " + factionAgainst + "!");
                return true;
            }

            if (factionAgainst.equals(senderFaction)) {
                commandSender.sendMessage(Static.ERROR + "Are you kidding me? You're stupid. You need a ban...");
                return true;
            }

            //Check if they are in alliance
            if (config.getString(senderFaction + status + "alliance") != null) {
                if (Arrays.asList(config.getString(senderFaction + status + "alliance").split(FactionCommand.UUID_SEPARATOR)).contains(factionAgainst)) {
                    commandSender.sendMessage(Static.ERROR + "Bro, please, you're in alliance with them!");
                    return true;
                }
            }


            final String war = config.getString(senderFaction + status + "war");

            config.set(senderFaction + status + "war", war + factionAgainst + FactionCommand.UUID_SEPARATOR);
            config.set(factionAgainst + status + "war", war + senderFaction + FactionCommand.UUID_SEPARATOR);

            FactionCommand.saveFile(config, basesFile); //save factions.yml

            final Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);

            for (Player i : players) {
                i.sendMessage(Static.SEPARATOR_COLOR + "[" + ChatColor.GREEN + "WAR" + Static.SEPARATOR_COLOR + "] " +
                        Static.SUCCESS + config.getString(senderFaction + status + "name") + " declared the war on " + config.getString(factionAgainst + status + "name") + "!");
                i.playSound(i.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            }

            commandSender.sendMessage(Static.SUCCESS + "The war was declared!");
            ArchitectsLand.LOGGER.info("Faction " + strings[1] + " declared the war on " + factionAgainst + " by " + ((Player) commandSender).getDisplayName());
        }
        return true;
    }
}
