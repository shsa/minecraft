package demo.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
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
	public void onGuiOpen(GuiOpenEvent event)
	{
		try
		{
			if (event.gui instanceof GuiInventory)
			{
				EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
				if (!(player.inventoryContainer instanceof DemoPlayerContainer))
				{
					player.inventory = new DemoPlayerInventory(player);
					//player.inventoryContainer = new DemoPlayerContainer(player);
				}
				event.gui = new DemoPlayerInventoryGui(player);
			}
			Log.msg("%s", event.gui.getClass().getName());
		}
		catch (Exception e)
		{

		}
	}
}
