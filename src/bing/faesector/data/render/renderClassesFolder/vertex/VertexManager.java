package bing.faesector.data.render.renderClassesFolder.vertex;

import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.util.Misc;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static bing.faesector.data.render.RenderMisc.SetColor;
import static org.lwjgl.opengl.GL11.*;
import static bing.faesector.data.render.RenderMisc.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

public class VertexManager {

    private List<VertexData> vertexes = new ArrayList<>();
    public boolean monoColor = true;
    public Color monoColorColor = Color.red;

    public void AddVertex(VertexData vertex) {
        vertexes.add(vertex);
    }

    public void WorldToScreen(ViewportAPI viewport) {
        for (VertexData vertex : vertexes) {
            vertex.coords = worldVectorToScreenVector(vertex.coords, viewport);
        }
    }

    public void ScreenToWorld(ViewportAPI viewport) {
        for (VertexData vertex : vertexes) {
            vertex.coords = screenVectorToWorldVector(vertex.coords, viewport);
        }
    }

    public void Rotate(float angle, Vector2f center) {
        for (VertexData vertex : this.vertexes) {

            vertex.coords = Misc.rotateAroundOrigin(vertex.coords, angle, center);

        }
    }

    public void RenderTriangleStrip(ViewportAPI viewport) {

        for (VertexData vertex : vertexes) {

            if (monoColor) {
                SetColor(monoColorColor);
            } else {
                SetColor(vertex.color);
            }

            if (vertex.textured) {
                glTexCoord2f(vertex.textCoords.x, vertex.textCoords.y);
            }

            glVertex2f(vertex.coords.x, vertex.coords.y);
        }

    }

}
