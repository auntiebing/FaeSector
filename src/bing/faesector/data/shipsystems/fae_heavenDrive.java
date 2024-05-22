package bing.faesector.data.shipsystems;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.CombatUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import bing.faesector.data.fae_misc;
import bing.faesector.data.fae_misc.*;
import org.magiclib.plugins.MagicTrailPlugin;
import org.magiclib.util.MagicRender;

public class fae_heavenDrive extends BaseShipSystemScript {

	public static float SPEED_BONUS(){
		return  600f;
	}
	public static float TURN_BONUS = 20f;
	
	private Color color = new Color(100,255,100,255);
	
//	private Color [] colors = new Color[] {
//			new Color(140, 100, 235),
//			new Color(180, 110, 210),
//			new Color(150, 140, 190),
//			new Color(140, 190, 210),
//			new Color(90, 200, 170), 
//			new Color(65, 230, 160),
//			new Color(20, 220, 70)
//	};


	private List<Color> colorList(){
		return Arrays.asList(
				Color.WHITE, Color.CYAN, Global.getSettings().getColor("fae_color_teal"), Global.getSettings().getColor("fae_color_green")
		);
	}

	float shift = 1f;
	private static boolean k1(ShipAPI ship){
		boolean result = false;
		ShipSystemAPI.SystemState prev = ShipSystemAPI.SystemState.IDLE;
		ShipSystemAPI.SystemState cur = ship.getSystem().getState();
		String key = fae_heavenDrive.class.getName()+"_started";
		if(!ship.getCustomData().containsKey(key)){result = false; ship.setCustomData(key, prev);}
		prev = (ShipSystemAPI.SystemState)ship.getCustomData().get(key);
		if(prev == ShipSystemAPI.SystemState.IN && cur== ShipSystemAPI.SystemState.ACTIVE){
			result=true;
		}
		ship.setCustomData(key, cur);
		return result;
	}
	private float freq = 1f/60f;
	private static String key(){
		return "fairysector_aetherdrive_trail_key_";
	}

	private static float area(ShipAPI source){
		float col = source.getCollisionRadius();
		switch (source.getHullSize()){
			case FRIGATE: return col+100f;
			case DESTROYER: return col+200f;
			case CRUISER: return col+350f;
			case CAPITAL_SHIP: return col+500f;
		}
		return col+50f;
	}
	private static float force(ShipAPI source){
		switch (source.getHullSize()){
			case FRIGATE: return 500f;
			case DESTROYER: return 1000f;
			case CRUISER: return 2000f;
			case CAPITAL_SHIP: return 5000f;
		}
		return 500f;
	}

	private static void pushNearbyObjects(ShipAPI source){
		CombatEngineAPI engine = Global.getCombatEngine();
		float amount = engine.getElapsedInLastFrame();
		Vector2f point = source.getLocation();
		float size = area(source);
		float force = force(source);
		float col = source.getCollisionRadius();
		List<CombatEntityAPI> targets = new ArrayList<>();
		targets.addAll(engine.getShips());
		targets.addAll(engine.getAsteroids());
		targets.addAll(engine.getMissiles());
		for(CombatEntityAPI s: targets){
			if(s==source)continue;
			if(s instanceof ShipAPI && ((ShipAPI) s).getParentStation()!=null)continue;
			float c1 = s.getCollisionRadius();
			float s1 = size-col;
			Vector2f tl = s.getLocation();
			float d1 = m.dist(point,tl)-c1-col;
			if(d1>s1)continue;
			float l1 = 1f-d1/s1;
			float l2 = 1f-l1*l1;
			float a1 = m.dir(point, tl);
			float dif = Math.abs(MathUtils.getShortestRotation(source.getFacing(), a1));
			float l3 = 0f;
			if(dif<120f){l3=1f-dif/120f;}
			float ds = m.ds(source.getFacing(), a1);
			float a2 = source.getFacing()+ds*(90f - 70f*l2*l3);
			CombatUtils.applyForce(s, a2, force*l1*l3*amount);
		}
	}

	private IntervalUtil pi1 = new IntervalUtil(freq, freq);
	private IntervalUtil pi2 = new IntervalUtil(0f, 0f);
	public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
		
		if (stats.getEntity() instanceof ShipAPI) {
			CombatEngineAPI engine = Global.getCombatEngine();
			float amount = engine.getElapsedInLastFrame();

			shift+=amount;


			ShipAPI ship = (ShipAPI) stats.getEntity();
			for(int i=0; i<5; i++){
				if(!ship.getCustomData().containsKey(key()+i)){ship.setCustomData(key()+i, MagicTrailPlugin.getUniqueID());}
			}
			float angle = ship.getFacing();
			float size = ship.getCollisionRadius();
			Vector2f center = ship.getLocation();
			Vector2f enginePoint = m.pc(center, size, angle+180);
			float tilt = 70f;
			float hmult = m.cos(tilt);
			ship.getEngineController().fadeToOtherColor(this, color, new Color(0,0,0,0), effectLevel, 0.67f);
			//ship.getEngineController().fadeToOtherColor(this, Color.white, new Color(0,0,0,0), effectLevel, 0.67f);
			ship.getEngineController().extendFlame(this, 2f * effectLevel, 0f * effectLevel, 0f * effectLevel);

			Vector2f vel = ship.getVelocity();
			float speed = m.dist(null, vel);

			if(state!=State.COOLDOWN){

				int n = 5;
				int k= 3;
				for(int i=0; i<n; i++){
					float l3 = 1f;
					float ol = (i)/(n+1f);
					float os = (i+1f)/(n+1f);
					if(effectLevel<os && effectLevel>=ol){l3 = (effectLevel-ol)/(os-ol);}
					if(effectLevel<ol){l3=0f;}
					float s3 = size+size*l3 - size*m.isq(os);
					float l2 = m.phase((shift+os)*360f);
					for(int j=0; j<k; j++){
						float offset = i*(10f+30f*os)+j;
						Vector2f p1 = m.pc(enginePoint, offset, angle+180);
						MagicRender.singleframe(
								fae_misc.getSpriteFromSheet("fae_rings_large_sheet", 2,2,0), p1, new Vector2f(s3*hmult, s3), angle+180, fae_misc.adjustAlpha(colorList().get(2), l3*(1f-0.5f*l2)/k), true, CombatEngineLayers.ABOVE_SHIPS_LAYER
						);
					}

				}
				if(effectLevel>=1f){
					fae_misc.renderEngineFlareSingleframe(enginePoint, angle+180f, size, size/4f, Color.PINK, Color.WHITE, 0.05f);
				}

				Vector2f p2 = m.pc(center, size*1.1f, angle);

				pi2.advance(amount);
				pi1.advance(amount);

				if(pi1.intervalElapsed()){
					//MagicTrailPlugin.addTrailMemberSimple(ship, tid, Global.getSettings().getSprite("fx", "base_trail_heavySmoke"), p2, 600, angle+180f, size, size*2f, Color.WHITE, 1f, 0.5f, 0.5f, 2f, false);

					float l1 = effectLevel;

					MagicTrailPlugin.addTrailMemberAdvanced(
							ship, /* linkedEntity */
							(float)ship.getCustomData().get(key()+1), /* ID */
							Global.getSettings().getSprite("fx", "fae_stream_dark"), /* sprite */
							p2, /* position */
							100f-speed, /* startSpeed */
							2000f-speed, /* endSpeed */
							angle - 180f, /* angle */
							0f, /* startAngularVelocity */
							0f, /* endAngularVelocity */
							size/4f, /* startSize */
							size*3f, /* endSize */
							Color.WHITE, /* startColor */
							Color.WHITE, /* endColor */
							l1, /* opacity */
							0.3f, /* inDuration */
							0.05f, /* mainDuration */
							0.3f, /* outDuration */
							GL11.GL_SRC_ALPHA, /* blendModeSRC */
							GL11.GL_ONE_MINUS_SRC_ALPHA, /* blendModeDEST */
							128f, /* textureLoopLength */
							100f, /* textureScrollSpeed */
							600f*pi1.getMaxInterval(), /* textureOffset */
							new Vector2f(), /* offsetVelocity */
							null, /* advancedOptions */
							CombatEngineLayers.BELOW_SHIPS_LAYER, /* layerToRenderOn */
							1f /* frameOffsetMult */
					);

					MagicTrailPlugin.addTrailMemberAdvanced(
							ship, /* linkedEntity */
							(float)ship.getCustomData().get(key()+2), /* ID */
							Global.getSettings().getSprite("fx", "fae_stream_layer2"), /* sprite */
							p2, /* position */
							100f-speed, /* startSpeed */
							2000f-speed, /* endSpeed */
							angle - 180f, /* angle */
							0f, /* startAngularVelocity */
							0f, /* endAngularVelocity */
							size/4f, /* startSize */
							size*2f, /* endSize */
							colorList().get(0), /* startColor */
							colorList().get(1), /* endColor */
							l1, /* opacity */
							0.2f, /* inDuration */
							0.05f, /* mainDuration */
							0.3f, /* outDuration */
							GL11.GL_SRC_ALPHA, /* blendModeSRC */
							GL11.GL_ONE_MINUS_SRC_ALPHA, /* blendModeDEST */
							256, /* textureLoopLength */
							5000f, /* textureScrollSpeed */
							300f*pi1.getMaxInterval(), /* textureOffset */
							new Vector2f(), /* offsetVelocity */
							null, /* advancedOptions */
							CombatEngineLayers.CONTRAILS_LAYER, /* layerToRenderOn */
							1f /* frameOffsetMult */
					);
					MagicTrailPlugin.addTrailMemberAdvanced(
							ship, /* linkedEntity */
							(float)ship.getCustomData().get(key()+3), /* ID */
							Global.getSettings().getSprite("fx", "fae_stream_layer1"), /* sprite */
							p2, /* position */
							100f-speed, /* startSpeed */
							2000f-speed, /* endSpeed */
							angle - 180f, /* angle */
							0f, /* startAngularVelocity */
							0f, /* endAngularVelocity */
							size/2f, /* startSize */
							size*6f, /* endSize */
							colorList().get(2), /* startColor */
							colorList().get(1), /* endColor */
							l1*0.3f, /* opacity */
							0.2f, /* inDuration */
							0.05f, /* mainDuration */
							0.3f, /* outDuration */
							GL11.GL_SRC_ALPHA, /* blendModeSRC */
							GL11.GL_ONE_MINUS_SRC_ALPHA, /* blendModeDEST */
							256, /* textureLoopLength */
							5000f, /* textureScrollSpeed */
							300f*pi1.getMaxInterval(), /* textureOffset */
							new Vector2f(), /* offsetVelocity */
							null, /* advancedOptions */
							CombatEngineLayers.CONTRAILS_LAYER, /* layerToRenderOn */
							1f /* frameOffsetMult */
					);
				}
			}
			if(state == State.IN){
				float l1 = effectLevel;
				float dml = 1f- 0.95f*l1;
				stats.getMaxSpeed().modifyMult(id, dml);
				stats.getAcceleration().modifyMult(id, dml);
				stats.getTurnAcceleration().modifyMult(id, 1f+3f * l1);
				stats.getMaxTurnRate().modifyMult(id, 1f+3f*l1);

			}
			if(k1(ship)){
				Vector2f v1 = m.pc(null, 2000f, angle);
				ship.getVelocity().set(v1);

				ship.setAngularVelocity(0f);

				fae_misc.spawnCircularBlastWave(enginePoint, size, angle+180f, 1.5f, 2000f, 70f, 10, colorList(),GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			}
			if(state == State.ACTIVE){
				pushNearbyObjects(ship);


				ship.giveCommand(ShipCommand.ACCELERATE, null, 0);
				ship.blockCommandForOneFrame(ShipCommand.DECELERATE);
				ship.blockCommandForOneFrame(ShipCommand.ACCELERATE_BACKWARDS);


				stats.getMaxSpeed().unmodifyMult(id);
				stats.getAcceleration().unmodifyMult(id);
				stats.getTurnAcceleration().modifyMult(id, 0.1f);
				stats.getMaxTurnRate().modifyMult(id, 0.1f);


				stats.getMaxSpeed().modifyFlat(id, SPEED_BONUS());
				stats.getAcceleration().modifyPercent(id, SPEED_BONUS());
				stats.getDeceleration().modifyPercent(id, SPEED_BONUS());

				



			}
			if (state == State.OUT) {
				stats.getMaxSpeed().unmodify(id); // to slow down ship to its regular top speed while powering drive down
				stats.getMaxTurnRate().unmodify(id);
				float l1 = effectLevel;
				stats.getTurnAcceleration().modifyMult(id, 1f+3f * l1);
				stats.getMaxTurnRate().modifyMult(id, 1f+3f*l1);
			}
			if(state == State.COOLDOWN || state == State.IDLE){
				stats.getMaxSpeed().unmodify(id);
				stats.getMaxTurnRate().unmodify(id);
				stats.getTurnAcceleration().unmodify(id);
				stats.getAcceleration().unmodify(id);
				stats.getDeceleration().unmodify(id);
			}
		}
	}

	public void unapply(MutableShipStatsAPI stats, String id) {
		stats.getMaxSpeed().unmodify(id);
		stats.getMaxTurnRate().unmodify(id);
		stats.getTurnAcceleration().unmodify(id);
		stats.getAcceleration().unmodify(id);
		stats.getDeceleration().unmodify(id);
	}
	
	public StatusData getStatusData(int index, State state, float effectLevel) {
		if (index == 0) {
			return new StatusData("improved maneuverability", false);
		} else if (index == 1) {
			return new StatusData("+" + (int)SPEED_BONUS() + " top speed", false);
		}
		return null;
	}
}
