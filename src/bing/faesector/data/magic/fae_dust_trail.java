package bing.faesector.data.magic;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.WeightedRandomPicker;
import bing.faesector.data.fae_misc;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import java.awt.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class fae_dust_trail extends BaseEveryFrameCombatPlugin {
    private final CombatEngineAPI engine;
    private DamagingProjectileAPI proj;

    private final float range;
    private final float dur;
    private final String loopSound;
    private final List<Color> colors;
    private final float interval;
    private final float vmult;
    private final float rd;
    private int num;
    private IntervalUtil si = new IntervalUtil(0,0);

    public void advance(float amount, List<InputEventAPI> events) {
        if(engine.isPaused())return;
        si.advance(amount);

        if(!fae_misc.isProjValid(proj)){
            engine.removePlugin(this);
        }
        if(loopSound!=null && !loopSound.equals("")){

            Global.getSoundPlayer().playLoop(loopSound,proj,1f,1f,proj.getLocation(), proj.getVelocity());
        }
        if(si.intervalElapsed()){
            si=new IntervalUtil(interval/2f, interval);
            Vector2f loc = proj.getLocation();
            Vector2f v1 = proj.getVelocity();
            float av = VectorUtils.getAngle(new Vector2f(), v1);
            for(int i=0; i<num; i++){
                Vector2f p1= MathUtils.getRandomPointInCircle(loc, range);
                float s1 = vmult*MathUtils.getDistance(new Vector2f(), v1);
                v1 = MathUtils.getPointOnCircumference(new Vector2f(), s1, av);
                if(rd>0){
                    v1 = MathUtils.getPointOnCircumference(v1, MathUtils.getRandomNumberInRange(-rd, rd), av+90f);
                }
                engine.addSmoothParticle(p1, v1, MathUtils.getRandomNumberInRange(5f, 10f), 1f,0.3f, dur,randomColor());
            }
        }
    }

    private Color randomColor(){
        WeightedRandomPicker<Color> picker = new WeightedRandomPicker<>();
        picker.addAll(colors);
        return picker.pick();
    }

    public fae_dust_trail(DamagingProjectileAPI proj, float range, float dur, float interval, String loopSound, List<Color> colors, float projVelocityMult, float randomDrift, int numPerSpawn) {
        this.engine = Global.getCombatEngine();
        this.range = range;
        this.dur = dur;
        this.loopSound = loopSound;
        this.colors = colors;
        engine.addPlugin(this);
        this.proj=proj;
        this.interval=interval;
        this.vmult=projVelocityMult;
        this.rd=randomDrift;
        this.num=numPerSpawn;
    }


}
