package bing.faesector.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;

public class fae_people {

    public static String queen = "queen"; //bow to her
    public static String lilith = "lilith"; //we stan mommy lilith
    public static String pixie = "pixie"; //our favourite war criminal
    public static String miniem = "miniem"; //she fights so we dont have to
    public static String kusakari = "kusakari"; //you made what

    public static void create() {
        createFaepeople();
    }

    public static PersonAPI createFaepeople() {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();

        MarketAPI market1 = Global.getSector().getEconomy().getMarket("fae_capitalmarket");
        if (market1 != null) {
            // Queen Xintalvia
            PersonAPI queenperson = Global.getFactory().createPerson();
            queenperson.setId(queen);
            queenperson.setFaction("fairies");
            queenperson.setGender(FullName.Gender.FEMALE);
            queenperson.setRankId(Ranks.FACTION_LEADER);
            queenperson.setPostId(Ranks.POST_FACTION_LEADER);
            queenperson.addTag(Tags.CONTACT_MILITARY);
            queenperson.setImportance(PersonImportance.VERY_HIGH);
            queenperson.getName().setFirst("Xintalvia");
            queenperson.getName().setLast("Xielie");
            queenperson.setVoice(Voices.SOLDIER);
            queenperson.setPersonality(Personalities.AGGRESSIVE);
            queenperson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "fae_Queen"));
            queenperson.getStats().setLevel(10);
            queenperson.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 2);
            queenperson.getStats().setSkillLevel(Skills.ENERGY_WEAPON_MASTERY, 2);
            queenperson.getStats().setSkillLevel(Skills.SYSTEMS_EXPERTISE, 2);
            queenperson.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
            queenperson.getStats().setSkillLevel(Skills.ORDNANCE_EXPERTISE, 2);
            queenperson.getStats().setSkillLevel(Skills.MISSILE_SPECIALIZATION, 2);
            queenperson.getStats().setSkillLevel(Skills.POLARIZED_ARMOR, 2);
            queenperson.getStats().setSkillLevel(Skills.CREW_TRAINING, 1);
            queenperson.getStats().setSkillLevel(Skills.WOLFPACK_TACTICS, 1);
            queenperson.getStats().setSkillLevel(Skills.SUPPORT_DOCTRINE, 1);
            queenperson.addTag("coff_nocapture");
            market1.addPerson(queenperson);
            market1.getCommDirectory().addPerson(queenperson, 0);
            ip.addPerson(queenperson);

            // Commander Lilith
            PersonAPI lilithperson = Global.getFactory().createPerson();
            lilithperson.setId(lilith);
            lilithperson.setFaction("fairies");
            lilithperson.setGender(FullName.Gender.MALE);
            lilithperson.setPostId(Ranks.POST_FLEET_COMMANDER);
            lilithperson.setRankId(Ranks.SPACE_ADMIRAL);
            lilithperson.setVoice(Voices.ARISTO);
            lilithperson.addTag(Tags.CONTACT_MILITARY);
            lilithperson.setImportance(PersonImportance.VERY_HIGH);
            lilithperson.getName().setFirst("Lilith");
            lilithperson.getName().setLast("Salemsky");
            lilithperson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "fae_Lilith"));
            lilithperson.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 1);
            market1.setAdmin(lilithperson);
            market1.getCommDirectory().addPerson(lilithperson, 1);
            market1.addPerson(lilithperson);
            ip.addPerson(lilithperson);

            // Admiral Velixie
            PersonAPI velixieperson = Global.getFactory().createPerson();
            velixieperson.setId(pixie);
            velixieperson.setFaction("fairies");
            velixieperson.setGender(FullName.Gender.FEMALE);
            velixieperson.setRankId(Ranks.SPACE_COMMANDER);
            velixieperson.setPostId(Ranks.POST_OFFICER);
            velixieperson.addTag(Tags.CONTACT_MILITARY);
            velixieperson.setImportance(PersonImportance.VERY_HIGH);
            velixieperson.getName().setFirst("Velixie");
            velixieperson.getName().setLast("Xielie");
            velixieperson.setVoice(Voices.SOLDIER);
            velixieperson.setPersonality(Personalities.AGGRESSIVE);
            velixieperson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "fae_Pixie"));
            velixieperson.getStats().setLevel(12);
            velixieperson.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 2);
            velixieperson.getStats().setSkillLevel(Skills.ENERGY_WEAPON_MASTERY, 2);
            velixieperson.getStats().setSkillLevel(Skills.SYSTEMS_EXPERTISE, 2);
            velixieperson.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
            velixieperson.getStats().setSkillLevel(Skills.ORDNANCE_EXPERTISE, 2);
            velixieperson.getStats().setSkillLevel(Skills.POINT_DEFENSE, 2);
            velixieperson.getStats().setSkillLevel(Skills.MISSILE_SPECIALIZATION, 2);
            velixieperson.getStats().setSkillLevel(Skills.POLARIZED_ARMOR, 2);
            velixieperson.getStats().setSkillLevel(Skills.CREW_TRAINING, 1);
            velixieperson.getStats().setSkillLevel(Skills.CARRIER_GROUP, 1);
            velixieperson.getStats().setSkillLevel(Skills.WOLFPACK_TACTICS, 1);
            velixieperson.getStats().setSkillLevel(Skills.SUPPORT_DOCTRINE, 1);
            velixieperson.addTag("coff_nocapture");
            market1.addPerson(velixieperson);
            market1.getCommDirectory().addPerson(velixieperson, 2);
            ip.addPerson(velixieperson);
        }

        MarketAPI market2 = Global.getSector().getEconomy().getMarket("fae_miningMarket");
        if (market2 != null){
            // Rebel Leader
            PersonAPI miniemperson = Global.getFactory().createPerson();
            miniemperson.setId(miniem);
            miniemperson.setFaction(Factions.PIRATES);
            miniemperson.setGender(FullName.Gender.FEMALE);
            miniemperson.setPostId(Ranks.POST_WARLORD);
            miniemperson.setRankId(Ranks.SPACE_COMMANDER);
            miniemperson.setVoice(Voices.SPACER);
            miniemperson.setImportance(PersonImportance.MEDIUM);
            miniemperson.addTag(Tags.CONTACT_UNDERWORLD);
            miniemperson.getName().setFirst("Miniem");
            miniemperson.getName().setLast("Ritatoni");
            miniemperson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "fae_Miniem"));
            market2.addPerson(miniemperson);
            market2.getCommDirectory().addPerson(miniemperson, 0);
            market2.setAdmin(miniemperson);
            ip.addPerson(miniemperson);

            // Cringe

            PersonAPI kusakariperson = Global.getFactory().createPerson();
            kusakariperson.setId(fae_people.kusakari);
            kusakariperson.setFaction(Factions.PIRATES);
            kusakariperson.setGender(FullName.Gender.FEMALE);
            kusakariperson.setPostId(Ranks.POST_SCIENTIST);
            kusakariperson.setRankId(Ranks.CITIZEN);
            kusakariperson.setVoice(Voices.SCIENTIST);
            kusakariperson.setImportance(PersonImportance.MEDIUM);
            kusakariperson.addTag(Tags.CONTACT_UNDERWORLD);
            kusakariperson.getName().setFirst("Kuyo");
            kusakariperson.getName().setLast("Kusakari");
            kusakariperson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "fae_Kusakari"));
            market2.addPerson(kusakariperson);
            market2.getCommDirectory().addPerson(kusakariperson, 1);
            ip.addPerson(kusakariperson);
        }

        return null;
    }
}
