package bing.faesector.data.render.renderClassesFolder;

import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class RingData {

    public RingData(Vector2f center, float segments, float circleAngle, float height, float lineWidth, Color lineColor) {
        this.center = center;
        this.segments = segments;
        this.circleAngle = circleAngle;
        this.height = height;
        this.lineWidth = lineWidth;
        this.lineColor = lineColor;
    }

    public Vector2f center;
    public float segments;
    public float circleAngle;
    public float height;
    public float lineWidth;
    public Color lineColor;

}
