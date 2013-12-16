package mj.i;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ItemLoader {

	public static Item fruit;


	public static boolean load( ) {
		fruit = new Fruit( 8001 );
		initRecipes( );
		return true;
	}
	
	private static void initRecipes( ) {
		CraftingManager cm = CraftingManager.getInstance( );
		
		cm.addRecipe( new ItemStack( fruit, 1, 1 ), new Object[] { 
			"EEE",
			"EAE",
			"EEE",
			'A', new ItemStack( fruit, 1, 0 ),
			'E', Item.emerald
		} );
		
		cm.addRecipe( new ItemStack( fruit, 1, 2 ), new Object[] { 
			"EEE",
			"EAE",
			"EEE",
			'A', new ItemStack( fruit, 1, 0 ),
			'E', Block.blockEmerald
		} );
		
		cm.addShapelessRecipe( new ItemStack( fruit, 1, 3 ), new Object[] { 
			new ItemStack( fruit, 1, 0 ),
			Item.fermentedSpiderEye,
			Block.mushroomRed
		} );
		
		cm.addRecipe( new ItemStack( fruit, 1, 4 ), new Object[] { 
			"BBB",
			"BAB",
			"BBB",
			'A', new ItemStack( fruit, 1, 0 ),
			'B', Item.blazePowder
		} );
		
		cm.addRecipe( new ItemStack( fruit, 1, 5 ), new Object[] { 
			"nnn",
			"nAn",
			"nnn",
			'A', new ItemStack( fruit, 1, 0 ),
			'n', Item.goldNugget
		} );
	
	
	}


}