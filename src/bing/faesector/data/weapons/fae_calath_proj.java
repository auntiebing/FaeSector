package bing.faesector.data.weapons;

import bing.faesector.data.helpers.Logger;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;
import bing.faesector.data.fae_misc;
import bing.faesector.data.magic.fae_calath_cloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class fae_calath_proj extends BaseEveryFrameCombatPlugin {
    private final CombatEngineAPI engine;
    private MissileAPI missile;


    private static float range(){
        return 500f;
    }
    private static float explRange(){
        return 250f;
    }
    private static float interval(){
        return 0.5f;
    }
    private IntervalUtil sparkInterval = new IntervalUtil(interval(),interval());

    public void advance(float amount, List<InputEventAPI> events) {
        if(engine.isPaused())return;
        sparkInterval.advance(amount);

        if(!fae_misc.isProjValid(missile)){
            Global.getSoundPlayer().playSound("fae_torpedo_impact", 1f, 1f, missile.getLocation(), new Vector2f());
            final float dur = 10f;
            final float dmg = 0.2f*missile.getDamageAmount()/dur;
            final float emp = 0.2f*missile.getEmpAmount()/dur;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    new fae_calath_cloud(missile.getLocation(), 800f, dur, 0.1f, 0.2f, dmg, emp, missile.getDamageType(), missile.getSource());
                }
            }, 900);
            engine.removePlugin(this);
        }

        Vector2f loc = missile.getLocation();
    }

    public fae_calath_proj(MissileAPI missile) {
        engine = Global.getCombatEngine();
        engine.addPlugin(this);
        this.missile=missile;
    }

}
