package bing.faesector.archive;

import bing.faesector.data.render.RenderMisc;
import bing.faesector.data.render.renderClassesFolder.*;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import static bing.faesector.data.render.RenderMisc.worldVectorToScreenVector;
import static java.lang.Math.*;
import static org.lazywizard.lazylib.FastTrig.sin;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;

import bing.faesector.data.render.renderClassesFolder.RenderMode;

import static bing.faesector.data.render.RenderMisc.worldVectorToScreenVector;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

class RingRenderE {
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
        glBegin(GL_TRIANGLE_FAN);

        Vector2f center = ring.center;
        Vector2f shape = ring.shape;
        float width = ring.width;
        if (textured) {
            ring.color = texture.getColor();
        }
        RenderMisc.SetColor(ring.color);
        if (ring.getMode() == RenderMode.SCALE) {
            Vector2f locF = worldVectorToScreenVector(center, viewport);
            if (textured)
                glTexCoord2f(locF.x / texture.getTextureWidth(), locF.y / texture.getTextureHeight());
            glVertex2f(locF.x, locF.y);

            for (int i = -1; i < 360; i += 1) {

                //https://discord.com/channels/908453691747606529/908476833736060969/1242442927251128390

                //like, you cast getPointOnCircumference(center, radius, angle)
                //that makes a circle
                //but then you make radius = r1+r2*sin(angle)
                //that makes oval

                //r1 is width of the oval
                //r2 is (length - width)

                //region innerRender
                double iRadius = shape.x + (shape.y - shape.x) * sin(Math.toRadians(i));
                Vector2f locI = MathUtils.getPointOnCircumference(center, (float) (iRadius), i);

                locI = worldVectorToScreenVector(locI, viewport);

                if (textured)
                    glTexCoord2f(locI.x / texture.getTextureWidth(), locI.y / texture.getTextureHeight());
                glVertex2f(locI.x, locI.y);

                //endregion

                //region outerRender

//                float shapeXWW = shape.x + width;
//                float shapeYWW = shape.y + width;
//                double oRadius = shapeXWW + (shapeYWW - shapeXWW) * Math.toRadians(sin(i));
//                Vector2f locO = MathUtils.getPointOnCircumference(center, (float) (oRadius), i);
//
//                locO = worldVectorToScreenVector(locO, viewport);
//
//                if (textured)
//                    glTexCoord2f(locO.x / texture.getTextureWidth(), locO.y / texture.getTextureHeight());
//                glVertex2f(locO.x, locO.y);

////                //endregion
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
