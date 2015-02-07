package demo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
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
	private Entity targetEntity;
	private Date targetTime = new Date();

	public Overlay(Minecraft mc)
	{
		super();

		// We need this to invoke the render engine.
		this.mc = mc;
		res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	}

	private ScaledResolution res = null;

	private float timeTarget;
	
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
		
        Entity pointedEntity = null;
        double d0 = 100;
        MovingObjectPosition obj = this.mc.renderViewEntity.rayTrace(d0, event.partialTicks);
		if (obj != null)
		{
	        Vec3 vec3 = this.mc.renderViewEntity.getPosition(event.partialTicks);
            Vec3 vec31 = this.mc.renderViewEntity.getLook(event.partialTicks);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            float f1 = 1.0F;
            List list = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
	        
            double d2 = d0;
            if (obj != null)
            {
                d2 = obj.hitVec.distanceTo(vec3);
            }
            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);

                if (entity.canBeCollidedWith())
                {
                    float f2 = entity.getCollisionBorderSize();
                    AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                    MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                    if (axisalignedbb.isVecInside(vec3))
                    {
                        if (0.0D < d2 || d2 == 0.0D)
                        {
                            pointedEntity = entity;
                            d2 = 0.0D;
                        }
                    }
                    else if (movingobjectposition != null)
                    {
                        double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                        if (d3 < d2 || d2 == 0.0D)
                        {
                            if (entity == this.mc.renderViewEntity.ridingEntity && !entity.canRiderInteract())
                            {
                                if (d2 == 0.0D)
                                {
                                    pointedEntity = entity;
                                }
                            }
                            else
                            {
                                pointedEntity = entity;
                            }
                        }
                    }
                }
            }
		}
		
		if (pointedEntity != null)
		{
			this.targetEntity = pointedEntity;
			this.targetTime = new Date();
		}
		else
		{
			Date time = new Date();
			if ((time.getTime() - this.targetTime.getTime()) > 500)
				this.targetEntity = null;
		}

		if (this.targetEntity == null)
			return;
		
		//Log.msg("%s", object.toString());

		final int width = res.getScaledWidth();
		final int height = res.getScaledHeight();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.mc.getTextureManager().bindTexture(texture);
		int width1 = width / 4;
		int height1 = (int) (29 * (width1 / 259.0F));
		int left = (width - width1) / 2;
		this.drawTexturedRect(left, 0, width1, height1, 0, 0, 259, 29, 512);
		this.drawCenteredString(mc.fontRenderer, this.targetEntity.getClass().getSimpleName(), width / 2, 2, (int)this.zLevel);
		//func_147046_a(k + 51, l + 75, 30, (float) (k + 51) - this.xSizeFloat, (float) (l + 75 - 50) - this.ySizeFloat, this.mc.thePlayer);

		if (mc.currentScreen == null && !mc.gameSettings.showDebugInfo && mc.thePlayer != null)
		{
		}
	}

    /**
     * Renders the specified text to the screen, center-aligned.
     */
	@Override
    public void drawCenteredString(FontRenderer font, String text, int x, int y, int z)
    {
        font.drawString(text, x - font.getStringWidth(text) / 2, y, z);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedRect(int x, int y, int width, int height, int u, int v, int uwidth, int vheight, int textureSize)
    {
        float f = 1.0F / textureSize;
        float f1 = f;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + vheight) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)((float)(u + uwidth) * f), (double)((float)(v + vheight) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)this.zLevel, (double)((float)(u + uwidth) * f), (double)((float)(v + 0) * f1));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
        tessellator.draw();
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