package bing.faesector.data.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lwjgl.util.vector.Vector2f;
import bing.faesector.data.fae_misc;
import bing.faesector.data.magic.fae_calath_cloud;

import java.util.List;

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
            float dur = 10f;
            Global.getSoundPlayer().playSound("fae_torpedo_impact", 1f, 1f, missile.getLocation(), new Vector2f());
            float dmg = 0.05f*missile.getDamageAmount()/dur;
            float emp = 0.05f*missile.getEmpAmount()/dur;
            new fae_calath_cloud(missile.getLocation(), 800f, dur, 0.1f, 0.2f, dmg, emp, missile.getDamageType(), missile.getSource());

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
