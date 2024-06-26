//Copyright 2015 Ryan Hamshire
package me.ryanhamshire.diamondguarantee;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DataStore {
    //in-memory cache for messages
    private String[] messages;
    private static final String dataLayerFolderPath = "plugins" + File.separator + "DiamondGuarantee";
    static final String playerDataFolderPath = dataLayerFolderPath + File.separator + "PlayerData";
    static final String messagesFilePath = dataLayerFolderPath + File.separator + "messages.yml";

    public DataStore() {
        //ensure data folders exist
        File playerDataFolder = new File(playerDataFolderPath);
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
        this.loadMessages();
    }

    private void loadMessages() {
        Messages[] messageIDs = Messages.values();
        this.messages = new String[Messages.values().length];

        HashMap<String, CustomizableMessage> defaults = new HashMap<>();

        //initialize defaults
        this.addDefault(defaults, Messages.RELOAD_COMPLETE, "Config reload complete.  If you've updated your JAR file, you'll have to /reload or reboot your server to activate the update.", null);
        this.addDefault(defaults, Messages.PLAYER_NOT_FOUND, "Player not found.", null);
        this.addDefault(defaults, Messages.DIAMOND_SCORE, "Current Score: {0}", "0: score");
        this.addDefault(defaults, Messages.SET_DIAMOND_SCORE, "Score updated.", null);

        //load the config file
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(messagesFilePath));
        FileConfiguration outConfig = new YamlConfiguration();

        //for each message ID
        for (Messages messageID : messageIDs) {
            CustomizableMessage messageData = defaults.get(messageID.name());
            if (messageData == null) {
                DiamondGuarantee.addLogEntry("Missing message for " + messageID.name() + ".  Please contact the developer.");
                messageData = new CustomizableMessage(messageID, "Missing message!  ID: " + messageID.name() + ".  Please contact a server admin.", null);
            }
            this.messages[messageID.ordinal()] = config.getString("Messages." + messageID.name() + ".Text", messageData.text);
            outConfig.set("Messages." + messageID.name() + ".Text", this.messages[messageID.ordinal()]);

            if (messageData.notes != null) {
                messageData.notes = config.getString("Messages." + messageID.name() + ".Notes", messageData.notes);
                outConfig.set("Messages." + messageID.name() + ".Notes", messageData.notes);
            }
        }

        // save any changes
        try {
            outConfig.save(DataStore.messagesFilePath);
        } catch (IOException exception) {
            DiamondGuarantee.addLogEntry("Unable to write to the configuration file at \"" + DataStore.messagesFilePath + "\"");
        }

        defaults.clear();
    }

    private void addDefault(HashMap<String, CustomizableMessage> defaults,
                            Messages id, String text, String notes) {
        CustomizableMessage message = new CustomizableMessage(id, text, notes);
        defaults.put(id.name(), message);
    }

    public synchronized String getMessage(Messages messageID, String... args) {
        String message = messages[messageID.ordinal()];

        for (int i = 0; i < args.length; i++) {
            String param = args[i];
            message = message.replace("{" + i + "}", param);
        }

        return message;
    }
}
