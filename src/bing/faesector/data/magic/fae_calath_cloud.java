package bing.faesector.data.magic;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.CombatUtils;
import java.awt.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;
import bing.faesector.data.fae_misc;

public class fae_calath_cloud extends BaseEveryFrameCombatPlugin {
    public final Vector2f point;
    public final float size;
    public final float duration;
    public final float in;
    public final float out;
    private MissileAPI threat;
    private String id = "faesector_calathDebuff";
    private List<ShipAPI> affectedShips = new ArrayList<>();
    private float effectLevel(Vector2f loc){
        float dist = MathUtils.getDistance(point, loc);
        if(dist>size)return 0f;
        return 0.5f+0.5f*(1f-dist/size);
    }


    private IntervalUtil flashInterval = new IntervalUtil(0,0);
    private IntervalUtil cloudInterval = new IntervalUtil(0,0);
    private final CombatEngineAPI engine;

    private float timer;

    private final float dps;
    private final float emp;
    private final DamageType damageType;
    private final ShipAPI source;

    private final float getTimeLevel(){
        float l1 = 1f;
        float fr = timer/duration;
        if(fr<in)l1=fr/in;
        if(fr>(1-out)){
            l1=(1-fr)/out;
        }
        return l1;
    }

    public void advance(float amount, List<InputEventAPI> events) {
        if(engine.isPaused())return;
        timer+=amount;
        flashInterval.advance(amount);
        cloudInterval.advance(amount);
        if(threat == null || !fae_misc.isProjValid(threat)){

            //engine.removeEntity(threat);
            threat =(MissileAPI) engine.spawnProjectile( null, null, "fae_Z_threat", point, 0, new Vector2f());
            threat.setMineExplosionRange(size);
        }

        Global.getSoundPlayer().playLoop("fae_cloud_loop", threat, 1f, getTimeLevel(), point, new Vector2f());

        float r1 = 700f;
        float r2 = size;
        if(cloudInterval.intervalElapsed()){
            Color col= fae_misc.getRandomColor();
            cloudInterval=new IntervalUtil(0.01f, 0.05f);

            float dur= MathUtils.getRandomNumberInRange(1.5f, 3.5f);
            Vector2f p1 = MathUtils.getRandomPointInCircle(point, r2);
            float r0 = MathUtils.getDistance(point, p1);
            engine.addNebulaParticle(p1, new Vector2f(), r1, 2f, 0.3f, 0.1f, dur, fae_misc.adjustAlpha(col,0.1f ) );

        }
        if(flashInterval.intervalElapsed()){
            flashInterval=new IntervalUtil(0.01f, 0.02f);
            for(int i=0; i<3; i++){
                Vector2f p1 = MathUtils.getRandomPointInCircle(point,size);
                engine.addHitParticle(p1, new Vector2f(), 10f, 1f, 0f, 0.1f, fae_misc.getRandomColor());
            }
        }
        if(!affectedShips.isEmpty()){
            for(ShipAPI s: new ArrayList<>(affectedShips)){
                if(s.getCollisionClass()!=CollisionClass.SHIP
                ||s.getCollisionClass()==CollisionClass.FIGHTER)
                    continue;
                if(s.getCollisionRadius()<=0)continue;
                if(s.getExactBounds()==null)continue;
                if(MathUtils.getDistance(s.getLocation(), point)<size){
                    apply(s, amount);

                    if(s==Global.getCombatEngine().getPlayerShip()){
                        String title = fae_misc.str("calath_aoe_title");
                        String out = fae_misc.str("calath_aoe_desc");
                        Global.getCombatEngine().maintainStatusForPlayerShip(id, "graphics/icons/tactical/nebula_slowdown.png",title,out,true);
                    }
                }else{
                    unapply(s);
                }
            }
        }


        if(timer>=duration){
            for(ShipAPI s: new ArrayList<>(affectedShips)){
                unapply(s);
                //affectedShips.remove(s);
            }
            engine.removeEntity(threat);
            engine.removePlugin(this);
        }

        affectedShips.addAll(new ArrayList<>(CombatUtils.getShipsWithinRange(point, size)));
    }

    private void apply(ShipAPI ship, float amount){

        float level = getTimeLevel()*effectLevel(ship.getLocation());

        engine.applyDamage(ship, fae_misc.getRandomPointOnShip(ship), dps*amount*level, damageType, emp*amount*level, true, false, source, false);
        MutableShipStatsAPI stats = ship.getMutableStats();
        stats.getMaxSpeed().modifyMult(id, 1f-level);

        ship.setJitter(this, fae_misc.adjustAlpha(fae_misc.getRandomColor(), level), level, 2, 5f, 5f);
    }
    private void unapply(ShipAPI ship){
        MutableShipStatsAPI stats = ship.getMutableStats();
        stats.getMaxSpeed().unmodify(id);
    }

    public fae_calath_cloud(Vector2f point, float size, float duration, float in, float out, float dps, float emp, DamageType damageType, ShipAPI source) {
        this.point = point;
        this.size = size;
        this.duration = duration;
        this.in = in;
        this.out = out;
        this.dps = dps;
        this.emp = emp;
        this.damageType = damageType;
        this.source = source;

        this.engine = Global.getCombatEngine();
        engine.addPlugin(this);
        this.timer=0f;

        this.threat =null;
    }

}
