package bing.faesector.data.weapons.ball;

import bing.faesector.data.helpers.EMPArcHelper;
import bing.faesector.data.helpers.VectorHelper;
import bing.faesector.data.helpers.classes.fae_DamageBaseClass;
import bing.faesector.data.render.RenderMisc;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.listeners.AdvanceableListener;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lazywizard.lazylib.LazyLib;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lazywizard.lazylib.combat.CombatUtils;
import org.lazywizard.lazylib.combat.WeaponUtils;
import org.lwjgl.util.vector.Vector2f;
import org.magiclib.util.MagicRender;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class fae_ball_listener implements AdvanceableListener {

    private List<projPastLoc> projectiles = new ArrayList<>();
    private IntervalUtil interval = new IntervalUtil(1, 1);
    private float aoe = 200f;
    private byte arcAmount;

    @Override
    public void advance(float amount) {

        interval.advance(amount);
        if (!interval.intervalElapsed()) return;

        for (projPastLoc ball : projectiles) {
            EMPArcHelper.SpawnEMPArcVisual(ball.proj.getLocation(), ball.lastLoc, 1, Color.CYAN, Color.PINK);

            ball.lastLoc = new Vector2f(ball.proj.getLocation());

            List<DamagingProjectileAPI> projsToEMP = new ArrayList<>();

            for (DamagingProjectileAPI projToKill : CombatUtils.getMissilesWithinRange(ball.proj.getLocation(), aoe)) {
                if (projToKill.getOwner() == ball.proj.getOwner()) continue;

                projsToEMP.add(projToKill);
            }

            if (projsToEMP.isEmpty()) continue;

            Collections.shuffle(projsToEMP);

            for (int i = 0; i < arcAmount; i++) {

                EMPArcHelper.SpawnDamagingEMPArc(
                        ball.proj.getSource(),
                        ball.proj.getLocation(),
                        projsToEMP.get(i),
                        new fae_DamageBaseClass(
                                20f,
                                300f,
                                DamageType.ENERGY
                        ),
                        200,
                        "",
                        3,
                        Color.CYAN,
                        Color.PINK
                );

            }

        }



        List<projPastLoc> bufferProjList = new ArrayList<>();
        for (projPastLoc projectile : projectiles) {
            if (!projectile.proj.isExpired()) {
                bufferProjList.add(projectile);
            }
        }

        projectiles = bufferProjList;
    }

    public void AddBall(projPastLoc ball) {
        projectiles.add(ball);
    }


}

class projPastLoc {
    public projPastLoc(DamagingProjectileAPI proj, Vector2f lastLoc) {
        this.proj = proj;
        this.lastLoc = lastLoc;
    }

    public DamagingProjectileAPI proj;
    public Vector2f lastLoc;

}
