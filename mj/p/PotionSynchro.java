package mj.p;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;

public class PotionSynchro extends Potion {

	public PotionSynchro( int index, boolean flag, int c ) {
		super( index, flag, c );
	}
	
	private boolean isLiving( Entity e ) {
		return e instanceof EntityLivingBase;
	}
	
	private boolean isPlayer( Entity e ) {
		return e instanceof EntityPlayer;
	}
	
	// 9-10 haunches (18-20 food) == 1f
	// More than 5 haunches = 0.5f
	// More than 3 haunches = 0.2f
	// less than 3 haunches = 0f
	private float getContributionByHunger( EntityPlayer p ) {
		int foodBar = p.getFoodStats( ).getFoodLevel( );
		return foodBar >= 18 ? 1f : ( foodBar >= 10 ? 0.5F : (  foodBar >= 6 ? 0.2f : 0f ) );
	}
	
	public void performEffect( EntityLivingBase e, int level ) {
	
		double radius = level == 0 ? 10D : level == 1 ? 30D : 10D;
		List l = e.worldObj.getEntitiesWithinAABB( e.getClass( ), e.boundingBox.expand( radius, radius, radius ) );
		// We don't want this effect to work if we're by our lonesome.
		if( l.isEmpty( ) || l.size( ) == 1 )
			return;
		else {
		
			float sizef = isPlayer( e ) ? getContributionByHunger( (EntityPlayer)e ) : 0f;
			int validEntities = 0;
			Iterator<Entity> i = l.iterator( );
			while( i.hasNext( ) ) {
				Entity te = i.next( );
				if( isLiving(te) ) {
					if( ((EntityLivingBase)te).isPotionActive(this) ) {
						validEntities++;
						sizef+= ( ((EntityLivingBase)te).isPotionActive( this ) ? ( isPlayer(te) ? getContributionByHunger( (EntityPlayer)te ) : 0.2f ) : 0f );
					}
				}
			}
			
			// Nobody else has the effect, give up now
			if( validEntities == 1 )
				return;
			
			//Optimally, 1f per person
			int size = MathHelper.floor_float( sizef );
			
			// 5 people = Resist I, 10 = Resist II, 15 = Resist III
			int resistBoostFactor = Math.min( (size/5 ), 3 );
			// 5 people = Strength I, 10 = Strength II, 15 = Strength III
			int damageBoostFactor = resistBoostFactor;
			// 5 people = Speed I, 10+ = Speed II
			int speedBoostFactor = Math.min( (size/5), 2 );
			
			if( resistBoostFactor > 0 )
				e.addPotionEffect( new PotionEffect( Potion.resistance.id, mj.c.Const.MINUTE/2, resistBoostFactor-1, true ) );
			if( damageBoostFactor > 0 )
				e.addPotionEffect( new PotionEffect( Potion.damageBoost.id, mj.c.Const.MINUTE/2, damageBoostFactor-1, true ) );
			if( speedBoostFactor > 0 )
				e.addPotionEffect( new PotionEffect( Potion.moveSpeed.id, mj.c.Const.MINUTE/2, speedBoostFactor-1, true ) );
			
			if( level == 1 ) {
				// 5 people = 3 hearts, 10 = 6, 15 = 10
				int absorb = Math.min( (size/5), 3 );
				if( absorb > 0 )
					e.addPotionEffect( new PotionEffect( Potion.field_76444_x.id, mj.c.Const.MINUTE/2, absorb-1, true ) );
				
				if( size >= 5 )
					e.addPotionEffect( new PotionEffect( Potion.field_76443_y.id, mj.c.Const.MINUTE/2, 0, true ) );
			
			}
		}
	
	}
	
	public boolean isReady(int par1, int par2) {
		return true;
	}
}