package bing.faesector.world.systems;

import bing.faesector.world.AddMarketplace;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.terrain.DebrisFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;




public class arbora implements SectorGeneratorPlugin {

    public void generate(SectorAPI sector) {
        StarSystemAPI system = sector.createStarSystem("Arbora");
        LocationAPI hyper = Global.getSector().getHyperspace();
        system.setOptionalUniqueId("arbora");
        system.setBackgroundTextureFilename("graphics/fae/backgrounds/bg5.jpg");

        PlanetAPI arbora = system.initStar(
                "home_star_a", // star id
                "star_white", // sprite id
                550f,          // radius
                300,        // corona
                4f, // solar wind burn level
                0.3f, // flare prob.
                1.0f); // cr loss mult. range 1-5 is good
        arbora.setCustomDescriptionId("stardesc");
        system.setLightColor(new Color(243, 240, 236)); // SYSTEM LIGHT COLOUR

        //system.getLocation().set(30000, -8000); CURRENT LOCATION IS JUST FOR TESTING
        system.getLocation().set(-400, -9400);

        //1550 - goldmine
        PlanetAPI HS1 = system.addPlanet(
                "alnath",
                arbora,
                "Alnath",
                "lava_minor",
                20,
                70,
                1550,
                100);
        HS1.setCustomDescriptionId("hsadesc");

        //JUMP POINT - alnath gets it's own jump point. why? fuck you.
        JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint("alnath_jp", "Alnath Jump-Point");
        OrbitAPI orbit = Global.getFactory().createCircularOrbit(arbora, 30, 1100, 100);
        jumpPoint1.setOrbit(orbit);
        jumpPoint1.setRelatedPlanet(HS1);
        jumpPoint1.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint1);

        //COND - literally mustafar
        Misc.initConditionMarket(HS1);
        HS1.getMarket().addCondition(Conditions.VERY_HOT);
        HS1.getMarket().addCondition(Conditions.TECTONIC_ACTIVITY);
        HS1.getMarket().addCondition(Conditions.LOW_GRAVITY);
        HS1.getMarket().addCondition(Conditions.ORE_ABUNDANT);
        HS1.getMarket().addCondition(Conditions.RARE_ORE_ABUNDANT);
        HS1.getMarket().getFirstCondition(Conditions.RARE_ORE_ABUNDANT).setSurveyed(true);

        //2650 - intentionally awful
        PlanetAPI HS2 = system.addPlanet(
                "vylora",
                arbora,
                "Vylora",
                "desert",
                70,
                110,
                2650,
                195);
        HS2.setCustomDescriptionId("hsbdesc");

        //COND - worse tatooine
        Misc.initConditionMarket(HS2);
        HS2.getMarket().addCondition(Conditions.HABITABLE);
        HS2.getMarket().addCondition(Conditions.DECIVILIZED);
        HS2.getMarket().addCondition(Conditions.HOT);
        HS2.getMarket().addCondition(Conditions.FARMLAND_POOR);
        HS2.getMarket().addCondition(Conditions.ORE_MODERATE);
        HS2.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        HS2.getMarket().getFirstCondition(Conditions.RARE_ORE_SPARSE).setSurveyed(true);

        //3855 - home of fairies
        PlanetAPI HS3 = system.addPlanet(
                "arboranita",
                arbora,
                "Arboranita",
                "jungle",
                180,
                160,
                3855,
                335);
        HS3.setCustomDescriptionId("arbordesc");

        //MARKET

//      MarketAPI Homemarket = AddMarketplace.addMarketplace(
//              "fairies",
//              HS3,
//              null,
//              "Arboranita", // NAME
//              5,                  // SIZE
//              new ArrayList<>(
//                      Arrays.asList( // COND
//                              Conditions.HABITABLE,
//                              Conditions.FARMLAND_BOUNTIFUL,
//                                Conditions.ORGANICS_ABUNDANT,
//                                Conditions.ORE_MODERATE,
//                                Conditions.MILD_CLIMATE,
//                                Conditions.INIMICAL_BIOSPHERE,
//                                Conditions.POPULATION_5)),
//                new ArrayList<>(
//                        Arrays.asList( // IND
//                                Industries.SPACEPORT,
//                                Industries.WAYSTATION,
//                                Industries.POPULATION,
//                                Industries.ORBITALSTATION,
//                                Industries.FARMING,
//                                Industries.LIGHTINDUSTRY)),
//                new ArrayList<>(Arrays.asList(
//                        Submarkets.SUBMARKET_OPEN,
//                        Submarkets.SUBMARKET_BLACK,
//                        Submarkets.SUBMARKET_STORAGE)),
//                0.3f);// tariff amount

        //DUST RING - woah space dust

        system.addRingBand(
                HS3,
                "misc",
                "rings_dust0",
                256f,
                3, Color.white,
                256f,
                480,
                90f,
                Terrain.RING,
                "The Baker's Lament");

        // 4550
        SectorEntityToken pirBase = system.addCustomEntity("freeport", "Celetse Freeport", "station_mining00", "pirates");
        pirBase.setCircularOrbitWithSpin(arbora, 350 * (float) Math.random(), 4550, 345, 9, 27);
        pirBase.setDiscoverable(true);
        pirBase.setDiscoveryXP(2500f);
        pirBase.setSensorProfile(1.0f);

        //4550
//      MarketAPI freefairies = AddMarketplace.addMarketplace(
//              "pirates",
//              pirBase,
//              null,
//              "Celeste Freeport",
//              3,
//              new ArrayList<>(Arrays.asList(
//                      Industries.SPACEPORT,
//                      Industries.WAYSTATION,
//                      Industries.POPULATION,
//                      Industries.ORBITALSTATION,
//                      Industries.MINING,
//                          Industries.TAG_STATION)),
//              new ArrayList<>(Arrays.asList(
//                      Conditions.FREE_PORT,
//                      Conditions.ORGANIZED_CRIME,
//                     Conditions.POLLUTION,
//                          Conditions.POPULATION_3)),
//              new ArrayList<>(Arrays.asList(
//                      Submarkets.SUBMARKET_OPEN,
//                      Submarkets.SUBMARKET_BLACK,
//                      Submarkets.SUBMARKET_STORAGE
//                      )),
//              0.3f); // tariff amount

        //7125
        PlanetAPI HS4 = system.addPlanet(
                "zephyra",
                arbora,
                "Zephyra",
                "gas_giant",
                80,
                315,
                7125,
                630);

        //ASTEROIDS - yay. rocks.
        system.addAsteroidBelt(HS4, 50, 600, 128, 39, 45);

        //JUMP POINT - Should circle around zephyra..?

        JumpPointAPI jumpPoint2 = Global.getFactory().createJumpPoint("zephyra_jp", "Zephyra Jump-Point");
        OrbitAPI orbit2 = Global.getFactory().createCircularOrbit(HS4, 60, 400, 315);
        jumpPoint2.setOrbit(orbit2);
        jumpPoint2.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint2);

        //MAG FIELD - big boy get big field.

        SectorEntityToken HS4_field = system.addTerrain(Terrain.MAGNETIC_FIELD,
                new MagneticFieldTerrainPlugin.MagneticFieldParams(60f, // terrain effect band width
                        90, // terrain effect middle radius
                        HS4, // entity that it's around
                        60f, // visual band start
                        120f, // visual band end
                        new Color(50, 30, 100, 30), // base color
                        0.3f, // probability to spawn aurora sequence, checked once/day when no aurora in progress
                        new Color(50, 20, 110, 130),
                        new Color(200, 50, 130, 190),
                        new Color(250, 70, 150, 240),
                        new Color(200, 80, 130, 255),
                        new Color(75, 0, 160),
                        new Color(127, 0, 255)));
        HS4_field.setCircularOrbit(HS4, 0, 0, 100);

        //COND
        Misc.initConditionMarket(HS4);
        HS4.getMarket().addCondition(Conditions.THIN_ATMOSPHERE);
        HS4.getMarket().addCondition(Conditions.LOW_GRAVITY);
        HS4.getMarket().addCondition(Conditions.COLD);
        HS4.getMarket().addCondition(Conditions.VOLATILES_ABUNDANT);

        //9550 - Disant ice planet, just vibin
        PlanetAPI HS5 = system.addPlanet(
                "vylarya",
                arbora,
                "Vylarys",
                "ice_giant",
                180,
                300,
                9550,
                950);

        //COND
        Misc.initConditionMarket(HS5);
        HS5.getMarket().addCondition(Conditions.LOW_GRAVITY);
        HS5.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        HS5.getMarket().addCondition(Conditions.VERY_COLD);
        HS5.getMarket().addCondition(Conditions.VOLATILES_DIFFUSE);
        HS5.getMarket().addCondition(Conditions.POOR_LIGHT);


        //OTHER CRAP//

        //ASTEROID BELTS

        //ALPHA BELT// - Very thick asteroid belt that has some salvage kicking around.

        system.addAsteroidBelt(arbora, 475, 1950, 406, 300, 340);
        system.addRingBand(arbora, "misc", "rings_asteroids0", 256f, 1, Color.white, 256f, 1850, 125f, null, null);
        system.addRingBand(arbora, "misc", "rings_dust0", 256f, 0, Color.white, 256f, 2050, 105f, null, null);

        //BETA BELT// - Thinner belt. aint nuffin there but rocks.

        system.addAsteroidBelt(arbora, 90, 4550, 500, 290, 310, Terrain.ASTEROID_BELT, "Martinique Beta Belt");
        system.addRingBand(arbora, "misc", "rings_dust0", 256f, 1, Color.white, 256f, 4500, 305f, null, null);
        system.addRingBand(arbora, "misc", "rings_ice0", 256f, 2, Color.white, 256f, 4600, 285f, null, null);

        // Add debris to Alpha Belt
        DebrisFieldTerrainPlugin.DebrisFieldParams params1 = new DebrisFieldTerrainPlugin.DebrisFieldParams(
                450f, // field radius - should not go above 1000 for performance reasons
                1.5f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        params1.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        params1.baseSalvageXP = 500; // base XP for scavenging in field
        SectorEntityToken debrisBeta1 = Misc.addDebrisField(system, params1, StarSystemGenerator.random);
        debrisBeta1.setSensorProfile(1000f);
        debrisBeta1.setDiscoverable(true);
        debrisBeta1.setCircularOrbit(arbora, 60f, 1875, 210f);
        debrisBeta1.setId("fae_alphadebris1");

        DebrisFieldTerrainPlugin.DebrisFieldParams params2 = new DebrisFieldTerrainPlugin.DebrisFieldParams(
                300f, // field radius - should not go above 1000 for performance reasons
                1.0f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        params2.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        params2.baseSalvageXP = 500; // base XP for scavenging in field
        SectorEntityToken debrisBeta2 = Misc.addDebrisField(system, params2, StarSystemGenerator.random);
        debrisBeta2.setSensorProfile(1000f);
        debrisBeta2.setDiscoverable(true);
        debrisBeta2.setCircularOrbit(arbora, 150f, 1900, 210f);
        debrisBeta2.setId("fae_alphadebris2");

        DebrisFieldTerrainPlugin.DebrisFieldParams params3 = new DebrisFieldTerrainPlugin.DebrisFieldParams(
                400f, // field radius - should not go above 1000 for performance reasons
                1.2f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        params3.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        params3.baseSalvageXP = 500; // base XP for scavenging in field
        SectorEntityToken debrisBeta3 = Misc.addDebrisField(system, params3, StarSystemGenerator.random);
        debrisBeta3.setSensorProfile(1000f);
        debrisBeta3.setDiscoverable(true);
        debrisBeta3.setCircularOrbit(arbora, 270f, 1775, 225f);
        debrisBeta3.setId("fae_alphadebris3");

        //OLD RELAY

        CustomCampaignEntityAPI relay = system.addCustomEntity(
                "test_relay", //id
                "Test Relay Alpha", //display name
                "comm_relay", //types are found in data/config/custom_entities.json
                Factions.INDEPENDENT
        );
        relay.setCircularOrbit(arbora, 150, 2250, 200);

        //STATION

        CustomCampaignEntityAPI station1 = system.addCustomEntity(
                "test_relay", //id
                "Test Station Alpha", //display name
                "station_side03", //types are found in data/config/custom_entities.json
                Factions.INDEPENDENT
        );
        station1.setCircularOrbit(HS3, 0, 200, 60);

        StarSystemGenerator.addOrbitingEntities(system, arbora, StarAge.AVERAGE,
                1, 2, // min/max entities to add
                11000, // radius to start adding at
                5, // name offset - next planet will be <system name> <roman numeral of this parameter + 1>
                false); // whether to use custom or system-name based names

        system.autogenerateHyperspaceJumpPoints(true, true, true);
        system.setEnteredByPlayer(false);
        Misc.setAllPlanetsSurveyed(system, false);
        for (MarketAPI market : Global.getSector().getEconomy().getMarkets(system)) {
            market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        }
        Misc.generatePlanetConditions(system, StarAge.AVERAGE);
        cleanup(system);
    }

    void cleanup(StarSystemAPI system) {
        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius * 0.5f, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    }
}