package bing.faesector.data.render.renderClassesFolder;

import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class ElipseData {


    public SpriteAPI texture = null;
    public boolean filled = true;
    public Color color = Color.red;
    private RenderMode mode = RenderMode.SCALE;
    public Vector2f shape = new Vector2f(0, 0);
    public Vector2f center = new Vector2f(0, 0);

    public RenderMode getMode() {
        return mode;
    }

}
