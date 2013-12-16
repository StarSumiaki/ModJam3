package mj.p;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;

public class PotionAntivenin extends Potion {

	public PotionAntivenin( int index, boolean flag, int c ) {
		super( index, flag, c );
	}
	
	private boolean isLiving( Entity e ) {
		return e instanceof EntityLivingBase;
	}

	public void performEffect( EntityLivingBase e, int level ) {

		Collection c = e.getActivePotionEffects();
		
		if( c.isEmpty( ) )
			return;
		else {
			Iterator i = c.iterator( );
			while( i.hasNext( ) ) {
				PotionEffect ef = (PotionEffect)i.next( );
				if( ef.getPotionID( ) == Potion.poison.id )
					i.remove( );
			}
		}
	}
	
	public boolean isReady(int par1, int par2) {
		return true;
	}
}