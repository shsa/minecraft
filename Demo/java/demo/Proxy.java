package demo;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;

public class Proxy 
{
	public Proxy()
	{
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void preInit(FMLPreInitializationEvent event)
	{
	}
	
	public void init(FMLInitializationEvent event)
	{
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
	}

	public void updatePlayer(EntityPlayer player)
	{
		if (!(player.inventoryContainer instanceof DemoPlayerContainer))
		{
			player.inventory = new DemoPlayerInventory(player);
			player.inventoryContainer = new DemoPlayerContainer(player);
			player.openContainer = player.inventoryContainer; 
		}
	}
	
	@SubscribeEvent
	public void onServerConnectionFromClient(FMLNetworkEvent.ServerConnectionFromClientEvent event)
	{
		NetHandlerPlayServer handler = (NetHandlerPlayServer)event.handler;
		EntityPlayer player = handler.playerEntity;
		updatePlayer(player);
	}
}
