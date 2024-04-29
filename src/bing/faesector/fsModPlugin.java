package bing.faesector;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import exerelin.campaign.SectorManager;
import bing.faesector.world.FaesectorGen;

public class fsModPlugin extends BaseModPlugin {
    public static final boolean isExerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");

    @Override
    public void onNewGame() {
        if (!isExerelin || SectorManager.getManager().isCorvusMode()) {
            new FaesectorGen().generate(Global.getSector());
        }
       //if (!isExerelin || SectorManager.getCorvusMode())
        //{
       //     goblinmode();
       // }
       // goblinmode();


    }

    //private static void goblinmode()
   // {
        //new FaesectorGen().generate(Global.getSector());
   // }
}