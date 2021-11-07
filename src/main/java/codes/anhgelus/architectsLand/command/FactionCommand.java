package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.faction.FactionCreate;
import main.java.codes.anhgelus.architectsLand.command.faction.FactionHelp;
import main.java.codes.anhgelus.architectsLand.command.faction.FactionJoin;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class FactionCommand implements CommandExecutor {

    public static final String PERMISSION_FACTION = ArchitectsLand.PERMISSION + "faction.";

    private final ArchitectsLand main;

    public FactionCommand (ArchitectsLand main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (s.equals("f") && commandSender instanceof Player) {
            if (strings.length != 0) {
                if (strings[0].equals("create") && commandSender.hasPermission(PERMISSION_FACTION + "create")) {
                    FactionCreate fCreate = new FactionCreate(strings, commandSender, main);
                    return fCreate.command();
                } else if (strings[0].equals("join") && commandSender.hasPermission(PERMISSION_FACTION + "join")) {
                    FactionJoin fJoin = new FactionJoin(strings, commandSender, main);
                    return fJoin.command();
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
}
