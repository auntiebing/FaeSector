package bing.faesector.data.render;

import bing.faesector.data.render.renderClassesFolder.SquareData;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.opengl.DrawUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

import static bing.faesector.data.render.renderFunctions.SquareRenderer.DrawSquare;
import static org.lwjgl.opengl.GL11.*;

public class at_RenderPluginFunctions {

    //dont run anything here outside of render

    /**
     * makes polygon with height 30 makes a circle like thing</br>
     * <p>
     * MAX SİDES İS 360F the method wont do anything if you put more than that and return false</br>
     *
     * @param center      center of the circle
     * @param segments    amount of sides the "circle" will have
     * @param circleAngle angle of the circle, yk i named this circle but you can use this to make triangles and stuff too so thats for this, can be 0 but cant be more than 360
     * @param height      height of the circle
     * @param lineWidth   height of the circle
     * @param lineColor   color of the line
     */
    public static void DrawRing(Vector2f center, float segments, float circleAngle, float height, float lineWidth, Color lineColor, ViewportAPI viewport) {
        if (lineColor == null) return;

//        StarSystemAPI jsut change the location of the system


        if (segments > 360f || circleAngle > 360f) {
//            return "DEA_ERROR please put less than 360 and more than 0";
            return;
        }
//            errText.append("passed side validation");
//            errText.append("    *-*   ");


        Vector2f from = new Vector2f();
        Vector2f to = new Vector2f();

        from = MathUtils.getPointOnCircumference(center, height, circleAngle);
        int z = 0;

        for (float i = circleAngle; i < circleAngle + 360 + segments; i += 360 / segments) {
            if (z % 2 == 0) {
                to = MathUtils.getPointOnCircumference(center, height, i);
            } else {
                from = MathUtils.getPointOnCircumference(center, height, i);
            }


            DrawSquare(
                    new SquareData(RenderMisc.LineToCorners(from, to, lineWidth),
                            lineColor,
                            null,
                            null,
                            new Vector2f(0, 0),
                            new Vector2f(0, 0)
                    ), viewport);//400 makes orbiting lines same for -400//doesnt work rn
            z++;
        }
    }

    /**
     * draws a line, not suggested because you cant change width just use DEAlib_DrawLineWWidthForPlugin
     *
     * @param from      start of the line
     * @param to        end of the line
     * @param lineColor color of the line
     */
    public static void DrawLineForPlugin(Vector2f from, Vector2f to, Color lineColor, ViewportAPI viewport) {
        if (lineColor == null) return;
        glBegin(GL_LINE_STRIP);
//
//        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
//
//        glGetFloat(GL_SMOOTH_LINE_WIDTH_RANGE, floatBuffer);
//
//        DEA_Logger.DEA_log(DEA_RenderPluginFunctions.class, "BufferUtils.createFloatBuffer(2) - ", "   first element:" + String.valueOf(floatBuffer.get(0)),"   second element:"+ String.valueOf(floatBuffer.get(1)));
//
//        DEA_Logger.DEA_log(DEA_RenderPluginFunctions.class, "glGetFloat(GL_SMOOTH_LINE_WIDTH_RANGE) - ", String.valueOf(glGetFloat(GL_ALIASED_LINE_WIDTH_RANGE)));

        glColor4f(lineColor.getRed() / 255f, lineColor.getGreen() / 255f, lineColor.getBlue() / 255f, lineColor.getAlpha() / 255f);


        glVertex2f(from.x, from.y);
        glVertex2f(to.x, to.y);
        glEnd();


    }


    /**
     * draw circle, the script uses lazy libs draw circle thx to him for the script :people_hugging:
     *
     * @param Center      center of the circle
     * @param raidus      the raidus
     * @param circleColor color of the circle
     * @param numSegments the amount of sides the circle will have, higher numbers are more laggy so stick with low ones (dont mind it much tho :p)
     * @param filled      is filled?
     */
    public static void drawCircle(Vector2f Center, float raidus, int numSegments, boolean filled, Color circleColor, ViewportAPI viewport) {
        if (circleColor == null) return;
        glDisable(GL_TEXTURE_2D);
        glBegin(GL_LINE);

        glColor4f(circleColor.getRed() / 255f, circleColor.getGreen() / 255f, circleColor.getBlue() / 255f, circleColor.getAlpha() / 255f);


        DrawUtils.drawCircle(Center.x, Center.y, raidus, numSegments, filled);

        glEnd();
        glEnable(GL_TEXTURE_2D);
    }

    public static void DrawTriangle(Vector2f corner1, Vector2f corner2, Vector2f corner3, Color color, boolean filled, ViewportAPI viewport) {

        if (color == null) return;

        if (filled) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }
        glDisable(GL_TEXTURE_2D);//doesnt work without this
        glBegin(GL_TRIANGLES);

        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        glVertex2f(corner1.x, corner1.y);
        glVertex2f(corner2.x, corner2.y);
        glVertex2f(corner3.x, corner3.y);

//        glEnd();

//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
//        glBegin(GL_POLYGON);


        glEnd();
//        glFlush();
        glEnable(GL_TEXTURE_2D);


    }

    public static void DrawTriangle(Vector2f corner1, Vector2f corner2, Vector2f corner3, Color color, boolean filled, SpriteAPI texture, Boolean textureSide, ViewportAPI viewport) {

//        if (color == null) return;

        if (texture == null) return;

        if (filled) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }

//        texture.setTexHeight(17f);
//        texture.setTexWidth(17f);

//        texture.setHeight(64f);
//        texture.setWidth(64f);

        texture.bindTexture();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);//doesnt work without this
        glBegin(GL_TRIANGLES);


        if (color != null) {
            glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        } else {
            color = texture.getColor();
            glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        }

        if (!textureSide) {//left bottom


            glTexCoord2f(texture.getTextureWidth(), 0f);
            glVertex2f(corner1.x, corner1.y);

            glTexCoord2f(0f, 0f);
            glVertex2f(corner2.x, corner2.y);


            glTexCoord2f(0f, texture.getTextureHeight());
            glVertex2f(corner3.x, corner3.y);

        } else {

            glTexCoord2f(texture.getTextureWidth(), 0f);
            glVertex2f(corner1.x, corner1.y);

            glTexCoord2f(texture.getTextureWidth(), texture.getTextureHeight());
            glVertex2f(corner2.x, corner2.y);


            glTexCoord2f(0f, texture.getTextureHeight());
            glVertex2f(corner3.x, corner3.y);

        }
//        glEnd();

//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
//        glBegin(GL_POLYGON);


        glEnd();

//        glFlush();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);


    }

//    public static void DrawPoint(Vector2f location, float pointSize, Color color, ViewportAPI viewport) {
//
//        glDisable(GL_TEXTURE_2D);
//
//        glPointSize(pointSize);
//
//        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
//
//
//        glVertex2f(viewport.convertWorldXtoScreenX(location.x), viewport.convertWorldYtoScreenY(location.y));
//
//        glEnable(GL_TEXTURE_2D);
//    }

}
