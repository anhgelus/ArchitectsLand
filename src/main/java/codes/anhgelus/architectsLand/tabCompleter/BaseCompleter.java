package main.java.codes.anhgelus.architectsLand.tabCompleter;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.BaseCommand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.command.faction.FactionModify;
import main.java.codes.anhgelus.architectsLand.manager.BaseManager;
import main.java.codes.anhgelus.architectsLand.manager.FactionManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BaseCompleter implements TabCompleter {

    private final ArchitectsLand main;

    public BaseCompleter(ArchitectsLand main) {
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        /* Check the command name. */
        if(command.getName().equalsIgnoreCase("base") || command.getName().equalsIgnoreCase("b")) {
            final List<String> baseList = Arrays.asList(new BaseManager(new YamlConfiguration(), (Player) commandSender).getBaseList(main));
            final List<String> noArgs = List.of("<NoArgs>");
            if(strings.length == 1) {
                return BaseCommand.COMMANDS;
                /* all /f */
            } else if (Objects.equals(strings[0], "home") && strings.length == 2) {
                return noArgs;
            } else if (Objects.equals(strings[0], "list") && strings.length == 2) {
                return noArgs;
            } else if (Objects.equals(strings[0], "set") && strings.length == 2) {
                return List.of("public", "private");
            } else if (Objects.equals(strings[0], "tp") && strings.length == 2) {
                return baseList;
            }
        }
        return null;
    }
}
