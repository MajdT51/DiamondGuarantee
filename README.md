# DiamondGuarantee

From **BigScary**: "I created this Minecraft server plugin using the Bukkit API to fix starvation issues with rare resources like diamonds, glowstone, nether wart, and elytras.  In Vanilla multiplayer, all of these quickly disappear from areas of the world, making it difficult for players who come later to gather those resources.  DiamondGuarantee ensures that players who are making a real effort (as opposed to trying to farm or cheat) will find those resources.  For example, it will add new diamond ores for players who are mining to find, and adds glowstone dust / nether wart drops to some nether monsters (with some anti-farming logic as well), and guarantees the ender dragon will drop an elytra on death.

BigScary stopped working on this project after Minecraft 1.10. Anyone may use my source code to continue the project, but see the LICENSE file for restrictions."

The updated version of the plugin works for `1.18.x`, `1.19.x`. You can try the plugin on the **CaveRaiders** server. IP: `caveraiders.minecraft.best`

## Description
Guarantee players who put in the work will find diamonds! Even when faced with hoarders, cheaters, and just plain bad luck.

Despite the mining limiters and anti-x-ray solutions you've probably added to your server, areas can still run low on diamond ore and other desirable rares due to many factors - hoarding, greed, overpopulation, cheating, and randomness in Vanilla world gen. This problem is worst when new players get frustrated and quit your server before they've given it a fair assessment.

DiamondGuarantee solves the diamond starvation problem by detecting when a miner is having a streak of bad luck, then cleverly injecting diamond ores just out of sight where he's sure to discover them! It also ensures players can get glowstone and ghast tear, and have an opportunity to battle the Ender Dragon and get an elytra, egg, head as a reward (all configurable, and disable-able).

## Installation

Download DiamondGuarantee.jar into your server's plugins folder, then /reload or reboot your server.

### How Diamond-Related Settings Work

The default configuration guarantees diamonds at the Vanilla rate - assuming efficient mining strategies like branch mining - without changing gameplay. You can tweak the settings to make diamonds more plentiful, or to require worse bad luck before the plugin will inject ore.

The concept is simple - players get invisible points for breaking stone (value depends on Y level where the block is broken), and lose points for breaking diamond ore. If a player's score reaches a level you specify, the plugin will generate an ore for the player to find (which, due to its placement, will be very difficult to miss). Blocks placed by players don't grant or subtract points when they're broken.

Diamonds will only ever be generated in the "diamond zone" in "normal" worlds. If your worlds aren't generated using the default world generator with default settings, then you should check and possibly adjust the diamond zone settings in the config file.

All config settings are per-world, so you can tweak settings for specific worlds.

### Configurations

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

### Commands

* Use `/DGReload` to reload settings from the config file.

* Use `/DiamondScore` to check a player's current score.

* Use `/SetDiamondScore` to change a player's current score.

### Permissions

All your players will benefit from the plugin. These permission nodes control who has access to administrative features.

* `diamondguarantee.admin.*` will conveniently grant all of the following permissions:

* `diamondguarantee.reload`

* `diamondguarantee.getscore`

* `diamondguarantee.setscore`

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
