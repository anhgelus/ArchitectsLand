package main.java.codes.anhgelus.architectsLand.util;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import org.bukkit.ChatColor;

import java.util.Objects;
import java.util.function.Supplier;

public class Static {

    public static final ChatColor ERROR = ChatColor.RED;
    public static final ChatColor SUCCESS = ChatColor.AQUA;
    public static final ChatColor EXAMPLE = ChatColor.DARK_AQUA;
    public static final ChatColor SEPARATOR_COLOR = ChatColor.YELLOW;
    public static final ChatColor IMPORTANT = ChatColor.BLUE;

    public static final String SEPARATOR = SEPARATOR_COLOR + "===============";
    public static final String EOL = "\n";

    public static final String[] COLOR_CODE = {"§0,black", "§1,dark_blue", "§2,dark_green", "§3,dark_aqua", "§4,dark_red", "§5,dark_purple", "§6,gold", "§7,gray", "§8,dark_gray", "§9,blue", "§a,green", "§b,aqua", "§c,red", "§d,light_purple", "§e,yellow", "§f,white"};
    public static final ChatColor[] CHAT_COLORS = {ChatColor.AQUA,ChatColor.BLUE,ChatColor.BLACK,ChatColor.DARK_AQUA,ChatColor.DARK_RED,ChatColor.DARK_BLUE,ChatColor.DARK_GRAY,ChatColor.DARK_GREEN,ChatColor.DARK_PURPLE,ChatColor.GOLD,ChatColor.GRAY,ChatColor.GREEN,ChatColor.LIGHT_PURPLE,ChatColor.RED,ChatColor.WHITE,ChatColor.YELLOW};
    public static final String[] CHAT_COLORS_NAME = {"AQUA","BLUE","BLACK","DARK_AQUA","DARK_RED","DARK_BLUE","DARK_GRAY","DARK_GREEN","DARK_PURPLE","GOLD","GRAY","GREEN","LIGHT_PURPLE","RED","WHITE","YELLOW"};

    /*
    * toJson method not finished
    public static String toJson(String str) {
        String finalStr = str;
        int count = 0;
        // For every colors
        for (String i : COLOR_CODE) {
            // Split colors (0 => ChatColor ; 1 => Json)
            String[] color = i.split(",");

            // If the str contains the ChatColor
            if (str.contains(color[0])) {
                // Get color
                for (int n = str.length(); n != 0; n--) {

                }
                count++;
            }
        }
        return null;
    }
    */

    public static String prefixCreatorJson(String prefix, String color) {
        return "[\"\",{\"text\":\"[\",\"color\":\"yellow\"},{\"text\":\"" + prefix + "\",\"color\":\"" + color + "\"},{\"text\":\"] \",\"color\":\"yellow\"}]";
    }

    public static boolean colorExist(String color) {
        for (String i : COLOR_CODE) {
            String[] realColor = i.split(",");
            if (Objects.equals(color, realColor[1])) {
                return true;
            }
        }
        return false;
    }

    public static ChatColor getChatColor(String color) {
        return getChatColor(color, ChatColor.WHITE);
    }
    public static ChatColor getChatColor(String color, ChatColor defaultColor) {
        for (int i = 0; i < CHAT_COLORS_NAME.length; i++) {
            if (Objects.equals(color.toUpperCase(), String.valueOf(CHAT_COLORS_NAME[i]))) {
                return CHAT_COLORS[i];
            }
        }
        return defaultColor;
    }

    public static String prefixCreatorYml(String str, String color) {
        return ChatColor.YELLOW + "[" + getChatColor(color) + str + ChatColor.YELLOW + "]";
    }
}
