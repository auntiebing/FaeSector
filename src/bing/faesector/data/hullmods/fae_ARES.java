package bing.faesector.data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class fae_ARES extends BaseHullMod {

	public static float DAMAGE_BONUS = 50f;
	public static float RANGE_BONUS = 10f;

	@Override
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

		stats.getDynamic().getMod(Stats.PD_IGNORES_FLARES).modifyFlat(id, 1f);
		stats.getDynamic().getMod(Stats.PD_BEST_TARGET_LEADING).modifyFlat(id, 1f);
		stats.getDamageToMissiles().modifyPercent(id, DAMAGE_BONUS);
		stats.getDamageToFighters().modifyPercent(id, DAMAGE_BONUS);
		stats.getAutofireAimAccuracy().modifyFlat(id, 1f);
		stats.getBeamPDWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		stats.getNonBeamPDWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int)Math.round(DAMAGE_BONUS) + "%";
		return null;
	}


}