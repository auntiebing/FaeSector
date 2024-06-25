package bing.faesector.data.render.renderClassesFolder;

import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class RingData {


    public float width = 0f;

    public SpriteAPI texture = null;
    public boolean filled = true;
    public Color color = new Color(255, 0, 0, 255);
    private RenderMode mode = RenderMode.SCALE;
    public Vector2f shape = new Vector2f(0, 0);
    public Vector2f center = new Vector2f(0, 0);

    public RenderMode getMode() {
        return mode;
    }

}
