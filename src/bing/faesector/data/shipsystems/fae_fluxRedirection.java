package bing.faesector.data.shipsystems;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import static bing.faesector.data.helpers.fae_stringsManager.txt;

public class fae_fluxRedirection extends BaseShipSystemScript {

    private final float
            ROF_BONUS_PERCENT = 50,
            BEAM_BONUS_PERCENT = 50,
            FLUX_REDUCTION = 0.5f;

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {

        float beamPercent = BEAM_BONUS_PERCENT * effectLevel;
        stats.getBeamWeaponDamageMult().modifyPercent(id, beamPercent);

        float rofPercent = ROF_BONUS_PERCENT * effectLevel;
        stats.getBallisticRoFMult().modifyPercent(id, rofPercent);
        stats.getEnergyRoFMult().modifyPercent(id, rofPercent);

        float fluxMult = 1-(FLUX_REDUCTION* effectLevel);
        stats.getBallisticWeaponFluxCostMod().modifyMult(id,fluxMult);
        stats.getEnergyWeaponFluxCostMod().modifyMult(id,fluxMult);
        stats.getBeamWeaponFluxCostMult().modifyMult(id,fluxMult);
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getBallisticRoFMult().unmodify(id);
        stats.getBallisticWeaponFluxCostMod().unmodify(id);
        stats.getEnergyRoFMult().unmodify(id);
        stats.getEnergyWeaponFluxCostMod().unmodify(id);
        stats.getBeamWeaponDamageMult().unmodify(id);
    }


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
