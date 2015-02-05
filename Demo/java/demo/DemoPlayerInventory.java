package demo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.concurrent.Callable;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ReportedException;

public class DemoPlayerInventory extends InventoryPlayer
{
	/**
	 * An array of 9 item stacks containing the currently crafted result items.
	 * 3x3 + 1(result)
	 */
	public ItemStack[] craftInventory = new ItemStack[9];

	public DemoPlayerInventory(EntityPlayer player)
	{
		super(player);
	}

	/**
	 * Clear this player's inventory (including armor), using the specified Item
	 * and metadata as filters or -1 for no filter.
	 */
	@Override
	public int clearInventory(Item item, int meta)
	{
		int j = 0;
		int k;
		ItemStack itemstack;

		for (k = 0; k < this.mainInventory.length; ++k)
		{
			itemstack = this.mainInventory[k];

			if (itemstack != null && (item == null || itemstack.getItem() == item) && (meta <= -1 || itemstack.getItemDamage() == meta))
			{
				j += itemstack.stackSize;
				this.mainInventory[k] = null;
			}
		}

		for (k = 0; k < this.armorInventory.length; ++k)
		{
			itemstack = this.armorInventory[k];

			if (itemstack != null && (item == null || itemstack.getItem() == item) && (meta <= -1 || itemstack.getItemDamage() == meta))
			{
				j += itemstack.stackSize;
				this.armorInventory[k] = null;
			}
		}

		if (this.getItemStack() != null)
		{
			if (item != null && this.getItemStack().getItem() != item)
			{
				return j;
			}

			if (meta > -1 && this.getItemStack().getItemDamage() != meta)
			{
				return j;
			}

			j += this.getItemStack().stackSize;
			this.setItemStack((ItemStack) null);
		}

		return j;
	}

	/**
	 * Decrement the number of animations remaining. Only called on client side.
	 * This is used to handle the animation of receiving a block.
	 */
	public void decrementAnimations()
	{
		for (int i = 0; i < this.mainInventory.length; ++i)
		{
			if (this.mainInventory[i] != null)
			{
				this.mainInventory[i].updateAnimation(this.player.worldObj, this.player, i, this.currentItem == i);
			}
		}

		for (int i = 0; i < armorInventory.length; i++)
		{
			if (armorInventory[i] != null)
			{
				armorInventory[i].getItem().onArmorTick(player.worldObj, player, armorInventory[i]);
			}
		}

		for (int i = 0; i < craftInventory.length; i++)
		{
			if (craftInventory[i] != null)
			{
				craftInventory[i].updateAnimation(this.player.worldObj, this.player, i, this.currentItem == i);
			}
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	public ItemStack getStackInSlotOnClosing(int slotIndex)
	{
		ItemStack[] aitemstack = this.mainInventory;

		if (slotIndex >= this.mainInventory.length)
		{
			aitemstack = this.armorInventory;
			slotIndex -= this.mainInventory.length;

			if (slotIndex >= this.armorInventory.length)
			{
				slotIndex -= aitemstack.length;
				aitemstack = this.craftInventory;
			}
		}

		if (aitemstack[slotIndex] != null)
		{
			ItemStack itemstack = aitemstack[slotIndex];
			aitemstack[slotIndex] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	public ItemStack decrStackSize(int slotIndex, int count)
	{
		ItemStack[] aitemstack = this.mainInventory;

		if (slotIndex >= this.mainInventory.length)
		{
			aitemstack = this.armorInventory;
			slotIndex -= this.mainInventory.length;

			if (slotIndex >= this.armorInventory.length)
			{
				slotIndex -= aitemstack.length;
				aitemstack = this.craftInventory;
			}
		}

		if (aitemstack[slotIndex] != null)
		{
			ItemStack itemstack;

			if (aitemstack[slotIndex].stackSize <= count)
			{
				itemstack = aitemstack[slotIndex];
				aitemstack[slotIndex] = null;
				return itemstack;
			}
			else
			{
				itemstack = aitemstack[slotIndex].splitStack(count);

				if (aitemstack[slotIndex].stackSize == 0)
				{
					aitemstack[slotIndex] = null;
				}

				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
	{
		ItemStack[] aitemstack = this.mainInventory;

		if (slotIndex >= aitemstack.length)
		{
			slotIndex -= aitemstack.length;
			aitemstack = this.armorInventory;

			if (slotIndex >= this.armorInventory.length)
			{
				slotIndex -= aitemstack.length;
				aitemstack = this.craftInventory;
			}
		}

		aitemstack[slotIndex] = itemStack;
	}

	/**
	 * Writes the inventory out as a list of compound tags. This is where the
	 * slot indices are used (+100 for armor, +80 for crafting).
	 */
	public NBTTagList writeToNBT(NBTTagList tag)
	{
		int i;
		NBTTagCompound nbttagcompound;

		for (i = 0; i < this.mainInventory.length; ++i)
		{
			if (this.mainInventory[i] != null)
			{
				nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.mainInventory[i].writeToNBT(nbttagcompound);
				tag.appendTag(nbttagcompound);
			}
		}

		for (i = 0; i < this.armorInventory.length; ++i)
		{
			if (this.armorInventory[i] != null)
			{
				nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) (i + 100));
				this.armorInventory[i].writeToNBT(nbttagcompound);
				tag.appendTag(nbttagcompound);
			}
		}

		for (i = 0; i < this.craftInventory.length; ++i)
		{
			if (this.craftInventory[i] != null)
			{
				nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) (i + 200));
				this.craftInventory[i].writeToNBT(nbttagcompound);
				tag.appendTag(nbttagcompound);
			}
		}

		return tag;
	}

	/**
	 * Reads from the given tag list and fills the slots in the inventory with
	 * the correct items.
	 */
	public void readFromNBT(NBTTagList tag)
	{
		this.mainInventory = new ItemStack[36];
		this.armorInventory = new ItemStack[4];

		for (int i = 0; i < tag.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = tag.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

			if (itemstack != null)
			{
				if (j >= 0 && j < this.mainInventory.length)
				{
					this.mainInventory[j] = itemstack;
				}

				if (j >= 100 && j < this.armorInventory.length + 100)
				{
					this.armorInventory[j - 100] = itemstack;
				}

				if (j >= 200 && j < (this.craftInventory.length + 200))
				{
					this.craftInventory[j - 200] = itemstack;
				}
			}
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return this.mainInventory.length + 4 + this.craftInventory.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int slotIndex)
	{
		ItemStack[] aitemstack = this.mainInventory;
		int a = 1;
		if (slotIndex >= aitemstack.length)
		{
			slotIndex -= aitemstack.length;
			aitemstack = this.armorInventory;

			if (slotIndex >= this.armorInventory.length)
			{
				slotIndex -= aitemstack.length;
				aitemstack = this.craftInventory;
			}
		}

		return aitemstack[slotIndex];
	}

	/**
	 * Returns the name of the inventory
	 */
	public String getInventoryName()
	{
		return "container.inventory";
	}

	/**
	 * Returns if the inventory is named
	 */
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Returns true if the specified ItemStack exists in the inventory.
	 */
	public boolean hasItemStack(ItemStack itemStack)
	{
		int i;

		for (i = 0; i < this.armorInventory.length; ++i)
		{
			if (this.armorInventory[i] != null && this.armorInventory[i].isItemEqual(itemStack))
			{
				return true;
			}
		}

		for (i = 0; i < this.mainInventory.length; ++i)
		{
			if (this.mainInventory[i] != null && this.mainInventory[i].isItemEqual(itemStack))
			{
				return true;
			}
		}

		for (i = 0; i < this.craftInventory.length; ++i)
		{
			if (this.craftInventory[i] != null && this.craftInventory[i].isItemEqual(itemStack))
			{
				return true;
			}
		}

		return false;
	}

	public void openInventory()
	{
	}

	public void closeInventory()
	{
	}

	/**
	 * Copy the ItemStack contents from another InventoryPlayer instance
	 */
	public void copyInventory(InventoryPlayer inventory)
	{
		int i;

		for (i = 0; i < this.mainInventory.length; ++i)
		{
			this.mainInventory[i] = ItemStack.copyItemStack(inventory.mainInventory[i]);
		}

		for (i = 0; i < this.armorInventory.length; ++i)
		{
			this.armorInventory[i] = ItemStack.copyItemStack(inventory.armorInventory[i]);
		}

		this.currentItem = inventory.currentItem;
	}
}
