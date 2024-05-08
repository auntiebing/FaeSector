package bing.faesector.data;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.WeightedRandomPicker;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.List;
import java.util.*;

public class fae_misc {


    public static String str(String key){
	    return Global.getSettings().getString("faesector", key);
    }

    public static Color adjustAlpha(Color color, float alpha){
        float f = alpha;
        if(f>1f)f=1f;
        if(f<0f)f=0f;
        Color result = new Color(color.getRed(),color.getGreen(),color.getBlue(), (int)(254*f));
        return result;
    }

    public static List<Vector2f> getAbsoluteFireOffsetCoordinates(WeaponAPI weapon){
        List<Vector2f> result = new ArrayList<>();
        List<Vector2f> output = new ArrayList<>();
        if(weapon.getSlot().isHidden()){
            output = weapon.getSpec().getHiddenFireOffsets();
        }
        if(weapon.getSlot().isTurret()){
            output = weapon.getSpec().getTurretFireOffsets();
        }
        if(weapon.getSlot().isHardpoint()){
            output = weapon.getSpec().getHardpointFireOffsets();
        }


        if(output.size()>0){
            //result = output;
            result = convertOffsetsToAbsolute(output, weapon);
        }

        return result;
    }

    public static List<Vector2f> convertOffsetsToAbsolute(List<Vector2f> input, WeaponAPI weapon){
        List<Vector2f> result = new ArrayList<>();
        for(int i=0; i<input.size(); i++){
            Vector2f offset = MathUtils.getPointOnCircumference(
                    MathUtils.getPointOnCircumference(weapon.getLocation(), input.get(i).getX(), weapon.getCurrAngle()),
                    input.get(i).getY(), weapon.getCurrAngle()+90f);
            result.add(offset);
        }
        return result;
    }

    public static boolean isProjValid(DamagingProjectileAPI pr) {

        if(pr==null)return false;
        if(pr.isExpired())return false;
        //if(pr.isFading())return false;
        //if(pr.didDamage())return false;
        if(!Global.getCombatEngine().isEntityInPlay(pr))return false;

        return true;
    }

    public static Color getRandomColor(){
        WeightedRandomPicker<Color> cc = new WeightedRandomPicker<>();
        cc.add(Global.getSettings().getColor("fae_color_teal"));
        cc.add(Global.getSettings().getColor("fae_color_pink"));
        cc.add(Global.getSettings().getColor("fae_color_blue"));
        return cc.pick();
    }

    public static Vector2f getRandomPointOnShip(ShipAPI ship){
        WeightedRandomPicker<BoundsAPI.SegmentAPI> seg = new WeightedRandomPicker<>();
        seg.addAll(ship.getExactBounds().getSegments());
        BoundsAPI.SegmentAPI s = seg.pick();
        Vector2f p = MathUtils.getRandomPointOnLine(new Vector2f(), s.getP1());
        Vector2f p2 = Misc.rotateAroundOrigin(p, ship.getFacing());
        Vector2f p3 = Vector2f.add(p2,ship.getLocation(), null);
        return p3;
    }

}



