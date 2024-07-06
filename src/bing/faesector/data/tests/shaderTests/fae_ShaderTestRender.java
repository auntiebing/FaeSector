package bing.faesector.data.tests.shaderTests;

import bing.faesector.data.Statics;
import bing.faesector.data.helpers.Helper;
import bing.faesector.data.render.FBO.Framebuffer;
import bing.faesector.data.render.RenderMisc;
import bing.faesector.data.render.shader.Shader;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.json.JSONException;
import org.lwjgl.util.vector.Vector2f;
import org.magiclib.util.MagicRender;

import java.awt.*;
import java.io.IOException;
import java.util.EnumSet;

import java.util.List;

import static bing.faesector.data.render.RenderMisc.worldVectorToScreenVector;
import static org.lwjgl.opengl.GL11.*;

public class fae_ShaderTestRender extends BaseCombatLayeredRenderingPlugin {

    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_SHIPS_LAYER;
    private Shader shader = null;

    private Framebuffer fbo = new Framebuffer((int) Global.getCombatEngine().getPlayerShip().getSpriteAPI().getTextureWidth(), (int) Global.getCombatEngine().getPlayerShip().getSpriteAPI().getTextureHeight());

    public fae_ShaderTestRender(Shader shader) {
        this.shader = shader;
    }

    @Override
    public void render(CombatEngineLayers layer, ViewportAPI viewport) {
        CMUKitUI.openGLForMisc(); // gl open
        glPushMatrix();

        ShipAPI ship = Global.getCombatEngine().getPlayerShip();
        SpriteAPI tex = ship.getSpriteAPI();
//        Vector2f center = ship.getMouseTarget();
//        Vector2f center = ship.getLocation();
        Vector2f center = null;
        try {
            center = Helper.getShipCenter(ship);
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }


//        shipTex.setColor(new Color(255, 255, 255, 0));

        // order: https://stackoverflow.com/a/21967602/21149029
        // glCreateProgram()
        // glAttachShader()
        // glLinkProgram()
        // glUseProgram()
        // glGetUniformLocation()
        // glUniformMatrix4fv()

        Vector2f shape = new Vector2f(Global.getSettings().getScreenWidth(), Global.getSettings().getScreenHeight());

//        Vector2f shaderShape = new Vector2f(2048, 1024);
//        Vector2f shaderShape = new Vector2f(shape);
        Vector2f shaderShape = new Vector2f(tex.getWidth(), tex.getHeight());

//        List<Vector2f> list = worldVectorToScreenVector(
//                new ArrayList<Vector2f>(
//                        Arrays.asList(
//                                new Vector2f(center.x - 50, center.y + 50),
//                                new Vector2f(center.x + 50, center.y + 50),
//                                new Vector2f(center.x - 50, center.y - 50),
//                                new Vector2f(center.x + 50, center.y - 50)
//                        )
//                ),
//                viewport
//        );

//        List<Vector2f> list = Arrays.asList(
//                new Vector2f(0, shape.y),
//                new Vector2f(shape),
//                new Vector2f(0, 0),
//                new Vector2f(shape.x, 0)
//        );

        List<Vector2f> list = worldVectorToScreenVector(RenderMisc.GetTextureLocs(tex, center), viewport);

//        fbo.bind();

//        shader.bind();

//        shader.SetFloat("width", shaderShape.x);
//        shader.SetFloat("height", shaderShape.y);
//        shader.SetFloat("t", Statics.t);


        tex.bindTexture();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);//https://www.codeproject.com/Questions/512386/TransparentplustextureplusinplusOpenGL

        glBegin(GL_TRIANGLE_STRIP);

        RenderMisc.SetColor(new Color(255, 0, 0, 128));

        glTexCoord2f(0f, tex.getTextureHeight());
        glVertex2f(list.get(0).x, list.get(0).y);
        glTexCoord2f(tex.getTextureWidth(), tex.getTextureHeight());
        glVertex2f(list.get(1).x, list.get(1).y);
        glTexCoord2f(0f, 0f);
        glVertex2f(list.get(2).x, list.get(2).y);
        glTexCoord2f(tex.getTextureWidth(), 0f);
        glVertex2f(list.get(3).x, list.get(3).y);

        glEnd();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);

//        shader.unbind();

//        fbo.unbind();

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
