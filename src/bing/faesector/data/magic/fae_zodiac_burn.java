package bing.faesector.data.magic;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipCommand;
import com.fs.starfarer.api.input.InputEventAPI;
import bing.faesector.data.fae_misc;

import java.util.List;

public class fae_zodiac_burn extends BaseEveryFrameCombatPlugin {
    private final CombatEngineAPI engine;
    private float timer;
    private float duration;
    private MissileAPI missile;

    public void advance(float amount, List<InputEventAPI> events) {
        if(engine.isPaused())return;
        timer+=amount;
        if(!fae_misc.isProjValid(missile)){
            engine.removePlugin(this);
        }
        missile.giveCommand(ShipCommand.ACCELERATE);
        if(timer>=duration){
            engine.removePlugin(this);
        }
    }

    public fae_zodiac_burn(MissileAPI missile, float duration) {
        engine = Global.getCombatEngine();
        engine.addPlugin(this);
        this.timer=0f;
        this.missile=missile;
        this.duration = duration;
        //System.out.println("blastwave is running");
    }
}
