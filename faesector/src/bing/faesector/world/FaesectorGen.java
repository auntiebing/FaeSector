package bing.faesector.world;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import bing.faesector.world.systems.arbora;

public class FaesectorGen implements SectorGeneratorPlugin
{
    @Override
    public void generate(SectorAPI sector)
    {
        initFactionRelationships(sector);
        new arbora().generate(sector);
}

    public static void initFactionRelationships(SectorAPI sector)
    {
        FactionAPI fairies = sector.getFaction("fairies");
        FactionAPI hegemony = sector.getFaction(Factions.HEGEMONY);
        FactionAPI tritachyon = sector.getFaction(Factions.TRITACHYON);
        FactionAPI pirates = sector.getFaction(Factions.PIRATES);
        FactionAPI independent = sector.getFaction(Factions.INDEPENDENT);
        FactionAPI kol = sector.getFaction(Factions.KOL);
        FactionAPI church = sector.getFaction(Factions.LUDDIC_CHURCH);
        FactionAPI path = sector.getFaction(Factions.LUDDIC_PATH);
        FactionAPI diktat = sector.getFaction(Factions.DIKTAT);
        FactionAPI league = sector.getFaction(Factions.PERSEAN);
        FactionAPI remnants = sector.getFaction(Factions.REMNANTS);
        FactionAPI neutral = sector.getFaction(Factions.NEUTRAL);

        fairies.setRelationship(hegemony.getId(), RepLevel.WELCOMING);
        fairies.setRelationship(tritachyon.getId(), RepLevel.INHOSPITABLE);
        fairies.setRelationship(pirates.getId(), RepLevel.VENGEFUL);
        fairies.setRelationship(independent.getId(), RepLevel.FAVORABLE);
        fairies.setRelationship(kol.getId(), RepLevel.HOSTILE);
        fairies.setRelationship(church.getId(), RepLevel.HOSTILE);
        fairies.setRelationship(path.getId(), RepLevel.VENGEFUL);
        fairies.setRelationship(diktat.getId(), RepLevel.HOSTILE);
        fairies.setRelationship(league.getId(), RepLevel.INHOSPITABLE);
        fairies.setRelationship(remnants.getId(), RepLevel.HOSTILE);

        //fairies.setRelationship("blade_breakers", RepLevel.VENGEFUL);
        //fairies.setRelationship("magellan_protectorate", RepLevel.HOSTILE);
        //fairies.setRelationship("magellan_leveller", RepLevel.SUSPICIOUS);
        //fairies.setRelationship("shadow_industry", RepLevel.FAVORABLE);
        //fairies.setRelationship("blackrock_driveyards", RepLevel.SUSPICIOUS);
        //fairies.setRelationship("tiandong", RepLevel.WELCOMING);
        //fairies.setRelationship("diableavionics", RepLevel.HOSTILE);
        //fairies.setRelationship("roider", RepLevel.WELCOMING);
        //fairies.setRelationship("al_ars", RepLevel.HOSTILE);
        //fairies.setRelationship("scalartech", RepLevel.HOSTILE);
        //fairies.setRelationship("vic", RepLevel.HOSTILE);
        //fairies.setRelationship("new_galactic_order", RepLevel.VENGEFUL);
    }
}