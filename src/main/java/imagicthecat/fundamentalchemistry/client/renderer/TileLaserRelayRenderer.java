package imagicthecat.fundamentalchemistry.client.renderer;

import org.lwjgl.opengl.GL11;

import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class TileLaserRelayRenderer extends TileEntitySpecialRenderer{

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float partialTicks, int destroyStage) {
		/*
		TileLaserRelay ent = (TileLaserRelay)te;
		
		Tessellator tes = Tessellator.getInstance();
		WorldRenderer wre = tes.getWorldRenderer();
		
		// display connections
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		
		// change matrix for absolute coordinates
		BlockPos cpos = ent.getPos();
		
		
		GL11.glTranslated(-cpos.getX()+x, -cpos.getY()+y, -cpos.getZ()+z);
		
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.color(1,1,1,1);

		GL11.glBegin(GL11.GL_TRIANGLES);

		for(BlockPos pos : ent.inputs){
			TileEntity ce = te.getWorld().getTileEntity(pos);
			if(ce != null && ce instanceof TileLaserRelay){
				//draw connection
				GL11.glVertex3d(cpos.getX()+0.5, cpos.getY()+0.4, cpos.getZ()+0.5);
				GL11.glVertex3d(cpos.getX()+0.5, cpos.getY()+0.6, cpos.getZ()+0.5);
				GL11.glVertex3d(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
			}
		}
		
		
		GL11.glEnd();
		
		GlStateManager.popMatrix();
		GlStateManager.popAttrib();
		*/
	}
}
