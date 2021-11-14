package main.java.codes.anhgelus.architectsLand.tabCompleter;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.command.faction.FactionModify;
import main.java.codes.anhgelus.architectsLand.manager.FactionManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class FactionCompleter implements TabCompleter {

    private final ArchitectsLand main;

    public FactionCompleter(ArchitectsLand main) {
        this.main = main;
    }

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
            final List<String> faction_indication = Arrays.asList(new FactionManager(main).getFactions());
            if(strings.length == 1) {
                return FactionCommand.COMMANDS;
            /* all /f */
            } else if (Objects.equals(strings[0], "create") && strings.length == 2) {
                return faction_indication;
            } else if (Objects.equals(strings[0], "delete") && strings.length == 2) {
                return faction_indication;
            } else if (Objects.equals(strings[0], "join") && strings.length == 2) {
                return faction_indication;
            } else if (Objects.equals(strings[0], "leave") && strings.length == 2) {
                return faction_indication;
            } else if (Objects.equals(strings[0], "status") && strings.length == 2) {
                return faction_indication;
            } else if (Objects.equals(strings[0], "invite") && strings.length == 2) {
                return Static.getEveryPlayer();
            } else if (Objects.equals(strings[0], "list") && strings.length == 2) {
                return List.of("NoArgs");
            /* /f war && /f alliance */
            } else if (Objects.equals(strings[0], "war") && strings.length == 2) {
                return faction_indication;
            } else if ((Objects.equals(strings[0], "ba") || Objects.equals(strings[0], "break") || Objects.equals(strings[0], "breakalliance")) && strings.length == 2) {
                return faction_indication;
            /* /f modify */
            } else if (Objects.equals(strings[0], "modify") && strings.length == 2) {
                return faction_indication;
            } else if (strings.length == 2) {
                return Static.getEveryPlayer();
            } else if(Objects.equals(strings[0], "modify") && strings.length == 3) {
                return FactionModify.MODIFY_TYPE;
            } else if (Objects.equals(strings[2], "color") && strings.length == 4) {
                return Arrays.asList(Static.arrayToString(Static.CHAT_COLORS_NAME).toLowerCase().split(" "));
            } else if (Objects.equals(strings[2], "description") && strings.length == 4) {
                return List.of("<description>");
            } else if (Objects.equals(strings[2], "name") && strings.length == 4) {
                return List.of("<name>");
            } else if (Objects.equals(strings[2], "prefix") && strings.length == 4) {
                return List.of("<prefix>");
            } else if (Objects.equals(strings[2], "prefix-color") && strings.length == 4) {
                return Arrays.asList(Static.arrayToString(Static.CHAT_COLORS_NAME).toLowerCase().split(" "));
            }
        }
        return null;
    }
}
