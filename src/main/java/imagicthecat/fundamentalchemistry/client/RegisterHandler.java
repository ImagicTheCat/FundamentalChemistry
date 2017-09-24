package imagicthecat.fundamentalchemistry.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.client.renderer.TileLaserRelayRenderer;
import imagicthecat.fundamentalchemistry.shared.ForgeRegisterHandler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;

public class RegisterHandler extends ForgeRegisterHandler{
	@Override
	public void register()
	{
		super.register();
		
  	//inventory renderers
  	
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_test), 0, new ModelResourceLocation("fundamentalchemistry:test", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_laser_relay), 0, new ModelResourceLocation("fundamentalchemistry:laser_relay", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_periodic_storage), 0, new ModelResourceLocation("fundamentalchemistry:periodic_storage", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_molecular_storage), 0, new ModelResourceLocation("fundamentalchemistry:molecular_storage", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_energy_storage), 0, new ModelResourceLocation("fundamentalchemistry:energy_storage", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_item_breaker), 0, new ModelResourceLocation("fundamentalchemistry:item_breaker", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_item_analyzer), 0, new ModelResourceLocation("fundamentalchemistry:item_analyzer", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_molecule_breaker), 0, new ModelResourceLocation("fundamentalchemistry:molecule_breaker", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_molecule_assembler), 0, new ModelResourceLocation("fundamentalchemistry:molecule_assembler", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_item_assembler), 0, new ModelResourceLocation("fundamentalchemistry:item_assembler", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_versatile_generator), 0, new ModelResourceLocation("fundamentalchemistry:versatile_generator", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_versatile_extractor), 0, new ModelResourceLocation("fundamentalchemistry:versatile_extractor", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_positive_nuclear_transmuter), 0, new ModelResourceLocation("fundamentalchemistry:positive_nuclear_transmuter", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(Item.getItemFromBlock(FundamentalChemistry.block_negative_nuclear_transmuter), 0, new ModelResourceLocation("fundamentalchemistry:negative_nuclear_transmuter", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(FundamentalChemistry.item_vibrant_catalyst_stone, 0, new ModelResourceLocation("fundamentalchemistry:vibrant_catalyst_stone", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(FundamentalChemistry.item_atom_display, 0, new ModelResourceLocation("fundamentalchemistry:atom_display", "inventory"));
  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
  	.register(FundamentalChemistry.item_molecule_display, 0, new ModelResourceLocation("fundamentalchemistry:molecule_display", "inventory"));
  	
  	//tile entity renderers
  	 
    ClientRegistry.bindTileEntitySpecialRenderer(TileLaserRelay.class, new TileLaserRelayRenderer());
	}
}
