package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class FactionInvite {
    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "invitation";

    public FactionInvite (String[] strings, CommandSender commandSender, ArchitectsLand main) {
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
            final File basesFile = new FactionCommand(main).getFactionsData();
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            final File playersFile = new FactionCommand(main).getPlayersData();
            final YamlConfiguration playersConfig = YamlConfiguration.loadConfiguration(playersFile);
            final Server server = Bukkit.getServer();

            final Player invitedPlayer = server.getPlayer(strings[1]);

            // Check if the player is connected
            if (invitedPlayer == null) {
                commandSender.sendMessage(Static.ERROR + "This player is not connected!");
                return true;
            }

            final String invitedPlayerUUID = String.valueOf(invitedPlayer.getUniqueId());

            final String faction = playersConfig.getString(((Player) commandSender).getUniqueId() + ".faction");

            if (faction == null) {
                commandSender.sendMessage(Static.ERROR + "You're not in a faction");
                return true;
            }

            final String invitation = config.getString(faction + ".invitation");
            final String[] invitedPlayers = invitation.split(FactionCommand.UUID_SEPARATOR);
            final String[] players = config.getString(faction + ".members").split(FactionCommand.UUID_SEPARATOR);

            // Check if the player was already invited
            for (String i : invitedPlayers) {
                if (Objects.equals(i, invitedPlayerUUID)) {
                    commandSender.sendMessage(Static.ERROR + "This player was already invited.");
                    return true;
                }
            }

            // Check if the player is in the faction
            for (String i : players) {
                if (Objects.equals(i, invitedPlayerUUID)) {
                    commandSender.sendMessage(Static.ERROR + "This player is in your faction.");
                    return true;
                }
                commandSender.sendMessage(Static.ERROR + i);
            }

            config.set(faction + ".invitation", invitation + invitedPlayerUUID + FactionCommand.UUID_SEPARATOR);

            FactionCommand.saveFile(config, basesFile);
            commandSender.sendMessage(Static.SUCCESS + "You invited " + strings[1] + "!");
            invitedPlayer.sendMessage(Static.EXAMPLE + "The " + faction + " invited you to join them!" + Static.EOL +
                    "Use /f join <faction> to join this faction!");
            ArchitectsLand.LOGGER.info(((Player) commandSender).getDisplayName() + " invited " + strings[1]);
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the player's name to invite it!");
        }
        return true;
    }
}
