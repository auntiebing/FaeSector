package bing.faesector.data.render.renderFunctions;

import bing.faesector.data.render.renderClassesFolder.SquareData;
import bing.faesector.data.render.renderClassesFolder.RenderMode;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class SquareRenderer {
    public static void DrawSquare(
            SquareData square,
            ViewportAPI viewport
    ) {//i am KİLLİNG myself java is such a retarted language thersnt even default paramaters even on the latest version for fucks sake

        SpriteAPI texture = square.texture;
        boolean textureBool = false;
        if (texture != null) textureBool = true;

        if (square.filled) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }

        if (textureBool)
            texture.bindTexture();

//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        if (textureBool) {
            glEnable(GL_BLEND);//for transparency
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);//https://www.codeproject.com/Questions/512386/TransparentplustextureplusinplusOpenGL
        } else {
            if (square.filled) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            } else {
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            }
        }

        if (textureBool) {
            glEnable(GL_TEXTURE_2D);
        } else {
            glDisable(GL_TEXTURE_2D);
        }

        glBegin(GL_TRIANGLE_STRIP);

        Color color = square.color;

        if (color != null && !textureBool) {
            glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        } else if (texture != null) {
            color = texture.getColor();
            glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        }

        boolean vertexColorsBool = false;
        List<Color> vertexColors = null;
        if (square.vertexColors != null) {
            vertexColorsBool = true;
            vertexColors = square.vertexColors;
        }
        Vector2f leftTop = square.leftTop;
        Vector2f rightTop = square.rightTop;
        Vector2f leftBottom = square.leftBottom;
        Vector2f rightBottom = square.rightBottom;
        Vector2f textureOffSet = square.textureOffSet;
        if (square.getMode() == RenderMode.SCALE) {

            if (vertexColorsBool)
                glColor4f(vertexColors.get(0).getRed() / 255f, vertexColors.get(0).getGreen() / 255f, vertexColors.get(0).getBlue() / 255f, vertexColors.get(0).getAlpha() / 255f);
            if (textureBool)
                glTexCoord2f(square.textLeftTop.x * square.textCoordMult.x + textureOffSet.x, square.textLeftTop.y * square.textCoordMult.y + textureOffSet.y);
            glVertex2f(leftTop.x, leftTop.y);

            if (vertexColorsBool)
                glColor4f(vertexColors.get(1).getRed() / 255f, vertexColors.get(1).getGreen() / 255f, vertexColors.get(1).getBlue() / 255f, vertexColors.get(1).getAlpha() / 255f);
            if (textureBool)
                glTexCoord2f(square.textRightTop.x * square.textCoordMult.x + textureOffSet.x, square.textRightTop.y * square.textCoordMult.y + textureOffSet.y);
            glVertex2f(rightTop.x, rightTop.y);

            if (vertexColorsBool)
                glColor4f(vertexColors.get(2).getRed() / 255f, vertexColors.get(2).getGreen() / 255f, vertexColors.get(2).getBlue() / 255f, vertexColors.get(2).getAlpha() / 255f);
            if (textureBool)
                glTexCoord2f(square.textLeftBottom.x * square.textCoordMult.x + textureOffSet.x, square.textLeftBottom.y * square.textCoordMult.y + textureOffSet.y);
            glVertex2f(leftBottom.x, leftBottom.y);

            if (vertexColorsBool)
                glColor4f(vertexColors.get(3).getRed() / 255f, vertexColors.get(3).getGreen() / 255f, vertexColors.get(3).getBlue() / 255f, vertexColors.get(3).getAlpha() / 255f);
            if (textureBool)
                glTexCoord2f(square.textRightBottom.x * square.textCoordMult.x + textureOffSet.x, square.textRightBottom.y * square.textCoordMult.y + textureOffSet.y);
            glVertex2f(rightBottom.x, rightBottom.y);

        } else if (square.getMode() == RenderMode.TILE) {

            float leftTopY = viewport.convertScreenYToWorldY(leftTop.y);
            float leftBottomY = viewport.convertScreenYToWorldY(leftBottom.y);

            float texCoordHeight = (leftTopY - leftBottomY) / square.textureSizeInGame.y;

            float leftBottomX = viewport.convertScreenXToWorldX(leftBottom.x);
            float rightBottomX = viewport.convertScreenXToWorldX(rightBottom.x);

            float texCoordWidth = (leftBottomX - rightBottomX) / square.textureSizeInGame.x;


            if (texCoordHeight < 0) texCoordHeight *= -1;
            if (texCoordWidth < 0) texCoordWidth *= -1;

            if (vertexColorsBool)
                glColor4f(vertexColors.get(0).getRed() / 255f, vertexColors.get(0).getGreen() / 255f, vertexColors.get(0).getBlue() / 255f, vertexColors.get(0).getAlpha() / 255f);
            if (textureBool)
                glTexCoord2f(0f * square.textCoordMult.x + textureOffSet.x, texCoordHeight * square.textCoordMult.y + textureOffSet.y);
            glVertex2f(leftTop.x, leftTop.y);

            if (vertexColorsBool)
                glColor4f(vertexColors.get(1).getRed() / 255f, vertexColors.get(1).getGreen() / 255f, vertexColors.get(1).getBlue() / 255f, vertexColors.get(1).getAlpha() / 255f);
            if (textureBool)
                glTexCoord2f(texCoordWidth * square.textCoordMult.x + textureOffSet.x, texCoordHeight * square.textCoordMult.y + textureOffSet.y);
            glVertex2f(rightTop.x, rightTop.y);

            if (vertexColorsBool)
                glColor4f(vertexColors.get(2).getRed() / 255f, vertexColors.get(2).getGreen() / 255f, vertexColors.get(2).getBlue() / 255f, vertexColors.get(2).getAlpha() / 255f);
            if (textureBool)
                glTexCoord2f(0f * square.textCoordMult.x + textureOffSet.x, 0f * square.textCoordMult.y + textureOffSet.y);
            glVertex2f(leftBottom.x, leftBottom.y);

            if (vertexColorsBool)
                glColor4f(vertexColors.get(3).getRed() / 255f, vertexColors.get(3).getGreen() / 255f, vertexColors.get(3).getBlue() / 255f, vertexColors.get(3).getAlpha() / 255f);
            if (textureBool)
                glTexCoord2f(texCoordWidth * square.textCoordMult.x + textureOffSet.x, 0f * square.textCoordMult.y + textureOffSet.y);
            glVertex2f(rightBottom.x, rightBottom.y);

        }


        glEnd();
//        glFlush();
        if (textureBool) {
            glDisable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
        } else {
            glEnable(GL_TEXTURE_2D);
        }


        CMUKitUI.closeGLForMisc();
        CMUKitUI.openGLForMisc();


    }
}
