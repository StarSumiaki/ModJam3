package mj;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;

import java.io.File;
import java.util.logging.Level;

import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import mj.c.Config;
import mj.c.Const;
import mj.i.ItemLoader;
import mj.p.PotionAntivenin;
import mj.p.PotionSynchro;

@Mod(
	modid = "mokyuu.modjam3",
	name = "Bizarre Fruits Mod",
	version = "1.0.0"
)
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = true
)
public class MJ {

	@EventHandler
	public void pre( FMLPreInitializationEvent e ) {
		this.init_IDs( new Configuration(
				new File( e.getModConfigurationDirectory(), "BizarreFruits_1.0.0.cfg" )
		));
	}

	@EventHandler
	public void init( FMLInitializationEvent e ) {
	
		//int index = mj.u.Helper.firstEmpty( Potion.potionTypes );
		Potion.potionTypes[ Config.synchroPotionIndex ] =
			(new PotionSynchro( Config.synchroPotionIndex, false, 0x7efdae )).setPotionName( "Synchro" ).setIconIndex( 2, 2 );
		Potion.potionTypes[ Config.antiveninPotionIndex ] =
			(new PotionAntivenin( Config.antiveninPotionIndex, false, 0xbeed4d )).setPotionName( "Antivenin" ).setIconIndex( 7, 0 );
		ItemLoader.load( );
		
		MinecraftForge.EVENT_BUS.register( new mj.e.EventHandler( ) );
	}
	
	private void init_IDs( Configuration c ) {
		try {
			c.load( );
			Config.fruitID = c.getItem( "fruit", Config.FRUIT_ID_DEFAULT ).getInt( Config.FRUIT_ID_DEFAULT );
			Config.synchroPotionIndex = c.get( c.CATEGORY_GENERAL, "synchroPotion", Config.SYNCHRO_POTION_INDEX_DEFAULT ).getInt( Config.SYNCHRO_POTION_INDEX_DEFAULT );
			Config.antiveninPotionIndex = c.get( c.CATEGORY_GENERAL, "antiveninPotion", Config.ANTIVENIN_POTION_INDEX_DEFAULT ).getInt( Config.ANTIVENIN_POTION_INDEX_DEFAULT );
		} catch( Exception exception ) {
			FMLLog.log( Level.SEVERE, exception, "Error initializing Bizarre Fruits mod!" );
		} finally {
			c.save( );
		}
	}
}