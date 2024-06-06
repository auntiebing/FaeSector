package bing.faesector.data.render;

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

import static bing.faesector.data.render.renderFunctions.SquareRenderer.DrawSquare;
import static org.lwjgl.opengl.GL11.*;

public class RenderMisc {

    public static Vector2f worldVectorToScreenVector(Vector2f worldVector, ViewportAPI viewport) {
        return new Vector2f(viewport.convertWorldXtoScreenX(worldVector.x), viewport.convertWorldYtoScreenY(worldVector.y));//screen vector
    }

    public static void drawTestSquare(Vector2f center, ViewportAPI viewport) {
        DrawSquare(
                new SquareData(
                        worldVectorToScreenVector(
                                new ArrayList<Vector2f>(
                                        Arrays.asList(
                                                new Vector2f(center.x - 50, center.y + 50),
                                                new Vector2f(center.x + 50, center.y + 50),
                                                new Vector2f(center.x - 50, center.y - 50),
                                                new Vector2f(center.x + 50, center.y - 50)
                                        )
                                ),
                                viewport
                        ),
                        Color.red,
                        true
                ),
                viewport
        );
    }

    public static void drawTestSquare(Vector2f center, ViewportAPI viewport, SpriteAPI texture) {
        DrawSquare(
                new SquareData(
                        worldVectorToScreenVector(
                                new ArrayList<Vector2f>(
                                        Arrays.asList(
                                                new Vector2f(center.x - 50, center.y + 50),
                                                new Vector2f(center.x + 50, center.y + 50),
                                                new Vector2f(center.x - 50, center.y - 50),
                                                new Vector2f(center.x + 50, center.y - 50)
                                        )
                                ),
                                viewport
                        ),
                        Color.red,
                        texture,
                        RenderMode.SCALE,
                        new Vector2f(0, 0),
                        new Vector2f(0, 0)
                ),
                viewport
        );
    }

    public static List<Vector2f> worldVectorToScreenVector(List<Vector2f> worldVectorList, ViewportAPI viewport) {

        List<Vector2f> screenVectorList = new ArrayList<>();

        for (Vector2f screenVector : worldVectorList) {

            screenVectorList.add(worldVectorToScreenVector(screenVector, viewport));

        }

        return screenVectorList;//screen vector
    }

    public static Vector2f screenVectorToWorldVector(Vector2f worldVector, ViewportAPI viewport) {
        return new Vector2f(viewport.convertScreenXToWorldX(worldVector.x), viewport.convertScreenYToWorldY(worldVector.y));//screen vector
    }

    public static List<Vector2f> screenVectorToWorldVector(List<Vector2f> worldVectorList, ViewportAPI viewport) {

        List<Vector2f> screenVectorList = new ArrayList<>();

        for (Vector2f screenVector : worldVectorList) {

            screenVectorList.add(screenVectorToWorldVector(screenVector, viewport));

        }

        return screenVectorList;//screen vector
    }


    /**
     * the order of the return list is <br>
     * 1---2<br>
     * &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;|<br>
     * 3---4<br>
     */
    public static List<Vector2f> LineToCorners(Vector2f from, Vector2f to, float width) {
        List<Vector2f> list = new ArrayList<>();

        float angle = VectorUtils.getAngle(from, to);

        Vector2f leftTop = MathUtils.getPointOnCircumference(to, width / 2, angle + 90);
        list.add(leftTop);
        Vector2f rightTop = MathUtils.getPointOnCircumference(to, width / 2, angle - 90);
        list.add(rightTop);

        Vector2f leftBottom = MathUtils.getPointOnCircumference(from, width / 2, angle + 90);
        list.add(leftBottom);
        Vector2f rightBottom = MathUtils.getPointOnCircumference(from, width / 2, angle - 90);
        list.add(rightBottom);

        return list;
    }

    public static void SetColor(Color color) {
        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
    }

    public static void Transform(Vector2f loc, ViewportAPI viewport) {
        float mult = viewport.getViewMult();
        glTranslatef(loc.x / mult, loc.y / mult, 0);
    }

}
