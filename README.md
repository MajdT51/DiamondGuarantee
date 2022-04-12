# DiamondGuarantee

From BigScary: "I created this Minecraft server plugin using the Bukkit API to fix starvation issues with rare resources like diamonds, glowstone, nether wart, and elytras.  In Vanilla multiplayer, all of these quickly disappear from areas of the world, making it difficult for players who come later to gather those resources.  DiamondGuarantee ensures that players who are making a real effort (as opposed to trying to farm or cheat) will find those resources.  For example, it will add new diamond ores for players who are mining to find, and adds glowstone dust / nether wart drops to some nether monsters (with some anti-farming logic as well), and guarantees the ender dragon will drop an elytra on death.

BigScary stopped working on this project after Minecraft 1.10. Anyone may use my source code to continue the project, but see the LICENSE file for restrictions."

The updated new version of the plugin work for `Minecraft 1.18.2` and it can be tested on the **CaveRaiders** server. IP: `caveraiders.minecraft.best`

## Configurations

    diamondZoneMinY: "Diamond Zone.Minimum Y Value" default -64
    diamondZoneMaxY: "Diamond Zone.Maximum Y Value" default 20
    generateDiamonds: "Diamond Generation.Enabled" default true
    diamondValue: "Diamond Generation. Diamond Value" default 1000;
    stoneValueOutsideZone: "Diamond Generation. Stone Value.Outside Diamond Zone" default 1 
    stoneValueInsideZone: "Diamond Generation. Stone Value.Inside Diamond Zone" default 3
    deepslateValueOutsideZone: "Diamond Generation. Deepslate Value. Outside Diamond Zone" default 1 
    deepslateValueInsideZone: "Diamond Generation. Deepslate Value. Inside Diamond Zone" default 4
    generateDiamondsLog: "Diamond Generation. Log Details" default false
    ghastDustCount: "Ghasts Drop This Many Glowstone Dust" default 4
    dragonDropsEggs: "Ender Dragon. Drops Dragon Eggs" default true
    dragonDropsElytras: "Ender Dragon. Drops Elytras" default true
    dragonDropsHeads: "Ender Dragon. Drops Heads" default true

## Updates

06.11.2021
* Version 0.1.0
* Updated only for testing and learning purposes! no warranty!

01.01.2022
* Version 0.1.1
* Add deepslate: stone and deepslate are counted the same way now (has the same value)

22.01.2022
* Version 0.1.2
* Add 2 new configs `deepslateValueOutsideZone` and `deepslateValueInsideZone` for deepslate value inside and outside confifured diamond Zone
* Add Ender Dragon Drops Heads back to the plugin

12.04.2022
* Version 0.2.0
* Update to Minecraft 1.18.2
* Update java version to java 11
