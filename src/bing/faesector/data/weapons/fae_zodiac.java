package bing.faesector.data.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import bing.faesector.data.magic.fae_dust_trail;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import bing.faesector.data.fae_misc.*;
import bing.faesector.data.fae_misc;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;
import org.magiclib.util.MagicAnim;
import org.magiclib.util.MagicRender;

import java.util.List;
import java.util.Vector;
import bing.faesector.data.magic.fae_zodiac_burn;

public class fae_zodiac implements EveryFrameWeaponEffectPlugin, OnFireEffectPlugin {

    private boolean hasFired = false;


    public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {
        hasFired=true;
        for(int i=0; i<numSigns(); i++){
            float a1 = i*360f/numSigns();
            float r1 = ringRadius();
            Vector2f pp1 = MathUtils.getPointOnCircumference(point, r1, a1);
            Vector2f v1 = MathUtils.getPointOnCircumference(new Vector2f(), 200f, a1);
            v1= new Vector2f();
            DamagingProjectileAPI proj = (DamagingProjectileAPI) engine.spawnProjectile(weapon.getShip(), weapon, "fae_zethell_F", pp1, a1+90f, v1);
            new fae_dust_trail(proj, proj.getCollisionRadius(), 1f, 0.05f, "fae_torpedo_loop", Arrays.asList(Color.CYAN, Color.WHITE, Color.PINK), -0.1f, 50f, 5);
            new fae_zodiac_burn((MissileAPI)proj, 30f );
            engine.removeEntity(projectile);
        }

        //new fae_sparkledust_proj((MissileAPI)projectile );
        //new fae_sparkle_trail(projectile, projectile.getCollisionRadius(), 1f, 0.05f, "fae_sparkledust_loop", Arrays.asList(Color.BLUE, Color.GREEN, Color.RED), -0.1f, 50f, 5);
    }
    private static int numSigns(){return 12;}
    private List<Float> signs = new ArrayList<>();
    float shift = 0f;

    private static float ringRadius(){return 100f;}
    private static float signRadius(){return 40f;}

    private static float signOffset(){return 0.3f;}
    IntervalUtil pi1= new IntervalUtil(0.1f,0.1f);
    IntervalUtil pi2= new IntervalUtil(0.05f,0.05f);

    private List<Color> colorList(){
        return Arrays.asList(
          Color.WHITE, Color.CYAN, Global.getSettings().getColor("fae_color_teal"), Global.getSettings().getColor("fae_color_green")
        );
    }
    private boolean runOnce = false;
    Vector2f point = new Vector2f();
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        if(engine.isPaused())return;

        if(!runOnce){
            runOnce=true;
            for(int i=0; i<numSigns(); i++){
                signs.add(0f);
            }
        }

        float level = 0f;
        float lc1 = 0f;
        shift+=amount;


        float step = (1f-signOffset())/(numSigns()+2f);

        if(weapon.getChargeLevel()<=0f){
            hasFired=false;
        }


        level = weapon.getChargeLevel();

        if(weapon.getChargeLevel()>0f && !hasFired){

            point = MathUtils.getPointOnCircumference(weapon.getLocation(), 0f, weapon.getSlot().getAngle()+weapon.getShip().getFacing());
            //chargeup
            level = weapon.getChargeLevel();
            if(level<signOffset()){
                lc1= level/signOffset();
            }else{lc1 = 1f;}
            for(int i=0; i<numSigns(); i++){
                float sa = signOffset()+i*step;
                float sb = sa+2f*step;
                if(level<sa)signs.set(i,0f);
                if(level>sb)signs.set(i,1f);
                if(level<=sb && level>=sa){
                    signs.set(i, (level-sa)/(sb-sa));
                }
            }
        }
        if(weapon.getChargeLevel()>0f && hasFired){
            lc1 = level;
            for(int i=0; i<numSigns(); i++){
                signs.set(i,level);
            }
        }
        if(weapon.getChargeLevel()>0){
            renderCenter(point, level, lc1, shift);
            renderBalls(point, level, shift);
        }

        pi1.advance(amount*lc1);

        float l1 = m.isq(level);




    }


    private void renderBalls(Vector2f point, float level, float shift){
        for(int i=0; i<numSigns(); i++){
            float l1 = signs.get(i);
            float l2 = MagicAnim.smoothReturnNormalizeRange(l1, 0, 1f);
            float size = signRadius();

            float a1 = (i+0.5f)*360f/numSigns();
            float r1 = ringRadius()*l1;
            float s1 = 1.5f*size*l1+size*l2;
            float s2 = 0.5f*size*m.isq(l1);
            float s3 = 1.5f*size*m.isq(l1);
            if(hasFired) {
                s1 = size;
                r1 = ringRadius() + ringRadius() * m.isq(1f - l1 * l1);
            }

            Vector2f pp1 = MathUtils.getPointOnCircumference(point, r1, a1);

            MagicRender.singleframe(
                    fae_misc.getSpriteFromSheet("fae_flowers_sheet", 3,3,2),
                    pp1, new Vector2f(s1, s1), 360f*m.isq(l1), fae_misc.adjustAlpha(colorList().get(3), l1*0.3f), false, CombatEngineLayers.ABOVE_SHIPS_LAYER
            );
            MagicRender.singleframe(
                    fae_misc.getSpriteFromSheet("fae_rings_sheet", 4,4,5),
                    pp1, new Vector2f(s3, s3), 0f, fae_misc.adjustAlpha(colorList().get(1), l1*0.7f), true, CombatEngineLayers.ABOVE_SHIPS_LAYER
            );

            MagicRender.singleframe(
                    fae_misc.getSpriteFromSheet("fae_runes_sheet", 5,5,i),
                    pp1, new Vector2f(s2, s2), 0f, fae_misc.adjustAlpha(colorList().get(2), l1*0.9f), true, CombatEngineLayers.ABOVE_SHIPS_LAYER
            );

        }
    }
    private void renderCenter(Vector2f point, float level, float lc1, float shift){

        float l1 = lc1;
        float l2 = MagicAnim.smoothReturnNormalizeRange(lc1, 0, 1f);
        float size = ringRadius()*2f;
        float s1 = size*l1/2.5f;
        float s2 = size*l1/2f;
        float s3 = 1.5f*size+0.3f*size*l2;
        float s4 = 3f*size-0.3f*size*l2;
       // float s4 =
        float as = shift*360f;
        MagicRender.singleframe(
                fae_misc.getSpriteFromSheet("fae_flowers_sheet", 3,3,7),
                point, new Vector2f(s1, s1), as, fae_misc.adjustAlpha(colorList().get(3), l1*0.3f), true, CombatEngineLayers.ABOVE_SHIPS_LAYER
        );
        MagicRender.singleframe(
                fae_misc.getSpriteFromSheet("fae_flowers_sheet", 3,3,7),
                point, new Vector2f(s2, s2), -0.5f*as, fae_misc.adjustAlpha(colorList().get(3), l1*0.3f), true, CombatEngineLayers.ABOVE_SHIPS_LAYER
        );
        MagicRender.singleframe(
                fae_misc.getSpriteFromSheet("fae_rings_large_sheet", 2,2,3), point, new Vector2f(s3, s3), 0, fae_misc.adjustAlpha(colorList().get(2), l1*0.9f), true, CombatEngineLayers.ABOVE_SHIPS_LAYER
        );
        MagicRender.singleframe(
                fae_misc.getSpriteFromSheet("fae_rings_large_sheet", 2,2,0), point, new Vector2f(s4, s4), 90f, fae_misc.adjustAlpha(colorList().get(2), l1*0.3f), true, CombatEngineLayers.ABOVE_SHIPS_LAYER
        );

    }

}
