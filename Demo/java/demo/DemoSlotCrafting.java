package demo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class DemoSlotCrafting extends SlotCrafting
{
	public DemoPlayerContainer container;
	
	public DemoSlotCrafting(EntityPlayer player, DemoPlayerContainer container, int stackIndex, int x, int y)
	{
		super(player, container.craftMatrix, container.craftResult, stackIndex, x, y);
		this.container = container;
	}

	protected void onCrafting(ItemStack itemStack)
	{
		super.onCrafting(itemStack);
		this.container.onCrafting(itemStack);
	}
}
