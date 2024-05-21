package bing.faesector.data.tests;

import bing.faesector.data.render.renderClassesFolder.RingData;
import bing.faesector.data.render.renderFunctions.RingRenderer;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;

import java.util.EnumSet;

import static bing.faesector.data.render.RenderMisc.*;
import static bing.faesector.data.render.renderFunctions.RingRenderer.*;

public class fae_RingRenderTest extends BaseCombatLayeredRenderingPlugin {

    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_PLANETS;

    private Vector2f sourceLocation;
    private float angle;
    private float OvalX;
    private float OvalY;

    public fae_RingRenderTest(Vector2f sourceLocation, float angle, float OvalX, float OvalY) {
        this.sourceLocation = sourceLocation;
        this.angle = angle;
        this.OvalX = OvalX;
        this.OvalY = OvalY;
    }

    @Override
    public void render(CombatEngineLayers layer, ViewportAPI viewport) {
        CMUKitUI.openGLForMisc(); // gl open

        RingData ring = new RingData();

        ring.center = sourceLocation;
        ring.width = 50f;
        ring.shape = new Vector2f(OvalX, OvalY);

        DrawRing(ring, viewport);

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
