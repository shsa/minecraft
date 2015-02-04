package demo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class DemoPlayerContainer extends Container
{
	private final EntityPlayer thePlayer;
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;

	private World worldObj;

	public DemoPlayerContainer(EntityPlayer player)
	{
		this.thePlayer = player;
		this.worldObj = player.worldObj;
		craftMatrix = new InventoryCrafting(this, 5, 5);
		craftResult = new InventoryCraftResult();

		int slotIndex = player.inventory.getSizeInventory() - 1;
		
		// Output
		this.addSlotToContainer(new SlotCrafting(player, craftMatrix, craftResult, slotIndex--, 152, 26));

		// Fuel
		// this.addSlotToContainer(new Slot(tileEntity, 0, 112, 60));

		// Crafting input
		for (int row = 0; row < 3; row++)
		{
			for (int column = 0; column < 3; column++)
			{
				this.addSlotToContainer(new Slot(craftMatrix, slotIndex--, 85 + column * 18, 8 + row * 18));
			}
		}

		for (int i = 0; i < 4; ++i)
		{
			final int k = i;
			this.addSlotToContainer(new Slot(player.inventory, slotIndex--, 8, 8 + i * 18)
			{
				private static final String __OBFID = "CL_00001755";

				/**
				 * Returns the maximum stack size for a given slot (usually the
				 * same as getInventoryStackLimit(), but 1 in the case of armor
				 * slots)
				 */
				public int getSlotStackLimit()
				{
					return 1;
				}

				/**
				 * Check if the stack is a valid item for this slot. Always true
				 * beside for the armor slots.
				 */
				public boolean isItemValid(ItemStack itemStack)
				{
					if (itemStack == null)
						return false;
					return itemStack.getItem().isValidArmor(itemStack, k, thePlayer);
				}

				/**
				 * Returns the icon index on items.png that is used as
				 * background image of the slot.
				 */
				@SideOnly(Side.CLIENT)
				public IIcon getBackgroundIconIndex()
				{
					return ItemArmor.func_94602_b(k);
				}
			});
		}

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(player.inventory, slotIndex--, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player.inventory, slotIndex--, 8 + i * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	public void onCraftMatrixChanged(IInventory iinventory)
	{
		this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer)
	{
		super.onContainerClosed(entityplayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int index)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0)
			{
				if (!this.mergeItemStack(itemstack1, 14, 50, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index >= 1 && index < 10)
			{
				if (!this.mergeItemStack(itemstack1, 14, 50, false))
				{
					return null;
				}
			}
			else if (index >= 10 && index < 14)
			{
				if (!this.mergeItemStack(itemstack1, 14, 50, false))
				{
					return null;
				}
			}
			else if (itemstack.getItem() instanceof ItemArmor
					&& !((Slot) this.inventorySlots.get(10 + ((ItemArmor) itemstack.getItem()).armorType)).getHasStack())
			{
				int j = 10 + ((ItemArmor) itemstack.getItem()).armorType;

				if (!this.mergeItemStack(itemstack1, j, j + 1, false))
				{
					return null;
				}
			}
			else if (index >= 14 && index < 41)
			{
				if (!this.mergeItemStack(itemstack1, 41, 50, false))
				{
					return null;
				}
			}
			else if (index >= 41 && index < 50)
			{
				if (!this.mergeItemStack(itemstack1, 14, 48, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 14, 48, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(entityPlayer, itemstack1);
		}

		return itemstack;
	}
}