package me.savvy.iskyblock.facades;

public interface IslandSettingsFacade {

    IslandFacade getIsland();

    boolean isPressurePlates();
    void setPressurePlates(boolean b);

    boolean isAnvilUse();
    void setAnvilUse(boolean b);

    boolean isPortalUse();
    void setPortalUse(boolean b);

    boolean isArmorStandUse();
    void setArmorStandUse(boolean b);

    boolean isChestUse();
    void setChestUse(boolean b);

    boolean isDoorUse();
    void setDoorUse(boolean b);

    boolean isGateUse();
    void setGateUse(boolean b);

    boolean isSheepSheering();
    void setSheepSheering(boolean b);

    boolean isAnimalBreeding();
    void setAnimalBreeding(boolean b);

    boolean isPotionBrewing();
    void setPotionBrewing(boolean b);

    boolean isJukeBoxUse();
    void setJukeBoxUse(boolean b);

    boolean isBlockBreak();
    void setBlockBreak(boolean b);

    boolean isEnchanting();
    void setEnchanting(boolean b);

    boolean isBeaconUse();
    void setBeaconUse(boolean b);

    boolean isRedstoneUse();
    void setRedstoneUse(boolean b);

    boolean isBedUse();
    void setBedUse(boolean b);

    boolean isFurnaceUse();
    void setFurnaceUse(boolean b);

    boolean isWorkBenchUse();
    void setWorkBenchUse(boolean b);

    boolean isEggUse();
    void setEggUse(boolean b);

    boolean isBlockPlace();
    void setBlockPlace(boolean b);

    boolean isHorseRiding();
    void setHorseRiding(boolean b);

}
