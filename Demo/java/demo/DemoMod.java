package demo;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(name = "Demo", modid = DemoMod.MODID, version = DemoMod.VERSION)
public class DemoMod
{
	public static final String MODID = "demo";
	public static final String VERSION = "1.0";

	@SidedProxy(modId=DemoMod.MODID, clientSide="demo.client.ClientProxy", serverSide="demo.server.ServerProxy")
	public static Proxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.PreInit(event);
	}
}
