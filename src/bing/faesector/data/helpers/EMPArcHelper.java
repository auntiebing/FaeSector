package bing.faesector.data.helpers;


import bing.faesector.data.helpers.classes.fae_DamageBaseClass;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.CombatUtils;
import org.lwjgl.util.vector.Vector2f;

import java.util.Map;

import static bing.faesector.data.helpers.BoundsHelper.GetRandomBoundLocation;


public class EMPArcHelper {

    /**
     * spawns visual arc </br>
     *
     * @param from      the arcs source location
     * @param to        the arcs end location
     * @param thickness thiccness of the arc
     * @param fringe    fringe color
     * @param core      core color
     * @return if the proccess completed successfully returns true, false othervise. so instead of crashing it just, doesnt work
     */
    public static boolean SpawnEMPArcVisual(Vector2f from, Vector2f to, float thickness, java.awt.Color fringe, java.awt.Color core) {
        try {
            Global.getCombatEngine().spawnEmpArcVisual(
                    from,
                    CombatEntityAPIForEMPArcVisaul(from),
                    to,
                    CombatEntityAPIForEMPArcVisaul(to),
                    thickness,
                    fringe,
                    core
            );
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * spawns damaging arc </br>
     *
     * @param DamageSource    the damages source, ShipAPI
     * @param from            the arcs source location
     * @param target          target entitty
     * @param DamageBaseClass DamageBaseClass only need damage type emp and damage
     * @param range           range of the arc
     * @param ImpactSoundID   id of the sound when the arc hits
     * @param thiccness       thiccness of the arc
     * @param fringe          fringe color
     * @param core            core color
     * @return if the proccess completed successfully returns true, false othervise. so instead of crashing it just, doesnt work
     */
    public static boolean SpawnDamagingEMPArc(ShipAPI DamageSource, Vector2f from, CombatEntityAPI target, fae_DamageBaseClass DamageBaseClass, float range, String ImpactSoundID, float thiccness, java.awt.Color fringe, java.awt.Color core) {

        try {
            Global.getCombatEngine().spawnEmpArc(
                    DamageSource,
                    from,
                    CombatEntityAPIForEMPArcVisaul(from),
                    target,
                    DamageBaseClass.DamageType,
                    DamageBaseClass.Damage,
                    DamageBaseClass.EMPDamage,
                    range,
                    ImpactSoundID,
                    thiccness,
                    fringe,
                    core
            );
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * you dont need to use it but if you do this just gives and combatentittyapi for emp arc visual that (probably) wont crash as long as you use in emp arc visual
     */
    private static CombatEntityAPI CombatEntityAPIForEMPArcVisaul(final Vector2f location) {
        return new CombatEntityAPI() {
            @Override
            public Vector2f getLocation() {
                return location;
            }

            @Override
            public Vector2f getVelocity() {
                return null;
            }

            @Override
            public float getFacing() {
                return 0;
            }

            @Override
            public void setFacing(float facing) {

            }

            @Override
            public float getAngularVelocity() {
                return 0;
            }

            @Override
            public void setAngularVelocity(float angVel) {

            }

            @Override
            public int getOwner() {
                return 0;
            }

            @Override
            public void setOwner(int owner) {

            }

            @Override
            public float getCollisionRadius() {
                return 1;
            }

            @Override
            public CollisionClass getCollisionClass() {
                return CollisionClass.NONE;
            }

            @Override
            public void setCollisionClass(CollisionClass collisionClass) {

            }

            @Override
            public float getMass() {
                return 0;
            }

            @Override
            public void setMass(float mass) {

            }

            @Override
            public BoundsAPI getExactBounds() {
                return null;
            }

            @Override
            public ShieldAPI getShield() {
                return null;
            }

            @Override
            public float getHullLevel() {
                return 0;
            }

            @Override
            public float getHitpoints() {
                return 0;
            }

            @Override
            public float getMaxHitpoints() {
                return 0;
            }

            @Override
            public void setCollisionRadius(float radius) {

            }

            @Override
            public Object getAI() {
                return null;
            }

            @Override
            public boolean isExpired() {
                return false;
            }

            @Override
            public void setCustomData(String key, Object data) {

            }

            @Override
            public void removeCustomData(String key) {

            }

            @Override
            public Map<String, Object> getCustomData() {
                return null;
            }

            @Override
            public void setHitpoints(float hitpoints) {

            }
        };
    }

    /**
     * spawns an arc between 2 randomly chosen bounds, if it selects the same ones it might not spawn an arc </br>
     *
     * @param ship      the ship
     * @param thiccness thiccness of the arc
     * @param fringe    fringe color
     * @param core      core color
     * @return if the proccess completed successfully returns true, false othervise. so instead of crashing it just, doesnt work
     */
    public static boolean SpawnEMPArcsBetweenShipsBounds(ShipAPI ship, float thiccness, java.awt.Color fringe, java.awt.Color core) {
        try {
            Vector2f from = GetRandomBoundLocation(ship);

            Vector2f to = GetRandomBoundLocation(ship);

            SpawnEMPArcVisual(from, to, thiccness, fringe, core);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * spawns an arc line, (un)intentionally it stops when it hits an obsteckle</br>
     *
     * @param from      from
     * @param angle     angle
     * @param lenght    lenght of the line
     * @param space     space between arcs
     * @param thiccness thiccness of the arc
     * @param fringe    fringe color
     * @param core      core color
     * @return if the proccess completed successfully returns true, false othervise. so instead of crashing it just, doesnt work
     */
    public static boolean SpawnEMPArcLineVisual(Vector2f from, float angle, int lenght, float thiccness, float space, java.awt.Color fringe, java.awt.Color core) {
        try {
            Vector2f to = new Vector2f();

            for (int i = 0; i < lenght; i++) {
                if (i % 2 == 0) {
                    to = MathUtils.getPointOnCircumference(from, space, angle);
                    SpawnEMPArcVisual(from, to, thiccness, fringe, core);
                } else {
                    from = MathUtils.getPointOnCircumference(to, space, angle);
                    SpawnEMPArcVisual(to, from, thiccness, fringe, core);
                }
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * spawns damaging arc line </br>
     * DONT PLAY WİTH THİS İT GİVES WEİRD ERRORS THAT İ DONT KNOW HOW TO SOLVE</br>
     *
     * @param from       from
     * @param angle      angle
     * @param lenght     lenght of the line
     * @param thiccness  thiccness of the arc
     * @param space      space between arcs
     * @param DamageInfo DEA_DamageBaseClass only need explosion spec
     * @param source     the damages source, ShipAPI
     * @param fringe     fringe color
     * @param core       core color
     * @return if the proccess completed successfully returns true, false othervise. so instead of crashing it just, doesnt work
     */
    public static boolean SpawnEMPArcLineDamaging(Vector2f from, float angle, int lenght, float thiccness, float space, fae_DamageBaseClass DamageInfo, ShipAPI source, java.awt.Color fringe, java.awt.Color core) {
        try {
            Vector2f to = new Vector2f();

            for (int i = 0; i < lenght / space; i++) {

                boolean IsNearSomething = false;

                if (i % 2 == 0) {
                    to = MathUtils.getPointOnCircumference(from, space, angle);
                    SpawnEMPArcVisual(from, to, thiccness, fringe, core);

                    for (CombatEntityAPI s : CombatUtils.getEntitiesWithinRange(to, space)) {//those are for checking if they are near anything but it lags more than without it rn so
                        if (s instanceof ShipAPI) {
                            IsNearSomething = true;
                            break;
                        }
                    }

                    if (IsNearSomething) {
//                    MagicFakeBeam.spawnFakeBeam(
//                            Global.getCombatEngine(),
//                            to,
//                            50f,
//                            0f,
//                            5f,
//                            1f,
//                            1f,
//                            5f,
//                            transparent,
//                            transparent,
//                            DamageInfo.Damage,
//                            DamageInfo.DamageType,
//                            DamageInfo.EMPDamage,
//                            source
//                    );
                        Global.getCombatEngine().spawnDamagingExplosion(DamageInfo.DamagingExplosionSpec, source, to, false);
                    }
                } else {
                    from = MathUtils.getPointOnCircumference(to, space, angle);
                    SpawnEMPArcVisual(to, from, thiccness, fringe, core);
                    for (CombatEntityAPI s : CombatUtils.getEntitiesWithinRange(from, space)) {
                        if (s instanceof ShipAPI) {
                            IsNearSomething = true;
                            break;
                        }
                    }

                    if (IsNearSomething) {
//                    MagicFakeBeam.spawnFakeBeam(
//                            Global.getCombatEngine(),
//                            from,
//                            1f,
//                            0f,
//                            1f,
//                            1f,
//                            1f,
//                            1f,
//                            transparent,
//                            transparent,
//                            DamageInfo.Damage,
//                            DamageInfo.DamageType,
//                            DamageInfo.EMPDamage,
//                            source
//                    );

                        Global.getCombatEngine().spawnDamagingExplosion(DamageInfo.DamagingExplosionSpec, source, from, false);

                    }
                }
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
