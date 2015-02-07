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
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import demo.DemoPlayerInventory;
import demo.Log;
import demo.DemoPlayerContainer;
import demo.Proxy;
import demo.DemoMod;
import demo.inventoryitem.InventoryItemMain;

public class ClientProxy extends Proxy
{
	private Overlay overlay; 
	
	public ClientProxy()
	{
		super();
		//MinecraftForge.EVENT_BUS.register(new PlayerTracking());
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
			updatePlayer((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
		try
		{
			if (event.gui instanceof GuiInventory && !Minecraft.getMinecraft().playerController.isInCreativeMode())
			{
				if (event.gui instanceof DemoPlayerInventoryGui)
					return;
				//event.gui = new DemoPlayerInventoryGui(Minecraft.getMinecraft().thePlayer);
				event.setCanceled(true);
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				player.openGui(DemoMod.instance, 100, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			}
			Log.msg("%s", event.gui.getClass().getName());
		}
		catch (Exception e)
		{

		}
	}
	
	@SubscribeEvent
	public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event)
	{
		if (overlay == null)
			overlay = new Overlay(Minecraft.getMinecraft());
		overlay.onRenderExperienceBar(event);
		//Log.msg("%s", event.type.toString());
	}
}
