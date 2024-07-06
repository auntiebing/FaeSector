package bing.faesector.data.tests.sphere;

import bing.faesector.data.Statics;
import bing.faesector.data.render.RenderMisc;
import bing.faesector.data.render.renderClassesFolder.vertex.VertexManager;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.combat.ViewportAPI;

import static org.lwjgl.opengl.GL11.*;

import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ball {// https://discord.com/channels/187635036525166592/310517733458706442/1085218517927858196

    public Ball() {
    }

    private Sphere ball = new Sphere();

    public Ball setTexture(SpriteAPI texture) {
        this.texture = texture;
        return this;
    }

    private SpriteAPI texture;

    public Ball setLocation(Vector2f location) {
        this.location = location;
        return this;
    }

    private Vector2f location = new Vector2f();

    public Ball setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    private float radius = 64f;

    public Ball setSlices(int slices) {
        this.slices = slices;
        return this;
    }

    private int slices = 32;

    public Ball setStacks(int stacks) {
        this.stacks = stacks;
        return this;
    }

    private int stacks = 32;

    public Ball setRotation(List<Vector4f> rotations) {
        this.rotations = rotations;
        return this;
    }

    private List<Vector4f> rotations = new ArrayList<>(Arrays.asList(
            new Vector4f(1, 0, 0, -90)
    ));

    public Ball render(ViewportAPI viewport) {
        CMUKitUI.closeGLForMisc();

        ball.setTextureFlag(true);
        ball.setDrawStyle(100012);
        ball.setNormals(100000);
        ball.setOrientation(100020);

        RenderMisc.Transform(location);

        for (Vector4f rotation : rotations) {
            glRotatef(rotation.w, rotation.x, rotation.y, rotation.z);
        }

        glEnable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_CULL_FACE);

        this.texture.bindTexture();

        RenderMisc.SetColor(Color.WHITE);
        ball.draw(radius, slices, stacks);

        glDisable(GL_CULL_FACE);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);

        CMUKitUI.openGLForMisc();

        return this;
    }


}
