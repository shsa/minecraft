package demo;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.FMLCommonHandler;
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
	
	public void updatePlayer(EntityPlayer player)
	{
		if (!(player.inventoryContainer instanceof DemoPlayerContainer))
		{
			DemoPlayerInventory inventory = new DemoPlayerInventory(player);
			//inventory.copyInventory(player.inventory);
			player.inventory = inventory;
			player.inventoryContainer = new DemoPlayerContainer(player);
			player.openContainer = player.inventoryContainer; 
		}
	}
	
	@SubscribeEvent
	public void onClientConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		//updatePlayer(player);
	}
	
	@SubscribeEvent
	public void onServerConnectionFromClient(FMLNetworkEvent.ServerConnectionFromClientEvent event)
	{
		NetHandlerPlayServer handler = (NetHandlerPlayServer)event.handler;
		EntityPlayer player = handler.playerEntity;
		//updatePlayer(player);
	}
	
	/*
	@SubscribeEvent
	public void onPlayerLoading(PlayerEvent.LoadFromFile event)
	{
		//event.entityPlayer.inventory = new DemoPlayerInventory(event.entityPlayer);
		//event.entityPlayer.inventoryContainer = new DemoPlayerContainer(event.entityPlayer);
		//event.
	}
	*/
}
