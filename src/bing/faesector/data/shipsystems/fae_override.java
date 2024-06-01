
package bing.faesector.data.shipsystems;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;

import static bing.faesector.data.helpers.stringsManager.txt;

public class fae_override extends BaseShipSystemScript {

    private static Map mag = new HashMap();
    static {
        mag.put(HullSize.FIGHTER, 0.60f);
        mag.put(HullSize.FRIGATE, 0.75f);
        mag.put(HullSize.DESTROYER, 0.75f);
        mag.put(HullSize.CRUISER, 0.80f);
        mag.put(HullSize.CAPITAL_SHIP, 0.80f);
    }

    protected Object STATUSKEY1 = new Object();

    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        effectLevel = 1f;

        float mult = (Float) mag.get(HullSize.CRUISER);
        if (stats.getVariant() != null) {
            mult = (Float) mag.get(stats.getVariant().getHullSize());
        }
        stats.getHullDamageTakenMult().modifyMult(id, 1f - (1f - mult) * effectLevel);
        stats.getArmorDamageTakenMult().modifyMult(id, 1f - (1f - mult) * effectLevel);
        stats.getEmpDamageTakenMult().modifyMult(id, 1f - (1f - mult) * effectLevel);
        stats.getMaxSpeed().modifyMult(id, 0f);
        stats.getDeceleration().modifyPercent(id, 100);
        stats.getEngineMalfunctionChance().modifyPercent(id, 50);

        float beamPercent = BEAM_BONUS_PERCENT * effectLevel;
        stats.getBeamWeaponDamageMult().modifyPercent(id, beamPercent);

        float rofPercent = ROF_BONUS_PERCENT * effectLevel;
        stats.getBallisticRoFMult().modifyPercent(id, rofPercent);
        stats.getEnergyRoFMult().modifyPercent(id, rofPercent);

        float fluxMult = 1-(FLUX_REDUCTION* effectLevel);
        stats.getBallisticWeaponFluxCostMod().modifyMult(id,fluxMult);
        stats.getEnergyWeaponFluxCostMod().modifyMult(id,fluxMult);
        stats.getBeamWeaponFluxCostMult().modifyMult(id,fluxMult);



        ShipAPI ship = null;
        boolean player = false;
        if (stats.getEntity() instanceof ShipAPI) {
            ship = (ShipAPI) stats.getEntity();
            player = ship == Global.getCombatEngine().getPlayerShip();
        }
        if (player) {
            ShipSystemAPI system = getDamper(ship);
            if (system != null) {
                float percent = (1f - mult) * effectLevel * 100;
                Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY1,
                        system.getSpecAPI().getIconSpriteName(), system.getDisplayName(),
                        (int) Math.round(percent) + "% less damage taken", false);
            }

        }
    }

    public static ShipSystemAPI getDamper(ShipAPI ship) {
        ShipSystemAPI system = ship.getPhaseCloak();
        if (system != null && system.getId().equals("damper")) return system;
        if (system != null && system.getId().equals("damper_omega")) return system;
        if (system != null && system.getSpecAPI() != null && system.getSpecAPI().hasTag(Tags.SYSTEM_USES_DAMPER_FIELD_AI)) return system;
        return ship.getSystem();
    }

    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getHullDamageTakenMult().unmodify(id);
        stats.getArmorDamageTakenMult().unmodify(id);
        stats.getEmpDamageTakenMult().unmodify(id);
        stats.getBallisticRoFMult().unmodify(id);
        stats.getBallisticWeaponFluxCostMod().unmodify(id);
        stats.getEnergyRoFMult().unmodify(id);
        stats.getEnergyWeaponFluxCostMod().unmodify(id);
        stats.getBeamWeaponDamageMult().unmodify(id);
        stats.getMaxSpeed().unmodify(id);
        stats.getDeceleration().unmodify(id);
    }
    private final float
            ROF_BONUS_PERCENT = 50,
            BEAM_BONUS_PERCENT = 50,
            FLUX_REDUCTION = 0.5f;

    private final String TXT1 = txt("redirection1");
    private final String TXT2 = txt("%");
    private final String TXT3 = txt("redirection2");
    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        int mult = Math.round(ROF_BONUS_PERCENT * effectLevel);
        int flux = Math.round(FLUX_REDUCTION*100 * effectLevel);
        if (index == 0) {
            return new StatusData(TXT1 + mult + TXT2, false);
        }
        if (index == 1) {
            return new StatusData(TXT3 + flux + TXT2, false);
        }
        return null;
    }

}

