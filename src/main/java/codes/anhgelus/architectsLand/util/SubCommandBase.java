package main.java.codes.anhgelus.architectsLand.util;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import org.bukkit.command.CommandSender;

public interface SubCommandBase {
    boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main);
}
