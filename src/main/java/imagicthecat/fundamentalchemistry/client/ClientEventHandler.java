package imagicthecat.fundamentalchemistry.client;

import org.lwjgl.opengl.GL11;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ForgeEventHandler;
import imagicthecat.fundamentalchemistry.shared.capability.IPlayerCapability;
import imagicthecat.fundamentalchemistry.shared.capability.PlayerProvider;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

@Mod.EventBusSubscriber
public class ClientEventHandler extends ForgeEventHandler{
	public static float laser_height = 0.562f;
	
	public ClientEventHandler()
	{
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent evt)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_test), 0, new ModelResourceLocation("fundamentalchemistry:test", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_laser_relay), 0, new ModelResourceLocation("fundamentalchemistry:laser_relay", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_periodic_storage), 0, new ModelResourceLocation("fundamentalchemistry:periodic_storage", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_molecular_storage), 0, new ModelResourceLocation("fundamentalchemistry:molecular_storage", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_energy_storage), 0, new ModelResourceLocation("fundamentalchemistry:energy_storage", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_item_breaker), 0, new ModelResourceLocation("fundamentalchemistry:item_breaker", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_item_analyzer), 0, new ModelResourceLocation("fundamentalchemistry:item_analyzer", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_molecule_breaker), 0, new ModelResourceLocation("fundamentalchemistry:molecule_breaker", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_molecule_assembler), 0, new ModelResourceLocation("fundamentalchemistry:molecule_assembler", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_item_assembler), 0, new ModelResourceLocation("fundamentalchemistry:item_assembler", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_versatile_generator), 0, new ModelResourceLocation("fundamentalchemistry:versatile_generator", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_versatile_extractor), 0, new ModelResourceLocation("fundamentalchemistry:versatile_extractor", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_positive_nuclear_transmuter), 0, new ModelResourceLocation("fundamentalchemistry:positive_nuclear_transmuter", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(FundamentalChemistry.block_negative_nuclear_transmuter), 0, new ModelResourceLocation("fundamentalchemistry:negative_nuclear_transmuter", "inventory"));
	
		ModelLoader.setCustomModelResourceLocation(FundamentalChemistry.item_vibrant_catalyst_stone, 0, new ModelResourceLocation("fundamentalchemistry:vibrant_catalyst_stone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(FundamentalChemistry.item_atom_display, 0, new ModelResourceLocation("fundamentalchemistry:atom_display", "inventory"));
		ModelLoader.setCustomModelResourceLocation(FundamentalChemistry.item_molecule_display, 0, new ModelResourceLocation("fundamentalchemistry:molecule_display", "inventory"));
	}
	
	@SubscribeEvent
	public void onWorldLastRender(RenderWorldLastEvent evt)
	{
		World world = Minecraft.getMinecraft().theWorld;
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
    double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * evt.getPartialTicks();
    double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * evt.getPartialTicks();
    double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * evt.getPartialTicks();
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		
		GL11.glTranslated(-x, -y, -z);

		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		
		int shade_model = GL11.glGetInteger(GL11.GL_SHADE_MODEL);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		
		GL11.glLineWidth(3);

		GL11.glBegin(GL11.GL_LINES);
		
		//display link process
		IPlayerCapability pcap = (IPlayerCapability)player.getCapability(PlayerProvider.PLAYER_CAPABILITY, null);

		if(pcap != null && pcap.getLinkPos() != null 
				&& pcap.getLinkPos().distanceSq(x,y+1,z) <= FundamentalChemistry.MAX_RELAY_DISTANCE-1f){ //distance check with some margin
			GlStateManager.color(0f,1f,0.5f,1f);
			GL11.glVertex3d(pcap.getLinkPos().getX()+0.5,pcap.getLinkPos().getY()+laser_height, pcap.getLinkPos().getZ()+0.5);
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
							shift = 1/16.0f*2;
						
						//draw connection
						GlStateManager.color(0f,0.5f,1f,1f);
						GL11.glVertex3d(cpos.getX()+0.5, cpos.getY()+laser_height-shift, cpos.getZ()+0.5);
						GlStateManager.color(0f,1f,0.5f,1f);
						GL11.glVertex3d(pos.getX()+0.5, pos.getY()+laser_height-shift, pos.getZ()+0.5);
					}
				}
			}
		}
		
		GL11.glEnd();
		
		GlStateManager.shadeModel(shade_model);
		
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		
		GlStateManager.popMatrix();
		GlStateManager.popAttrib();
	}
}
