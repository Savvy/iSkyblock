package me.savvy.iskyblock.main;

import lombok.Getter;
import lombok.Setter;
import me.savvy.iskyblock.facades.IslandFacade;
import me.savvy.iskyblock.facades.IslandSettingsFacade;

public class IslandSettings implements IslandSettingsFacade {

    public IslandSettings(IslandFacade island) {
        this.island = island;
    }

    @Getter private IslandFacade island;
    @Getter @Setter private boolean pressurePlates;
    @Getter @Setter private boolean anvilUse;
    @Getter @Setter private boolean portalUse;
    @Getter @Setter private boolean armorStandUse;
    @Getter @Setter private boolean chestUse;
    @Getter @Setter private boolean doorUse;
    @Getter @Setter private boolean gateUse;
    @Getter @Setter private boolean sheepSheering;
    @Getter @Setter private boolean animalBreeding;
    @Getter @Setter private boolean potionBrewing;
    @Getter @Setter private boolean jukeBoxUse;
    @Getter @Setter private boolean blockBreak;
    @Getter @Setter private boolean blockPlace;
    @Getter @Setter private boolean enchanting;
    @Getter @Setter private boolean beaconUse;
    @Getter @Setter private boolean redstoneUse;
    @Getter @Setter private boolean bedUse;
    @Getter @Setter private boolean furnaceUse;
    @Getter @Setter private boolean workBenchUse;
    @Getter @Setter private boolean eggUse;
    @Getter @Setter private boolean horseRiding;
}
