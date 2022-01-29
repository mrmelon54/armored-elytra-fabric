package xyz.mrmelon54.armoredelytra.duckinterfaces;

import xyz.mrmelon54.armoredelytra.ChestplateWithElytraItem;

public interface ArmoredElytraWearingEntity {
    ChestplateWithElytraItem getArmoredElytra();

    void setArmoredElytra(ChestplateWithElytraItem value);

    void updateWearingArmoredElytra();
}
