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
    boolean dragonDropsEggs;
    boolean dragonDropsElytras;
    
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
        this.dragonDropsEggs = other.dragonDropsEggs;
        this.dragonDropsElytras = other.dragonDropsElytras;
    }
}
