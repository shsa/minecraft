package demo.inventoryitem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStore extends Item
{
	public ItemStore()
	{
		// ItemStacks that store an NBT Tag Compound are limited to stack size of 1
		setMaxStackSize(1);
		// you'll want to set a creative tab as well, so you can get your item
		setCreativeTab(CreativeTabs.tabMisc);
	}

	// Without this method, your inventory will NOT work!!!
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1; // return any value greater than zero
	}
   
  	//@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		if (world.isRemote)
		{
			// If player not sneaking, open the inventory gui
			if (!player.isSneaking()) {
				//player.openGui(InventoryItemMain.instance, InventoryItemMain.GUI_ITEM_INV, world, (int) player.posX, (int) player.posY, (int) player.posZ);
				//player.openGui(mod, modGuiId, world, x, y, z);
				Minecraft.getMinecraft().displayGuiScreen(new GuiItemInventory(new ContainerItem(player, player.inventory, new InventoryItem(itemstack))));
			}
			
			// Otherwise, stealthily place some diamonds in there for a nice surprise next time you open it up :)
			else {
				new InventoryItem(player.getHeldItem()).setInventorySlotContents(0, new ItemStack(Item.getItemById(264),4));
			}
		}
		
		return itemstack;
	}
}
