package bing.faesector.data.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.AsteroidAPI;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.magiclib.util.MagicLensFlare;
import org.magiclib.util.MagicRender;
import java.awt.Color;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.entities.SimpleEntity;
import org.lwjgl.util.vector.Vector2f;

public class fae_tasuanEffect implements OnHitEffectPlugin {

    @Override
    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {

        if(target instanceof MissileAPI || target instanceof AsteroidAPI)return;
//        if(!projectile.isFading())return;

        MagicRender.battlespace(
                Global.getSettings().getSprite("fx","muzzleTasuan"),
                point,
                new Vector2f(),
                new Vector2f(96,96),
                new Vector2f(400,400),
                //angle,
                360*(float)Math.random(),
                0,
                new Color(255,200,200,255),
                true,
                0,
                0.1f,
                0.15f
        );
        MagicRender.battlespace(
                Global.getSettings().getSprite("fx","muzzleTasuan"),
                point,
                new Vector2f(),
                new Vector2f(128,128),
                new Vector2f(200,200),
                //angle,
                360*(float)Math.random(),
                0,
                new Color(255,225,225,225),
                true,
                0.2f,
                0.0f,
                0.3f
        );
        MagicRender.battlespace(
                Global.getSettings().getSprite("fx","muzzleTasuan"),
                point,
                new Vector2f(),
                new Vector2f(196,196),
                new Vector2f(100,100),
                //angle,
                360*(float)Math.random(),
                0,
                new Color(255,255,255,200),
                true,
                0.4f,
                0.0f,
                0.6f
        );

        engine.addHitParticle(
                point,
                new Vector2f(),
                250,
                0.1f,
                1f,
                Color.cyan);
        engine.addSmoothParticle(
                point,
                new Vector2f(),
                350,
                2f,
                0.25f,
                Color.pink);
        engine.addSmoothParticle(
                point,
                new Vector2f(),
                300,
                2f,
                0.1f,
                Color.white);

        if(MagicRender.screenCheck(0.25f, projectile.getLocation())){

            int offset = MathUtils.getRandomNumberInRange(20, 80);
            boolean closed=false;
            Vector2f pointA = MathUtils.getPoint(point, MathUtils.getRandomNumberInRange(40, 80), projectile.getFacing());
            Vector2f pointB = MathUtils.getPoint(point, MathUtils.getRandomNumberInRange(40, 80), projectile.getFacing()+offset);

            while (!closed){
                engine.spawnEmpArc(
                        projectile.getSource(),
                        pointA,
                        new SimpleEntity(point),
                        new SimpleEntity(pointB),
                        DamageType.KINETIC,
                        0,
                        0,
                        200,
                        null,
                        MathUtils.getRandomNumberInRange(2, 4),
                        new Color(150,20,200,32),
                        new Color(50,10,150,90)
                );
                offset+= MathUtils.getRandomNumberInRange(20, 80);
                if(offset>=360){
                    offset=360;
                    closed=true;
                }
                pointA=pointB;
                pointB=MathUtils.getPoint(point, MathUtils.getRandomNumberInRange(40, 80), projectile.getFacing()+offset);
            }

            if(MagicRender.screenCheck(0.25f, point)){
                MagicLensFlare.createSharpFlare(
                        engine,
                        projectile.getSource(),
                        point,
                        6,
                        300,
                        0,
                        new Color(150,20,200,100),
                        new Color(50,10,150,100)
                );
            }
        }
        Global.getSoundPlayer().playSound(
                "tachyon_lance_emp_impact",
                1.25f,
                1,
                point,
                target.getLocation()
        );
    }
}