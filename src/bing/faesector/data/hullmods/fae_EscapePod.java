package bing.faesector.data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class fae_EscapePod extends BaseHullMod {

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
//stats.getMissileRoFMult().modifyPercent(id, ROF_PENALTY);
		stats.getCrewLossMult().modifyMult(id, 0.1f);
	}
}