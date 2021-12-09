package main.java.codes.anhgelus.architectsLand.manager;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;

public class ChatManager {

    /**
     * Send a message in the console
     *
     * @param message Message
     */
    public static void sendConsoleMessage(String message) {
        ArchitectsLand.LOGGER.info("[" + ArchitectsLand.PLUGIN_NAME + "] " + message);
    }

    /**
     * Send a warning in the console
     *
     * @param message Message
     */
    public static void sendWarnMessage(String message) {
        ArchitectsLand.LOGGER.warning("[" + ArchitectsLand.PLUGIN_NAME + "] " + message);
    }

}
