package bing.faesector.data.weapons.ball;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnFireEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;

import java.util.List;

public class fae_ball_onFire implements OnFireEffectPlugin {
    @Override
    public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {

        List<fae_ball_listener> listeners = Global.getCombatEngine().getListenerManager().getListeners(fae_ball_listener.class);

        if (listeners.isEmpty()) {
            return;
        }

        fae_ball_listener listener = Global.getCombatEngine().getListenerManager().getListeners(fae_ball_listener.class).get(0);

        listener.AddBall(new projPastLoc(projectile, projectile.getLocation()));
    }
}
