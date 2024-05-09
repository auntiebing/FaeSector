package data.missions.fae_ourfinesthour;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;
import com.fs.starfarer.api.util.Misc;
import java.util.List;
import java.util.Random;

public class MissionDefinition implements MissionDefinitionPlugin {

	public void defineMission(MissionDefinitionAPI api) {

		// Set up the fleets
		api.initFleet(FleetSide.PLAYER, "XRS", FleetGoal.ATTACK, false, 10);
		api.initFleet(FleetSide.ENEMY, "HSS", FleetGoal.ATTACK, true, 10);

		// Set a blurb for each fleet
		api.setFleetTagline(FleetSide.PLAYER, "Inran Testing Fleet. Just a bunch of Lupines.");
		api.setFleetTagline(FleetSide.ENEMY, "One poor, poor Onslaught.");
		
		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("Brother is not about to have a good time.");
		api.addBriefingItem("Don't lose the flagship.");
		//api.addBriefingItem("Maintain tactical awareness and use superior mobility to choose your battles.");
		//api.addBriefingItem("Remember: If you engage the enemy flagship in a fair fight, you will lose.");
		
		// Set up the player's fleet
		// Set up the player's fleet
		api.getDefaultCommander(FleetSide.PLAYER).getStats().setSkillLevel(Skills.ENERGY_WEAPON_MASTERY, 1);
		FleetMemberAPI member = api.addToFleet(FleetSide.PLAYER, "faesector_lupine_Brute", FleetMemberType.SHIP, "XRS Holdma", true);
		PersonAPI officer = OfficerManagerEvent.createOfficer(Global.getSettings().getModManager().isModEnabled("faesector") ? Global.getSector().getFaction("fairies") : Global.getSector().getFaction(Factions.INDEPENDENT), 4, FleetFactoryV3.getSkillPrefForShip(member), true, null, true, false, 0, new Random());
		member.setCaptain(officer);
		FleetMemberAPI member2 = api.addToFleet(FleetSide.PLAYER, "faesector_lupine_Brute", FleetMemberType.SHIP, "XRS Sugma", false);
		member2.setCaptain(OfficerManagerEvent.createOfficer(Global.getSettings().getModManager().isModEnabled("faesector") ? Global.getSector().getFaction("fairies") : Global.getSector().getFaction(Factions.INDEPENDENT), 1, FleetFactoryV3.getSkillPrefForShip(member2), true, null, true, false, 0, new Random()));
		FleetMemberAPI member3 = api.addToFleet(FleetSide.PLAYER, "faesector_lupine_Brute", FleetMemberType.SHIP, "XRS Ligma", false);
		member3.setCaptain(OfficerManagerEvent.createOfficer(Global.getSettings().getModManager().isModEnabled("faesector") ? Global.getSector().getFaction("fairies") : Global.getSector().getFaction(Factions.INDEPENDENT), 1, FleetFactoryV3.getSkillPrefForShip(member3), true, null, true, false, 0, new Random()));
		FleetMemberAPI member4 = api.addToFleet(FleetSide.PLAYER, "faesector_lupine_Brute", FleetMemberType.SHIP, "XRS Grabma", false);
		member4.setCaptain(OfficerManagerEvent.createOfficer(Global.getSettings().getModManager().isModEnabled("faesector") ? Global.getSector().getFaction("fairies") : Global.getSector().getFaction(Factions.INDEPENDENT), 1, FleetFactoryV3.getSkillPrefForShip(member4), true, null, true, false, 0, new Random()));

		// Mark player flagship as essential
		api.defeatOnShipLoss("XRS Holdma");

		// Set up the enemy fleet
		api.getDefaultCommander(FleetSide.ENEMY).getStats().setSkillLevel(Skills.FIGHTER_UPLINK, 1);
		FleetMemberAPI member7 = api.addToFleet(FleetSide.ENEMY, "onslaught_Standard", FleetMemberType.SHIP, "HSS Naga", true);
		PersonAPI officer2 = OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.HEGEMONY), 5, FleetFactoryV3.getSkillPrefForShip(member7), true, null, true, true, 1, new Random());
		officer2.getName().setFirst(""); //No first name? unless I find it somehow....
		officer2.getName().setLast("Jensulte");
		officer2.setGender(FullName.Gender.MALE);
		officer2.setPortraitSprite(OfficerManagerEvent.pickPortraitPreferNonDuplicate(Global.getSector().getFaction(Factions.HEGEMONY), FullName.Gender.MALE));
		officer2.setPersonality(Personalities.AGGRESSIVE);
		member7.setCaptain(officer2);
		//FleetMemberAPI member8 = api.addToFleet(FleetSide.ENEMY, "dominator_Assault", FleetMemberType.SHIP, false);
		//member8.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.HEGEMONY), 1, FleetFactoryV3.getSkillPrefForShip(member8), true, null, true, true, 1, new Random()));
		//member8.getCaptain().setPersonality(Personalities.AGGRESSIVE);
		//FleetMemberAPI member9 = api.addToFleet(FleetSide.ENEMY, "dominator_Assault", FleetMemberType.SHIP, false);
		//member9.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.HEGEMONY), 1, FleetFactoryV3.getSkillPrefForShip(member9), true, null, true, true, 1, new Random()));
		//member9.getCaptain().setPersonality(Personalities.AGGRESSIVE);
		//FleetMemberAPI member10 = api.addToFleet(FleetSide.ENEMY, "mora_Assault", FleetMemberType.SHIP, false);
		//member10.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.HEGEMONY), 1, FleetFactoryV3.getSkillPrefForShip(member10), true, null, true, true, 1, new Random()));
		//member10.getCaptain().setPersonality(Personalities.STEADY);
		//FleetMemberAPI member11 = api.addToFleet(FleetSide.ENEMY, "mora_Strike", FleetMemberType.SHIP, false);
		//member11.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.HEGEMONY), 1, FleetFactoryV3.getSkillPrefForShip(member11), true, null, true, true, 1, new Random()));
		//member11.getCaptain().setPersonality(Personalities.STEADY);
		//FleetMemberAPI member12 = api.addToFleet(FleetSide.ENEMY, "mora_Strike", FleetMemberType.SHIP, false);
		//member12.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.HEGEMONY), 1, FleetFactoryV3.getSkillPrefForShip(member12), true, null, true, true, 1, new Random()));
		//member12.getCaptain().setPersonality(Personalities.STEADY);
		//FleetMemberAPI member13 = api.addToFleet(FleetSide.ENEMY, "mora_Support", FleetMemberType.SHIP, false);
		//member13.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.HEGEMONY), 1, FleetFactoryV3.getSkillPrefForShip(member13), true, null, true, true, 1, new Random()));
		//member13.getCaptain().setPersonality(Personalities.STEADY);
		//api.addToFleet(FleetSide.ENEMY, "condor_Support", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "condor_Support", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "enforcer_Assault", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "enforcer_Assault", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "enforcer_CS", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "hound_Standard", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);



		// Set up the map.
		float width = 24000f;
		float height = 18000f;
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);

		float minX = -width/2;
		float minY = -height/2;

		for (int i = 0; i < 15; i++) {
			float x = (float) Math.random() * width - width/2;
			float y = (float) Math.random() * height - height/2;
			float radius = 100f + (float) Math.random() * 900f;
			api.addNebula(x, y, radius);
		}

		api.addNebula(minX + width * 0.8f - 1000, minY + height * 0.4f, 2000);
		api.addNebula(minX + width * 0.8f - 1000, minY + height * 0.5f, 2000);
		api.addNebula(minX + width * 0.8f - 1000, minY + height * 0.6f, 2000);

		api.addObjective(minX + width * 0.8f - 1000, minY + height * 0.4f, "nav_buoy");
		api.addObjective(minX + width * 0.8f - 1000, minY + height * 0.6f, "nav_buoy");
		api.addObjective(minX + width * 0.3f + 1000, minY + height * 0.3f, "comm_relay");
		api.addObjective(minX + width * 0.3f + 1000, minY + height * 0.7f, "comm_relay");
		api.addObjective(minX + width * 0.5f, minY + height * 0.5f, "sensor_array");
		api.addObjective(minX + width * 0.2f + 1000, minY + height * 0.5f, "sensor_array");

		// Add an asteroid field
		api.addAsteroidField(minX + width * 0.3f, minY, 90, 3000f,
				20f, 70f, 50);

		// Add some planets.  These are defined in data/config/planets.json.
		//api.addPlanet(0, 0, 200f, "irradiated", 350f, true);

		api.addPlugin(new BaseEveryFrameCombatPlugin() {
			public void init(CombatEngineAPI engine) {
			}
			public void advance(float amount, List events) {
				if (Global.getCombatEngine().isPaused()) {
					return;
				}
				for (ShipAPI ship : Global.getCombatEngine().getShips()) {
					if (ship.getCustomData().get("poopystinky") == null) {
						if (ship.getCaptain() != null && ship.getOwner() == 0 && ship.getCaptain().getStats().getSkillsCopy().size() > 4) {
							String text = "";
							for (int u = 4; u < ship.getCaptain().getStats().getSkillsCopy().size(); u++) {
								if (u < ship.getCaptain().getStats().getSkillsCopy().size()-1) {text = text+(((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getLevel() > 1 ?  ((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getSkill().getName()+"+, " :  ((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getSkill().getName()+", ");} else {text = text+(((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getLevel() > 1 ? ((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getSkill().getName()+"+." :  ((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getSkill().getName()+".");}
							}
							if (ship.getFleetMember() != null) {
								Global.getCombatEngine().getCombatUI().addMessage(1, ship.getFleetMember(), Misc.getPositiveHighlightColor(), ship.getName(), Misc.getTextColor(), "", Global.getSettings().getColor("standardTextColor"), "is skilled in "+text);}
						}
						ship.setCurrentCR(ship.getCurrentCR()+ship.getMutableStats().getMaxCombatReadiness().getModifiedValue()); //Properly adds the max CR, for some reason it cannot be caught as FleetMemberAPI or this would have been easier...
						ship.setCRAtDeployment(ship.getCRAtDeployment()+ship.getMutableStats().getMaxCombatReadiness().getModifiedValue()); //This only affects the "score" result of said mission, but the algorithm is mostly 100% since you have to basically LOSE ships to lose score. I don't think this needs setting, but eh couldn't help but tried.
						ship.setCustomData("poopystinky", true); //Fires once per ship.
					}
				}
			}
		});
	}

}






