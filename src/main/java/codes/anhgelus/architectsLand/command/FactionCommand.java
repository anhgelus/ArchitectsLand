package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.faction.*;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class FactionCommand implements CommandExecutor {

    public static final String PERMISSION_FACTION = ArchitectsLand.PERMISSION + "faction.";
    public static final String UUID_SEPARATOR = ",";
    public static final String FACTION_CHECKER_TARGET = ".name";

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

    public File getFactionsData() {
        return new File(this.main.getDataFolder(), "data/factions.yml");
    }

    public static void saveFile(YamlConfiguration config, File basesFile) {
        try {
            config.save(basesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean doubleFaction(YamlConfiguration config, String faction) {
        return config.getString(faction.toLowerCase() + FACTION_CHECKER_TARGET) == null;
    }

}
