package imagicthecat.fundamentalchemistry;

import imagicthecat.fundamentalchemistry.shared.BiMap;
import imagicthecat.fundamentalchemistry.shared.Command;
import imagicthecat.fundamentalchemistry.shared.ForgeEventHandler;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import imagicthecat.fundamentalchemistry.shared.block.BlockTest;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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

  // blocks
  
  public static Block block_test;
  
  // API
  
  public static BiMap<String, Integer> elements = new BiMap<String, Integer>();
  public static BiMap<String, Molecule> molecules = new BiMap<String, Molecule>();
  
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
  
  // events
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
   	block_test = new BlockTest();
   	GameRegistry.registerBlock(block_test, "fundamentalchemistry:test");
  }

  @EventHandler
  public void init(FMLInitializationEvent event)
  {
  	registerElement("C", 6);
  	registerElement("H", 1);
  	registerElement("O", 8);
  	
  	registerMolecule("water", "H2O");
  	registerMolecule("dioxygen", "O2");
  	
    MinecraftForge.EVENT_BUS.register(event_handler);
    //CapabilityManager.INSTANCE.register(IStrings.class, new StringsStorage(), Strings.class);
  	
		GameRegistry.addRecipe(new ItemStack(block_test),
		  "A A",
		  " B ",
		  "A A",
		  'A', Blocks.cobblestone, 'B', Items.redstone
		);
		
    if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_test), 0, new ModelResourceLocation("fundamentalchemistry:test", "inventory"));
    }
  }
  
  @EventHandler
  public void serverLoad(FMLServerStartingEvent event){
  	event.registerServerCommand(new Command());
  }
}
