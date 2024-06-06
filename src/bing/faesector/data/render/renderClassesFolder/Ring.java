package bing.faesector.data.render.renderClassesFolder;

import bing.faesector.data.render.RenderMisc;
import bing.faesector.data.render.renderClassesFolder.vertex.VertexData;
import bing.faesector.data.render.renderClassesFolder.vertex.VertexManager;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.*;

import static bing.faesector.data.render.RenderMisc.*;
import static java.lang.Math.cos;
import static org.lazywizard.lazylib.FastTrig.sin;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;


public class Ring {


    public float width = 0f;

    public SpriteAPI texture = null;
    public boolean filled = true;
    private RenderMode mode = RenderMode.SCALE;
    public Vector2f shape = new Vector2f(0, 0);
    public Vector2f center = new Vector2f(0, 0);
    public short segmentAmount = 18;
    private List<Color> innerColor = new ArrayList<>(Arrays.asList(
            new Color(255, 0, 255, 255)
    ));
    private List<Color> outerColor = new ArrayList<>(Arrays.asList(
            new Color(0, 255, 255, 255)
    ));

    private final Color color = new Color(255, 255, 255, 255);
    public float angle = 0;

    public void setColor(List<Color> color) {
        this.innerColor = color;
        this.outerColor = color;
    }

    public void setColor(List<Color> innerColor, List<Color> outerColor) {
        this.innerColor = innerColor;
        this.outerColor = outerColor;
    }

    public List<Color> getInnerColor() {
        return innerColor;
    }

    public List<Color> getOuterColor() {
        return outerColor;
    }

    public RenderMode getMode() {
        return mode;
    }


    public void DrawRing(ViewportAPI viewport) {

        boolean textured = texture != null;


        if (textured) {
            innerColor = new ArrayList<>(Arrays.asList(
                    texture.getColor()
            ));

            outerColor = new ArrayList<>(Arrays.asList(
                    texture.getColor()
            ));
        }
        VertexManager manager = new VertexManager();
        manager.monoColor = false;

        //calculate vertex locations
        if (mode == RenderMode.SCALE) {


            int currInnerColorI = 0;
            int currOuterColorI = 0;

            for (int i = -91; i < 270; i += 360 / segmentAmount) {

                float rad = i * (3.1415f / 180f);

                float xLocF = (float) (cos(rad));
                float yLocF = (float) (sin(rad));

                //region innerRender

                float xLocI = (float) (xLocF * shape.x);
                float yLocI = (float) (yLocF * shape.y);

                Vector2f locI = new Vector2f(xLocI, yLocI);

                VertexData innerVertex = new VertexData();
                innerVertex.textured = textured;
                if (textured) {
                    innerVertex.textCoords = new Vector2f(xLocF / texture.getTextureWidth(), yLocF / texture.getTextureHeight());
                }
                innerVertex.color = innerColor.get(currInnerColorI);
                innerVertex.coords = locI;
                manager.AddVertex(innerVertex);

                //endregion

                //region outerRender

                float xLocO = (float) (xLocF * (shape.x + width));
                float yLocO = (float) (yLocF * (shape.y + width));

                Vector2f locO = new Vector2f(xLocO, yLocO);

                VertexData outerVertex = new VertexData();
                outerVertex.textured = textured;
                if (textured) {
                    outerVertex.textCoords = new Vector2f(xLocF / texture.getTextureWidth(), yLocF / texture.getTextureHeight());
                }
                outerVertex.color = outerColor.get(currOuterColorI);
                outerVertex.coords = locO;
                manager.AddVertex(outerVertex);

                //endregion

                currInnerColorI++;
                if (currInnerColorI > innerColor.size() - 1) {
                    currInnerColorI = 0;
                }

                currOuterColorI++;
                if (currOuterColorI > outerColor.size() - 1) {
                    currOuterColorI = 0;
                }

            }


        } else if (getMode() == RenderMode.TILE) {

            int currInnerColorI = 0;
            int currOuterColorI = 0;

            for (int i = -91; i < 270; i += 360 / segmentAmount) {

                float rad = i * (3.1415f / 180f);

                float xLocF = (float) (cos(rad));
                float yLocF = (float) (sin(rad));

                //region innerRender

                float xLocI = (float) (xLocF * shape.x);
                float yLocI = (float) (yLocF * shape.y);

                Vector2f locI = new Vector2f(xLocI, yLocI);

                VertexData innerVertex = new VertexData();
                innerVertex.textured = textured;
                if (textured) {
                    innerVertex.textCoords = new Vector2f(xLocF / 2 + 0.5f, yLocF / 2 + 0.5f);
                }
                innerVertex.color = innerColor.get(currInnerColorI);
                innerVertex.coords = locI;
                manager.AddVertex(innerVertex);

                //endregion

                //region outerRender

                float xLocO = (float) (xLocF * (shape.x + width));
                float yLocO = (float) (yLocF * (shape.y + width));

                Vector2f locO = new Vector2f(xLocO, yLocO);

                VertexData outerVertex = new VertexData();
                outerVertex.textured = textured;
                if (textured) {
                    outerVertex.textCoords = new Vector2f(xLocF / 2 + 0.5f, yLocF / 2 + 0.5f);
                }
                outerVertex.color = outerColor.get(currOuterColorI);
                outerVertex.coords = locO;
                manager.AddVertex(outerVertex);

                //endregion

                currInnerColorI++;
                if (currInnerColorI > innerColor.size() - 1) {
                    currInnerColorI = 0;
                }

                currOuterColorI++;
                if (currOuterColorI > outerColor.size() - 1) {
                    currOuterColorI = 0;
                }

            }
        }


        if (filled) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }

        if (textured) {//enable the things for textures
            glEnable(GL_BLEND);//for transparency
            glEnable(GL_TEXTURE_2D);
        } else {//disable textures if you wont be texturing // TODO add this to oval renderer
            glDisable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);//for transparency
        }
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);//https://www.codeproject.com/Questions/512386/TransparentplustextureplusinplusOpenGL

        manager.Rotate(angle, new Vector2f(0, 0));
        RenderMisc.Transform(center, viewport);

        glBegin(GL_TRIANGLE_STRIP);

        manager.WorldToScreen(viewport);

        manager.RenderTriangleStrip(viewport);

        glEnd();


        if (textured) {//disable them to not cause problems
            glDisable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
        } else {
            glEnable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);
        }


    }

}
