package bing.faesector;

import bing.faesector.data.helpers.ScriptBuilder;
import bing.faesector.data.tests.fae_CampaignStarTest;
import bing.faesector.data.weapons.fae_calathAI;
import bing.faesector.world.FaesectorGen;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import exerelin.campaign.SectorManager;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.ScriptEvaluator;

public class fsModPlugin extends BaseModPlugin {

  public static final String CALATH_ID = "fae_calath";
  public static final boolean isExerelin = Global
    .getSettings()
    .getModManager()
    .isModEnabled("nexerelin");

  //public static boolean hasGraphicsLib;
  @Override
  public void onNewGame() {
    if (!isExerelin || SectorManager.getManager().isCorvusMode()) {
      new FaesectorGen().generate(Global.getSector());
    }
  }

  @Override
  public PluginPick<MissileAIPlugin> pickMissileAI(
    MissileAPI missile,
    ShipAPI launchingShip
  ) {
    switch (missile.getProjectileSpecId()) {
      case CALATH_ID:
        return new PluginPick<MissileAIPlugin>(
          new fae_calathAI(missile, launchingShip),
          CampaignPlugin.PickPriority.MOD_SPECIFIC
        );
      default:
    }
    return null;
  }

  @Override
  public void onGameLoad(boolean onNewGame) {
    Global.getSector().addScript(new fae_CampaignStarTest());
  }
  /*@Override
    public void onNewGameAfterEconomyLoad() {
        MarketAPI fae_capitalmarket = Global.getSector().getEconomy().getMarket("fae_capitalmarket");
        if (fae_capitalmarket != null) {
            fae_people.create();
        }
    }
 */

}
