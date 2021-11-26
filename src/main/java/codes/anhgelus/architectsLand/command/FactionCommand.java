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
                    final FactionCreate fCreate = new FactionCreate(strings, commandSender, main);
                    return fCreate.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"join"}, FactionJoin.PERMISSION)) {
                    final FactionJoin fJoin = new FactionJoin(strings, commandSender, main);
                    return fJoin.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"leave"}, FactionLeave.PERMISSION)) {
                    final FactionLeave fLeave = new FactionLeave(strings, commandSender, main);
                    return fLeave.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"delete"}, FactionDelete.PERMISSION)) {
                    final FactionDelete fDelete = new FactionDelete(strings, commandSender, main);
                    return fDelete.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"modify"}, FactionModify.PERMISSION)) {
                    final FactionModify fModify = new FactionModify(strings, commandSender, main);
                    return fModify.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"status"}, FactionStatus.PERMISSION)) {
                    final FactionStatus fStatus = new FactionStatus(strings, commandSender, main);
                    return fStatus.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"invite"}, FactionInvite.PERMISSION)) {
                    final FactionInvite fInvite = new FactionInvite(strings, commandSender, main);
                    return fInvite.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"war"}, FactionWar.PERMISSION)) {
                    final FactionWar fWar = new FactionWar(strings, commandSender, main);
                    return fWar.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"alliance"}, FactionAlliance.PERMISSION)) {
                    final FactionAlliance fAlliance = new FactionAlliance(strings, commandSender, main);
                    return fAlliance.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"ba", "break", "breakalliance"}, FactionBreakAlliance.PERMISSION)) {
                    final FactionBreakAlliance fBreak = new FactionBreakAlliance(strings, commandSender, main);
                    return fBreak.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"peace", "makepeace"}, FactionMakePeace.PERMISSION)) {
                    final FactionMakePeace fPeace = new FactionMakePeace(strings, commandSender, main);
                    return fPeace.command();
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"list"}, FactionList.PERMISSION)) {
                    final FactionList fList = new FactionList(strings, commandSender, main);
                    return fList.command();
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
