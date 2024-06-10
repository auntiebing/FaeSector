package bing.faesector.data.hullmods.fae_Buffer;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.CombatUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class fae_Buffer extends BaseHullMod {

    private float buffDist = 700f;

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {

        if (ship.getCustomData().get("runOnce") == null) {
            Global.getCombatEngine().addLayeredRenderingPlugin(new fae_BufferRender(ship, buffDist));
            ship.setCustomData("runOnce", false);
        }

        for (ShipAPI shipi : Global.getCombatEngine().getShips()) {
            if (Objects.equals(shipi.getId(), ship.getId())) {
                continue;
            }

            if (shipi.getOwner() != ship.getOwner()) {
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
