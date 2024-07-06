package bing.faesector.data.tests;

import bing.faesector.data.render.shader.Shader;
import bing.faesector.data.tests.frameBuffer.fae_FrameBufferTest;
import bing.faesector.data.tests.shaderTests.fae_ShaderTestRender;
import bing.faesector.data.tests.sphere.fae_sphereTest;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.input.InputEventAPI;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class fae_TestEFS extends BaseEveryFrameCombatPlugin {


    public void processInputPreCoreControls(float amount, List<InputEventAPI> events) {
        for (InputEventAPI event : events) {
            if (event.isKeyboardEvent() && event.getEventChar() == 'b') {

//                Vector2f center = Global.getCombatEngine().getPlayerShip().getMouseTarget();
//
//                byte riftCount = 18;
//                double angleIncreasePerRift = 360 / riftCount;
//                double angle = -90;
//
//
//                for (int i = 0; i < riftCount; i++) {
//
//                    Vector2f riftCenter = MathUtils.getPointOnCircumference(center, 900, (float) angle);
//
//                    float riftAngle = VectorUtils.getAngle(riftCenter, center);
//
//                    at_CombatRiftRenderer plugin = new at_CombatRiftRenderer(
//                            riftCenter,
//                            riftAngle
//                    );
//
//                    angle += angleIncreasePerRift;
//                    Global.getCombatEngine().addLayeredRenderingPlugin(plugin);
//                }

                Vector2f center = Global.getCombatEngine().getPlayerShip().getMouseTarget();

//                fae_CombatRiftRenderer plugin = new fae_CombatRiftRenderer(
//                        new Vector2f(center),
//                        VectorUtils.getAngle(Global.getCombatEngine().getPlayerShip().getMouseTarget(), Global.getCombatEngine().getPlayerShip().getLocation())
//                );

//                fae_OvalRiftRenderer plugin = new fae_OvalRiftRenderer(
//                        new Vector2f(center),
//                        VectorUtils.getAngle(Global.getCombatEngine().getPlayerShip().getMouseTarget(), Global.getCombatEngine().getPlayerShip().getLocation()),
//                        500,
//                        250
//                );

//                fae_RingRenderTest plugin = new fae_RingRenderTest(
//                                                new Vector2f(center),
//                        VectorUtils.getAngle(Global.getCombatEngine().getPlayerShip().getMouseTarget(), Global.getCombatEngine().getPlayerShip().getLocation()),
//                        500,
//                        250
//                );

//                fae_glRotateFTest plugin = new fae_glRotateFTest();


                fae_ShaderTestRender plugin = new fae_ShaderTestRender(
//                        new Shader().loadVertexShader("data/gfx/default.vert").loadFragShader("data/gfx/defaultFrag/defaultFrag.frag").link()
                        new Shader().loadVertexShader("data/gfx/default.vert").loadFragShader("data/gfx/Voronoi/Voronoi.frag").link()
                );

//                fae_sphereTest plugin = new fae_sphereTest();

//                fae_FrameBufferTest plugin = new fae_FrameBufferTest();

                Global.getCombatEngine().addLayeredRenderingPlugin(plugin);

            }
        }
    }

}
