package demo.inventoryitem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import demo.DemoPlayerContainer;
import demo.client.DemoPlayerInventoryGui;

public class GuiHandler implements IGuiHandler
{
	public void registerRenderers() {}

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		if (guiId == 100)
		{
			return new DemoPlayerContainer(player);
		}
		
		// Hooray, no 'magic' numbers - we know exactly which Gui this refers to
		if (guiId == InventoryItemMain.GUI_ITEM_INV)
		{
			// Use the player's held item to create the inventory
			return new ContainerItem(player, player.inventory, new InventoryItem(player.getHeldItem()));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		if (guiId == 100)
		{
			return new DemoPlayerInventoryGui(player);
		}
		
		if (guiId == InventoryItemMain.GUI_ITEM_INV)
		{
			// We have to cast the new container as our custom class
			// and pass in currently held item for the inventory
			return new GuiItemInventory((ContainerItem) new ContainerItem(player, player.inventory, new InventoryItem(player.getHeldItem())));
		}
		return null;
	}
}
