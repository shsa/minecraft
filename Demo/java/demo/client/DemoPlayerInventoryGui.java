package demo.client;

import org.lwjgl.opengl.GL11;

import demo.DemoMod;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class DemoPlayerInventoryGui extends GuiInventory
{
	protected static final ResourceLocation texture = new ResourceLocation(DemoMod.MODID, "textures/gui/container/inventory.png");

	/**
	 * x size of the inventory window in pixels. Defined as float, passed as int
	 */
	private float xSizeFloat;
	/**
	 * y size of the inventory window in pixels. Defined as float, passed as
	 * int.
	 */
	private float ySizeFloat;

	public DemoPlayerInventoryGui(EntityPlayer player)
	{
		super(player);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		//this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int x, int y, float z)
	{
		super.drawScreen(x, y, z);
		this.xSizeFloat = (float) x;
		this.ySizeFloat = (float) y;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float z, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = this.guiLeft;
		int l = this.guiTop;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		func_147046_a(k + 51, l + 75, 30, (float) (k + 51) - this.xSizeFloat, (float) (l + 75 - 50) - this.ySizeFloat, this.mc.thePlayer);
	}

}
