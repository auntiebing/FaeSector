package bing.faesector.data.tests.shaderTests;

import bing.faesector.data.Statics;
import bing.faesector.data.render.RenderMisc;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ViewportAPI;
import org.dark.shaders.util.ShaderLib;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

import bing.faesector.data.render.renderClassesFolder.SquareData;
import bing.faesector.data.render.renderClassesFolder.RenderMode;
import bing.faesector.data.render.renderClassesFolder.vertex.VertexData;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bing.faesector.data.render.RenderMisc.worldVectorToScreenVector;
import static bing.faesector.data.render.renderFunctions.SquareRenderer.DrawSquare;
import static org.lwjgl.opengl.GL11.*;

public class fae_ShaderTestRender extends BaseCombatLayeredRenderingPlugin {

    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_SHIPS_LAYER;
    private Shader shader = null;

    public fae_ShaderTestRender(Shader shader) {
        this.shader = shader;
    }

    @Override
    public void render(CombatEngineLayers layer, ViewportAPI viewport) {
        CMUKitUI.openGLForMisc(); // gl open

        ShipAPI ship = Global.getCombatEngine().getPlayerShip();

        Vector2f center = ship.getMouseTarget();

        // order: https://stackoverflow.com/a/21967602/21149029
        // glCreateProgram()
        // glAttachShader()
        // glLinkProgram()
        // glUseProgram()
        // glGetUniformLocation()
        // glUniformMatrix4fv()


        List<Vector2f> list = worldVectorToScreenVector(
                new ArrayList<Vector2f>(
                        Arrays.asList(
                                new Vector2f(center.x - 50, center.y + 50),
                                new Vector2f(center.x + 50, center.y + 50),
                                new Vector2f(center.x - 50, center.y - 50),
                                new Vector2f(center.x + 50, center.y - 50)
                        )
                ),
                viewport
        );

        glPushMatrix();

        shader.bind();

        shader.SetFloat("width", 100f);
        shader.SetFloat("height", 100f);
        shader.SetFloat("t", Statics.t);

        glBegin(GL_TRIANGLE_STRIP);

        glVertex2f(list.get(0).x, list.get(0).y);
        glVertex2f(list.get(1).x, list.get(1).y);
        glVertex2f(list.get(2).x, list.get(2).y);
        glVertex2f(list.get(3).x, list.get(3).y);

        glEnd();

        shader.unbind();

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
