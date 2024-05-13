package bing.faesector.data.render.renderClassesFolder;

import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class PointData {
//not used

    public PointData(Vector2f location, float pointSize, Color color) {
        this.location = location;
        this.color = color;
        this.pointSize = pointSize;
    }


    public Vector2f location;
    public float pointSize;

    public Color color;

}
