package main.java.codes.anhgelus.architectsLand.tabCompleter;

import main.java.codes.anhgelus.architectsLand.command.faction.FactionModify;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class FactionCompleter implements TabCompleter {
    /**
     * Complete the tab of /f commands
     *
     * @param commandSender Sender of the command
     * @param command Command
     * @param s Command in string
     * @param strings Args
     * @return List<String> of auto-completion | null
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        /* Check the command name. */
        if(command.getName().equalsIgnoreCase("f")) {
            final String faction_indication = "faction";
            if(strings.length == 1) {
                return Arrays.asList("create", "delete", "join", "leave", "status", "modify");
            /* all /f */
            } else if (Objects.equals(strings[0], "create") && strings.length == 2) {
                return Arrays.asList(faction_indication);
            } else if (Objects.equals(strings[0], "delete") && strings.length == 2) {
                return Arrays.asList(faction_indication);
            } else if (Objects.equals(strings[0], "join") && strings.length == 2) {
                return Arrays.asList(faction_indication);
            } else if (Objects.equals(strings[0], "leave") && strings.length == 2) {
                return Arrays.asList(faction_indication);
            } else if (Objects.equals(strings[0], "status") && strings.length == 2) {
                return Arrays.asList(faction_indication);
            /* /f modify */
            } else if (Objects.equals(strings[0], "modify") && strings.length == 2) {
                return Arrays.asList(faction_indication);
            } else if(Objects.equals(strings[0], "modify") && strings.length == 3) {
                return FactionModify.MODIFY_TYPE;
            } else if (Objects.equals(strings[2], "color") && strings.length == 4) {
                return Arrays.asList(Static.arrayToString(Static.CHAT_COLORS_NAME).toLowerCase().split(" "));
            } else if (Objects.equals(strings[2], "description") && strings.length == 4) {
                return Arrays.asList("description");
            } else if (Objects.equals(strings[2], "name") && strings.length == 4) {
                return Arrays.asList("name");
            } else if (Objects.equals(strings[2], "prefix") && strings.length == 4) {
                return Arrays.asList("prefix");
            } else if (Objects.equals(strings[2], "prefix-color") && strings.length == 4) {
                return Arrays.asList(Static.arrayToString(Static.CHAT_COLORS_NAME).toLowerCase().split(" "));
            }
        }
        return null;
    }
}
