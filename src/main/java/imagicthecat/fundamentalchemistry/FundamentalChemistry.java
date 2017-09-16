package imagicthecat.fundamentalchemistry;

import java.util.HashMap;
import java.util.Map;

import imagicthecat.fundamentalchemistry.client.renderer.TileLaserRelayRenderer;
import imagicthecat.fundamentalchemistry.shared.BiMap;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Command;
import imagicthecat.fundamentalchemistry.shared.ForgeEventHandler;
import imagicthecat.fundamentalchemistry.shared.ForgeGuiHandler;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import imagicthecat.fundamentalchemistry.shared.block.BlockItemAssembler;
import imagicthecat.fundamentalchemistry.shared.block.BlockItemBreaker;
import imagicthecat.fundamentalchemistry.shared.block.BlockLaserRelay;
import imagicthecat.fundamentalchemistry.shared.block.BlockMolecularStorage;
import imagicthecat.fundamentalchemistry.shared.block.BlockMoleculeAssembler;
import imagicthecat.fundamentalchemistry.shared.block.BlockMoleculeBreaker;
import imagicthecat.fundamentalchemistry.shared.block.BlockPeriodicStorage;
import imagicthecat.fundamentalchemistry.shared.block.BlockTest;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemAssembler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemBreaker;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileMoleculeAssembler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileMoleculeBreaker;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = FundamentalChemistry.MODID, version = FundamentalChemistry.VERSION)
public class FundamentalChemistry
{
  public static final String MODID = "fundamentalchemistry";
  public static final String VERSION = "0.1";
  
  @SidedProxy(clientSide="imagicthecat.fundamentalchemistry.client.ClientEventHandler", serverSide="imagicthecat.fundamentalchemistry.server.ServerEventHandler")
  public static ForgeEventHandler event_handler;
  
  @Instance(FundamentalChemistry.MODID)
  public static FundamentalChemistry instance;
  
  // config
  
  public static float MAX_RELAY_DISTANCE = 200f;
  public static int RELAY_TICKS = 40;

  // blocks
  
  public static Block block_test;
  public static Block block_laser_relay;
  public static Block block_periodic_storage;
  public static Block block_molecular_storage;
  public static Block block_item_breaker;
  public static Block block_molecule_breaker;
  public static Block block_molecule_assembler;
  public static Block block_item_assembler;
  
  // API
  
  public static BiMap<String, Integer> elements = new BiMap<String, Integer>();
  public static BiMap<String, Molecule> molecules = new BiMap<String, Molecule>();
  public static BiMap<Item, Map<Molecule, Integer>> item_compositions = new BiMap<Item, Map<Molecule, Integer>>();
  
  // register atomic element
  public static void registerElement(String name, int atomic_number)
  {
  	elements.put(name, atomic_number);
  }
 
  // register molecule
  public static void registerMolecule(String name, String notation)
  {
  	molecules.put(name, Molecule.fromNotation(notation));
  }
  
  // register item composition
  public static void registerItemComposition(Item item, Map<Molecule, Integer> molecules)
  {
  	item_compositions.put(item,  molecules);
  }
  
  // register item composition (using format "<number> <molecule_name>", "<number> <molecule_name>",...)
  public static void registerItemComposition(Item item, String... strings)
  {
  	Map<Molecule, Integer> _molecules = new HashMap<Molecule, Integer>();
  	for(String str : strings){
  		String[] parts = str.split(" ");
  		if(parts.length == 2){
  			Molecule m = molecules.get(parts[1]);
  			if(m != null){
  				try{
  					int amount = Integer.parseInt(parts[0]);
  					if(amount > 0)
  						_molecules.put(m, amount);
  				
  				}catch(NumberFormatException e){}
  			}
  		}
  	}
  	
  	item_compositions.put(item,  _molecules);
  }
  
  // events
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
   	block_test = new BlockTest();
   	block_laser_relay = new BlockLaserRelay();
   	block_periodic_storage = new BlockPeriodicStorage();
   	block_molecular_storage = new BlockMolecularStorage();
   	block_item_breaker = new BlockItemBreaker();
   	block_molecule_breaker = new BlockMoleculeBreaker();
   	block_molecule_assembler = new BlockMoleculeAssembler();
   	block_item_assembler = new BlockItemAssembler();
   	GameRegistry.registerBlock(block_test, "fundamentalchemistry:test");
   	GameRegistry.registerBlock(block_laser_relay, "fundamentalchemistry:laser_relay");
   	GameRegistry.registerBlock(block_periodic_storage, "fundamentalchemistry:periodic_storage");
  	GameRegistry.registerBlock(block_molecular_storage, "fundamentalchemistry:molecular_storage");
  	GameRegistry.registerBlock(block_item_breaker, "fundamentalchemistry:item_breaker");
  	GameRegistry.registerBlock(block_molecule_breaker, "fundamentalchemistry:molecule_breaker");
  	GameRegistry.registerBlock(block_molecule_assembler, "fundamentalchemistry:molecule_assembler");
  	GameRegistry.registerBlock(block_item_assembler, "fundamentalchemistry:item_assembler");
   	
   	GameRegistry.registerTileEntity(TileLaserRelay.class, "fundamentalchemistry:laser_relay");
   	GameRegistry.registerTileEntity(TileChemicalStorage.class, "fundamentalchemistry:chemical_storage");
   	GameRegistry.registerTileEntity(TileItemBreaker.class, "fundamentalchemistry:item_breaker");
   	GameRegistry.registerTileEntity(TileMoleculeBreaker.class, "fundamentalchemistry:molecule_breaker");
   	GameRegistry.registerTileEntity(TileMoleculeAssembler.class, "fundamentalchemistry:molecule_assembler");
   	GameRegistry.registerTileEntity(TileItemAssembler.class, "fundamentalchemistry:item_assembler");
  }

  @EventHandler
  public void init(FMLInitializationEvent event)
  {
  	registerElement("C", 6);
  	registerElement("H", 1);
  	registerElement("O", 8);
  	
  	registerMolecule("water", "H2O");
  	registerMolecule("dioxygen", "O2");
  	registerMolecule("metal_carbon", "C40");
  	
  	registerItemComposition(Items.water_bucket, "10 water");
  	registerItemComposition(Items.iron_ingot, "5 metal_carbon");
  	
    MinecraftForge.EVENT_BUS.register(event_handler);
    NetworkRegistry.INSTANCE.registerGuiHandler(FundamentalChemistry.instance, new ForgeGuiHandler());
		
    if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
    	//inventory renderers
    	
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_test), 0, new ModelResourceLocation("fundamentalchemistry:test", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_laser_relay), 0, new ModelResourceLocation("fundamentalchemistry:laser_relay", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_periodic_storage), 0, new ModelResourceLocation("fundamentalchemistry:periodic_storage", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_molecular_storage), 0, new ModelResourceLocation("fundamentalchemistry:molecular_storage", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_item_breaker), 0, new ModelResourceLocation("fundamentalchemistry:item_breaker", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_molecule_breaker), 0, new ModelResourceLocation("fundamentalchemistry:molecule_breaker", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_molecule_assembler), 0, new ModelResourceLocation("fundamentalchemistry:molecule_assembler", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_item_assembler), 0, new ModelResourceLocation("fundamentalchemistry:item_assembler", "inventory"));
	  	
	  	//tile entity renderers
	  	 
      ClientRegistry.bindTileEntitySpecialRenderer(TileLaserRelay.class, new TileLaserRelayRenderer());
    }
  }
  
  @EventHandler
  public void serverLoad(FMLServerStartingEvent event){
  	event.registerServerCommand(new Command());
  }
}
