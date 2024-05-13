package bing.faesector.data.tests;

import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;

import java.util.EnumSet;

import static bing.faesector.data.render.at_RendererHelper.worldVectorToScreenVector;
import static org.lwjgl.opengl.GL11.*;

public class fae_OvalRiftRenderer extends BaseCombatLayeredRenderingPlugin {

    private SpriteAPI backgroundOfRift = Global.getSettings().getSprite("backgrounds", "defaultHyperBackground");
    private Vector2f sourceLocation;
    private float angle;
    private float OvalX;
    private float OvalY;
    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_PLANETS;

    public fae_OvalRiftRenderer(Vector2f sourceLocation, float angle, float OvalX, float OvalY) {
        this.sourceLocation = sourceLocation;
        this.angle = angle;
        this.OvalX = OvalX;
        this.OvalY = OvalY;
    }

    public void render(CombatEngineLayers layer, ViewportAPI viewport) {
        CMUKitUI.openGLForMisc(); // gl open

        glEnable(GL_BLEND);//for transparency
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);//https://www.codeproject.com/Questions/512386/TransparentplustextureplusinplusOpenGL
        backgroundOfRift.bindTexture();
        glBegin(GL_TRIANGLE_FAN);

        Vector2f locF = worldVectorToScreenVector(sourceLocation, viewport);
        glTexCoord2f(locF.x / 1024f, locF.y / 1024f);
        glVertex2f(locF.x, locF.y);

        for (int i = -91; i < 270; i++) {


            float rad = i * (3.1415f / 180f);
            float xLoc = (float) (Math.cos(rad) * OvalX);
            float yLoc = (float) (Math.sin(rad) * OvalY);
            Vector2f loc = new Vector2f(xLoc + sourceLocation.x, yLoc + sourceLocation.y);

            loc = worldVectorToScreenVector(loc, viewport);
//            loc = at_RendererHelper.screenVectorToWorldVector(loc, viewport);


            glTexCoord2f(loc.x / 1024f, loc.y / 1024f);
            glVertex2f(loc.x, loc.y);


        }

        glEnd();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);

//        drawTestSquare(sourceLocation, viewport, backgroundOfRift);

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
