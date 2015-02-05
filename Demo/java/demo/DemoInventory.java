package demo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class DemoInventory  implements IInventory
{
	public ItemStack[] list = new ItemStack[90];
	public boolean inventoryChanged;
	
	@Override
	public int getSizeInventory()
	{
		return this.list.length;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.list[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemStack = this.list[index].splitStack(count);

		if (this.list[index].stackSize == 0)
		{
			this.list[index] = null;
		}

		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack)
	{
		this.list[index] = itemStack;
	}

	@Override
	public String getInventoryName()
	{
		return "demo.inventory";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
        this.inventoryChanged = true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
        //return player.isDead ? false : player.getDistanceSqToEntity(player) <= 64.0D;
		return true;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack)
	{
		return true;
	}

}
