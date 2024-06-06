package bing.faesector.data.tests;

import bing.faesector.data.Statics;
import bing.faesector.data.helpers.PerlinNoise;
import bing.faesector.data.render.RenderMisc;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;
import penner.easing.Circ;
import penner.easing.Sine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static bing.faesector.data.render.RenderMisc.*;

public class fae_OvalRiftRenderer extends BaseCombatLayeredRenderingPlugin {

    private SpriteAPI backgroundOfRift = Global.getSettings().getSprite("backgrounds", "defaultHyperBackground");
    private Vector2f sourceLocation;
    private float angle;
    private float OvalX;
    private float OvalY;
    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_PLANETS;
    private PerlinNoise noise = new PerlinNoise(Statics.random.nextInt(Integer.MAX_VALUE));
    private int time = 0;
    private float beginning = 0;
    private float change = 200;
    private float duration = 200;

    public fae_OvalRiftRenderer(Vector2f sourceLocation, float angle, float OvalX, float OvalY) {
        this.sourceLocation = sourceLocation;
        this.angle = angle;
        this.OvalX = OvalX;
        this.OvalY = OvalY;
        backgroundOfRift.setAlphaMult(500f);
    }

    public void render(CombatEngineLayers layer, ViewportAPI viewport) {
        CMUKitUI.openGLForMisc(); // gl open

        float sizeMultX = Sine.easeOut(time, beginning, change, duration) / duration;
        float sizeMultY = Circ.easeInOut(time, beginning, change, duration) / duration;

        if (time < duration) {
            if (Global.getCombatEngine().isPaused()) time--;
            time++;
        }

//        drawTestSquare(sourceLocation, viewport, backgroundOfRift);

        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        backgroundOfRift.bindTexture();
        glEnable(GL_BLEND);//for transparency
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);//https://www.codeproject.com/Questions/512386/TransparentplustextureplusinplusOpenGL
        glEnable(GL_TEXTURE_2D);

        glBegin(GL_TRIANGLE_FAN);

        RenderMisc.SetColor(backgroundOfRift.getColor());

        Vector2f locF = worldVectorToScreenVector(sourceLocation, viewport);
        glTexCoord2f(0.5f, 0.5f);
        glVertex2f(locF.x, locF.y);

        for (int i = -91; i < 290; i += 1) {

            float rad = i * (3.1415f / 180f);

            float xLocF = (float) (Math.cos(rad + noise.noise2(i + Statics.t, i + Statics.t)));
            float yLocF = (float) (Math.sin(rad));

            float xLoc = (xLocF * OvalX) * sizeMultX;
            float yLoc = (yLocF * OvalY) * sizeMultY;

            Vector2f loc = new Vector2f(xLoc + sourceLocation.x, yLoc + sourceLocation.y);

            loc = worldVectorToScreenVector(loc, viewport);

            glTexCoord2f(xLocF / 2 + 0.5f, yLocF / 2 + 0.5f);
            glVertex2f(loc.x, loc.y);

        }

        glEnd();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);


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
