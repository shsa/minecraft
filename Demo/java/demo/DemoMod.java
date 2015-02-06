package demo;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import demo.inventoryitem.GuiHandler;
import demo.inventoryitem.InventoryItemMain;

@Mod(name = "Demo", modid = DemoMod.MODID, version = DemoMod.VERSION)
public class DemoMod
{
	public static final String MODID = "demo";
	public static final String VERSION = "1.0";

	@SidedProxy(modId = DemoMod.MODID, clientSide = "demo.client.ClientProxy", serverSide = "demo.server.ServerProxy")
	public static Proxy proxy;

	@Instance(DemoMod.MODID)
	public static DemoMod instance;
	
	public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(DemoMod.MODID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);

		InventoryItemMain.instance.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
		InventoryItemMain.instance.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
