package bing.faesector.data.render.renderClassesFolder;

import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class LineWWidthData {

    public LineWWidthData(Vector2f fromCenter, Vector2f toCenter, float width, Color lineColor) {
        this.fromCenter = fromCenter;
        this.toCenter = toCenter;
        this.width = width;
        this.lineColor = lineColor;
    }

    public Vector2f fromCenter;

    public Vector2f toCenter;

    public float width;

    public Color lineColor;

}
