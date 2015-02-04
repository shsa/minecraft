package demo.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import demo.DemoPlayerInventory;
import demo.Log;
import demo.DemoPlayerContainer;
import demo.Proxy;
import demo.DemoMod;

public class ClientProxy extends Proxy
{
	public ClientProxy()
	{
		super();
	}

	@SubscribeEvent
	public void onPlayerLogged(PlayerEvent.PlayerLoggedInEvent event)
	{
		Log.msg("ok");
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityClientPlayerMP)
		{
			Log.msg("client");
			updatePlayer((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
		try
		{
			if (event.gui instanceof GuiInventory)
			{
				//updatePlayer(Minecraft.getMinecraft().thePlayer);
				event.gui = new DemoPlayerInventoryGui(Minecraft.getMinecraft().thePlayer);
			}
			Log.msg("%s", event.gui.getClass().getName());
		}
		catch (Exception e)
		{

		}
	}
}
