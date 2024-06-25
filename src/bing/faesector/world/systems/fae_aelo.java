package bing.faesector.world.systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.System.out;

public class fae_aelo implements SectorGeneratorPlugin { //A SectorGeneratorPlugin is a class from the game, that identifies this as a script that will have a 'generate' method
    @Override
    public void generate(SectorAPI sector) { //the parameter sector is passed. This is the instance of the campaign map that this script will add a star system to
        //initialise system
        StarSystemAPI system = sector.createStarSystem("Aelô"); //create a new variable called system. this is assigned an instance of the new star system added to the Sector at the same time
        system.getLocation().set(3000, 4800); //sets location of system in hyperspace. map size is in the order of 100000x100000, and 0, 0 is the center of the map
        system.setBackgroundTextureFilename("graphics/backgrounds/background1.jpg"); //sets the background image for when in the system. this is a filepath to an image in the core game files

        //set up star
        PlanetAPI star = system.initStar( //stars and planets are technically the same category of object, so stars use PlanetAPI
                "fae_aelo", //set star id, this should be unique
                "fae_star_pink", //set star type, the type IDs come from starsector-core/data/campaign/procgen/star_gen_data.csv
                1800, //set radius, 900 is a typical radius size
                3000, //sets the location of the star's one-way jump point in hyperspace, since it is the center of the star system, we want it to be in the center of the star system jump points in hyperspace
                4800,
                900 //radius of corona terrain around star
        );

        system.setEnteredByPlayer(true);


        //add an asteroid belt. asteroids are separate entities inside these, it will randomly distribute a defined number of them around the ring
        system.addAsteroidBelt(
                star, //orbit focus
                80, //number of asteroid entities
                2800, //orbit radius
                255, //width of band
                190, //minimum and maximum visual orbit speeds of asteroids
                220,
                Terrain.ASTEROID_BELT, //ID of the terrain type that appears in the section above the abilities bar
                "Fairy breast milk I" //display name
        );

        //add a ring texture. it will go under the asteroid entities generated above
        system.addRingBand(star,
                "misc", //used to access band texture, this is the name of a category in settings.json
                "rings_asteroids0", //specific texture id in category misc in settings.json
                256f, //texture width, can be used for scaling shenanigans
                2,
                Color.white, //colour tint
                256f, //band width in game
                2800, //same as above
                200f,
                null,
                null
        );

        PlanetAPI fae_hanulan = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_lava_one", //unique id string
                star, //orbit focus for planet
                "Hanulan", //display name of planet
                "lava", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                0f, //starting angle in orbit
                140f, //planet size
                3400, //radius gap from the star
                180); //number of in-game days for it to orbit once
        fae_hanulan.getMarket().addCondition(Conditions.VERY_HOT);
        fae_hanulan.getMarket().addCondition(Conditions.DENSE_ATMOSPHERE);
        fae_hanulan.getMarket().addCondition(Conditions.EXTREME_TECTONIC_ACTIVITY);
        fae_hanulan.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        fae_hanulan.getMarket().addCondition(Conditions.ORE_ABUNDANT);
        fae_hanulan.getMarket().addCondition(Conditions.RARE_ORE_RICH);
        fae_hanulan.setCustomDescriptionId("fae_hanulan"); //reference descriptions.csv

        PlanetAPI fae_voldani = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_lava_two", //unique id string
                star, //orbit focus for planet
                "Voldâni", //display name of planet
                "lava", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                180f, //starting angle in orbit
                140f, //planet size
                3400, //radius gap from the star
                180); //number of in-game days for it to orbit once
        fae_voldani.getMarket().addCondition(Conditions.VERY_HOT);
        fae_voldani.getMarket().addCondition(Conditions.DENSE_ATMOSPHERE);
        fae_voldani.getMarket().addCondition(Conditions.EXTREME_TECTONIC_ACTIVITY);
        fae_voldani.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        fae_voldani.getMarket().addCondition(Conditions.ORE_MODERATE);
        fae_voldani.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        fae_voldani.setCustomDescriptionId("fae_voldani"); //reference descriptions.csv

        //add an asteroid belt. asteroids are separate entities inside these, it will randomly distribute a defined number of them around the ring
        system.addAsteroidBelt(
                star, //orbit focus
                80, //number of asteroid entities
                4000, //orbit radius
                255, //width of band
                190, //minimum and maximum visual orbit speeds of asteroids
                220,
                Terrain.ASTEROID_BELT, //ID of the terrain type that appears in the section above the abilities bar
                "Nirândueth Belt" //display name
        );

        //add a ring texture. it will go under the asteroid entities generated above
        system.addRingBand(star,
                "misc", //used to access band texture, this is the name of a category in settings.json
                "rings_asteroids0", //specific texture id in category misc in settings.json
                256f, //texture width, can be used for scaling shenanigans
                2,
                Color.white, //colour tint
                256f, //band width in game
                4000, //same as above
                200f,
                null,
                null
        );

        SectorEntityToken fae_mining = system.addCustomEntity("fae_mining","Fae mining outpost","station_mining00", "fairies");
        fae_mining.setCircularOrbitPointingDown(star,0,4150,135);
        MarketAPI fae_miningMarket = Global.getFactory().createMarket("fae_miningMarket","Fae mining outpost",4);
        fae_mining.setCustomDescriptionId("fae_mining");
        fae_mining.setMarket(fae_miningMarket);
        fae_miningMarket.setPrimaryEntity(fae_mining);
        //use helper method from other script to easily configure the market. feel free to copy it into your own project
        fae_miningMarket = fae_AddMarketplace.addMarketplace( //A Market is separate to a Planet, and contains data about population, industries and conditions. This is a method from the other script in this mod, that will assign all marketplace conditions to the planet in one go, making it simple and easy
                "fairies", //Factions.INDEPENDENT references the id String of the Independent faction, so it is the same as writing "independent", but neater. This determines the Faction associated with this market
                fae_mining, //the PlanetAPI variable that this market will be assigned to
                null, //some mods and vanilla will have additional floating space stations or other entities, that when accessed, will open this marketplace. We don't have any associated entities for this method to add, so we leave null
                "Fae mining outpost", //Display name of market
                3, //population size
                new ArrayList<>(Arrays.asList( //List of conditions for this method to iterate through and add to the market
                        Conditions.POPULATION_3,
                        Conditions.ORE_RICH,
                        Conditions.RARE_ORE_ABUNDANT,
                        Conditions.VERY_HOT,
                        Conditions.LOW_GRAVITY
                )),
                new ArrayList<>(Arrays.asList( //list of submarkets for this method to iterate through and add to the market. if a military base industry was added to this market, it would be consistent to add a military submarket too
                        Submarkets.SUBMARKET_OPEN, //add a default open market
                        Submarkets.SUBMARKET_STORAGE, //add a player storage market
                        Submarkets.SUBMARKET_BLACK //add a black market
                )),
                new ArrayList<>(Arrays.asList( //list of industries for this method to iterate through and add to the market
                        Industries.POPULATION, //population industry is required for weirdness to not happen
                        Industries.SPACEPORT, //same with spaceport
                        Industries.WAYSTATION,
                        Industries.ORBITALSTATION,
                        Industries.MINING
                )),
                true, //if true, the planet will have visual junk orbiting and will play an ambient chatter audio track when the player is nearby
                false //used by the method to make a market hidden like a pirate base, not recommended for generating markets in a core world
        );
        fae_miningMarket.setSurveyLevel(MarketAPI.SurveyLevel.FULL);


        //add ocean planet
        PlanetAPI brinath = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_ocean", //unique id string
                star, //orbit focus for planet
                "Brinath", //display name of planet
                "water", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                170f, //starting angle in orbit
                200f, //planet size
                5700, //radius gap from the star
                270); //number of in-game days for it to orbit once // important, make sure fae capital has the same orbit days
        brinath.getMarket().addCondition(Conditions.HABITABLE);
        brinath.getMarket().addCondition(Conditions.MILD_CLIMATE);
        brinath.getMarket().addCondition(Conditions.WATER_SURFACE);
        brinath.getMarket().addCondition(Conditions.ORGANICS_ABUNDANT);
        brinath.setCustomDescriptionId("fae_ocean"); //reference descriptions.csv

        //ocean planet belt
        system.addAsteroidBelt(
                brinath, //orbit focus
                8, //number of asteroid entities
                600, //orbit radius
                255, //width of band
                220, //minimum and maximum visual orbit speeds of asteroids
                300,
                Terrain.ASTEROID_BELT, //ID of the terrain type that appears in the section above the abilities bar
                "Brinath's Shield" //display name
        );

        //add a ring texture. it will go under the asteroid entities generated above
        system.addRingBand(brinath,
                "misc", //used to access band texture, this is the name of a category in settings.json
                "rings_special0", //specific texture id in category misc in settings.json
                256f, //texture width, can be used for scaling shenanigans
                2,
                Color.white, //colour tint
                256f, //band width in game
                600, //same as above
                200f,
                null,
                null
        );

        //ocean planet moon
        PlanetAPI fae_vilnarua = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_oceanmoon", //unique id string
                brinath, //orbit focus for planet
                "Vilnârua", //display name of planet
                "arid", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                40f, //starting angle in orbit
                60f, //planet size
                900, //radius gap from the star
                12); //number of in-game days for it to orbit once
        fae_vilnarua.getMarket().addCondition(Conditions.THIN_ATMOSPHERE);
        fae_vilnarua.getMarket().addCondition(Conditions.LOW_GRAVITY);
        fae_vilnarua.getMarket().addCondition(Conditions.COLD);
        fae_vilnarua.getMarket().addCondition(Conditions.POOR_LIGHT);
        fae_vilnarua.setCustomDescriptionId("fae_oceanmoon"); //reference descriptions.csv

        //fae capital
        PlanetAPI fae_arbora = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_arbora", //unique id string
                star, //orbit focus for planet
                "Arborantia", //display name of planet
                "arbora_fae", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                20f, //starting angle in orbit
                300f, //planet size
                6000, //radius gap from the star
                270); //number of in-game days for it to orbit once
        fae_arbora.getMarket().addCondition(Conditions.HABITABLE);
        fae_arbora.getMarket().addCondition(Conditions.INIMICAL_BIOSPHERE);
        fae_arbora.getMarket().addCondition(Conditions.ORGANICS_ABUNDANT);
        fae_arbora.getMarket().addCondition(Conditions.FARMLAND_BOUNTIFUL);
        fae_arbora.setCustomDescriptionId("fae_capital"); //reference descriptions.csv

        //fae capital dust
        system.addRingBand(fae_arbora,
                "misc", //used to access band texture, this is the name of a category in settings.json
                "rings_dust0", //specific texture id in category misc in settings.json
                256f, //texture width, can be used for scaling shenanigans
                1,
                Color.white, //colour tint
                256f, //band width in game
                428, //distance from planet
                200f,
                null,
                null
        );

        //use helper method from other script to easily configure the market. feel free to copy it into your own project
        MarketAPI fae_capitalmarket = fae_AddMarketplace.addMarketplace( //A Market is separate to a Planet, and contains data about population, industries and conditions. This is a method from the other script in this mod, that will assign all marketplace conditions to the planet in one go, making it simple and easy
                "fairies", //Factions.INDEPENDENT references the id String of the Independent faction, so it is the same as writing "independent", but neater. This determines the Faction associated with this market
                fae_arbora, //the PlanetAPI variable that this market will be assigned to
                null, //some mods and vanilla will have additional floating space stations or other entities, that when accessed, will open this marketplace. We don't have any associated entities for this method to add, so we leave null
                "Arborantia", //Display name of market
                6, //population size
                new ArrayList<>(Arrays.asList( //List of conditions for this method to iterate through and add to the market
                        Conditions.POPULATION_6,
                        Conditions.HABITABLE,
                        Conditions.INIMICAL_BIOSPHERE,
                        Conditions.ORGANICS_ABUNDANT,
                        Conditions.FARMLAND_BOUNTIFUL
                )),
                new ArrayList<>(Arrays.asList( //list of submarkets for this method to iterate through and add to the market. if a military base industry was added to this market, it would be consistent to add a military submarket too
                        Submarkets.SUBMARKET_OPEN, //add a default open market
                        Submarkets.SUBMARKET_STORAGE, //add a player storage market
                        Submarkets.SUBMARKET_BLACK, //add a black market
                        Submarkets.GENERIC_MILITARY //add a military market
                )),
                new ArrayList<>(Arrays.asList( //list of industries for this method to iterate through and add to the market
                        Industries.POPULATION, //population industry is required for weirdness to not happen
                        Industries.MEGAPORT, //same with spaceport
                        Industries.FARMING,
                        Industries.MINING,
                        Industries.BATTLESTATION,
                        Industries.WAYSTATION,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE
                )),
                true, //if true, the planet will have visual junk orbiting and will play an ambient chatter audio track when the player is nearby
                false //used by the method to make a market hidden like a pirate base, not recommended for generating markets in a core world
        );
        fae_capitalmarket.addIndustry(Industries.ORBITALWORKS,
                Collections.singletonList(Items.PRISTINE_NANOFORGE));

        // Admiral Velixie
        PersonAPI velixie = Global.getFactory().createPerson();
        velixie.setId("velixie");
        velixie.setFaction("fairies");
        velixie.setGender(FullName.Gender.FEMALE);
        velixie.setRankId(Ranks.PILOT);
        velixie.setPostId(Ranks.POST_OFFICER);
        velixie.setImportance(PersonImportance.VERY_LOW);
        velixie.setPersonality(Personalities.AGGRESSIVE);
        velixie.getName().setFirst("Velixie");
        velixie.getName().setLast("Xielie");
        velixie.setPortraitSprite(Global.getSettings().getSpriteName("characters", velixie.getId()));
        velixie.getStats().setLevel(1);
        velixie.getStats().setSkillLevel(Skills.HELMSMANSHIP, 1);
        Global.getSector().getImportantPeople().addPerson(velixie);

        fae_capitalmarket.getCommDirectory().addPerson(velixie);
        fae_capitalmarket.getCommDirectory().getEntryForPerson(velixie).setHidden(false);
        fae_capitalmarket.addPerson(velixie);

        // Queen Xintalvia
        PersonAPI queen = Global.getFactory().createPerson();
        queen.setId("queen");
        queen.setFaction("fairies");
        queen.setGender(FullName.Gender.FEMALE);
        queen.setRankId(Ranks.FACTION_LEADER);
        queen.setPostId(Ranks.POST_ADMINISTRATOR);
        queen.setImportance(PersonImportance.VERY_HIGH);
        queen.setPersonality(Personalities.AGGRESSIVE);
        queen.getName().setFirst("Xintalvia");
        queen.getName().setLast("Xielie");
        queen.setPortraitSprite(Global.getSettings().getSpriteName("characters", queen.getId()));
        Global.getSector().getImportantPeople().addPerson(queen);
        Global.getSector().getImportantPeople().getPerson("queen").addTag("trade");

        fae_capitalmarket.getCommDirectory().addPerson(queen);
        fae_capitalmarket.getCommDirectory().getEntryForPerson(queen).setHidden(false);
        fae_capitalmarket.addPerson(queen);

        // Commander Lilith
        PersonAPI lilith = Global.getFactory().createPerson();
        lilith.setId("lilith");
        lilith.setFaction("fairies");
        lilith.setGender(FullName.Gender.FEMALE);
        lilith.setRankId(Ranks.GROUND_MAJOR);
        lilith.setPostId(Ranks.POST_BASE_COMMANDER);
        lilith.setImportance(PersonImportance.HIGH);
        lilith.getName().setFirst("Lilith");
        lilith.getName().setLast("Star");
        Global.getSector().getImportantPeople().addPerson(lilith);
        Global.getSector().getImportantPeople().getPerson("lilith").addTag("military");
        lilith.setPortraitSprite(Global.getSettings().getSpriteName("characters", lilith.getId()));

        fae_capitalmarket.getCommDirectory().addPerson(lilith);
        fae_capitalmarket.getCommDirectory().getEntryForPerson(lilith).setHidden(false);
        fae_capitalmarket.addPerson(lilith);

        //one of capital's many moons
        PlanetAPI fae_moon_one = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_moon_one", //unique id string
                fae_arbora, //orbit focus for planet
                "Laothir", //display name of planet
                "rocky_unstable", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                0f, //starting angle in orbit
                100f, //planet size
                900, //radius gap from the star
                24); //number of in-game days for it to orbit once
        fae_moon_one.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        fae_moon_one.getMarket().addCondition(Conditions.LOW_GRAVITY);
        fae_moon_one.getMarket().addCondition(Conditions.DECIVILIZED);
        fae_moon_one.getMarket().addCondition(Conditions.RUINS_SCATTERED);
        fae_moon_one.setCustomDescriptionId("fae_moon_one"); //reference descriptions.csv

        //one of capital's many moons
        PlanetAPI fae_moon_two = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_moon_two", //unique id string
                fae_arbora, //orbit focus for planet
                "Barathir", //display name of planet
                "barren-bombarded", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                60f, //starting angle in orbit
                100f, //planet size
                900, //radius gap from the star
                24); //number of in-game days for it to orbit once
        fae_moon_two.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        fae_moon_two.getMarket().addCondition(Conditions.LOW_GRAVITY);
        fae_moon_two.setCustomDescriptionId("fae_moon_two"); //reference descriptions.csv

        //one of capital's many moons
        PlanetAPI fae_moon_three = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_moon_three", //unique id string
                fae_arbora, //orbit focus for planet
                "Silethir", //display name of planet
                "barren_venuslike", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                120f, //starting angle in orbit
                100f, //planet size
                900, //radius gap from the star
                24); //number of in-game days for it to orbit once
        fae_moon_three.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        fae_moon_three.getMarket().addCondition(Conditions.LOW_GRAVITY);
        fae_moon_three.setCustomDescriptionId("fae_moon_three"); //reference descriptions.csv

        //one of capital's many moons
        PlanetAPI fae_moon_four = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_moon_four", //unique id string
                fae_arbora, //orbit focus for planet
                "Althir", //display name of planet
                "barren", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                180f, //starting angle in orbit
                100f, //planet size
                900, //radius gap from the star
                24); //number of in-game days for it to orbit once
        fae_moon_four.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        fae_moon_four.getMarket().addCondition(Conditions.LOW_GRAVITY);
        fae_moon_four.setCustomDescriptionId("fae_moon_four"); //reference descriptions.csv

        //one of capital's many moons
        PlanetAPI fae_moon_five = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_moon_five", //unique id string
                fae_arbora, //orbit focus for planet
                "Aêthir", //display name of planet
                "barren", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                240f, //starting angle in orbit
                100f, //planet size
                900, //radius gap from the star
                24); //number of in-game days for it to orbit once
        fae_moon_five.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        fae_moon_five.getMarket().addCondition(Conditions.LOW_GRAVITY);
        fae_moon_five.setCustomDescriptionId("fae_moon_five"); //reference descriptions.csv

        //one of capital's many moons
        PlanetAPI fae_moon_six = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_moon_six", //unique id string
                fae_arbora, //orbit focus for planet
                "Dathir", //display name of planet
                "barren", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                300f, //starting angle in orbit
                100f, //planet size
                900, //radius gap from the star
                24); //number of in-game days for it to orbit once
        fae_moon_six.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        fae_moon_six.getMarket().addCondition(Conditions.LOW_GRAVITY);
        fae_moon_six.setCustomDescriptionId("fae_moon_one"); //reference descriptions.csv

        //outer capital ring
        system.addRingBand(fae_arbora,
                "misc", //used to access band texture, this is the name of a category in settings.json
                "rings_dust0", //specific texture id in category misc in settings.json
                256f, //texture width, can be used for scaling shenanigans
                4,
                Color.white, //colour tint
                128f, //band width in game
                1120, //distance from capital
                200f,
                null,
                null
        );


        //add ocean planet
        PlanetAPI fae_ice = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "fae_ice", //unique id string
                star, //orbit focus for planet
                "Estêle", //display name of planet
                "ice_giant", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                340f, //starting angle in orbit
                160f, //planet size
                8400, //radius gap from the star
                340); //number of in-game days for it to orbit once
        fae_ice.getMarket().addCondition(Conditions.VERY_COLD);
        fae_ice.getMarket().addCondition(Conditions.THIN_ATMOSPHERE);
        fae_ice.getMarket().addCondition(Conditions.DARK);
        fae_ice.getMarket().addCondition(Conditions.VOLATILES_ABUNDANT);
        fae_ice.getMarket().addCondition(Conditions.ICE);
        fae_ice.setCustomDescriptionId("fae_ice"); //reference descriptions.csv

        //add an asteroid belt. asteroids are separate entities inside these, it will randomly distribute a defined number of them around the ring
        system.addAsteroidBelt(
                star, //orbit focus
                80, //number of asteroid entities
                9000, //orbit radius
                255, //width of band
                190, //minimum and maximum visual orbit speeds of asteroids
                220,
                Terrain.ASTEROID_BELT, //ID of the terrain type that appears in the section above the abilities bar
                "Fairy breast milk III" //display name
        );

        //add a ring texture. it will go under the asteroid entities generated above
        system.addRingBand(star,
                "misc", //used to access band texture, this is the name of a category in settings.json
                "rings_asteroids0", //specific texture id in category misc in settings.json
                256f, //texture width, can be used for scaling shenanigans
                2,
                Color.white, //colour tint
                256f, //band width in game
                9000, //same as above
                200f,
                null,
                null
        );
        SectorEntityToken fae_rebel = system.addCustomEntity("fae_rebel","IARO Headquarters","station_mining00", "fairies");
        fae_rebel.setCircularOrbitPointingDown(star,0,4150,135);
        MarketAPI fae_rebelMarket = Global.getFactory().createMarket("fae_rebelmarket","IARO Headquarters",4);
        fae_rebel.setCustomDescriptionId("fae_rebel");
        fae_rebel.setMarket(fae_rebelMarket);
        fae_rebelMarket.setPrimaryEntity(fae_rebel);
        //use helper method from other script to easily configure the market. feel free to copy it into your own project
        fae_rebelMarket = fae_AddMarketplace.addMarketplace( //A Market is separate to a Planet, and contains data about population, industries and conditions. This is a method from the other script in this mod, that will assign all marketplace conditions to the planet in one go, making it simple and easy
                "fairies", //Factions.INDEPENDENT references the id String of the Independent faction, so it is the same as writing "independent", but neater. This determines the Faction associated with this market
                fae_rebel, //the PlanetAPI variable that this market will be assigned to
                null, //some mods and vanilla will have additional floating space stations or other entities, that when accessed, will open this marketplace. We don't have any associated entities for this method to add, so we leave null
                "IARO Headquarters", //Display name of market
                4, //population size
                new ArrayList<>(Arrays.asList( //List of conditions for this method to iterate through and add to the market
                        Conditions.POPULATION_4,
                        Conditions.ORE_RICH,
                        Conditions.RARE_ORE_ABUNDANT,
                        Conditions.COLD,
                        Conditions.FREE_PORT
                )),
                new ArrayList<>(Arrays.asList( //list of submarkets for this method to iterate through and add to the market. if a military base industry was added to this market, it would be consistent to add a military submarket too
                        Submarkets.SUBMARKET_OPEN, //add a default open market
                        Submarkets.SUBMARKET_STORAGE, //add a player storage market
                        Submarkets.SUBMARKET_BLACK //add a black market
                )),
                new ArrayList<>(Arrays.asList( //list of industries for this method to iterate through and add to the market
                        Industries.POPULATION, //population industry is required for weirdness to not happen
                        Industries.SPACEPORT, //same with spaceport
                        Industries.WAYSTATION,
                        Industries.ORBITALSTATION,
                        Industries.MINING,
                        Industries.HEAVYINDUSTRY
                )),
                true, //if true, the planet will have visual junk orbiting and will play an ambient chatter audio track when the player is nearby
                false //used by the method to make a market hidden like a pirate base, not recommended for generating markets in a core world
        );
        fae_rebelMarket.setSurveyLevel(MarketAPI.SurveyLevel.FULL);


        // Rebel Leader
        PersonAPI miniem = Global.getFactory().createPerson();
        miniem.setId("miniem");
        miniem.setFaction("fairies");
        miniem.setGender(FullName.Gender.FEMALE);
        miniem.setRankId(Ranks.FREEDOM_FIGHTER);
        miniem.setPostId(Ranks.POST_BASE_COMMANDER);
        miniem.setPersonality(Personalities.AGGRESSIVE);
        miniem.setImportance(PersonImportance.HIGH);
        miniem.getName().setFirst("Miniêm"); //'Flight of a dove'
        miniem.getName().setLast("Vejan"); //'Flambeau'
        Global.getSector().getImportantPeople().addPerson(miniem);
        Global.getSector().getImportantPeople().getPerson("miniem").addTag("military");
        miniem.setPortraitSprite(Global.getSettings().getSpriteName("characters", miniem.getId()));

        fae_rebelMarket.getCommDirectory().addPerson(miniem);
        fae_rebelMarket.getCommDirectory().getEntryForPerson(miniem).setHidden(false);
        fae_rebelMarket.addPerson(miniem);

        // Cringe
        PersonAPI kusakari = Global.getFactory().createPerson();
        kusakari.setId("kusakari");
        kusakari.setFaction("fairies");
        kusakari.setGender(FullName.Gender.FEMALE);
        kusakari.setRankId(Ranks.CITIZEN);
        kusakari.setPostId(Ranks.POST_SCIENTIST);
        kusakari.setPersonality(Personalities.RECKLESS);
        kusakari.setImportance(PersonImportance.VERY_HIGH);
        kusakari.getName().setFirst("Vaera");
        kusakari.getName().setLast("Loran");
        kusakari.setPortraitSprite(Global.getSettings().getSpriteName("characters", kusakari.getId()));

        fae_rebelMarket.getCommDirectory().addPerson(kusakari);
        fae_rebelMarket.getCommDirectory().getEntryForPerson(kusakari).setHidden(false);
        fae_rebelMarket.addPerson(kusakari);

        //add comm relay entity to system
        SectorEntityToken fae_relay = system.addCustomEntity(
                "fae_relay",
                "Ancient Relay",
                Entities.COMM_RELAY,
                "fairies"
        );
        //assign an orbit
        fae_relay.setCircularOrbit(star, 270f, 5800f, 270f); //important, make sure fae capital has the same orbit days

        //Stable point
        SectorEntityToken fae_comm = system.addCustomEntity(
                "fae_nav",
                "Ancient Buoy",
                Entities.NAV_BUOY,
                "fairies"
        );
        fae_relay.setCircularOrbit(star, 270f, 5800f, 270f); //important, make sure fae capital has the same orbit days


        //autogenerate jump points that will appear in hyperspace and in system
        system.autogenerateHyperspaceJumpPoints(true, true);

        //the following is hyperspace cleanup code that will remove hyperstorm clouds around this system's location in hyperspace
        //don't need to worry about this, it's more or less copied from vanilla

        //set up hyperspace editor plugin
        HyperspaceTerrainPlugin hyperspaceTerrainPlugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin(); //get instance of hyperspace terrain
        NebulaEditor nebulaEditor = new NebulaEditor(hyperspaceTerrainPlugin); //object used to make changes to hyperspace nebula

        //set up radiuses in hyperspace of system
        float minHyperspaceRadius = hyperspaceTerrainPlugin.getTileSize() * 2f; //minimum radius is two 'tiles'
        float maxHyperspaceRadius = system.getMaxRadiusInHyperspace();

        //hyperstorm-b-gone (around system in hyperspace)
        nebulaEditor.clearArc(system.getLocation().x, system.getLocation().y, 0, minHyperspaceRadius + maxHyperspaceRadius, 0f, 360f, 0.25f);

        //add nebula from png file
        Misc.addNebulaFromPNG("data/campaign/terrain/sex_anadolu.png", 0.0F, 0.0F, system, "terrain", "nebula", 4, 4, StarAge.OLD);
    }
}
