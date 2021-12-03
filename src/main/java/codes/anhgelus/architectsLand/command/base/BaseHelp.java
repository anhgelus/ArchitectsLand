package main.java.codes.anhgelus.architectsLand.command.base;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.command.CommandSender;

public class BaseHelp implements SubCommandBase {
    @Override
    public boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main) {
        commandSender.sendMessage(Static.SUCCESS + "Help faction command:\n\n" +
                Static.SEPARATOR + Static.EOL +
                Static.SUCCESS + "/b list - See the bases' list " + Static.EXAMPLE + "(you need " + BaseList.PERMISSION + " permission to see it" + Static.EOL +
                Static.SUCCESS + "/b set <public | private> - Set the faction's base " + Static.EXAMPLE + "(you need " + BaseSet.PERMISSION + " permission to set it" + Static.EOL +
                Static.SUCCESS + "/b tp <faction>- Teleport you to the faction's public base " + Static.EXAMPLE + "(you need " + BaseTp.PERMISSION + " permission to set it" + Static.EOL +
                Static.SUCCESS + "/b home - Teleport you to your faction's private base " + Static.EXAMPLE + "(you need " + BaseHome.PERMISSION + " permission to set it" + Static.EOL +
                Static.SEPARATOR + Static.EOL);
        return true;
    }
}
