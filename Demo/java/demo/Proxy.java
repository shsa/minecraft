package demo;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Proxy 
{
	public Proxy()
	{
		//FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void PreInit(FMLPreInitializationEvent event)
	{
	}
	
	/*
	@SubscribeEvent
	public void onPlayerLogged(PlayerEvent.PlayerLoggedInEvent event)
	{
		event.player.inventory = new DemoPlayerInventory(event.player);
		event.player.inventoryContainer = new DemoPlayerContainer(event.player);
	}
	*/
	@SubscribeEvent
	public void onPlayerLoading(PlayerEvent.LoadFromFile event)
	{
		
	}
}
