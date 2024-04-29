package bing.faesector.data;

import java.awt.Color;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.util.IntervalUtil;
import data.scripts.util.MagicRender;
import org.lazywizard.lazylib.FastTrig;
//import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lazywizard.lazylib.combat.CombatUtils;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Uncle Bing
 * Uses methods and concepts from Harmful Mechanics' Stutterwarp design without permission. Lmao.
 */
public class fae_StutterjumpMOD extends BaseShipSystemScript {

    private static final Color AFTERIMAGE_COLOR = new Color(47, 185, 189,85);
    private static final float AFTERIMAGE_THRESHOLD = 0.4f;
    public static final float MAX_TIME_MULT = 4f;

    public static final Color JITTER_COLOR = new Color(47, 185, 189, 85);
    public static final Color JITTER_UNDER_COLOR = new Color(47, 185, 189, 85);

    private IntervalUtil interval = new IntervalUtil(0.20f, 0.20f);
    private org.lazywizard.lazylib.combat.CombatUtils CombatUtils;

    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        ShipAPI ship = null;
        boolean player = false;
        CombatEngineAPI engine = Global.getCombatEngine();

        if (stats.getEntity() instanceof ShipAPI) {
            ship = (ShipAPI) stats.getEntity();
            player = ship == Global.getCombatEngine().getPlayerShip();
            id = id + "_" + ship.getId();
        } else {
            return;
        }

        float TimeMult = 1f + (MAX_TIME_MULT - 1f) * effectLevel;
        stats.getTimeMult().modifyMult(id, TimeMult);
        if (player) {
            Global.getCombatEngine().getTimeMult().modifyMult(id, (1f / TimeMult));
        } else {
            Global.getCombatEngine().getTimeMult().unmodify(id);
        }

        float driftamount = engine.getElapsedInLastFrame();

        if (state == State.IN) {

            ship.getMutableStats().getAcceleration().modifyFlat(id, 6000f);
            ship.getMutableStats().getDeceleration().modifyFlat(id, 6000f);

        } else if (state == State.ACTIVE) {

            stats.getAcceleration().unmodify(id);
            stats.getDeceleration().unmodify(id);
            stats.getHullDamageTakenMult().modifyMult(id, 0.80f);
            stats.getArmorDamageTakenMult().modifyMult(id, 0.80f);
            stats.getEmpDamageTakenMult().modifyMult(id, 0.80f);

            float speed = ship.getVelocity().length();
            if (speed <= 0.1f) {
                ship.getVelocity().set(VectorUtils.getDirectionalVector(ship.getLocation(), ship.getVelocity()));
            }
            if (speed < 900f) {
                ship.getVelocity().normalise();
                ship.getVelocity().scale(speed + driftamount * 3200f);
            }
        } else {
            float speed = ship.getVelocity().length();
            if (speed > ship.getMutableStats().getMaxSpeed().getModifiedValue()) {
                ship.getVelocity().normalise();
                ship.getVelocity().scale(speed - driftamount * 3200f);
            }
        }
        // Continuous spawning of EMP arcs
        List<CombatEntityAPI> targets = CombatUtils.getEntitiesWithinRange(
                ship.getLocation(), 900f); // Adjust range as needed

        for (CombatEntityAPI target : targets) {
            if (target instanceof ShipAPI && target.getOwner() != ship.getOwner()) {
                Global.getCombatEngine().spawnEmpArc(ship, ship.getLocation(),
                        ship, target, DamageType.ENERGY, 100f, 500f, // Adjust damage and EMP as needed
                        2f, "tachyon_lance_emp_impact", 10f,
                        JITTER_COLOR, JITTER_UNDER_COLOR);
            }
        }


        //For Afterimages
        if (!Global.getCombatEngine().isPaused()) {

            interval.advance(Global.getCombatEngine().getElapsedInLastFrame());

            if (interval.intervalElapsed()) {

                // Sprite offset fuckery - Don't you love trigonometry?
                SpriteAPI sprite = ship.getSpriteAPI();
                float offsetX = sprite.getWidth() / 2 - sprite.getCenterX();
                float offsetY = sprite.getHeight() / 2 - sprite.getCenterY();

                float trueOffsetX = (float) FastTrig.cos(Math.toRadians(ship.getFacing() - 90f)) * offsetX - (float) FastTrig.sin(Math.toRadians(ship.getFacing() - 90f)) * offsetY;
                float trueOffsetY = (float) FastTrig.sin(Math.toRadians(ship.getFacing() - 90f)) * offsetX + (float) FastTrig.cos(Math.toRadians(ship.getFacing() - 90f)) * offsetY;

                MagicRender.battlespace(
                        Global.getSettings().getSprite(ship.getHullSpec().getSpriteName()),
                        new Vector2f(ship.getLocation().getX() + trueOffsetX, ship.getLocation().getY() + trueOffsetY),
                        new Vector2f(0, 0),
                        new Vector2f(ship.getSpriteAPI().getWidth(), ship.getSpriteAPI().getHeight()),
                        new Vector2f(0, 0),
                        ship.getFacing() - 90f,
                        0f,
                        AFTERIMAGE_COLOR,
                        true,
                        0f,
                        0f,
                        0f,
                        0f,
                        0f,
                        0.1f,
                        0.1f,
                        1f,
                        CombatEngineLayers.BELOW_SHIPS_LAYER);
            }
        }
    }

    public void unapply(MutableShipStatsAPI stats, String id) {
        ShipAPI ship = null;
        boolean player = false;
        if (stats.getEntity() instanceof ShipAPI) {
            ship = (ShipAPI) stats.getEntity();
            player = ship == Global.getCombatEngine().getPlayerShip();
            id = id + "_" + ship.getId();
        } else {
            return;
        }
        Global.getCombatEngine().getTimeMult().unmodify(id);
        stats.getTimeMult().unmodify(id);

        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);

        stats.getMaxSpeed().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
    }
    public StatusData getStatusData(int index, State state, float effectLevel) {
        return null;
    }

    public float getActiveOverride(ShipAPI ship) {
        return -1;
    }

    public float getInOverride(ShipAPI ship) {
        return -1;
    }

    public float getOutOverride(ShipAPI ship) {
        return -1;
    }

    public float getRegenOverride(ShipAPI ship) {
        return -1;
    }

    public int getUsesOverride(ShipAPI ship) {
        return -1;
    }
}