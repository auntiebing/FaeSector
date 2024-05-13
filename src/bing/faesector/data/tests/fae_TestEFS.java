package bing.faesector.data.tests;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.input.InputEventAPI;
import org.lazywizard.lazylib.VectorUtils;
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

//                at_CombatRiftRenderer DiamondRift = new at_CombatRiftRenderer(
//                        new Vector2f(center),
//                        VectorUtils.getAngle(Global.getCombatEngine().getPlayerShip().getMouseTarget(), Global.getCombatEngine().getPlayerShip().getLocation())
//                );
//                Global.getCombatEngine().addLayeredRenderingPlugin(DiamondRift);

                fae_OvalRiftRenderer OvalRift = new fae_OvalRiftRenderer(
                        new Vector2f(center),
                        VectorUtils.getAngle(Global.getCombatEngine().getPlayerShip().getMouseTarget(), Global.getCombatEngine().getPlayerShip().getLocation()),
                        500,
                        250
                );
                Global.getCombatEngine().addLayeredRenderingPlugin(OvalRift);

            }
        }
    }

}
