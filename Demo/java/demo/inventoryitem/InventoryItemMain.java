package demo.inventoryitem;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import demo.DemoMod;
import demo.Proxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public final class InventoryItemMain extends Proxy
{
	public static InventoryItemMain instance = new InventoryItemMain();

	/** This is used to keep track of GUIs that we make */
	private static int modGuiIndex = 0;

	/** This is the starting index for all of our mod's item IDs */
	private static int modItemIndex = 7000;

	/** Set our custom inventory Gui index to the next available Gui index */
	public static final int GUI_ITEM_INV = modGuiIndex++;

	// ITEMS ETC.
	public static final Item itemstore = new ItemStore().setUnlocalizedName("item_store").setCreativeTab(CreativeTabs.tabMisc);

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		Item.itemRegistry.addObject(modItemIndex, "itemStore", (new ItemStore()).setUnlocalizedName("test").setTextureName("diamond").setCreativeTab(CreativeTabs.tabMisc));
		NetworkRegistry.INSTANCE.registerGuiHandler(DemoMod.instance, new GuiHandler());
	}
}