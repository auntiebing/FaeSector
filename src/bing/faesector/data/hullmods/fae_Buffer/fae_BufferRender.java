package bing.faesector.data.hullmods.fae_Buffer;

import bing.faesector.data.Statics;
import bing.faesector.data.helpers.ColorHelper;
import bing.faesector.data.render.renderClassesFolder.Ring;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ViewportAPI;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

import static java.lang.Math.*;

public class fae_BufferRender extends BaseCombatLayeredRenderingPlugin {

    public fae_BufferRender(ShipAPI ship, float ringRadius) {
        this.ship = ship;
        this.ringRadius = ringRadius;
    }

    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_PLANETS;
    private ShipAPI ship = null;
    private float ringRadius = 0f;

    @Override
    public void render(CombatEngineLayers layer, ViewportAPI viewport) {
        CMUKitUI.openGLForMisc(); // gl open

        Ring buffRing = new Ring();

        buffRing.filled = true;
        buffRing.shape = new Vector2f(ringRadius, ringRadius);
        buffRing.width = 20f;
        buffRing.segmentAmount = 36;
        buffRing.center = ship.getLocation();
        buffRing.angle = ship.getFacing();
        float alpha = (float) (70 * ((sin(Statics.t) > 0) ? sin(Statics.t) : -sin(Statics.t)) + 30);
        buffRing.setColor(
                new ArrayList<Color>(Arrays.asList(
                        ColorHelper.setAlpha(Global.getSettings().getColor("fae_color_teal"), (short) 0),
                        ColorHelper.setAlpha(Global.getSettings().getColor("fae_color_teal"), (short) alpha)
                )),
                new ArrayList<Color>(Arrays.asList(
                        ColorHelper.setAlpha(Global.getSettings().getColor("fae_color_teal"), (short) alpha),
                        ColorHelper.setAlpha(Global.getSettings().getColor("fae_color_teal"), (short) 0)
                ))
        );

        buffRing.DrawRing(viewport);

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
