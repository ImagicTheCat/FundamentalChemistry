package imagicthecat.fundamentalchemistry.client;

import org.lwjgl.opengl.GL11;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ForgeEventHandler;
import imagicthecat.fundamentalchemistry.shared.properties.PlayerProperties;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ClientEventHandler extends ForgeEventHandler{
	public ClientEventHandler()
	{
	}
	
  public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight, float z)
  {
      float f = 1.0F / textureWidth;
      float f1 = 1.0F / textureHeight;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double)x, (double)(y + height), z).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
      worldrenderer.pos((double)(x + width), (double)(y + height), z).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
      worldrenderer.pos((double)(x + width), (double)y, z).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
      worldrenderer.pos((double)x, (double)y, z).tex((double)(u * f), (double)(v * f1)).endVertex();
      tessellator.draw();
  }
  
	@SubscribeEvent
	public void onWorldLastRender(RenderWorldLastEvent evt)
	{
		World world = Minecraft.getMinecraft().theWorld;
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
    double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * evt.partialTicks;
    double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * evt.partialTicks;
    double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * evt.partialTicks;
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		
		GL11.glTranslated(-x, -y, -z);

		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		
		GL11.glLineWidth(3);

		GL11.glBegin(GL11.GL_LINES);
		
		//display link process
		PlayerProperties props = (PlayerProperties)player.getExtendedProperties(PlayerProperties.ID);

		if(props != null && props.p_link != null 
				&& props.p_link.distanceSq(x,y+1,z) <= FundamentalChemistry.MAX_RELAY_DISTANCE-1f){ //distance check with some margin
			GlStateManager.color(0f,1f,0.5f,1f);
			GL11.glVertex3d(props.p_link.getX()+0.5, props.p_link.getY()+0.5, props.p_link.getZ()+0.5);
			GlStateManager.color(0f,0.5f,1f,1f);
			GL11.glVertex3d(x, y+1, z);
		}

		for(TileEntity te : world.loadedTileEntityList){
			if(te instanceof TileLaserRelay){
				TileLaserRelay ent = (TileLaserRelay)te;
				
				// change matrix for absolute coordinates
				BlockPos cpos = ent.getPos();

				for(BlockPos pos : ent.inputs){
					TileEntity ce = te.getWorld().getTileEntity(pos);
					if(ce != null && ce instanceof TileLaserRelay && (player.getPosition().distanceSq(pos.getX(),pos.getY(),pos.getZ()) <= 500 
							|| player.getPosition().distanceSq(cpos.getX(),cpos.getY(),cpos.getZ()) <= 500)){
						//add height shift if it is a circular connections
						TileLaserRelay cent = (TileLaserRelay)ce;
						float shift = 0;
						if(cent.inputs.contains(cpos) && cpos.hashCode() < pos.hashCode())
							shift = 0.15f;
						
						//draw connection
						GlStateManager.color(0f,0.5f,1f,1f);
						GL11.glVertex3d(cpos.getX()+0.5, cpos.getY()+0.5-shift, cpos.getZ()+0.5);
						GlStateManager.color(0f,1f,0.5f,1f);
						GL11.glVertex3d(pos.getX()+0.5, pos.getY()+0.5-shift, pos.getZ()+0.5);
					}
				}
			}
		}
		
		GL11.glEnd();
		
		GlStateManager.popMatrix();
		GlStateManager.popAttrib();
		
	}
}
