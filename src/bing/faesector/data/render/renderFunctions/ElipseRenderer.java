package bing.faesector.data.render.renderFunctions;

import bing.faesector.data.Statics;
import bing.faesector.data.render.RenderMisc;
import bing.faesector.data.render.renderClassesFolder.ElipseData;
import bing.faesector.data.render.renderClassesFolder.RenderMode;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;

import static bing.faesector.data.render.RenderMisc.worldVectorToScreenVector;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class ElipseRenderer {

    public static void DrawElipse(ElipseData elipse, ViewportAPI viewport) {

        SpriteAPI texture = elipse.texture;
        boolean textured = texture != null;

        if (elipse.filled) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);//makes the polygon filled
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);//makes them LİNESSSSSSSSSSSSSSSSSSSSSSSSS
        }

        if (textured) {//enable the things for textures
            RenderMisc.SetColor(texture.getColor());
            glEnable(GL_BLEND);//for transparency
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);//https://www.codeproject.com/Questions/512386/TransparentplustextureplusinplusOpenGL
            glEnable(GL_TEXTURE_2D);
        } else {
            RenderMisc.SetColor(elipse.color);
        }
        glBegin(GL_TRIANGLE_FAN);


        Vector2f center = elipse.center;
        Vector2f shape = elipse.shape;
        if (elipse.getMode() == RenderMode.SCALE) {

            Vector2f locF = worldVectorToScreenVector(center, viewport);
            if (textured)
                glTexCoord2f(locF.x / texture.getTextureWidth(), locF.y / texture.getTextureHeight());
            glVertex2f(locF.x, locF.y);

            for (int i = -91; i < 270; i += 1) {

                float rad = i * (3.1415f / 180f);

                float xLocF = (float) (Math.cos(rad));
                float yLocF = (float) (Math.sin(rad));

                float xLoc = (float) (xLocF * shape.x);
                float yLoc = (float) (yLocF * shape.y);

                Vector2f loc = new Vector2f(xLoc + center.x, yLoc + center.y);

                loc = worldVectorToScreenVector(loc, viewport);

                if (textured)
                    glTexCoord2f(loc.x / texture.getTextureWidth(), loc.y / texture.getTextureHeight());
                glVertex2f(loc.x, loc.y);

            }

        } else if (elipse.getMode() == RenderMode.TILE) {
            Vector2f locF = worldVectorToScreenVector(center, viewport);
            if (textured)
                glTexCoord2f(0.5f, 0.5f);
            glVertex2f(locF.x, locF.y);

            for (int i = -91; i < 270; i += 1) {

                float rad = i * (3.1415f / 180f);

                float xLocF = (float) (Math.cos(rad));
                float yLocF = (float) (Math.sin(rad));

                float xLoc = (float) (xLocF * shape.x);
                float yLoc = (float) (yLocF * shape.y);

                Vector2f loc = new Vector2f(xLoc + center.x, yLoc + center.y);

                loc = worldVectorToScreenVector(loc, viewport);

                if (textured)
                    glTexCoord2f(xLocF / 2 + 0.5f, yLocF / 2 + 0.5f);
                glVertex2f(loc.x, loc.y);

            }
        }

        glEnd();
        if (textured) {//disable them to not cause problems
            glDisable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
        }


    }

}
