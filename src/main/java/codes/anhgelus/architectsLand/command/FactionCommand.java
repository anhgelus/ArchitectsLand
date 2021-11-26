package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.faction.*;
import main.java.codes.anhgelus.architectsLand.manager.DataManager;
import main.java.codes.anhgelus.architectsLand.manager.PermissionManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FactionCommand implements CommandExecutor {

    public static final String PERMISSION_FACTION = ArchitectsLand.PERMISSION + "faction.";
    public static final String UUID_SEPARATOR = ",";
    public static final String FACTION_CHECKER_TARGET = ".status.name";
    public static final List<String> COMMANDS = Arrays.asList("create", "delete", "join", "leave", "status", "modify", "invite", "war", "alliance", "beakalliance", "break", "ba", "makepeace", "peace", "list");

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
                if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"create"}, FactionCreate.PERMISSION)) {
                    final FactionCreate fCreate = new FactionCreate();
                    return fCreate.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"join"}, FactionJoin.PERMISSION)) {
                    final FactionJoin fJoin = new FactionJoin();
                    return fJoin.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"leave"}, FactionLeave.PERMISSION)) {
                    final FactionLeave fLeave = new FactionLeave();
                    return fLeave.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"delete"}, FactionDelete.PERMISSION)) {
                    final FactionDelete fDelete = new FactionDelete();
                    return fDelete.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"modify"}, FactionModify.PERMISSION)) {
                    final FactionModify fModify = new FactionModify();
                    return fModify.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"status"}, FactionStatus.PERMISSION)) {
                    final FactionStatus fStatus = new FactionStatus();
                    return fStatus.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"invite"}, FactionInvite.PERMISSION)) {
                    final FactionInvite fInvite = new FactionInvite();
                    return fInvite.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"war"}, FactionWar.PERMISSION)) {
                    final FactionWar fWar = new FactionWar();
                    return fWar.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"alliance"}, FactionAlliance.PERMISSION)) {
                    final FactionAlliance fAlliance = new FactionAlliance();
                    return fAlliance.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"ba", "break", "breakalliance"}, FactionBreakAlliance.PERMISSION)) {
                    final FactionBreakAlliance fBreak = new FactionBreakAlliance();
                    return fBreak.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"peace", "makepeace"}, FactionMakePeace.PERMISSION)) {
                    final FactionMakePeace fPeace = new FactionMakePeace();
                    return fPeace.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"list"}, FactionList.PERMISSION)) {
                    final FactionList fList = new FactionList();
                    return fList.command(strings, commandSender, main);
                }
                commandSender.sendMessage(Static.ERROR + "You don't have the permission to do this!");
                return true;
            }  else {
                FactionHelp fHelp = new FactionHelp();
                return fHelp.command(strings, commandSender, main);
            }
        }
        return false;
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

}
