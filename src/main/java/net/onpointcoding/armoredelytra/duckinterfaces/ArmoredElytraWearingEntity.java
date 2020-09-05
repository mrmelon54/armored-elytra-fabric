package net.onpointcoding.armoredelytra.duckinterfaces;

import net.onpointcoding.armoredelytra.ArmoredElytraItem;

public interface ArmoredElytraWearingEntity {
    ArmoredElytraItem getArmoredElytra();

    void setArmoredElytra(ArmoredElytraItem value);

    void updateWearingArmoredElytra();
}
