package bing.faesector.data;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.WeightedRandomPicker;
import org.lazywizard.lazylib.FastTrig;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;
import org.magiclib.plugins.MagicTrailPlugin;
import org.magiclib.util.MagicAnim;
import org.magiclib.util.MagicRender;

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
        cc.add(Global.getSettings().getColor("fae_color_green"));
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


    public static SpriteAPI getSpriteFromSheet(String key, int hor, int vert, int index){
        int k=0;
        int m=0;
        int g=0;
        int ii = hor*vert-index;
        for(int i = 0; i<hor; i++){
            for(int j = 0; j<hor; j++){
                if(g==ii){
                    k=j;m=i;
                }
                g++;
            }
        }

        return getSpriteFromSheet(key, hor, vert, k, m);

    }

    public static SpriteAPI getSpriteFromSheet(String key, int hor, int vert, int ix, int iy){

        SpriteAPI sprite = Global.getSettings().getSprite("fx", key);
        //sprite = Global.getSettings().getSprite("misc", "dust_particles");
        float hf = 1f/hor;
        float vf = 1f/vert;
        sprite.setTexWidth(hf);
        sprite.setTexHeight(vf);
        sprite.setTexX(ix * hf);
        sprite.setTexY(iy * vf);
        return sprite;
    }
    public static class m{
        public static Vector2f pc(Vector2f p, float r, float a){
            if(p == null){
                p = new Vector2f();
            }
            return MathUtils.getPointOnCircumference(p,r,a);
        }
        public static float dist(Vector2f a, Vector2f b){
            if(a == null){
                a = new Vector2f();
            }
            return MathUtils.getDistance(a,b);
        }
        public static float dir(Vector2f a, Vector2f b){
            return VectorUtils.getAngle(a,b);
        }
        public static float ds(float a, float b){
            return Math.signum(MathUtils.getShortestRotation(a, b));
        }
        public static float isq(float x){
            return 1-(x-1)*(x-1);
        }

        public static float sin(float x){
            return (float) FastTrig.sin(Math.toRadians(x));
        }
        public static float cos(float x){
            return (float)FastTrig.cos(Math.toRadians(x));
        }
        public static float phase(float x){
            return (1f+cos(MathUtils.clampAngle(x)))/2f;
        }


        public static float rn(float x) {
            return MathUtils.getRandomNumberInRange(1f-x, 1f);
        }
    }

    public static void spawnMagictrailRing(SpriteAPI sprite, Vector2f point, float size, Vector2f width, float tilt, float angle, Vector2f arc, Vector2f arcInOut, Vector2f velocity, float expandSpeed, Color colorIn, Color colorOut, float opacity, float in, float full, float out, int numpoints, CombatEngineLayers layer, int blendFuncSrc, int blendFuncDst){
        float id = MagicTrailPlugin.getUniqueID();
        float at = m.cos(tilt);
        float ww = size/2f;


        float hh = at*ww;
        float fullArc = arc.y-arc.x;
        //System.out.println("testing arc "+id);
        for(int i=0; i<numpoints; i++){

            float a1 = arc.x + i*fullArc/numpoints;

            float l1 = a1/fullArc;
            float ol1 = 1f;

            if(a1< arcInOut.x){ol1 = a1/arcInOut.x;}
            if(a1>arcInOut.y){ol1 = 1f-(a1-arcInOut.y)/(arc.y-arcInOut.y);}



            float wl1 = 0.1f+ 0.9f* MagicAnim.smoothReturnNormalizeRange(a1, arc.x, arc.y);
            wl1 = m.isq(wl1);

            float x = m.sin(a1)*ww;
            float y = m.cos(a1)*hh;
            Vector2f p1 = new Vector2f(x,y);


            Vector2f vl1 = new Vector2f(m.sin(a1)*expandSpeed,m.cos(a1)*expandSpeed*at);

            vl1 = Misc.rotateAroundOrigin(vl1, angle+90f);
            vl1 = Vector2f.add(velocity, vl1, null);
            p1= Misc.rotateAroundOrigin(p1, angle+90f);
            p1= Vector2f.add(point, p1, null);


            float a2 = angle +90f;
            if(tilt<45f) {
                a2 = m.dir(point, p1)+90f;
            }
            //wl1=1f;
            //float sl1 = m.sin(a1);
            //System.out .println("a1="+a1+"; a2="+a2);

            MagicTrailPlugin.addTrailMemberAdvanced(
                    null, /* linkedEntity */
                    id, /* ID */
                    sprite, /* sprite */
                    p1, /* position */
                    0f, /* startSpeed */
                    0f, /* endSpeed */
                    a2, /* angle */
                    0f, /* startAngularVelocity */
                    0f, /* endAngularVelocity */
                    width.x*wl1, /* startSize */
                    width.y*wl1, /* endSize */
                    colorIn, /* startColor */
                    colorOut, /* endColor */
                    opacity*ol1, /* opacity */
                    in, /* inDuration */
                    full, /* mainDuration */
                    out, /* outDuration */
                    blendFuncSrc,//GL11.GL_SRC_ALPHA, /* blendModeSRC */
                    blendFuncDst,//GL11.GL_ONE_MINUS_SRC_ALPHA, /* blendModeDEST */
                    512f, /* textureLoopLength */
                    5000f, /* textureScrollSpeed */
                    5f, /* textureOffset */
                    vl1, /* offsetVelocity */
                    null, /* advancedOptions */
                    layer, /* layerToRenderOn */
                    1f /* frameOffsetMult */
            );



        }
    }

    public static void spawnCircularBlastWave(Vector2f point, float size, float angle, float dur, float expandSpeed, float tilt, int num, List<Color> col, int blendFuncSrc, int blendFuncDst ){
        WeightedRandomPicker<Color> c1 = new WeightedRandomPicker<>();
        c1.addAll(col);

        WeightedRandomPicker<String>sprites= new WeightedRandomPicker<>();
        sprites.add("base_trail_mild");
        sprites.add("base_trail_fuzzy");
        sprites.add("base_trail_smooth");
        sprites.add("base_trail_zapWithCore");
        for(int i =0; i<num; i++){
            float w = 0.2f*size*m.rn(0.1f);
            float s = size*m.rn(0.1f);
            Vector2f p1 = m.pc(point, 0.3f*size*m.rn(1f)*m.cos(tilt), angle);
            float d1 = dur*m.rn(0.6f);
            float in = 0.1f*d1;
            float full = 0.01f*d1;
            float out = d1-in-full;
            float am = MathUtils.getRandomNumberInRange(90f, 270f);
            float al = MathUtils.getRandomNumberInRange(0f, am);
            float ar = MathUtils.getRandomNumberInRange(am, 360f);
            float fl = al+ (am-al)/2f;
            float fr = am + (ar-am)/2f;

            fae_misc.spawnMagictrailRing(
                    Global.getSettings().getSprite("fx", sprites.pick()), /* sprite */
                    p1, //point
                    s, //size
                    new Vector2f(w, w/2f), //width
                    tilt, //tilt
                    angle, //angle
                    new Vector2f(al, ar), //arc
                    new Vector2f(fl, fr), //arcInOut
                    m.pc(null, 50f*m.rn(0.3f),angle), //velocity of the whole ring
                    expandSpeed*m.rn(0.3f), //expandSpeed
                    c1.pick(), //color in
                    c1.pick(), //color out
                    1f, //opacity
                    in,
                    full,
                    out,
                    32,
                    CombatEngineLayers.ABOVE_SHIPS_LAYER,
                    blendFuncSrc,
                    blendFuncDst
            );
        }
    }

    public static void renderEngineFlareSingleframe(Vector2f point, float angle, float length, float width, Color c1, Color c2, float random){

        float len = length*2f;
        MagicRender.singleframe(
                Global.getSettings().getSprite("fx","fae_FX_engineflame32"),
                point,
                new Vector2f(len*m.rn(random), width*m.rn(random)),
                angle,
                c1,
                true
        );


        MagicRender.singleframe(
                Global.getSettings().getSprite("fx","fae_FX_star2"),
                point,
                new Vector2f(5f*width*m.rn(random), 2f*width*m.rn(random)),
                0f,
                c1,
                true
        );


        MagicRender.singleframe(
                Global.getSettings().getSprite("fx","fae_FX_engineflame32"),
                point,
                new Vector2f(0.8f*len*m.rn(random), 0.4f*width*m.rn(random)),
                angle,
                c2,
                true
        );


        MagicRender.singleframe(
                Global.getSettings().getSprite("fx","fae_FX_star0"),
                point,
                new Vector2f(2f*width*m.rn(random), 2f*width*m.rn(random)),
                0f,
                c2,
                true
        );


    }

}


