package mj.i;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import mj.p.PotionSynchro;
import mj.c.Config;
import mj.c.Const;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class Fruit extends Item {

	@SideOnly(Side.CLIENT)
	public static Icon bizarre;

	@SideOnly(Side.CLIENT)
	public static Icon synchro;
	
	@SideOnly(Side.CLIENT)
	public static Icon antidote;
	
	@SideOnly(Side.CLIENT)
	public static Icon vigor;
	
	@SideOnly(Side.CLIENT)
	public static Icon life;
	

	public Fruit( int id ) {
		super( id );
		this.setMaxStackSize( 64 );
		this.setHasSubtypes( true );
		this.setMaxDamage( 0 );
		this.setCreativeTab( CreativeTabs.tabFood );
	}
	
	public ItemStack onEaten( ItemStack is, World w, EntityPlayer p ) {
		if( !p.capabilities.isCreativeMode )
			--is.stackSize;
		
		p.getFoodStats().addStats( 4, 4.8F );

		if( !w.isRemote ) {
			int d = is.getItemDamage( );
			if( d == 1 || d == 2 )
				p.addPotionEffect( new PotionEffect( Config.synchroPotionIndex, Const.MINUTE*2, d-1 ) );
			
			if( d == 3 )
				p.addPotionEffect( new PotionEffect( Config.antiveninPotionIndex, Const.MINUTE*2, 0 ) );
			
			if( d == 4 )
				p.addPotionEffect( new PotionEffect( Potion.digSpeed.id, Const.MINUTE*8, 0 ) );
				
			if( d == 5 )
				p.addPotionEffect( new PotionEffect( Potion.field_76443_y.id, Const.MINUTE*2, 0 ) );
		}
		return is;
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.eat;
	}
	
	public ItemStack onItemRightClick( ItemStack is, World w, EntityPlayer p ) {
		if( p.canEat( is.getItemDamage( ) > 0 ) && is.getItemDamage( ) < 6 )
			p.setItemInUse( is, this.getMaxItemUseDuration( is ) );
		return is;
	}

	public String getItemDisplayName( ItemStack is ) {
		switch( is.getItemDamage( ) ) {
			case 0:
				return "Bizarre Fruit";
			case 1:
				return "Synchro Fruit";
			case 2:
				return "Synchro Fruit";
			case 3:
				return "Antivenin Fruit";
			case 4:
				return "Vigor Fruit";
			case 5:
				return "Life Fruit";
			default:
				return "Cheap Knockoff Fruit";
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation( ItemStack is, EntityPlayer p, List l, boolean b ) {
		int d = is.getItemDamage( );
		switch( d ) {
			case 1:
				l.add( "Synchro (2:00)" );
				break;
			case 2:
				l.add( "Synchro II (2:00)" );
				break;
			case 3:
				l.add( "Antivenin I (2:00)" );
				break;
			case 4:
				l.add( "Haste I (8:00)" );
				break;
			case 5:
				l.add( "Saturation I (2:00)" );
				break;
		}
		
		if( d > 0 && d < 6 ) {
			l.add( "" );
			l.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank") );
			switch( d ) {
				case 1:
					l.add( EnumChatFormatting.BLUE + "Buffs player proportionate to amount" );
					l.add( EnumChatFormatting.BLUE + "of Synced players in 10 block radius" );
					break;
				case 2:
					l.add( EnumChatFormatting.BLUE + "Buffs player proportionate to amount" );
					l.add( EnumChatFormatting.BLUE + "of Synced players in 30 block radius" );
					break;
				case 3:
					l.add( EnumChatFormatting.BLUE + "Temporary inoculation against poison" );
					break;
				case 4:
					l.add( EnumChatFormatting.BLUE + "+20% Block Mining Speed" );
					break;
				case 5:
					l.add( EnumChatFormatting.BLUE + "+1 Food (+1/2 Haunch) per Tick" );
					break;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect( ItemStack is ) {
		return is.getItemDamage( ) == 2;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems( int id, CreativeTabs t, List l ) {
		for( int i = 0; i < 6; ++i )
			l.add( new ItemStack( id, 1, i ) );
	}
	
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity( ItemStack is ) {
		int d = is.getItemDamage( );
		return (d == 1 || ( d > 2 && d < 6 ) ) ? EnumRarity.rare : is.getItemDamage( ) == 2 ? EnumRarity.epic : EnumRarity.common;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.bizarre = par1IconRegister.registerIcon( "bizarre_fruit" );
		this.synchro = par1IconRegister.registerIcon( "synchro_fruit" );
		this.antidote = par1IconRegister.registerIcon( "antidote_fruit" );
		this.vigor = par1IconRegister.registerIcon( "vigor_fruit" );
		this.life = par1IconRegister.registerIcon( "life_fruit" );
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage( int d ) {
		switch( d ) {
			case 1:
				return this.synchro;
			case 2:
				return this.synchro;
			case 3:
				return this.antidote;
			case 4:
				return this.vigor;
			case 5:
				return this.life;
			default:
				return this.bizarre;
		}
	}

}