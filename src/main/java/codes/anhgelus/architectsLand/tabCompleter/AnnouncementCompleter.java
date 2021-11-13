package main.java.codes.anhgelus.architectsLand.tabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class AnnouncementCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("announcement") || command.getName().equalsIgnoreCase("ann")) {
            return List.of("<message>");
        }
        return null;
    }
}
