package bing.faesector.data.render.renderClassesFolder;

import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.List;


public class PolygonData {

    public PolygonData(List<Vector2f> vector2fList, List<Color> colorList, boolean filled) {
        this.vector2fList = vector2fList;
        this.colorList = colorList;
        this.filled = filled;
    }

    public List<Vector2f> vector2fList;

    public List<Color> colorList;

    public boolean filled;

}
