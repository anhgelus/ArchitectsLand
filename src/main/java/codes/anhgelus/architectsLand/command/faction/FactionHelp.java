package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.command.CommandSender;

public class FactionHelp implements SubCommandBase {

    private final CommandSender commandSender;

    public FactionHelp (CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    /**
     * Execute the command
     *
     * @return true
     */
    public boolean command() {
        commandSender.sendMessage(Static.SUCCESS + "Help faction command:\n\n" +
                Static.SEPARATOR + Static.EOL +
                Static.SUCCESS + "/f create <faction> - Create a faction " + Static.EXAMPLE + "(you need " + FactionCreate.PERMISSION + " permission to create it" + Static.EOL +
                Static.SUCCESS + "/f invite <faction> - Invite a player to join your faction " + Static.EXAMPLE  + "(you need " + FactionInvite.PERMISSION + " permission to join it)" + Static.EOL +
                Static.SUCCESS + "/f join <faction> - Join a faction " + Static.EXAMPLE  + "(you need " + FactionJoin.PERMISSION + " permission to join it)" + Static.EOL +
                Static.SUCCESS + "/f status <faction> - See the status of a faction " + Static.EXAMPLE  + "(you need " + FactionStatus.PERMISSION + " permission to join it)" + Static.EOL +
                Static.SUCCESS + "/f leave <faction> - Leave a faction " + Static.EXAMPLE + "(you need " + FactionLeave.PERMISSION + " permission to leave it)" + Static.EOL +
                Static.SUCCESS + "/f modify <faction> <prefix | name | color | description> <modification> - Modify a faction " + Static.EXAMPLE + "(you need" + FactionModify.PERMISSION + " permission to delete it)" + Static.EOL +
                Static.SUCCESS + "/f war <faction> - Declare a war " + Static.EXAMPLE + "(you need" + FactionWar.PERMISSION + " permission to delete it)" + Static.EOL +
                Static.SUCCESS + "/f alliance <faction> - Declare an alliance " + Static.EXAMPLE + "(you need" + FactionAlliance.PERMISSION + " permission to delete it)" + Static.EOL +
                Static.SUCCESS + "/f delete <faction> - Delete a faction " + Static.EXAMPLE + "(you need" + FactionDelete.PERMISSION + " permission to delete it)" + Static.EOL +
                Static.SEPARATOR + Static.EOL);
        return true;
    }
}
