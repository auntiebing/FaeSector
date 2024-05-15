package bing.faesector.data.weapons;

import com.fs.starfarer.api.combat.*;
import bing.faesector.data.magic.fae_dust_trail;
import bing.faesector.data.weapons.fae_calathAI;

import java.awt.*;
import java.util.Arrays;

public class fae_calath_wpn implements EveryFrameWeaponEffectPlugin, OnFireEffectPlugin {

    private boolean hasFired = false;


    public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {
        hasFired=true;
        new fae_calath_proj((MissileAPI)projectile );
        new fae_dust_trail(projectile, projectile.getCollisionRadius(), 1f, 0.05f, "fae_torpedo_loop", Arrays.asList(Color.PINK, Color.PINK, Color.CYAN), -0.1f, 50f, 3);
    }

    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        if(engine.isPaused())return;



        if(weapon.getChargeLevel()<=0f){
            hasFired=false;
        }

        if(weapon.getChargeLevel()>0f && !hasFired){

            //chargeup


        }




    }

}
