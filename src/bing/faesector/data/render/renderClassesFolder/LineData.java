package bing.faesector.data.render.renderClassesFolder;

import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class LineData {

    public LineData(Vector2f from, Vector2f to, Color lineColor) {
        this.from = from;
        this.to = to;
        this.lineColor = lineColor;
    }

    public Vector2f from;

    public Vector2f to;

    public Color lineColor;

}
