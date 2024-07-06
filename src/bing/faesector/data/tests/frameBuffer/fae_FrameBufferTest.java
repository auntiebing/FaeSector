package bing.faesector.data.tests.frameBuffer;

import bing.faesector.data.Statics;
import bing.faesector.data.helpers.Logger;
import bing.faesector.data.render.RenderMisc;
import bing.faesector.data.render.shader.Shader;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.dark.shaders.util.ShaderLib;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static bing.faesector.data.render.RenderMisc.screenVectorToWorldVector;
import static bing.faesector.data.render.RenderMisc.worldVectorToScreenVector;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;


public class fae_FrameBufferTest extends BaseCombatLayeredRenderingPlugin {

    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_PLANETS;

    private Shader shader = new Shader().loadVertexShader("data/gfx/default.vert").loadFragShader("data/gfx/FBMAlphaMask/FBMAlphaMask.frag").link();

    @Override
    public void render(CombatEngineLayers layer, ViewportAPI viewport) { // https://learnopengl.com/Advanced-OpenGL/Framebuffers
        CMUKitUI.openGLForMisc(); // gl open

        glPushMatrix();

        ShipAPI ship = Global.getCombatEngine().getPlayerShip();

        Vector2f center = ship.getMouseTarget();

        Vector2f shape = new Vector2f(Global.getSettings().getScreenWidth(), Global.getSettings().getScreenHeight());

        float mult = viewport.getViewMult();

//        Vector2f shaderShape = new Vector2f(2048, 1024);
        Vector2f shaderShape = new Vector2f(shape);

        List<Vector2f> list = Arrays.asList(
                new Vector2f(0, shape.y),
                new Vector2f(shape),
                new Vector2f(0, 0),
                new Vector2f(shape.x, 0)
        );


        shader.bind();

        shader.SetFloat("width", shaderShape.x);
        shader.SetFloat("height", shaderShape.y);
        shader.SetFloat("t", Statics.t / 16f);
        int screenTextureID = ShaderLib.getScreenTexture();
        SpriteAPI texture = Global.getSettings().getSprite("fx", "fae_null");
        texture.setColor(new Color(255, 0, 255, 255));
        shader.SetTexture("texture", texture, 0);

        glBegin(GL_TRIANGLE_STRIP);

        RenderMisc.SetColor(Color.WHITE);
        glTexCoord2f(0f, 1f);
        glVertex2f(list.get(0).x, list.get(0).y);
        glTexCoord2f(1f, 1f);
        glVertex2f(list.get(1).x, list.get(1).y);
        glTexCoord2f(0f, 0f);
        glVertex2f(list.get(2).x, list.get(2).y);
        glTexCoord2f(1f, 0f);
        glVertex2f(list.get(3).x, list.get(3).y);

        glEnd();


        shader.unbind();

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