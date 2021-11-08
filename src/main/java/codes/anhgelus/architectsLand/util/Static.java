package main.java.codes.anhgelus.architectsLand.util;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import org.bukkit.ChatColor;

public class Static {

    public static final ChatColor ERROR = ChatColor.RED;
    public static final ChatColor SUCCESS = ChatColor.AQUA;
    public static final ChatColor EXAMPLE = ChatColor.DARK_AQUA;
    public static final ChatColor SEPARATOR_COLOR = ChatColor.YELLOW;

    public static final String SEPARATOR = SEPARATOR_COLOR + "===============";
    public static final String EOL = "\n";

    public static final String[] COLOR_CODE = {"§0,black", "§1,dark_blue", "§2,dark_green", "§3,dark_aqua", "§4,dark_red", "§5,dark_purple", "§6,gold", "§7,gray", "§8,dark_gray", "§9,blue", "§a,green", "§b,aqua", "§c,red", "§d,light_purple", "§e,yellow", "§f,white"};

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

    public static String prefixCreator(String prefix) {
        return "[\"\",{\"text\":\"[\",\"color\":\"yellow\"},{\"text\":\"" + prefix + "\",\"color\":\"white\"},{\"text\":\"] \",\"color\":\"yellow\"}]";
    }

}
