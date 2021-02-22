package net.onpointcoding.armoredelytra.duckinterfaces;

import net.onpointcoding.armoredelytra.ChestplateWithElytraItem;

public interface ArmoredElytraWearingEntity {
    ChestplateWithElytraItem getArmoredElytra();

    void setArmoredElytra(ChestplateWithElytraItem value);

    void updateWearingArmoredElytra();
}
