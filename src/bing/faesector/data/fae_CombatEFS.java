package bing.faesector.data;

import bing.faesector.data.weapons.ball.fae_ball_listener;
import bing.faesector.data.weapons.ball.fae_ball_onFire;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.combat.listeners.AdvanceableListener;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.util.IntervalUtil;

import java.util.ArrayList;
import java.util.List;

public class fae_CombatEFS extends BaseEveryFrameCombatPlugin {

    private List<AdvanceableListener> listeners = new ArrayList<AdvanceableListener>();

    @Override
    public void advance(float amount, List<InputEventAPI> events) {// use here as a generalised efs thing
        if (Global.getCombatEngine().getCustomData().get("fae_CombatEFSRunOnce") == null) {//runs once per combat


            Global.getCombatEngine().getCustomData().put("fae_CombatEFSRunOnce", false);
        }

        for (AdvanceableListener listener : listeners) {
            listener.advance(amount);
        }

        if (Global.getCombatEngine().isPaused()) return;

        Statics.t += Global.getCombatEngine().getElapsedInLastFrame(); // this function returns 1 if 1 frame happens in 1 second but
        // returns 1/60 if 60 frames happen in a second

        Statics.tMinus -= Global.getCombatEngine().getElapsedInLastFrame();

        if (Statics.t > Float.MAX_VALUE) Statics.t = Float.MIN_VALUE;
        if (Statics.tMinus < Float.MIN_VALUE) Statics.tMinus = Float.MAX_VALUE;

    }
}
