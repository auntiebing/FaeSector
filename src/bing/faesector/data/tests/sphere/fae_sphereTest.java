package bing.faesector.data.tests.sphere;

import bing.faesector.data.Statics;
import bing.faesector.data.helpers.Helper;
import bing.faesector.data.render.RenderMisc;
import bing.faesector.data.render.shader.Shader;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.coreui.V;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

import static org.lwjgl.opengl.GL11.*;

public class fae_sphereTest extends BaseCombatLayeredRenderingPlugin {

    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_PLANETS;

    private SmoothTurning turn = new SmoothTurning().SetAcceleration(0.13f).SetChangeAmount(5f);

    @Override
    public void render(CombatEngineLayers layer, ViewportAPI viewport) {
        CMUKitUI.openGLForMisc(); // gl open

        glPushMatrix();

        ShipAPI ship = Global.getCombatEngine().getPlayerShip();

        float angle = VectorUtils.getAngle(ship.getLocation(), ship.getMouseTarget()) - 90f;

        turn.setTargetAngle(angle).advance();

        new Ball().
                setTexture(Global.getSettings().getSprite("fx", "fae_eye1")).
                setLocation(ship.getMouseTarget()).
                setRadius(100f).
                setSlices(32).
                setStacks(32).
                setRotation(Arrays.asList(
                        new Vector4f(0, 0, 1,

                                turn.currentAngle

                        ), // turn to player ship
                        new Vector4f(0, 1, 0, Statics.t * 10f)
                )).
                render(viewport);

        glPopMatrix();

        CMUKitUI.closeGLForMisc(); // gl close
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