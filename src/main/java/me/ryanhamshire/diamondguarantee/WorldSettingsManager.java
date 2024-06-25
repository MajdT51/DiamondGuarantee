package me.ryanhamshire.diamondguarantee;

import org.bukkit.World;

import java.util.concurrent.ConcurrentHashMap;

class WorldSettingsManager {
    private static final String OTHER_WORLD_KEY = "Other Worlds";

    private ConcurrentHashMap<String, WorldSettings> nameToSettingsMap = new ConcurrentHashMap<>();

    WorldSettingsManager(WorldSettings otherWorldSettings) {
        this.nameToSettingsMap.put(OTHER_WORLD_KEY, new WorldSettings(otherWorldSettings));
    }

    void set(World world, WorldSettings settings) {
        this.set(world.getName(), settings);
    }

    public void set(String key, WorldSettings settings) {
        this.nameToSettingsMap.put(key, settings);
    }

    WorldSettings get(World world) {
        return this.get(world.getName());
    }

    WorldSettings get(String key) {
        WorldSettings settings = this.nameToSettingsMap.get(key);
        if (settings != null)
            return settings;
        return this.nameToSettingsMap.get(OTHER_WORLD_KEY);
    }

    public WorldSettings create(String worldName, WorldSettings defaultSettings) {
        WorldSettings settings = new WorldSettings(defaultSettings);
        this.nameToSettingsMap.put(worldName, settings);
        return settings;
    }
}
