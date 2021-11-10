package main.java.codes.anhgelus.architectsLand.tabCompleter;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class FactionCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        /* Check the command name. */
        if(command.getName().equalsIgnoreCase("f")) {
            if(strings.length == 1) {
                return Arrays.asList("create", "delete", "join", "leave", "status", "modify");
            } else if(Objects.equals(strings[0], "modify") && strings.length == 2) {
                return Arrays.asList("prefix", "color", "name", "description");
            } else if (Objects.equals(strings[1], "color") && strings.length == 3) {
                return Arrays.asList(Static.arrayToString(Static.CHAT_COLORS_NAME).toLowerCase().split(" "));
            }
        }
        return null;
    }
}
