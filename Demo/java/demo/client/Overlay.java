package demo.client;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import demo.DemoMod;
import demo.Log;

//
// GuiBuffBar implements a simple status bar at the top of the screen which 
// shows the current buffs/debuffs applied to the character.
//
public class Overlay extends Gui
{
	private Minecraft mc;
	private static final ResourceLocation texture = new ResourceLocation(DemoMod.MODID, "textures/gui/InfoPlayer.png");
	static RenderItem ri = new RenderItem();

	public Overlay(Minecraft mc)
	{
		super();

		// We need this to invoke the render engine.
		this.mc = mc;
		res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	}

	private ScaledResolution res = null;

	//
	// This event is called by GuiIngameForge during each frame by
	// GuiIngameForge.pre() and GuiIngameForce.post().
	//
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event)
	{
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
		{
			return;
		}
		
		//Object object = Minecraft.getMinecraft().renderViewEntity.rayTrace(100, event.partialTicks);
		Object object = Minecraft.getMinecraft().pointedEntity;
		if (object == null)
			return;
		Log.msg("%s", object.toString());

		final int width = res.getScaledWidth();
		final int height = res.getScaledHeight();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.mc.getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect(0, 0, 0, 0, 100, 100);
		//func_147046_a(k + 51, l + 75, 30, (float) (k + 51) - this.xSizeFloat, (float) (l + 75 - 50) - this.ySizeFloat, this.mc.thePlayer);

		if (mc.currentScreen == null && !mc.gameSettings.showDebugInfo && mc.thePlayer != null)
		{
		}
	}

	/**
	 * @param x
	 *            Location X
	 * @param y
	 *            Location Y
	 * @param w
	 *            Draw Width
	 * @param h
	 *            Draw Height
	 */
	public void drawTexture(int x, int y, int w, int h)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + h, this.zLevel, 0D, 1D);
		tessellator.addVertexWithUV(x + w, y + h, this.zLevel, 1D, 1D);
		tessellator.addVertexWithUV(x + w, y + 0, this.zLevel, 1D, 0D);
		tessellator.addVertexWithUV(x + 0, y + 0, this.zLevel, 0D, 0D);
		tessellator.draw();
	}
}