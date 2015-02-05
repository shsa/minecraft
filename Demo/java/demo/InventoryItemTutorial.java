/*
 * COMMON PROXY CLASS
 */
/*
public class CommonProxy implements IGuiHandler
{
	public void registerRenderers() {}

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
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
		if (guiId == InventoryItemMain.GUI_ITEM_INV)
		{
			// We have to cast the new container as our custom class
			// and pass in currently held item for the inventory
			return new GuiItemInventory((ContainerItem) new ContainerItem(player, player.inventory, new InventoryItem(player.getHeldItem())));
		}
		return null;
	}
}
*/
/*
 * CLIENT PROXY CLASS
 */
/*
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers() {}
}
*/
/*
If you want to be able to place your inventory-holding Item within another
instance of itself, you'll need to have a way to distinguish between each
instance of the item so you can check to make sure you're not placing the item
within itself. What you'll need to do is assign a UUID to a String variable
within your Inventory class for every ItemStack that holds an instance of your
Item, like so:
 */
// declaration of variable:
//protected String uniqueID;

/** initialize variable within the constructor: */
/*
uniqueID = "";

if (!itemstack.hasTagCompound())
{
	itemstack.setTagCompound(new NBTTagCompound());
	// no tag compound means the itemstack does not yet have a UUID, so assign one:
	uniqueID = UUID.randomUUID().toString();
}
*/
/** When reading from NBT: */
/*
if ("".equals(uniqueID))
{
	// try to read unique ID from NBT
	uniqueID = tagcompound.getString("uniqueID");
	// if it's still "", assign a new one:
	if ("".equals(uniqueID))
	{
		uniqueID = UUID.randomUUID().toString();
	}
}
*/
/** Writing to NBT: */
// just add this line:
//tagcompound.setString("uniqueID", this.uniqueID);
/*
Finally, in your Container class, you will need to check if the currently opened
inventory's uniqueID is equal to the itemstack's uniqueID in the method
'transferStackInSlot' as well as check if the itemstack is the currently equipped 
item in the method 'slotClick'. In both cases, you'll need to prevent the itemstack
from being moved or it will cause bad things to happen.
*/

/**
 * Step 3: Create a custom Container for your Inventory
 */
/*
There's a LOT of code in this one, but read through all of the comments carefully
and it should become clear what everything does.

As a bonus, one of my previous tutorials is included within!
"How to Properly Override Shift-Clicking" is here and better than ever!
At least in my opinion.

If you're like me, and you find no end of frustration trying to figure out which
f-ing index you should use for which slots in your container when overriding
transferStackInSlot, or if your following the original tutorial, then read on.
 */
/*
Special note: If your custom inventory's stack limit is 1 and you allow shift-clicking itemstacks into it,
you will need to override mergeStackInSlot to avoid losing all the items but one in a stack when you shift-click.
*/
/**
 * Vanilla mergeItemStack method doesn't correctly handle inventories whose
 * max stack size is 1 when you shift-click into the inventory.
 * This is a modified method I wrote to handle such cases.
 * Note you only need it if your slot / inventory's max stack size is 1
 */
/*
Making the custom slot is very simple, if you're going that route:
*/

/**
 * Step 4: Create the GUI for our custom Inventory
 */

/*
There's not much to this, mostly just copy and paste from vanilla classes.
 */

/**
 * Step 5: Finally, create your custom Item class that will open the Inventory
 */
/*
Nice to end on an easy one. The item only needs to open the inventory gui.
method. I named it ItemStore to more clearly distinguish it from the rest of my
classes. Not very creative, I know, but sufficient for a tutorial.
 */
/*
And that's it!
*/
