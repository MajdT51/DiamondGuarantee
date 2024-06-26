package me.ryanhamshire.diamondguarantee;

class WorldSettings {
    boolean generateDiamonds;
    boolean generateDiamondsLog;
    long diamondValue;
    long stoneValueOutsideZone;
    long stoneValueInsideZone;
    long deepslateValueOutsideZone;
    long deepslateValueInsideZone;
    int diamondZoneMaxY;
    int diamondZoneMinY;
    int ghastDustCount;
    int shulkerShellsCount;
    boolean dragonDropsEggs;
    boolean dragonDropsElytras;
    boolean dragonDropsHeads;
    
    WorldSettings() { }
    
    WorldSettings(WorldSettings other) {
        this.generateDiamonds = other.generateDiamonds;
        this.generateDiamondsLog = other.generateDiamondsLog;
        this.diamondValue = other.diamondValue;
        this.stoneValueInsideZone = other.stoneValueInsideZone;
        this.stoneValueOutsideZone = other.stoneValueOutsideZone;
        this.deepslateValueInsideZone = other.deepslateValueInsideZone;
        this.deepslateValueOutsideZone = other.deepslateValueOutsideZone;
        this.diamondZoneMaxY = other.diamondZoneMaxY;
        this.diamondZoneMinY = other.diamondZoneMinY;
        this.ghastDustCount = other.ghastDustCount;
        this.shulkerShellsCount = other.shulkerShellsCount;
        this.dragonDropsEggs = other.dragonDropsEggs;
        this.dragonDropsElytras = other.dragonDropsElytras;
        this.dragonDropsHeads = other.dragonDropsHeads;
    }
}
