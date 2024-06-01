package bing.faesector.data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.CombatUtils;

import java.util.HashMap;
import java.util.Map;

public class fae_Buffer extends BaseHullMod {

    private float buffDist = 700f;

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {


        for (ShipAPI shipi : Global.getCombatEngine().getShips()) {
            if (!shipi.isAlly()) {
                continue;
            }

            if (MathUtils.getDistance(shipi.getLocation(), ship.getLocation()) < buffDist) {
                shipi.getMutableStats().getFluxDissipation().modifyMult(shipi.getId(), 1.1f);
            } else {
                shipi.getMutableStats().getFluxDissipation().unmodify(shipi.getId());
            }

        }
    }

}
