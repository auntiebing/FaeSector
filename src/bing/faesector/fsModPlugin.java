package bing.faesector;

import bing.faesector.data.weapons.fae_calathAI;
import bing.faesector.world.FaesectorGen;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import exerelin.campaign.SectorManager;


public class fsModPlugin extends BaseModPlugin {
    public static final String CALATH_ID = "fae_calath";
    public static final boolean isExerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
    //public static boolean hasGraphicsLib;
    @Override
    public void onNewGame() {
        if (!isExerelin || SectorManager.getManager().isCorvusMode()) {
            new FaesectorGen().generate(Global.getSector());
        }
    }
    @Override
    public PluginPick<MissileAIPlugin> pickMissileAI(MissileAPI missile, ShipAPI launchingShip) {
        switch (missile.getProjectileSpecId()) {
            case CALATH_ID:
                return new PluginPick<MissileAIPlugin>(new fae_calathAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            default:
        }
        return null;
    }
}