package bing.faesector.data.missions.fae_unwelcomevisitors;

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
		api.initFleet(FleetSide.PLAYER, "XRS", FleetGoal.ATTACK, true);
		api.initFleet(FleetSide.ENEMY, "HSS", FleetGoal.ATTACK, false, 5);

		// Set a blurb for each fleet
		api.setFleetTagline(FleetSide.PLAYER, "XRS Holdma and her escort");
		api.setFleetTagline(FleetSide.ENEMY, "Hegemony Vanguard led by HSS Defiant");
		
		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("Destroy the invaders. Let none survive to tell the tale.");
		api.addBriefingItem("Outnumbered, but not outgunned. Pick your fights carefully and do not allow yourself to be surrounded.");
		api.addBriefingItem("Be wary of missiles and strike fighters. Utilize the EMP of your Zelênthar Boost to escape and neutralize these hazards.");
		api.addBriefingItem("Do not allow your Lupine-class Prototype to be destroyed.");
		
		// Set up the player's fleet
		FleetMemberAPI member = api.addToFleet(FleetSide.PLAYER, "faesector_lupine_Brute", FleetMemberType.SHIP, "XRS Holdma", true);
		PersonAPI officer = Global.getSettings().createPerson();
		officer.setPortraitSprite(OfficerManagerEvent.pickPortraitPreferNonDuplicate(Global.getSector().getFaction(Factions.INDEPENDENT), FullName.Gender.ANY));
		officer.getStats().setSkillLevel(Skills.SYSTEMS_EXPERTISE, 2);
		officer.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 1);
		officer.getStats().setSkillLevel(Skills.ENERGY_WEAPON_MASTERY, 1);
		officer.getStats().setSkillLevel(Skills.GUNNERY_IMPLANTS, 1);
		officer.getStats().setSkillLevel(Skills.MISSILE_SPECIALIZATION, 1);
		officer.getStats().setLevel(5);
		member.setCaptain(officer);

		FleetMemberAPI member2 = api.addToFleet(FleetSide.PLAYER, "lasher_CS", FleetMemberType.SHIP, "XRS Sugma", false);
		member2.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.INDEPENDENT), 1, FleetFactoryV3.getSkillPrefForShip(member2), true, null, true, true, 1, new Random()));
		FleetMemberAPI member3 = api.addToFleet(FleetSide.PLAYER, "condor_Support", FleetMemberType.SHIP, "XRS Ligma", false);
		member3.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.INDEPENDENT), 2, FleetFactoryV3.getSkillPrefForShip(member3), true, null, true, true, 1, new Random()));

		// Mark flagship as essential
		api.defeatOnShipLoss("ISS Enki");

		// Set up the enemy fleet
		FleetMemberAPI phoenix = api.addToFleet(FleetSide.ENEMY, "eagle_xiv_Elite", FleetMemberType.SHIP, "HSS Defiant", false);
		PersonAPI officer2 = Global.getSettings().createPerson();
		officer2.getStats().setSkillLevel(Skills.BALLISTIC_MASTERY, 1);
		officer2.getStats().setSkillLevel(Skills.ORDNANCE_EXPERTISE, 1);
		officer2.getStats().setSkillLevel(Skills.HELMSMANSHIP, 1);
		officer2.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
		officer2.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 1);
		officer2.getStats().setLevel(5);
		officer2.setFaction(Factions.HEGEMONY);
		officer2.setPortraitSprite(OfficerManagerEvent.pickPortraitPreferNonDuplicate(Global.getSector().getFaction(Factions.HEGEMONY), FullName.Gender.ANY));
		phoenix.setCaptain(officer2);

		FleetMemberAPI heg1 = api.addToFleet(FleetSide.ENEMY, "enforcer_XIV_Elite", FleetMemberType.SHIP, "HSS Magic", false);
		heg1.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.HEGEMONY), 4, FleetFactoryV3.getSkillPrefForShip(heg1), true, null, true, true, 1, new Random()));
		FleetMemberAPI heg2 = api.addToFleet(FleetSide.ENEMY, "enforcer_Assault", FleetMemberType.SHIP, false);
		heg2.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction(Factions.HEGEMONY), 3, FleetFactoryV3.getSkillPrefForShip(heg2), true, null, true, true, 1, new Random()));
		api.addToFleet(FleetSide.ENEMY, "condor_Strike", FleetMemberType.SHIP, "HSS Bulwark", false);

		api.addToFleet(FleetSide.ENEMY, "hound_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "hound_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);





		// Set up the map.
		float width = 24000f;
		float height = 18000f;
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);

		float minX = -width/2;
		float minY = -height/2;

		// All the addXXX methods take a pair of coordinates followed by data for
		// whatever object is being added.

		// And a few random ones to spice up the playing field.
		// A similar approach can be used to randomize everything
		// else, including fleet composition.
		for (int i = 0; i < 25; i++) {
			float x = (float) Math.random() * width - width/2;
			float y = (float) Math.random() * height - height/2;
			float radius = 400f + (float) Math.random() * 1000f;
			api.addNebula(x, y, radius);
		}

		// Add objectives. These can be captured by each side
		// and provide stat bonuses and extra command points to
		// bring in reinforcements.
		// Reinforcements only matter for large fleets - in this
		// case, assuming a 100 command point battle size,
		// both fleets will be able to deploy fully right away.
		api.addObjective(minX + width * 0.2f + 400 + 3000, minY + height * 0.2f + 400 + 2000, "sensor_array");
		api.addObjective(minX + width * 0.4f + 2000, minY + height * 0.7f, "sensor_array");
		api.addObjective(minX + width * 0.75f - 2000, minY + height * 0.7f, "comm_relay");
		api.addObjective(minX + width * 0.2f + 3000, minY + height * 0.5f, "nav_buoy");
		api.addObjective(minX + width * 0.85f - 3000, minY + height * 0.4f, "nav_buoy");

		//api.addPlanet(0, 0, 500f, "ice_giant", 300f, true);
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






