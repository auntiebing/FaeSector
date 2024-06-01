package bing.faesector.data.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.lwjgl.util.vector.Vector2f;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.dark.shaders.distortion.DistortionShader;
import org.dark.shaders.distortion.WaveDistortion;
import java.awt.Color;
import java.util.ArrayList;
import bing.faesector.data.fae_graphicLibEffects;
import org.lazywizard.lazylib.MathUtils;

public class fae_tasuanEffect implements OnHitEffectPlugin, OnFireEffectPlugin {
    private boolean firing=false;

    final float MUZZLE_OFFSET_HARDPOINT = 8.5f;
    final float MUZZLE_OFFSET_TURRET = 10.0f;

    private final ArrayList<SpriteAPI> ringList = new ArrayList<>();

    {
        ringList.add(Global.getSettings().getSprite("fx", "fae_tasuan_ring1"));
        ringList.add(Global.getSettings().getSprite("fx", "fae_tasuan_ring2"));
    }

    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {
        if (!shieldHit && !projectile.isFading() && target instanceof ShipAPI) {
            if (Math.random() > 0.80f) {
                engine.spawnEmpArc(projectile.getSource(),
                        point,
                        target,
                        target,
                        DamageType.ENERGY,
                        0,
                        50,
                        3000,
                        "tachyon_lance_emp_impact",
                        4,
                        Color.CYAN,
                        Color.PINK);
            }
        }
    }

    int count = 0;
    DamagingProjectileAPI lastPorj = null;

    @Override
    public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {
        engine.spawnProjectile(weapon.getShip(),
                weapon,
                "tasuan_real",
                projectile.getLocation(),
                projectile.getFacing(),
                weapon.getShip().getVelocity());
        engine.removeEntity(projectile);

        ShipAPI ship = weapon.getShip();
        Vector2f weaponLocation = weapon.getLocation();
        float shipFacing = weapon.getCurrAngle();
        Vector2f shipVelocity = ship.getVelocity();
        Vector2f muzzleLocation = MathUtils.getPointOnCircumference(weaponLocation,
                weapon.getSlot().isHardpoint() ? MUZZLE_OFFSET_HARDPOINT : MUZZLE_OFFSET_TURRET, shipFacing);

        if (Global.getSettings().getModManager().isModEnabled("shaderLib")) {
            fae_graphicLibEffects.CustomRippleDistortion(
                    muzzleLocation,
                    shipVelocity,
                    50f,
                    3f,
                    false,
                    weapon.getCurrAngle() + 180f,
                    90,
                    1f,
                    0.1f,
                    0.25f,
                    0.15f,
                    0.25f,
                    0f
            );
        }

        WaveDistortion wave = new WaveDistortion(muzzleLocation, shipVelocity);
        wave.setIntensity(3f);
        wave.setSize(35f);
        wave.flip(false);
        wave.setLifetime(0f);
        wave.fadeOutIntensity(0.3f);
        wave.setLocation(muzzleLocation);
        DistortionShader.addDistortion(wave);

        weapon.getShip().getFluxTracker().decreaseFlux(weapon.getFluxCostToFire() * 0.5f);
        if (count == 0){
            lastPorj = projectile;
            count += 1;
        } else if (count == 1){
            projectile.setAngularVelocity(lastPorj.getAngularVelocity());
            projectile.getVelocity().set(lastPorj.getVelocity());
            projectile.setFacing(lastPorj.getFacing());
            count = 0;
        }
        if(firing && weapon.getChargeLevel()<1){
            //sound
            Global.getSoundPlayer().playSound("fae_tasuan_chargedown", 1, 1, weapon.getLocation(), weapon.getShip().getVelocity());
            firing=false;
        } else if (weapon.getChargeLevel()==1){
            firing=true;
        }
    }
}
