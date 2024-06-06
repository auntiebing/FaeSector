package bing.faesector.data.tests;

import cmu.gui.CMUKitUI;
import cmu.plugins.RenderDebug;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;
import org.magiclib.util.MagicRender;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static bing.faesector.data.render.RenderMisc.*;
import static org.lwjgl.opengl.GL11.*;

public class fae_glRotateFTest extends BaseCombatLayeredRenderingPlugin {

    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_PLANETS;

    @Override
    public void render(CombatEngineLayers layer, ViewportAPI viewport) {
        CMUKitUI.openGLForMisc(); // gl open
        glPushMatrix();

        ShipAPI ship = Global.getCombatEngine().getPlayerShip();

        Vector2f center = new Vector2f(0, 0);
//        Vector2f center = ship.getLocation();

        List<Vector2f> boxLocs = new ArrayList<Vector2f>(
                Arrays.asList(
                        new Vector2f(center.x - 50, center.y + 50),
                        new Vector2f(center.x + 50, center.y + 50),
                        new Vector2f(center.x - 50, center.y - 50),
                        new Vector2f(center.x + 50, center.y - 50)
                )
        );


        boxLocs = worldVectorToScreenVector(boxLocs, viewport);

        center = ship.getLocation();
        float mult = viewport.getViewMult();

        Vector2f locAfterMult = new Vector2f(center.x / mult, center.y / mult);


        float angle = MathUtils.clampAngle(VectorUtils.getAngle(ship.getLocation(), ship.getMouseTarget()));

        glTranslatef(locAfterMult.x, locAfterMult.y, 0);


        glEnable(GL_BLEND);//for transparency
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        glBegin(GL_TRIANGLE_STRIP);

        Misc.setColor(Color.red);

        glVertex2f(boxLocs.get(0).x, boxLocs.get(0).y);
        glVertex2f(boxLocs.get(1).x, boxLocs.get(1).y);
        glVertex2f(boxLocs.get(2).x, boxLocs.get(2).y);
        glVertex2f(boxLocs.get(3).x, boxLocs.get(3).y);

        glEnd();
        glDisable(GL_BLEND);//for transparency

        //Global.getCombatEngine().getPlayerShip().getVelocity().set(0,0);
        //Global.getCombatEngine().getPlayerShip().getLocation().set(0,0);

        glPopMatrix();
        CMUKitUI.closeGLForMisc(); // gl open
    }

    public void ChangeRenderLayerInPlugin(CombatEngineLayers LAYER) {
        this.CURRENT_LAYER = LAYER;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public float getRenderRadius() {
        return Float.MAX_VALUE;
    }

    @Override
    public EnumSet<CombatEngineLayers> getActiveLayers() {
        return EnumSet.of(CURRENT_LAYER);
    }
}