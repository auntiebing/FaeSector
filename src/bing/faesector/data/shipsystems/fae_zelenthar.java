package bing.faesector.data.shipsystems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lazywizard.lazylib.combat.AIUtils;
import org.lwjgl.util.vector.Vector2f;
import org.magiclib.util.MagicRender;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class fae_zelenthar extends BaseShipSystemScript {

    static final float SPEED_BONUS = 400f;

    final IntervalUtil CD = new IntervalUtil(0.01f, 0.01f);

    boolean doOnce_speedUp = true;
    boolean doOnce_EndExplosion = true;

    final Map<ShipAPI.HullSize, Float> strafeMulti = new HashMap<>();

    {
        strafeMulti.put(ShipAPI.HullSize.FIGHTER, 1f);
        strafeMulti.put(ShipAPI.HullSize.FRIGATE, 1f);
        strafeMulti.put(ShipAPI.HullSize.DESTROYER, 0.75f);
        strafeMulti.put(ShipAPI.HullSize.CRUISER, 0.5f);
        strafeMulti.put(ShipAPI.HullSize.CAPITAL_SHIP, 0.25f);
    }

    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        ShipAPI ship = (ShipAPI) stats.getEntity();
        float amount = Global.getCombatEngine().getElapsedInLastFrame();
        if (Global.getCombatEngine().isPaused()) amount = 0;

        stats.getMaxSpeed().modifyFlat(id, SPEED_BONUS);

        if (state == State.IN) {
            //stats.getAcceleration().modifyFlat(id, 100f);
            //stats.getDeceleration().modifyFlat(id, 100f);
        }
        if (state == State.ACTIVE) {
            if (doOnce_speedUp) {
                Vector2f newVector = new Vector2f();
                if (ship.getEngineController().isAccelerating()) {
                    newVector.y += 1 * ship.getAcceleration();
                }
                if (ship.getEngineController().isAcceleratingBackwards() || ship.getEngineController().isDecelerating()) {
                    newVector.y -= 1 * ship.getDeceleration();
                }
                if (ship.getEngineController().isStrafingLeft()) {
                    newVector.x -= 1 * ship.getAcceleration() * strafeMulti.get(ship.getHullSize());
                }
                if (ship.getEngineController().isStrafingRight()) {
                    newVector.x += 1 * ship.getAcceleration() * strafeMulti.get(ship.getHullSize());
                }
                VectorUtils.rotate(newVector, ship.getFacing() - 90);
                Vector2f NewSpeed;
                if (VectorUtils.isZeroVector(newVector))
                    NewSpeed = (Vector2f) new Vector2f(ship.getVelocity()).normalise(newVector).scale(ship.getMaxSpeed());
                else NewSpeed = (Vector2f) newVector.normalise(newVector).scale(ship.getMaxSpeed());
                ship.getVelocity().set(NewSpeed);
                Global.getCombatEngine().addPlugin(new EMPWavePlugin(ship));
                doOnce_speedUp = false;
            }
            stats.getAcceleration().modifyFlat(id, 0f);
            stats.getDeceleration().modifyFlat(id, 0f);
            afterImage(ship);
        }
        if (state == State.OUT) {
            stats.getMaxSpeed().unmodify(id);
            stats.getMaxTurnRate().unmodify(id);

            if (ship.getVelocity().lengthSquared() > Math.pow(ship.getMaxSpeed(),2)){
                //ship.getSystem().forceState(ShipSystemAPI.SystemState.OUT, 0);
                ship.getVelocity().set(Vector2f.add(ship.getVelocity(),(Vector2f) new Vector2f(ship.getVelocity()).normalise().scale(-1 * ship.getMaxSpeed() * amount * 7), null));
            } else {
                if (doOnce_EndExplosion){
                    Global.getCombatEngine().addPlugin(new EMPWavePlugin(ship));
                    doOnce_EndExplosion = false;
                }
            }

        }

        if (stats.getEntity() instanceof ShipAPI) {
            ship.getEngineController().extendFlame(this, 0.5f * effectLevel, 0.5f * effectLevel, 0.25f * effectLevel);
        }
    }

    public void afterImage(ShipAPI ship) {
        if (Global.getCombatEngine().isPaused()) return;

        Color color = Misc.setAlpha(Color.CYAN, 50);

        float amount = Global.getCombatEngine().getElapsedInLastFrame();
        if (ship.getSystem().isActive()) {
            CD.advance(amount);
            if (CD.intervalElapsed()) {
                if (!MagicRender.screenCheck(1.5f, ship.getLocation())) return;


                SpriteAPI shipSprite = Global.getSettings().getSprite(ship.getHullSpec().getSpriteName());
                MagicRender.battlespace(
                        shipSprite,
                        new Vector2f(ship.getLocation()),
                        new Vector2f(),
                        new Vector2f(ship.getSpriteAPI().getWidth(), ship.getSpriteAPI().getHeight()),
                        new Vector2f(),
                        ship.getFacing() - 90,
                        0,
                        color,
                        true,
                        0f,
                        0f,
                        0f,
                        0,
                        0,
                        0.1f,
                        0f,
                        0.2f,
                        CombatEngineLayers.UNDER_SHIPS_LAYER);
            }
        }
    }

    public boolean isUsable(ShipSystemAPI system, ShipAPI ship) {
        Vector2f newVector = new Vector2f();
        if (ship.getEngineController().isAccelerating()) {
            newVector.y += 1 * ship.getAcceleration();
        }
        if (ship.getEngineController().isAcceleratingBackwards() || ship.getEngineController().isDecelerating()) {
            newVector.y -= 1 * ship.getDeceleration();
        }
        if (ship.getEngineController().isStrafingLeft()) {
            newVector.x -= 1 * ship.getAcceleration() * strafeMulti.get(ship.getHullSize());
        }
        if (ship.getEngineController().isStrafingRight()) {
            newVector.x += 1 * ship.getAcceleration() * strafeMulti.get(ship.getHullSize());
        }
        return !(VectorUtils.isZeroVector(newVector) && VectorUtils.isZeroVector(ship.getVelocity()));
    }

    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        doOnce_speedUp = true;
        doOnce_EndExplosion = true;
    }

    public StatusData getStatusData(int index, State state, float effectLevel) {
        if (index == 0) {
            if (state == State.IN || state == State.ACTIVE) {
                return new StatusData("+" + (int) SPEED_BONUS + " top speed", false);
            }
        }
        return null;
    }

    public static class EMPWavePlugin implements EveryFrameCombatPlugin {

        final float waveRange = 500f;
        final float waveSpeed = waveRange / 0.5f;

        float currRange = 0;

        final List<CombatEntityAPI> alreadyHit = new ArrayList<>();

        final IntervalUtil timer = new IntervalUtil(0.05f,0.05f);
        final Vector2f point;

        final ShipAPI ship;

        public EMPWavePlugin (ShipAPI ship){
            this.ship = ship;
            point = new Vector2f(ship.getLocation());
        }

        public void processInputPreCoreControls(float amount, java.util.List<InputEventAPI> events) {

        }

        public void advance(float amount, java.util.List<InputEventAPI> events) {
            if (Global.getCombatEngine().isPaused()) return;
            currRange +=  waveSpeed * amount;
            timer.advance(amount);
            if (timer.intervalElapsed()) {
                for (CombatEntityAPI target : AIUtils.getNearbyEnemies(ship, currRange)) {
                    if (alreadyHit.contains(target)) continue;
                    Vector2f arcLoc = Vector2f.add(point, (Vector2f) Vector2f.sub(target.getLocation(), point, null).normalise(null).scale(currRange), null);
                    for (int i = 0; i < 5; i++) {
                        EmpArcEntityAPI arc = Global.getCombatEngine().spawnEmpArc(ship,
                                arcLoc,
                                null,
                                target,
                                DamageType.ENERGY,
                                100,
                                500,
                                999999,
                                null,
                                10,
                                new Color(0, 202, 238,255),
                                new Color(35, 96, 204,255));
                        arc.setSingleFlickerMode();
                    }
                    alreadyHit.add(target);
                }
                for (CombatEntityAPI target : AIUtils.getNearbyEnemies(ship, currRange)) {
                    if (target instanceof MissileAPI && !alreadyHit.contains(target)) {
                        MissileAPI missile = (MissileAPI) target;
                        if (!missile.isFizzling() && !missile.isFading() && !missile.isExpired()) {
                            Global.getCombatEngine().applyDamage(missile, point, 10f, DamageType.ENERGY, 0f, false, false, ship);
                            Global.getCombatEngine().spawnEmpArc(ship, point, null, missile, DamageType.ENERGY, 100, 500, 999999, null, 10,
                                    new Color(0, 202, 238, 255), new Color(35, 96, 204, 255));
                            alreadyHit.add(missile);

                        }
                    }
                }
                int numArcs = Math.round(currRange / 10);
                if (numArcs >= 10) {
                    float anglePerStep = 360f / numArcs;
                    for (int i = 0; i < numArcs; i++) {
                        if (Math.random() <= 0.5f) continue;
                        Vector2f locStart = Vector2f.add(Vector2f.add(point, (Vector2f) Misc.getUnitVectorAtDegreeAngle(anglePerStep * (i + MathUtils.getRandomNumberInRange(-0.15f,0.15f))).scale(currRange + MathUtils.getRandomNumberInRange(-5,5)), null), new Vector2f(MathUtils.getRandomNumberInRange(-25f,25f),MathUtils.getRandomNumberInRange(-25f,25)),null);
                        Vector2f locEnd = Vector2f.add(Vector2f.add(point, (Vector2f) Misc.getUnitVectorAtDegreeAngle(anglePerStep * (i + 1.5f + MathUtils.getRandomNumberInRange(-0.15f,0.15f))).scale(currRange + MathUtils.getRandomNumberInRange(-5,5)), null), new Vector2f(MathUtils.getRandomNumberInRange(-25f,25f),MathUtils.getRandomNumberInRange(-25f,25)),null);
                        EmpArcEntityAPI arc = Global.getCombatEngine().spawnEmpArcVisual(locStart,
                                null,
                                locEnd,
                                null,
                                7,
                                new Color(0, 202, 238, 255),
                                new Color(35, 96, 204, 255));
                        arc.setSingleFlickerMode();
                    }

                }
            }
            if (currRange >= waveRange) Global.getCombatEngine().removePlugin(this);
        }

        @Override
        public void renderInWorldCoords(ViewportAPI viewport) {

        }

        @Override
        public void renderInUICoords(ViewportAPI viewport) {

        }

        @Override
        public void init(CombatEngineAPI engine) {

        }
    }
}








