package bing.faesector.data;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.input.InputEventAPI;

import java.util.List;

public class fae_CombatTimerEFS extends BaseEveryFrameCombatPlugin {
    @Override
    public void advance(float amount, List<InputEventAPI> events) {
        if (Global.getCombatEngine().isPaused()) return;

        Statics.t += Global.getCombatEngine().getElapsedInLastFrame(); // this function returns 1 if 1 frame happens in 1 second but
        // returns 1/60 if 60 frames happen in a second

        Statics.tMinus -= Global.getCombatEngine().getElapsedInLastFrame();

        if (Statics.t > Float.MAX_VALUE) Statics.t = Float.MIN_VALUE;
        if (Statics.tMinus < Float.MIN_VALUE) Statics.tMinus = Float.MAX_VALUE;

    }
}
