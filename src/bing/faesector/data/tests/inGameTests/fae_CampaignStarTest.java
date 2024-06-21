package bing.faesector.data.tests.inGameTests;


import bing.faesector.data.helpers.ColorHelper;
import bing.faesector.data.helpers.Rainbow;
import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;

import java.awt.*;

public class fae_CampaignStarTest implements EveryFrameScript {
    private boolean doOnce = true;
    private PlanetAPI star = null;
    private short colorValue = 0;

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {
        if (doOnce) {
            if (Global.getSector().getStarSystem("fae_Test") == null) {
                return;
            }
            this.star = Global.getSector().getStarSystem("fae_Test").getStar();
            doOnce = false;
        }

        Color color = ColorHelper.hslColor(colorValue / 360f, 0.7f, 0.4f);

        star.getSpec().setGlowColor(color);
        star.getSpec().setCoronaColor(color);
        star.getSpec().setPlanetColor(color);
        star.getSpec().setTexture(Global.getSettings().getSpriteName("systemMap", "radar_mask"));
        star.setLightColorOverrideIfStar(color);

        star.applySpecChanges();

        colorValue++;
        if (colorValue >= 360) colorValue = 0;

    }
}
