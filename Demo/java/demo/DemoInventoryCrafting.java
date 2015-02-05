package demo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class DemoInventoryCrafting extends InventoryCrafting
{
	private ItemStack[] craftList;
	private int inventoryWidth;
	private int inventoryHeight;
	private DemoPlayerContainer container;
	
	public DemoInventoryCrafting(Container container, int cols, int rows)
	{
		super(container, cols, rows);
		this.container = (DemoPlayerContainer)container;
		DemoPlayerInventory inventory = (DemoPlayerInventory)this.container.thePlayer.inventory;
		this.craftList = inventory.craftInventory;
		this.inventoryWidth = cols;
		this.inventoryHeight = rows;
	}

    /**
     * Returns the number of slots in the inventory.
     */
	@Override
    public int getSizeInventory()
    {
        return this.craftList.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int slotIndex)
    {
        return slotIndex >= this.getSizeInventory() ? null : this.craftList[slotIndex];
    }

    /**
     * Returns the itemstack in the slot specified (Top left is 0, 0). Args: row, column
     */
    @Override
    public ItemStack getStackInRowAndColumn(int column, int row)
    {
        if (column >= 0 && column < this.inventoryWidth)
        {
            int k = column + row * this.inventoryWidth;
            return this.getStackInSlot(k);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the name of the inventory
     */
    @Override
    public String getInventoryName()
    {
        return "container.crafting";
    }

    /**
     * Returns if the inventory is named
     */
    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        if (this.craftList[slotIndex] != null)
        {
            ItemStack itemstack = this.craftList[slotIndex];
            this.craftList[slotIndex] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int slotIndex, int count)
    {
        if (this.craftList[slotIndex] != null)
        {
            ItemStack itemstack;

            if (this.craftList[slotIndex].stackSize <= count)
            {
                itemstack = this.craftList[slotIndex];
                this.craftList[slotIndex] = null;
                this.container.onCraftMatrixChanged(this);
                return itemstack;
            }
            else
            {
                itemstack = this.craftList[slotIndex].splitStack(count);

                if (this.craftList[slotIndex].stackSize == 0)
                {
                    this.craftList[slotIndex] = null;
                }

                this.container.onCraftMatrixChanged(this);
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        this.craftList[slotIndex] = itemStack;
        this.container.onCraftMatrixChanged(this);
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    @Override
    public void markDirty() {}

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack)
    {
        return true;
    }
}
