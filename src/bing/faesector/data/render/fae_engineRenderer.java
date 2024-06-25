package bing.faesector.data.render;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.graphics.SpriteAPI;
import org.lwjgl.util.vector.Vector2f;
import org.magiclib.util.MagicAnim;
import org.magiclib.util.MagicRender;
import bing.faesector.data.render.fae_engineRenderer.engineLayerData;
import bing.faesector.data.fae_misc.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class fae_engineRenderer implements EveryFrameWeaponEffectPlugin {

    public static Map<String, List<engineLayerData>> layers(){
        Map<String, List<engineLayerData>> result = new HashMap<>();

        result.put("FAE_SMALL",  //sprite, width, length,
                Arrays.asList(
                        new engineLayerData("fae_enginesparks", new Vector2f(0.75f, 1f),0.08f, 0.1f,false,0,0.05f, 4f,   Color.PINK),

                        //new engineLayerData("fae_ring", new Vector2f(1f, 1f), 0.05f,0.05f, false,0,2f, 2f,  Color.PINK),

                        new engineLayerData("fae_ring2", new Vector2f(1f, 0.75f), 0f,0f, false,0,2f, 2f,  Color.PINK)
                        )
                );
        result.put("FAE_MEDIUM",
                Arrays.asList(
                        new engineLayerData("fae_enginesparks", new Vector2f(0.75f, 1.5f),0.08f, 0.1f,false,0,0.05f, 4f,   Color.PINK),

                        //new engineLayerData("fae_ring", new Vector2f(1f, 1f), 0.05f,0.05f, false,0,2f, 2f,  Color.PINK),

                        new engineLayerData("fae_ring2", new Vector2f(1f, 0.75f), 0f,0f, false,0,2f, 2f,  Color.PINK)
                )
        );
        result.put("FAE_LARGE",
                Arrays.asList(
                        new engineLayerData("fae_enginesparks", new Vector2f(0.75f, 1.5f),0.08f, 0.1f,false,0,0.05f, 4f,   Color.PINK),

                        //new engineLayerData("fae_ring", new Vector2f(1f, 1f), 0.05f,0.05f, false,0,2f, 2f,  Color.PINK),

                        new engineLayerData("fae_ring2", new Vector2f(1.25f, 1.25f), 0.05f,0.05f, false,0,2f, 2f,  Color.PINK)
                )
        );



        return result;
    }


    private Map<ShipEngineControllerAPI.ShipEngineAPI, engineLevel> engineLevels = new HashMap<>();

    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        if(engine.isPaused())return;
        if(!weapon.getShip().isAlive())return;
        ShipEngineControllerAPI ec = weapon.getShip().getEngineController();
            for(ShipEngineControllerAPI.ShipEngineAPI ee: ec.getShipEngines()){
                String style = ee.getStyleId();
                if(!layers().containsKey(style))continue;
                List<engineLayerData> el1 = layers().get(style);
                if(!engineLevels.containsKey(ee)){
                    engineLevels.put(ee,new engineLevel(el1.size()));
                }
                if(!ee.isActive()) {
                    engineLevels.get(ee).reset();
                    continue;
                }

                for(int i=0; i<el1.size(); i++){
                    if(ec.isAccelerating()){
                        engineLevels.get(ee).gain(i, amount*el1.get(i).gain);
                    }else{
                        engineLevels.get(ee).decay(i, amount*el1.get(i).decay);
                    }

                    el1.get(i).render(ee, weapon.getShip(), engineLevels.get(ee).levels.get(i));
                }


            }
    }
    public static class engineLevel{
        List<Float> levels;

        public void gain(int index, float amount){
            if(levels.get(index)<1f){
                levels.set(index, levels.get(index)+amount);
            }else {levels.set(index,1f);}
        }
        public void decay(int index, float amount){
            if(levels.get(index)>0f){
                levels.set(index, levels.get(index)-amount);
            }else {levels.set(index,0f);}
        }
        public void reset(){
            for(int i=0; i<levels.size(); i++){
                levels.set(i,0f);
            }
        }
        public engineLevel(int num) {
            levels=new ArrayList<>();
            for(int i=0; i<num; i++){
                levels.add(0f);
            }
        }
    }

    public static class engineLayerData{
        public String sprite;
        public Vector2f sizeMult;
        public float randomVar;
        public float minFract;
        public boolean isIgnoreFacing;

        public float angleOffset;
        public float gain;
        public float decay;

        public Color color;

        public engineLayerData(String sprite, Vector2f sizeMult, float randomVar, float minFract, boolean isIgnoreFacing, float angleOffset, float gain, float decay, Color color) {
            this.sprite = sprite;
            this.sizeMult = sizeMult;
            this.randomVar = randomVar;
            this.minFract = minFract;
            this.isIgnoreFacing = isIgnoreFacing;
            this.angleOffset = angleOffset;
            this.gain = gain;
            this.decay = decay;
            this.color = color;
        }

        private float of(float x, float l){
            return l + (1-l)*x;
        }

        public void render(ShipEngineControllerAPI.ShipEngineAPI engine, ShipAPI ship, float level){

            //System.out.println("engine is rendering");
            float l1 = of(level, 0.3f);
            Vector2f size = new Vector2f(2f*engine.getEngineSlot().getLength()*sizeMult.getY()*l1*m.rn(randomVar), engine.getEngineSlot().getWidth()*sizeMult.getX()*l1*m.rn(randomVar));
            //System.out.println(randomVar);
            Vector2f point = engine.getLocation();
            float angle = engine.getEngineSlot().getAngle()+ship.getFacing()+angleOffset;
            if(isIgnoreFacing){
                angle=angleOffset;
            }
            SpriteAPI tex = Global.getSettings().getSprite("fx", sprite);

            MagicRender.singleframe(
                    tex,
                    point,
                    size,
                    angle,
                    color,
                    true
            );
        }

    }
}
