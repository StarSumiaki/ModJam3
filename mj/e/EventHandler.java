package mj.e;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import mj.i.ItemLoader;

public class EventHandler {

	private Random r = new Random( );

	@ForgeSubscribe
	public void dropHander( LivingDropsEvent e ) {
		if( e.entityLiving instanceof EntityCreeper && e.recentlyHit ) {
			if( r.nextInt( 100 ) < ( 5 + (e.lootingLevel * 5) ) ) {
				EntityItem i = new EntityItem( e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ );
				i.setEntityItemStack( new ItemStack( ItemLoader.fruit, 1, 0 ) );
				e.drops.add( i );
			}
		}
	}



}