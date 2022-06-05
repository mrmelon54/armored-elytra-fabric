package xyz.mrmelon54.ArmoredElytra.duckinterfaces;

import xyz.mrmelon54.ArmoredElytra.ChestplateWithElytraItem;

public interface ArmoredElytraWearingEntity {
    ChestplateWithElytraItem getArmoredElytra();

    void setArmoredElytra(ChestplateWithElytraItem value);

    void updateWearingArmoredElytra();
}
