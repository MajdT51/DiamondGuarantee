//Copyright 2015 Ryan Hamshire

package me.ryanhamshire.diamondguarantee;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

public class DiamondGuarantee extends JavaPlugin {

    // for convenience, a reference to the instance of this plugin
    public static DiamondGuarantee instance;

    // for logging to the console and log file
    private static final Logger log = Logger.getLogger("Minecraft");

    // this handles data storage, like player and region data
    DataStore dataStore;

    // config options
    WorldSettingsManager worldSettingsManager;

    public static synchronized void addLogEntry(String entry) {
        log.info("DiamondGuarantee: " + entry);
    }

    @Override
    public void onEnable() {
        addLogEntry("DiamondGuarantee enabled.");

        instance = this;

        this.dataStore = new DataStore();

        this.loadConfig();

        // register for events
        PluginManager pluginManager = this.getServer().getPluginManager();

        DGEventHandler dGEventHandler = new DGEventHandler();
        pluginManager.registerEvents(dGEventHandler, this);

        @SuppressWarnings("unchecked")
        Collection<Player> players = (Collection<Player>) this.getServer().getOnlinePlayers();
        for (Player player : players) {
            PlayerData.preloadPlayerData(player);
        }

        // Enable metrics
        Metrics metrics = new Metrics(this, 22678);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (cmd.getName().equalsIgnoreCase("dgreload")) {
            this.loadConfig();
            DiamondGuarantee.sendMessage(player, TextMode.Success, Messages.RELOAD_COMPLETE);
            return true;
        } else if (cmd.getName().equalsIgnoreCase("diamondscore")) {
            if (args.length < 1)
                return false;

            Player target = this.getServer().getPlayerExact(args[0]);
            if (target == null) {
                DiamondGuarantee.sendMessage(player, TextMode.Err, Messages.PLAYER_NOT_FOUND);
                return true;
            }

            PlayerData playerData = PlayerData.fromPlayer(target);
            DiamondGuarantee.sendMessage(player, TextMode.Info, Messages.DIAMOND_SCORE,
                    String.valueOf(playerData.getDiamondScore()));
            return true;
        } else if (cmd.getName().equalsIgnoreCase("setdiamondscore")) {
            if (args.length < 2)
                return false;

            Player target = this.getServer().getPlayerExact(args[0]);
            if (target == null) {
                DiamondGuarantee.sendMessage(player, TextMode.Err, Messages.PLAYER_NOT_FOUND);
                return true;
            }

            try {
                long newScore = Long.parseLong(args[1]);
                PlayerData playerData = PlayerData.fromPlayer(target);
                playerData.setDiamondScore(newScore);
                DiamondGuarantee.sendMessage(player, TextMode.Success, Messages.SET_DIAMOND_SCORE);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        return false;
    }

    private void loadConfig() {
        // read configuration settings (note defaults)
        this.getDataFolder().mkdirs();
        File configFile = new File(this.getDataFolder().getPath() + File.separatorChar + "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        FileConfiguration outConfig = new YamlConfiguration();

        WorldSettings defaultSettings = new WorldSettings();
        defaultSettings.diamondZoneMinY = config.getInt("Diamond Zone.Minimum Y Value", -64);
        defaultSettings.diamondZoneMaxY = config.getInt("Diamond Zone.Maximum Y Value", 20);
        defaultSettings.generateDiamonds = config.getBoolean("Diamond Generation.Enabled", true);
        defaultSettings.diamondValue = config.getLong("Diamond Generation.Diamond Value", 1000);
        defaultSettings.stoneValueOutsideZone = config.getLong("Diamond Generation.Stone Value.Outside Diamond Zone",
                1);
        defaultSettings.stoneValueInsideZone = config.getLong("Diamond Generation.Stone Value.Inside Diamond Zone", 3);
        defaultSettings.deepslateValueOutsideZone = config
                .getLong("Diamond Generation.Deepslate Value.Outside Diamond Zone", 1);
        defaultSettings.deepslateValueInsideZone = config
                .getLong("Diamond Generation.Deepslate Value.Outside Diamond Zone", 4);
        defaultSettings.generateDiamondsLog = config.getBoolean("Diamond Generation.Log Details", false);
        defaultSettings.ghastDustCount = config.getInt("Ghasts Drop This Many Glowstone Dust", 4);
        defaultSettings.shulkerShellsCount = config.getInt("Shulker Drop This Many Shulker Shells", 1);
        defaultSettings.dragonDropsEggs = config.getBoolean("Ender Dragon.Drops Dragon Eggs", true);
        defaultSettings.dragonDropsElytras = config.getBoolean("Ender Dragon.Drops Elytras", true);
        defaultSettings.dragonDropsHeads = config.getBoolean("Ender Dragon.Drops Head", true);

        this.worldSettingsManager = new WorldSettingsManager(defaultSettings);

        for (World world : this.getServer().getWorlds()) {
            WorldSettings settings = new WorldSettings();
            settings.diamondZoneMinY = config.getInt(world.getName() + ".Diamond Zone.Minimum Y Value",
                    defaultSettings.diamondZoneMinY);
            settings.diamondZoneMaxY = config.getInt(world.getName() + ".Diamond Zone.Maximum Y Value",
                    defaultSettings.diamondZoneMaxY);
            settings.generateDiamonds = config.getBoolean(world.getName() + ".Diamond Generation.Enabled",
                    defaultSettings.generateDiamonds);
            settings.diamondValue = config.getLong(world.getName() + ".Diamond Generation.Diamond Value",
                    defaultSettings.diamondValue);
            settings.stoneValueOutsideZone = config.getLong(
                    world.getName() + ".Diamond Generation.Stone Value.Outside Diamond Zone",
                    defaultSettings.stoneValueOutsideZone);
            settings.stoneValueInsideZone = config.getLong(
                    world.getName() + ".Diamond Generation.Stone Value.Inside Diamond Zone",
                    defaultSettings.stoneValueInsideZone);
            settings.deepslateValueOutsideZone = config.getLong(
                    world.getName() + ".Diamond Generation.Deepslate Value.Outside Diamond Zone",
                    defaultSettings.deepslateValueOutsideZone);
            settings.deepslateValueInsideZone = config.getLong(
                    world.getName() + ".Diamond Generation.Deepslate Value.Inside Diamond Zone",
                    defaultSettings.deepslateValueInsideZone);
            settings.generateDiamondsLog = config.getBoolean(world.getName() + ".Diamond Generation.Log Details",
                    defaultSettings.generateDiamondsLog);
            settings.ghastDustCount = config.getInt(world.getName() + ".Ghasts Drop This Many Glowstone Dust",
                    defaultSettings.ghastDustCount);
            settings.shulkerShellsCount = config.getInt(world.getName() + ".Shulker Drop This Many Shulker Shells",
                    defaultSettings.shulkerShellsCount);
            settings.dragonDropsEggs = config.getBoolean(world.getName() + ".Ender Dragon.Drops Dragon Eggs",
                    defaultSettings.dragonDropsEggs);
            settings.dragonDropsElytras = config.getBoolean(world.getName() + ".Ender Dragon.Drops Elytras",
                    defaultSettings.dragonDropsElytras);
            settings.dragonDropsHeads = config.getBoolean(world.getName() + ".Ender Dragon. Drops Heads",
                    defaultSettings.dragonDropsHeads);

            if (world.getEnvironment() == Environment.NORMAL) {
                outConfig.set(world.getName() + ".Diamond Zone.Minimum Y Value", settings.diamondZoneMinY);
                outConfig.set(world.getName() + ".Diamond Zone.Maximum Y Value", settings.diamondZoneMaxY);
                outConfig.set(world.getName() + ".Diamond Generation.Enabled", settings.generateDiamonds);
                outConfig.set(world.getName() + ".Diamond Generation.Diamond Value", settings.diamondValue);
                outConfig.set(world.getName() + ".Diamond Generation.Stone Value.Outside Diamond Zone",
                        settings.stoneValueOutsideZone);
                outConfig.set(world.getName() + ".Diamond Generation.Stone Value.Inside Diamond Zone",
                        settings.stoneValueInsideZone);
                outConfig.set(world.getName() + ".Diamond Generation.Deepslate Value.Outside Diamond Zone",
                        settings.deepslateValueOutsideZone);
                outConfig.set(world.getName() + ".Diamond Generation.Deepslate Value.Inside Diamond Zone",
                        settings.deepslateValueInsideZone);
                outConfig.set(world.getName() + ".Diamond Generation.Log Details", settings.generateDiamondsLog);
            } else if (world.getEnvironment() == Environment.NETHER) {
                outConfig.set(world.getName() + ".Ghasts Drop This Many Glowstone Dust", settings.ghastDustCount);
            } else if (world.getEnvironment() == Environment.THE_END) {
                outConfig.set(world.getName() + ".Shulker Drop This Many Shulker Shells", settings.shulkerShellsCount);
                outConfig.set(world.getName() + ".Ender Dragon.Drops Dragon Eggs", settings.dragonDropsEggs);
                outConfig.set(world.getName() + ".Ender Dragon.Drops Elytras", settings.dragonDropsElytras);
                outConfig.set(world.getName() + ".Ender Dragon.Drops Heads", settings.dragonDropsHeads);
            }

            this.worldSettingsManager.set(world.getName(), settings);
        }

        String header = "Settings are per-world.  The block of settings with no world name is for worlds created/loaded after DiamondGuarantee finishes loading, for example worlds created on-demand by other plugins.";
        outConfig.options().setHeader(Arrays.asList(header));
        outConfig.set("Diamond Zone.Minimum Y Value", defaultSettings.diamondZoneMinY);
        outConfig.set("Diamond Zone.Maximum Y Value", defaultSettings.diamondZoneMaxY);
        outConfig.set("Diamond Generation.Enabled", defaultSettings.generateDiamonds);
        outConfig.set("Diamond Generation.Diamond Value", defaultSettings.diamondValue);
        outConfig.set("Diamond Generation.Stone Value.Outside Diamond Zone", defaultSettings.stoneValueOutsideZone);
        outConfig.set("Diamond Generation.Stone Value.Inside Diamond Zone", defaultSettings.stoneValueInsideZone);
        outConfig.set("Diamond Generation.Deepslate Value.Outside Diamond Zone",
                defaultSettings.deepslateValueOutsideZone);
        outConfig.set("Diamond Generation.Deepslate Value.Inside Diamond Zone",
                defaultSettings.deepslateValueInsideZone);
        outConfig.set("Diamond Generation.Log Details", defaultSettings.generateDiamondsLog);
        outConfig.set("Ghasts Drop This Many Glowstone Dust", defaultSettings.ghastDustCount);
        outConfig.set("Shulker Drop This Many Shulker Shells", defaultSettings.shulkerShellsCount);
        outConfig.set("Ender Dragon.Drops Dragon Eggs", defaultSettings.dragonDropsEggs);
        outConfig.set("Ender Dragon.Drops Elytras", defaultSettings.dragonDropsElytras);
        outConfig.set("Ender Dragon.Drops Heads", defaultSettings.dragonDropsHeads);

        try {
            outConfig.save(configFile);
        } catch (IOException e) {
            addLogEntry("Encountered an issue while writing to the config file.");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        @SuppressWarnings("unchecked")
        Collection<Player> players = (Collection<Player>) this.getServer().getOnlinePlayers();
        for (Player player : players) {
            PlayerData data = PlayerData.fromPlayer(player);
            data.saveChanges();
            data.waitForSaveComplete();
        }

        addLogEntry("DiamondGuarantee disabled.");
    }

    static boolean hasPermission(Features feature, Player player) {
        boolean hasPermission = false;
        if (feature == Features.GENERATE_DIAMONDS) {
            hasPermission = player.hasPermission("diamondguarantee.generatediamonds");
        }
        return hasPermission;
    }

    private static void sendMessage(Player player, String message) {
        if (player != null) {
            player.sendMessage(message);
            return;
        }
        DiamondGuarantee.addLogEntry(message);
    }

    static void sendMessage(Player player, ChatColor color, Messages messageID, String... args) {
        String message = DiamondGuarantee.instance.dataStore.getMessage(messageID, args);
        sendMessage(player, color, message);
    }

    static void sendMessage(Player player, ChatColor color, String message) {
        if (message == null || message.length() == 0)
            return;

        if (player == null) {
            DiamondGuarantee.addLogEntry(color + message);
        } else {
            sendMessage(player, color + message);
        }
    }
}