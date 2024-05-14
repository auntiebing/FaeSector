package bing.faesector.data.tests;

import bing.faesector.data.render.renderClassesFolder.SquareData;
import bing.faesector.data.render.renderClassesFolder.SquareMode;
import cmu.gui.CMUKitUI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseCombatLayeredRenderingPlugin;
import com.fs.starfarer.api.combat.CombatEngineLayers;
import com.fs.starfarer.api.combat.ViewportAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;
import penner.easing.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static bing.faesector.data.render.at_RenderPluginFunctions.DrawSquare;
import static bing.faesector.data.render.at_RendererHelper.worldVectorToScreenVector;


public class fae_CombatRiftRenderer extends BaseCombatLayeredRenderingPlugin {

    private Vector2f sourceLocation;
    private float angle;

    public fae_CombatRiftRenderer(Vector2f sourceLocation, float angle) {
        this.sourceLocation = sourceLocation;
        this.angle = angle;
        animatedTexture.setAlphaMult(0.3f);

    }

    private SpriteAPI backgroundOfRift = Global.getSettings().getSprite("backgrounds", "defaultHyperBackground");
    private SpriteAPI borderGradient = Global.getSettings().getSprite("fx", "at_radial_fx_rotated");
    private SpriteAPI animatedTexture = Global.getSettings().getSprite("misc", "fx_radial");

    private CombatEngineLayers CURRENT_LAYER = CombatEngineLayers.ABOVE_PLANETS;

    private float xOffSet = 0;
    private float yOffSet = 0;
    private final float riftMarginWidth = 140;
    private final float riftMarginOffSet = 0;
    private final float riftBorderWidth = 15;
    private final float riftBorderOffSet = 0;
    private final float riftGradientWidth = 70;
    private float riftGradientOffSet = 0;
    private float riftGradientOffSetSinValue = 0;
    private final List<Vector2f> rotatedTextureLocs = new ArrayList(
            Arrays.asList(
                    new Vector2f(1, 0),
                    new Vector2f(0, 0),
                    new Vector2f(1, 1),
                    new Vector2f(0, 1)
            )
    );
    private final Vector2f AnimatedBorderTextureSizeInGame = new Vector2f(512, 512);
    private final Vector2f GlowBorderTextureSizeInGame = new Vector2f(256, 256);

    private int time = 0;
    private float beginning = 0;
    private float change = 200;
    private float duration = 200;

    @Override
    public void render(CombatEngineLayers layer, ViewportAPI viewport) {
        CMUKitUI.openGLForMisc(); // gl open

        //FUCK THİNKİNG İS HARD

        float sizeMultX = Sine.easeOut(time, beginning, change, duration) / duration;
        float sizeMultY = Circ.easeInOut(time, beginning, change, duration) / duration;

        float frontLenght = 900f;
        float leftLenght = 150f;
        float rightLenght = 150f;
        float bottomLenght = 900f;

        //region locations
        Vector2f front0 = MathUtils.getPointOnCircumference(sourceLocation, frontLenght * sizeMultX, angle);
        Vector2f frontRiftBorderStart = MathUtils.getPointOnCircumference(sourceLocation, (frontLenght + riftBorderOffSet) * sizeMultX, angle);
        Vector2f frontRiftBorderEnd = MathUtils.getPointOnCircumference(sourceLocation, (frontLenght + riftBorderWidth) * sizeMultX, angle);
        Vector2f frontRiftGradientStart = MathUtils.getPointOnCircumference(sourceLocation, (frontLenght + riftGradientOffSet) * sizeMultX, angle);
        Vector2f frontRiftGradientEnd = MathUtils.getPointOnCircumference(sourceLocation, (frontLenght + riftGradientWidth) * sizeMultX, angle);
        Vector2f frontRiftMarginStart = MathUtils.getPointOnCircumference(sourceLocation, (frontLenght + riftMarginOffSet) * sizeMultX, angle);
        Vector2f frontRiftMarginWidth = MathUtils.getPointOnCircumference(sourceLocation, (frontLenght + riftMarginWidth) * sizeMultX, angle);

        Vector2f left0 = MathUtils.getPointOnCircumference(sourceLocation, leftLenght * sizeMultY, angle + 90);
        Vector2f leftRiftBorderStart = MathUtils.getPointOnCircumference(sourceLocation, (leftLenght + riftBorderOffSet) * sizeMultY, angle + 90);
        Vector2f leftRiftBorderEnd = MathUtils.getPointOnCircumference(sourceLocation, (leftLenght + riftBorderWidth) * sizeMultY, angle + 90);
        Vector2f leftRiftGradientStart = MathUtils.getPointOnCircumference(sourceLocation, (leftLenght + riftGradientOffSet) * sizeMultY, angle + 90);
        Vector2f leftRiftGradientEnd = MathUtils.getPointOnCircumference(sourceLocation, (leftLenght + riftGradientWidth) * sizeMultY, angle + 90);
        Vector2f leftRiftMarginStart = MathUtils.getPointOnCircumference(sourceLocation, (leftLenght + riftMarginOffSet) * sizeMultY, angle + 90);
        Vector2f leftRiftMarginWidth = MathUtils.getPointOnCircumference(sourceLocation, (leftLenght + riftMarginWidth) * sizeMultY, angle + 90);

        Vector2f right0 = MathUtils.getPointOnCircumference(sourceLocation, rightLenght * sizeMultY, angle - 90);
        Vector2f rightRiftBorderStart = MathUtils.getPointOnCircumference(sourceLocation, (rightLenght + riftBorderOffSet) * sizeMultY, angle - 90);
        Vector2f rightRiftBorderEnd = MathUtils.getPointOnCircumference(sourceLocation, (rightLenght + riftBorderWidth) * sizeMultY, angle - 90);
        Vector2f rightRiftGradientStart = MathUtils.getPointOnCircumference(sourceLocation, (rightLenght + riftGradientOffSet) * sizeMultY, angle - 90);
        Vector2f rightRiftGradientEnd = MathUtils.getPointOnCircumference(sourceLocation, (rightLenght + riftGradientWidth) * sizeMultY, angle - 90);
        Vector2f rightRiftMarginStart = MathUtils.getPointOnCircumference(sourceLocation, (rightLenght + riftMarginOffSet) * sizeMultY, angle - 90);
        Vector2f rightRiftMarginWidth = MathUtils.getPointOnCircumference(sourceLocation, (rightLenght + riftMarginWidth) * sizeMultY, angle - 90);

        Vector2f back0 = MathUtils.getPointOnCircumference(sourceLocation, bottomLenght * sizeMultX, angle - 180);
        Vector2f backRiftBorderStart = MathUtils.getPointOnCircumference(sourceLocation, (bottomLenght + riftBorderOffSet) * sizeMultX, angle - 180);
        Vector2f backRiftBorderEnd = MathUtils.getPointOnCircumference(sourceLocation, (bottomLenght + riftBorderWidth) * sizeMultX, angle - 180);
        Vector2f backRiftGradientStart = MathUtils.getPointOnCircumference(sourceLocation, (bottomLenght + riftGradientOffSet) * sizeMultX, angle - 180);
        Vector2f backRiftGradientEnd = MathUtils.getPointOnCircumference(sourceLocation, (bottomLenght + riftGradientWidth) * sizeMultX, angle - 180);
        Vector2f backRiftMarginStart = MathUtils.getPointOnCircumference(sourceLocation, (bottomLenght + riftMarginOffSet) * sizeMultX, angle - 180);
        Vector2f backRiftMarginWidth = MathUtils.getPointOnCircumference(sourceLocation, (bottomLenght + riftMarginWidth) * sizeMultX, angle - 180);
        //endregion

        if (time < duration) {
            if (Global.getCombatEngine().isPaused()) time--;
            time++;
        }

        //region colors
        List<Color> edgeColors = new ArrayList<>(Arrays.asList(
                new Color(0, 0, 0, 100),
                animatedTexture.getColor(),
                new Color(0, 0, 0, 100),
                animatedTexture.getColor()
        ));

        List<Color> gradientColors = new ArrayList<>(Arrays.asList(
                new Color(120, 30, 217, 0),
                new Color(120, 30, 217, 255),
                new Color(120, 30, 217, 0),
                new Color(120, 30, 217, 255)
        ));

        List<Color> riftBorderColors = new ArrayList<>(Arrays.asList(
                new Color(103, 164, 250, 0),
                new Color(103, 164, 250, 220),
                new Color(103, 164, 250, 0),
                new Color(103, 164, 250, 220)
        ));


        //endregion

        //region inner rift

        List<Vector2f> riftTextureLocation = worldVectorToScreenVector(
                new ArrayList<Vector2f>(
                        Arrays.asList(
                                front0,
                                left0,
                                right0,
                                back0
                        )
                ), viewport
        );


        SquareData riftBackground = new SquareData(
                riftTextureLocation,
                null,
                backgroundOfRift,
                SquareMode.SCALE,
                new Vector2f(0, 0),
                new Vector2f(256, 256)
        );

        riftBackground.textLeftTop = new Vector2f(riftTextureLocation.get(0).x / 1024f, riftTextureLocation.get(0).y / 1024f);
        riftBackground.textRightTop = new Vector2f(riftTextureLocation.get(1).x / 1024f, riftTextureLocation.get(1).y / 1024f);
        riftBackground.textLeftBottom = new Vector2f(riftTextureLocation.get(2).x / 1024f, riftTextureLocation.get(2).y / 1024f);
        riftBackground.textRightBottom = new Vector2f(riftTextureLocation.get(3).x / 1024f, riftTextureLocation.get(3).y / 1024f);

        DrawSquare(//texture
                riftBackground,
                viewport
        );

        //endregion inner rift end

        //region edges

        List<SquareData> animatedEdgeSquares = new ArrayList<>();

        //region frontLeft
        animatedEdgeSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        frontRiftMarginWidth,
                                        frontRiftMarginStart,
                                        leftRiftMarginWidth,
                                        leftRiftMarginStart
                                )
                        ), viewport),
                        null,
                        animatedTexture,
                        SquareMode.TILE,
                        new Vector2f(xOffSet, yOffSet),
                        AnimatedBorderTextureSizeInGame
                )
        );
        //endregion

        //region leftBack
        animatedEdgeSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        backRiftMarginWidth,
                                        backRiftMarginStart,
                                        leftRiftMarginWidth,
                                        leftRiftMarginStart
                                )
                        ), viewport),
                        null,
                        animatedTexture,
                        SquareMode.TILE,
                        new Vector2f(xOffSet, yOffSet),
                        AnimatedBorderTextureSizeInGame
                )
        );
        //endregion

        //region backRight
        animatedEdgeSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        backRiftMarginWidth,
                                        backRiftMarginStart,
                                        rightRiftMarginWidth,
                                        rightRiftMarginStart
                                )
                        ), viewport),
                        null,
                        animatedTexture,
                        SquareMode.TILE,
                        new Vector2f(xOffSet, yOffSet),
                        AnimatedBorderTextureSizeInGame
                )
        );
        //endregion

        //region frontRight
        animatedEdgeSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        frontRiftMarginWidth,
                                        frontRiftMarginStart,
                                        rightRiftMarginWidth,
                                        rightRiftMarginStart
                                )
                        ), viewport),
                        null,
                        animatedTexture,
                        SquareMode.TILE,
                        new Vector2f(xOffSet, yOffSet),
                        AnimatedBorderTextureSizeInGame
                )
        );
        //endregion

        animatedEdgeSquares.get(0).vertexColors = edgeColors;
        animatedEdgeSquares.get(1).vertexColors = edgeColors;
        animatedEdgeSquares.get(2).vertexColors = edgeColors;
        animatedEdgeSquares.get(3).vertexColors = edgeColors;

        for (SquareData square : animatedEdgeSquares) {

            square.textCoordMult = new Vector2f(0.7f, 0.7f);

            DrawSquare(
                    square,
                    viewport
            );
        }

        //endregion

        //region gradient

        List<SquareData> gradientSquares = new ArrayList<>();

        //region frontLeft
        gradientSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        frontRiftGradientEnd,
                                        frontRiftGradientStart,
                                        leftRiftGradientEnd,
                                        leftRiftGradientStart
                                )
                        ), viewport),
                        new Color(56, 30, 217, 255),
                        borderGradient,
                        SquareMode.TILE,
                        new Vector2f(0, 0),
                        new Vector2f(0, 0)
                )
        );
        //endregion

        //region leftBack
        gradientSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        backRiftGradientEnd,
                                        backRiftGradientStart,
                                        leftRiftGradientEnd,
                                        leftRiftGradientStart
                                )
                        ), viewport),
                        new Color(56, 30, 217, 255),
                        borderGradient,
                        SquareMode.TILE,
                        new Vector2f(0, 0),
                        new Vector2f(0, 0)
                )
        );
        //endregion

        //region backRight
        gradientSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        backRiftGradientEnd,
                                        backRiftGradientStart,
                                        rightRiftGradientEnd,
                                        rightRiftGradientStart
                                )
                        ), viewport),
                        new Color(56, 30, 217, 255),
                        borderGradient,
                        SquareMode.TILE,
                        new Vector2f(0, 0),
                        new Vector2f(0, 0)
                )
        );
        //endregion

        //region frontRight
        gradientSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        frontRiftGradientEnd,
                                        frontRiftGradientStart,
                                        rightRiftGradientEnd,
                                        rightRiftGradientStart
                                )
                        ), viewport),
                        new Color(56, 30, 217, 255),
                        borderGradient,
                        SquareMode.TILE,
                        new Vector2f(0, 0),
                        new Vector2f(0, 0)
                )
        );
        //endregion

        gradientSquares.get(0).vertexColors = gradientColors;
        gradientSquares.get(1).vertexColors = gradientColors;
        gradientSquares.get(2).vertexColors = gradientColors;
        gradientSquares.get(3).vertexColors = gradientColors;

        for (SquareData square : gradientSquares) {

            DrawSquare(
                    square,
                    viewport
            );

        }

        //endregion

        //region borders of in rift

        List<SquareData> borderSquares = new ArrayList<>();

        //region frontLeft
        borderSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        frontRiftBorderEnd,
                                        frontRiftBorderStart,
                                        leftRiftBorderEnd,
                                        leftRiftBorderStart
                                )
                        ), viewport),
                        null,
                        null,
                        null,
                        new Vector2f(0, 0),
                        new Vector2f(0, 0)
                )
        );
        //endregion

        //region leftBack
        borderSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        backRiftBorderEnd,
                                        backRiftBorderStart,
                                        leftRiftBorderEnd,
                                        leftRiftBorderStart
                                )
                        ), viewport),
                        null,
                        null,
                        null,
                        new Vector2f(0, 0),
                        new Vector2f(0, 0)
                )
        );
        //endregion

        //region backRight
        borderSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        backRiftBorderEnd,
                                        backRiftBorderStart,
                                        rightRiftBorderEnd,
                                        rightRiftBorderStart
                                )
                        ), viewport),
                        null,
                        null,
                        null,
                        new Vector2f(0, 0),
                        new Vector2f(0, 0)
                )
        );
        //endregion

        //region frontRight
        borderSquares.add(
                new SquareData(
                        worldVectorToScreenVector(new ArrayList<Vector2f>(
                                Arrays.asList(
                                        frontRiftBorderEnd,
                                        frontRiftBorderStart,
                                        rightRiftBorderEnd,
                                        rightRiftBorderStart
                                )
                        ), viewport),
                        null,
                        null,
                        null,
                        new Vector2f(0, 0),
                        new Vector2f(0, 0)
                )
        );
        //endregion

        borderSquares.get(0).vertexColors = riftBorderColors;
        borderSquares.get(1).vertexColors = riftBorderColors;
        borderSquares.get(2).vertexColors = riftBorderColors;
        borderSquares.get(3).vertexColors = riftBorderColors;

        for (SquareData square : borderSquares) {
            DrawSquare(
                    square,
                    viewport
            );
        }

        //endregion

        xOffSet += 0.0001f;
        if (xOffSet > 1f) xOffSet = 0;


        CMUKitUI.closeGLForMisc(); // gl close
    }

    public void ChangeRenderLayerInPlugin(CombatEngineLayers LAYER) {
        this.CURRENT_LAYER = LAYER;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public float getRenderRadius() {
        return Float.MAX_VALUE;
    }

    @Override
    public EnumSet<CombatEngineLayers> getActiveLayers() {
        return EnumSet.of(CURRENT_LAYER);
    }

}
