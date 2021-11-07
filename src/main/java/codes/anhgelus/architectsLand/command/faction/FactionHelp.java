package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class FactionHelp {

    private final CommandSender commandSender;

    public FactionHelp (CommandSender commandSender) {
        this.commandSender = commandSender;
    }
    public boolean command() {
        commandSender.sendMessage(ChatColor.AQUA + "Help faction command:\n" +
                "/f create FACTION_NAME - Create a faction (you need " + FactionCommand.PERMISSION_FACTION + "create permission to create it\n" +
                "/f join FACTION_NAME - Join a faction (you need " + FactionCommand.PERMISSION_FACTION + "join permission to join it)");
        return true;
    }
}
