package main.java.codes.anhgelus.architectsLand.manager;

import org.bukkit.command.CommandSender;

import java.util.Objects;

public class PermissionManager {

    /**
     * Check if the player as the permission
     *
     * @param commandSended string command name
     * @param commandSender CommandSender sender of the command
     * @param commands string command label
     * @param permission string permission
     * @return bool true = has permission | false = don't have permission
     */
    public static boolean permissionChecker(String commandSended, CommandSender commandSender, String[] commands, String permission) {
        for (String command : commands) {
            if (Objects.equals(commandSended, command)) {
                if (commandSender.hasPermission(permission) || commandSender.isOp()) {
                    return true;
                }
            }
        }

        return false;
    }
}
