package bing.faesector.data.render.renderFunctions;

import bing.faesector.data.Statics;
import bing.faesector.data.render.RenderMisc;
import bing.faesector.data.render.renderClassesFolder.*;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import static bing.faesector.data.render.RenderMisc.worldVectorToScreenVector;
import static java.lang.Math.*;
import static org.lazywizard.lazylib.FastTrig.sin;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;

import bing.faesector.data.render.RenderMisc;
import bing.faesector.data.render.renderClassesFolder.ElipseData;
import bing.faesector.data.render.renderClassesFolder.RenderMode;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;

import static bing.faesector.data.render.RenderMisc.worldVectorToScreenVector;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class RingRenderer {
    public static void DrawRing(RingData ring, ViewportAPI viewport) {

        SpriteAPI texture = ring.texture;
        boolean textured = texture != null;

        if (ring.filled) {
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
        glBegin(GL_TRIANGLE_STRIP);

        Vector2f center = ring.center;
        Vector2f shape = ring.shape;
        float width = ring.width;
        if (textured) {
            ring.color = texture.getColor();
        }
        RenderMisc.SetColor(ring.color);
        if (ring.getMode() == RenderMode.SCALE) {

            for (int i = -91; i < 270; i += 1) {

                float rad = i * (3.1415f / 180f);

                float xLocF = (float) (cos(rad));
                float yLocF = (float) (sin(rad));

                //region innerRender

                float xLocI = (float) (xLocF * shape.x);
                float yLocI = (float) (yLocF * shape.y);

                Vector2f locI = new Vector2f(xLocI + center.x, yLocI + center.y);

                locI = worldVectorToScreenVector(locI, viewport);

                if (textured)
                    glTexCoord2f(xLocF / texture.getTextureWidth(), yLocF / texture.getTextureHeight());
                glVertex2f(locI.x, locI.y);

                //endregion

                //region outerRender

                float xLocO = (float) (xLocF * (shape.x + width));
                float yLocO = (float) (yLocF * (shape.y + width));

                Vector2f locO = new Vector2f(xLocO + center.x, yLocO + center.y);

                locO = worldVectorToScreenVector(locO, viewport);

                if (textured)
                    glTexCoord2f(xLocF / texture.getTextureWidth(), yLocF / texture.getTextureHeight());
                glVertex2f(locO.x, locO.y);

                //endregion
            }

        } else if (ring.getMode() == RenderMode.TILE) {
            for (int i = -91; i < 270; i += 1) {

                float rad = i * (3.1415f / 180f);

                float xLocF = (float) (cos(rad));
                float yLocF = (float) (sin(rad));

                //region innerRender

                float xLocI = (float) (xLocF * shape.x);
                float yLocI = (float) (yLocF * shape.y);

                Vector2f locI = new Vector2f(xLocI + center.x, yLocI + center.y);

                locI = worldVectorToScreenVector(locI, viewport);

                if (textured)
                    glTexCoord2f(xLocF / 2 + 0.5f, yLocF / 2 + 0.5f);
                glVertex2f(locI.x, locI.y);

                //endregion

                //region outerRender

                float xLocO = (float) (xLocF * (shape.x + width));
                float yLocO = (float) (yLocF * (shape.y + width));

                Vector2f locO = new Vector2f(xLocO + center.x, yLocO + center.y);

                locO = worldVectorToScreenVector(locO, viewport);

                if (textured)
                    glTexCoord2f(xLocF / 2 + 0.5f, yLocF / 2 + 0.5f);
                glVertex2f(locO.x, locO.y);

                //endregion

            }
        }

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
