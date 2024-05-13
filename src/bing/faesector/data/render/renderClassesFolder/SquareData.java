package bing.faesector.data.render.renderClassesFolder;

import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.List;

public class SquareData {
    public SquareData(
            Vector2f leftTop,
            Vector2f rightTop,
            Vector2f leftBottom,
            Vector2f rightBottom,
            Color color,
            boolean filled
    ) {
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
        this.color = color;
        this.filled = filled;
        this.texture = null;
    }

    public SquareData(
            List<Vector2f> locs,
            Color color,
            boolean filled
    ) {
        this.leftTop = locs.get(0);
        this.rightTop = locs.get(1);
        this.leftBottom = locs.get(2);
        this.rightBottom = locs.get(3);
        this.color = color;
        this.filled = filled;
        this.texture = null;
    }

    public SquareData(
            Vector2f leftTop,
            Vector2f rightTop,
            Vector2f leftBottom,
            Vector2f rightBottom,
            Color color,
            SpriteAPI texture,
            SquareMode textureMode,
            Vector2f textureOffSet,
            Vector2f textureSizeInGame
    ) {
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
        this.color = color;
        this.texture = texture;
        if (texture != null) {
            UpdateTextureLocations();
        }
        this.mode = textureMode;
        this.textureOffSet = textureOffSet;
        this.textureSizeInGame = textureSizeInGame;
    }

    public SquareData(
            List<Vector2f> list,
            Color color,
            SpriteAPI texture,
            SquareMode textureMode,
            Vector2f textureOffSet,
            Vector2f textureSizeInGame
    ) {
        this.leftTop = list.get(0);
        this.rightTop = list.get(1);
        this.leftBottom = list.get(2);
        this.rightBottom = list.get(3);
        this.color = color;
        this.texture = texture;
        if (texture != null) {
            UpdateTextureLocations();
        }
        if (textureMode != null)
            this.mode = textureMode;
        this.textureOffSet = textureOffSet;
        this.textureSizeInGame = textureSizeInGame;
    }

    private void UpdateTextureLocations() {
        textLeftTop = new Vector2f(0, texture.getTextureHeight());
        textRightTop = new Vector2f(texture.getTextureWidth(), texture.getTextureHeight());
        textLeftBottom = new Vector2f(0, 0);
        textRightBottom = new Vector2f(texture.getTextureWidth(), 0);
    }

    public void UpdateTextureLocations(List<Vector2f> textLocs) {
        textLeftTop = new Vector2f(textLocs.get(0).x, textLocs.get(0).y);
        textRightTop = new Vector2f(textLocs.get(1).x, textLocs.get(1).y);
        textLeftBottom = new Vector2f(textLocs.get(2).x, textLocs.get(2).y);
        textRightBottom = new Vector2f(textLocs.get(3).x, textLocs.get(3).y);
    }

    public Vector2f leftTop;
    public Vector2f rightTop;
    public Vector2f leftBottom;
    public Vector2f rightBottom;
    public boolean filled = true;
    public Color color = Color.red;
    public List<Color> vertexColors = null;
    public SpriteAPI texture;
    private SquareMode mode = SquareMode.SCALE;
    public Vector2f textureOffSet;
    public Vector2f textureSizeInGame;

    public Vector2f textLeftTop;
    public Vector2f textRightTop;
    public Vector2f textLeftBottom;
    public Vector2f textRightBottom;
    public Vector2f textCoordMult = new Vector2f(1f, 1f);

    public SquareMode getMode() {
        return mode;
    }

    public void setMode(SquareMode mode) {
        this.mode = mode;
    }
}