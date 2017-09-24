package imagicthecat.fundamentalchemistry.shared;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.capability.PlayerProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class ForgeEventHandler {
	public static final ResourceLocation PLAYER_CAPABILITY = new ResourceLocation(FundamentalChemistry.MODID, "player_capability");

	public ForgeEventHandler()
	{

	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> evt){
		System.out.println("registered");
		evt.getRegistry().registerAll(
                  FundamentalChemistry.block_test,
                  FundamentalChemistry.block_laser_relay,
                  FundamentalChemistry.block_periodic_storage,
                  FundamentalChemistry.block_molecular_storage,
                  FundamentalChemistry.block_energy_storage,
                  FundamentalChemistry.block_item_analyzer,
                  FundamentalChemistry.block_item_breaker,
                  FundamentalChemistry.block_molecule_breaker,
                  FundamentalChemistry.block_molecule_assembler,
                  FundamentalChemistry.block_item_assembler,
                  FundamentalChemistry.block_versatile_generator,
                  FundamentalChemistry.block_versatile_extractor,
                  FundamentalChemistry.block_positive_nuclear_transmuter,
                  FundamentalChemistry.block_negative_nuclear_transmuter
		);
	}
	
	public static ItemBlock item_block(Block block){
		ItemBlock b = new ItemBlock(block);
		b.setRegistryName(block.getRegistryName());
		return b;
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt){
		//item blocks
		evt.getRegistry().registerAll(
				item_block(FundamentalChemistry.block_test),
				item_block(FundamentalChemistry.block_laser_relay),
				item_block(FundamentalChemistry.block_periodic_storage),
				item_block(FundamentalChemistry.block_molecular_storage),
				item_block(FundamentalChemistry.block_energy_storage),
				item_block(FundamentalChemistry.block_item_analyzer),
				item_block(FundamentalChemistry.block_item_breaker),
				item_block(FundamentalChemistry.block_molecule_breaker),
				item_block(FundamentalChemistry.block_molecule_assembler),
				item_block(FundamentalChemistry.block_item_assembler),
				item_block(FundamentalChemistry.block_versatile_generator),
				item_block(FundamentalChemistry.block_versatile_extractor),
				item_block(FundamentalChemistry.block_positive_nuclear_transmuter),
				item_block(FundamentalChemistry.block_negative_nuclear_transmuter)
		);
		
		//items
		evt.getRegistry().registerAll(
				FundamentalChemistry.item_vibrant_catalyst_stone,
				FundamentalChemistry.item_atom_display,
				FundamentalChemistry.item_molecule_display
		);
	}
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> evt){
		if(evt.getObject() instanceof EntityPlayer)
			evt.addCapability(PLAYER_CAPABILITY, new PlayerProvider());
	}
}
