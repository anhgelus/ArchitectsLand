package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.faction.*;
import main.java.codes.anhgelus.architectsLand.manager.DataManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FactionCommand implements CommandExecutor {

    public static final String PERMISSION_FACTION = ArchitectsLand.PERMISSION + "faction.";
    public static final String UUID_SEPARATOR = ",";
    public static final String FACTION_CHECKER_TARGET = ".status.name";
    public static final List<String> COMMANDS = Arrays.asList("create", "delete", "join", "leave", "status", "modify", "invite");

    private final ArchitectsLand main;

    public FactionCommand (ArchitectsLand main) {
        this.main = main;
    }

    /**
     * Create the /f
     *
     * @param commandSender Sender of the command
     * @param command Command
     * @param s Command string
     * @param strings Args
     * @return true
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // Check if the commandSend is a player or not
        if (s.equals("f") && commandSender instanceof Player) {
            // Check if the command has args
            if (strings.length != 0) {
                if (strings[0].equals("create") && commandSender.hasPermission(FactionCreate.PERMISSION) && commandSender.isOp()) {
                    final FactionCreate fCreate = new FactionCreate(strings, commandSender, main);
                    return fCreate.command();
                } else if (strings[0].equals("join") && commandSender.hasPermission(FactionJoin.PERMISSION) && commandSender.isOp()) {
                    final FactionJoin fJoin = new FactionJoin(strings, commandSender, main);
                    return fJoin.command();
                } else if (strings[0].equals("leave") && commandSender.hasPermission(FactionLeave.PERMISSION) && commandSender.isOp()) {
                    final FactionLeave fLeave = new FactionLeave(strings, commandSender, main);
                    return fLeave.command();
                } else if (strings[0].equals("delete") && commandSender.hasPermission(FactionDelete.PERMISSION) && commandSender.isOp()) {
                    final FactionDelete fDelete = new FactionDelete(strings, commandSender, main);
                    return fDelete.command();
                } else if (strings[0].equals("modify") && commandSender.hasPermission(FactionModify.PERMISSION) && commandSender.isOp()) {
                    final FactionModify fModify = new FactionModify(strings, commandSender, main);
                    return fModify.command();
                } else if (strings[0].equals("status") && commandSender.hasPermission(FactionStatus.PERMISSION) && commandSender.isOp()) {
                    final FactionStatus fStatus = new FactionStatus(strings, commandSender, main);
                    return fStatus.command();
                } else if (strings[0].equals("invite") && commandSender.hasPermission(FactionStatus.PERMISSION) && commandSender.isOp()) {
                    final FactionInvite fInvite = new FactionInvite(strings, commandSender, main);
                    return fInvite.command();
                }
                commandSender.sendMessage(Static.ERROR + "You don't have the permission to do this!");
                return true;
            }  else {
                FactionHelp fHelp = new FactionHelp(commandSender);
                return fHelp.command();
            }
        }
        return false;
    }

    /**
     * Get the factions' data (factions.yml)
     *
     * @return Factions data file
     */
    public File getFactionsData() {
        return new File(this.main.getDataFolder(), "data/factions.yml");
    }

    /**
     * Get the players' data (player.yml)
     *
     * @return Factions data file
     */
    public File getPlayersData() {
        return new File(this.main.getDataFolder(), "data/players.yml");
    }

    /**
     * Save YML File
     *
     * @param config Config to save (YamlConfiguration)
     * @param basesFile File to save (File)
     */
    public static void saveFile(YamlConfiguration config, File basesFile) {
        DataManager dm = new DataManager("yml");
        dm.save(config, basesFile);
    }

    /**
     * Detect if the faction exist or not
     *
     * @param config Config file (YamlConfiguration)
     * @param faction Detect this faction
     * @return true -> faction exist | false -> faction doesn't exist
     */
    public static boolean doubleFaction(YamlConfiguration config, String faction) {
        boolean toReturn = false;
        if (config.getString(faction.toLowerCase() + FACTION_CHECKER_TARGET) != null) {
            toReturn = true;
        }
        return toReturn;
    }

}
