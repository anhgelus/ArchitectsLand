package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class FactionHelp {

    private final CommandSender commandSender;

    public FactionHelp (CommandSender commandSender) {
        this.commandSender = commandSender;
    }
    public boolean command() {
        commandSender.sendMessage(Static.SUCCESS + "Help faction command:\n\n" +
                Static.SEPARATOR + Static.EOL +
                Static.SUCCESS + "/f create <faction> - Create a faction " + Static.EXAMPLE + "(you need " + FactionCreate.PERMISSION + " permission to create it" + Static.EOL +
                Static.SUCCESS + "/f join <faction> - Join a faction " + Static.EXAMPLE  + "(you need " + FactionJoin.PERMISSION + " permission to join it)" + Static.EOL +
                Static.SUCCESS + "/f status <faction> - See the status of a faction " + Static.EXAMPLE  + "(you need " + FactionStatus.PERMISSION + " permission to join it)" + Static.EOL +
                Static.SUCCESS + "/f leave <faction> - Leave a faction " + Static.EXAMPLE + "(you need " + FactionLeave.PERMISSION + " permission to leave it)" + Static.EOL +
                Static.SUCCESS + "/f modify <faction> <prefix | name | color> <modification> - Modify a faction " + Static.EXAMPLE + "(you need" + FactionModify.PERMISSION + " permission to delete it)" + Static.EOL +
                Static.SUCCESS + "/f delete <faction> - Delete a faction " + Static.EXAMPLE + "(you need" + FactionDelete.PERMISSION + " permission to delete it)" + Static.EOL +
                Static.SEPARATOR + Static.EOL);
        return true;
    }
}
