package imagicthecat.fundamentalchemistry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scala.reflect.io.Path;
import imagicthecat.fundamentalchemistry.client.renderer.TileLaserRelayRenderer;
import imagicthecat.fundamentalchemistry.shared.BiMap;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Command;
import imagicthecat.fundamentalchemistry.shared.ForgeCreativeTab;
import imagicthecat.fundamentalchemistry.shared.ForgeEventHandler;
import imagicthecat.fundamentalchemistry.shared.ForgeGuiHandler;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import imagicthecat.fundamentalchemistry.shared.block.BlockEnergyStorage;
import imagicthecat.fundamentalchemistry.shared.block.BlockItemAssembler;
import imagicthecat.fundamentalchemistry.shared.block.BlockItemBreaker;
import imagicthecat.fundamentalchemistry.shared.block.BlockLaserRelay;
import imagicthecat.fundamentalchemistry.shared.block.BlockMolecularStorage;
import imagicthecat.fundamentalchemistry.shared.block.BlockMoleculeAssembler;
import imagicthecat.fundamentalchemistry.shared.block.BlockMoleculeBreaker;
import imagicthecat.fundamentalchemistry.shared.block.BlockNegativeNuclearTransmuter;
import imagicthecat.fundamentalchemistry.shared.block.BlockPeriodicStorage;
import imagicthecat.fundamentalchemistry.shared.block.BlockPositiveNuclearTransmuter;
import imagicthecat.fundamentalchemistry.shared.block.BlockTest;
import imagicthecat.fundamentalchemistry.shared.block.BlockVersatileExtractor;
import imagicthecat.fundamentalchemistry.shared.block.BlockVersatileGenerator;
import imagicthecat.fundamentalchemistry.shared.item.ItemAtomDisplay;
import imagicthecat.fundamentalchemistry.shared.item.ItemMoleculeDisplay;
import imagicthecat.fundamentalchemistry.shared.item.ItemVibrantCatalystStone;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileEnergyStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemAssembler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemBreaker;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileMolecularStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileMoleculeAssembler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileMoleculeBreaker;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileNegativeNuclearTransmuter;
import imagicthecat.fundamentalchemistry.shared.tileentity.TilePeriodicStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TilePositiveNuclearTransmuter;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileVersatileExtractor;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileVersatileGenerator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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
  
  // creative tab 

	public static final CreativeTabs tab = new ForgeCreativeTab();

  // blocks
  
  public static Block block_test;
  public static Block block_laser_relay;
  public static Block block_periodic_storage;
  public static Block block_molecular_storage;
  public static Block block_energy_storage;
  public static Block block_item_breaker;
  public static Block block_molecule_breaker;
  public static Block block_molecule_assembler;
  public static Block block_item_assembler;
  public static Block block_versatile_generator;
  public static Block block_versatile_extractor;
  public static Block block_positive_nuclear_transmuter;
  public static Block block_negative_nuclear_transmuter;
  
  // items
  
  public static Item item_vibrant_catalyst_stone;
  public static Item item_atom_display;
  public static Item item_molecule_display;
  
  // API
  
  public static BiMap<String, Integer> elements = new BiMap<String, Integer>();
  public static BiMap<String, Molecule> molecules = new BiMap<String, Molecule>();
  public static Map<Item, Map<Molecule, Integer>> item_compositions = new HashMap<Item, Map<Molecule, Integer>>();
  public static Map<Item, Integer> nuclear_transmuter_catalysts = new HashMap<Item, Integer>();
  public static Map<Item, ItemStack[]> ingredients = new HashMap<Item, ItemStack[]>();
  
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
  
  // register item composition (using format "<number> <molecule_name>", "<number> <molecule_name>", ...)
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
  
  // register nuclear transmuter catalyst 
  // power: commonly 1-5 (50+power*10 % clamped to 99% chance of success to produce atom with this power variation) 
  public static void registerNuclearTransmuterCatalyst(Item item, int power)
  {
  	nuclear_transmuter_catalysts.put(item, power);
  }
  
  // get item composition
  public static Map<Molecule, Integer> getItemComposition(Item item)
  {
  	if(item == null)
  		return null;
  	
  	System.out.println("getcompo "+item.getUnlocalizedName());
  	
  	// return registered composition
  	Map<Molecule, Integer> registered = item_compositions.get(item);
  	if(registered != null)
  		return registered;
  	
  	// try to deduce composition using ingredients (crafting recipes)
  	ItemStack[] items = ingredients.get(item);
  	if(items != null){
  		ChemicalStorage composition = new ChemicalStorage();
  		
  		for(ItemStack ingredient : items){
  			if(ingredient != null){
	  			Map<Molecule, Integer> ic = getItemComposition(ingredient.getItem());
	  			if(ic != null){
	  				//add to composition
	  				for(int i = 0; i < ingredient.stackSize; i++)
	  					composition.addMolecules(new ChemicalStorage(null, ic));
	  			}
  			}
  		}
  		
  		if(!composition.isEmpty()){
  			//optimize by registering computed composition for further calls
  			registerItemComposition(item, composition.molecules);
  			
  			return composition.molecules;
  		}
  	}
  	
  	// custom composition deductions
  	
  	return null;
  }
  
  // events
  
  /*
  public void loadConfigAtoms(String path)
  {
   	File file = new File(path);
   	try {
			FileReader file_reader = new FileReader(file);
			BufferedReader breader = new BufferedReader(file_reader);
			String line;
			try {
				while ((line = breader.readLine()) != null){
					String[] parts = line.split(" ");
					if(parts.length >= 2){
						try{
							int an = Integer.parseInt(parts[1]);
							if(!parts[0].isEmpty())
								registerElement(parts[0], an);
						}catch(NumberFormatException e){}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
  }
  
  public void loadConfigMolecules(String path)
  {
   	File file = new File(path);
   	try {
			FileReader file_reader = new FileReader(file);
			BufferedReader breader = new BufferedReader(file_reader);
			String line;
			try {
				while ((line = breader.readLine()) != null){
					String[] parts = line.split(" ");
					if(parts.length >= 2){
						try{
							if(!parts[0].isEmpty())
								registerMolecule(parts[0], parts[1]);
						}catch(NumberFormatException e){}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
  }

  public void loadConfigItemCompositions(String path)
  {
   	File file = new File(path);
   	try {
			FileReader file_reader = new FileReader(file);
			BufferedReader breader = new BufferedReader(file_reader);
			String line;
			try {
				while ((line = breader.readLine()) != null){
					String[] parts = line.split(" ");
					if(parts.length >= 2){
						try{
							if(!parts[0].isEmpty())
								registerMolecule(parts[0], parts[1]);
						}catch(NumberFormatException e){}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
  }
  */
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
  	//blocks
  	
   	block_test = new BlockTest();
   	block_laser_relay = new BlockLaserRelay();
   	block_periodic_storage = new BlockPeriodicStorage();
   	block_molecular_storage = new BlockMolecularStorage();
   	block_energy_storage = new BlockEnergyStorage();
   	block_item_breaker = new BlockItemBreaker();
   	block_molecule_breaker = new BlockMoleculeBreaker();
   	block_molecule_assembler = new BlockMoleculeAssembler();
   	block_item_assembler = new BlockItemAssembler();
   	block_versatile_generator = new BlockVersatileGenerator();
   	block_versatile_extractor = new BlockVersatileExtractor();
   	block_positive_nuclear_transmuter = new BlockPositiveNuclearTransmuter();
   	block_negative_nuclear_transmuter = new BlockNegativeNuclearTransmuter();
   	
   	GameRegistry.registerBlock(block_test, "fundamentalchemistry:test");
   	GameRegistry.registerBlock(block_laser_relay, "fundamentalchemistry:laser_relay");
   	GameRegistry.registerBlock(block_periodic_storage, "fundamentalchemistry:periodic_storage");
  	GameRegistry.registerBlock(block_molecular_storage, "fundamentalchemistry:molecular_storage");
  	GameRegistry.registerBlock(block_energy_storage, "fundamentalchemistry:energy_storage");
  	GameRegistry.registerBlock(block_item_breaker, "fundamentalchemistry:item_breaker");
  	GameRegistry.registerBlock(block_molecule_breaker, "fundamentalchemistry:molecule_breaker");
  	GameRegistry.registerBlock(block_molecule_assembler, "fundamentalchemistry:molecule_assembler");
  	GameRegistry.registerBlock(block_item_assembler, "fundamentalchemistry:item_assembler");
  	GameRegistry.registerBlock(block_versatile_generator, "fundamentalchemistry:versatile_generator");
  	GameRegistry.registerBlock(block_versatile_extractor, "fundamentalchemistry:versatile_extractor");
  	GameRegistry.registerBlock(block_positive_nuclear_transmuter, "fundamentalchemistry:positive_nuclear_transmuter");
  	GameRegistry.registerBlock(block_negative_nuclear_transmuter, "fundamentalchemistry:negative_nuclear_transmuter");
   	
   	GameRegistry.registerTileEntity(TileLaserRelay.class, "fundamentalchemistry:laser_relay");
   	GameRegistry.registerTileEntity(TileChemicalStorage.class, "fundamentalchemistry:chemical_storage");
   	GameRegistry.registerTileEntity(TileMolecularStorage.class, "fundamentalchemistry:molecular_storage");
   	GameRegistry.registerTileEntity(TilePeriodicStorage.class, "fundamentalchemistry:periodic_storage");
   	GameRegistry.registerTileEntity(TileEnergyStorage.class, "fundamentalchemistry:energy_storage");
   	GameRegistry.registerTileEntity(TileItemBreaker.class, "fundamentalchemistry:item_breaker");
   	GameRegistry.registerTileEntity(TileMoleculeBreaker.class, "fundamentalchemistry:molecule_breaker");
   	GameRegistry.registerTileEntity(TileMoleculeAssembler.class, "fundamentalchemistry:molecule_assembler");
   	GameRegistry.registerTileEntity(TileItemAssembler.class, "fundamentalchemistry:item_assembler");
   	GameRegistry.registerTileEntity(TileVersatileGenerator.class, "fundamentalchemistry:versatile_generator");
   	GameRegistry.registerTileEntity(TileVersatileExtractor.class, "fundamentalchemistry:versatile_extractor");
   	GameRegistry.registerTileEntity(TilePositiveNuclearTransmuter.class, "fundamentalchemistry:positive_nuclear_transmuter");
   	GameRegistry.registerTileEntity(TileNegativeNuclearTransmuter.class, "fundamentalchemistry:negative_nuclear_transmuter");
   	
   	//items
   	
   	item_vibrant_catalyst_stone = new ItemVibrantCatalystStone();
   	item_atom_display = new ItemAtomDisplay();
   	item_molecule_display = new ItemMoleculeDisplay();
   	
   	GameRegistry.registerItem(item_vibrant_catalyst_stone, "fundamentalchemistry:vibrant_catalyst_stone");
   	GameRegistry.registerItem(item_atom_display, "fundamentalchemistry:atom_display");
   	GameRegistry.registerItem(item_molecule_display, "fundamentalchemistry:molecule_display");
   	
   	//CONFIG
   	//event.getModConfigurationDirectory().getAbsolutePath();
  }

  @EventHandler
  public void init(FMLInitializationEvent event)
  {
  	// recipes
  	
		GameRegistry.addRecipe(new ItemStack(item_vibrant_catalyst_stone, 8),
			" S ",
			"SDS",
			" O ",
			'D', Items.diamond, 'O', Blocks.obsidian, 'S', Blocks.stone
		);
		
		GameRegistry.addRecipe(new ItemStack(block_laser_relay),
				"   ",
				" V ",
				"SSS",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone
		);
		
		GameRegistry.addRecipe(new ItemStack(block_energy_storage),
			"SSS",
			"SCS",
			"SVS",
			'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.glowstone_dust
		);

		GameRegistry.addRecipe(new ItemStack(block_periodic_storage),
				"SSS",
				"SCS",
				"SVS",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.gold_ingot
		);
		
		GameRegistry.addRecipe(new ItemStack(block_molecular_storage),
				"SSS",
				"SCS",
				"SVS",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.iron_ingot
		);
		
		GameRegistry.addRecipe(new ItemStack(block_versatile_generator),
				"SCS",
				"CVC",
				"SCS",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.glowstone_dust
		);
		
		GameRegistry.addRecipe(new ItemStack(block_versatile_extractor),
				"SCS",
				"CVC",
				"SCS",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.iron_ingot
		);
		
		GameRegistry.addRecipe(new ItemStack(block_item_breaker),
				"SCS",
				"SVS",
				"CSC",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.iron_ingot
		);
		
		GameRegistry.addRecipe(new ItemStack(block_molecule_breaker),
				"SCS",
				"SVS",
				"CSC",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.gold_ingot
		);
		
		GameRegistry.addRecipe(new ItemStack(block_item_assembler),
				"SSS",
				"CVC",
				"SCS",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.iron_ingot
		);
		
		GameRegistry.addRecipe(new ItemStack(block_molecule_assembler),
				"SSS",
				"CVC",
				"SCS",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.gold_ingot
		);
		
		GameRegistry.addRecipe(new ItemStack(block_negative_nuclear_transmuter),
				"CCC",
				"RVR",
				"SSS",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.gold_ingot, 'R', Blocks.redstone_block
		);
		
		GameRegistry.addRecipe(new ItemStack(block_positive_nuclear_transmuter),
				"CRC",
				"RVR",
				"SRS",
				'V', item_vibrant_catalyst_stone, 'S', Blocks.stone, 'C', Items.gold_ingot, 'R', Blocks.redstone_block
		);


  	
  	FundamentalChemistryData.register();
  	
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
	  	.register(Item.getItemFromBlock(block_energy_storage), 0, new ModelResourceLocation("fundamentalchemistry:energy_storage", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_item_breaker), 0, new ModelResourceLocation("fundamentalchemistry:item_breaker", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_molecule_breaker), 0, new ModelResourceLocation("fundamentalchemistry:molecule_breaker", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_molecule_assembler), 0, new ModelResourceLocation("fundamentalchemistry:molecule_assembler", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_item_assembler), 0, new ModelResourceLocation("fundamentalchemistry:item_assembler", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_versatile_generator), 0, new ModelResourceLocation("fundamentalchemistry:versatile_generator", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_versatile_extractor), 0, new ModelResourceLocation("fundamentalchemistry:versatile_extractor", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_positive_nuclear_transmuter), 0, new ModelResourceLocation("fundamentalchemistry:positive_nuclear_transmuter", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(Item.getItemFromBlock(block_negative_nuclear_transmuter), 0, new ModelResourceLocation("fundamentalchemistry:negative_nuclear_transmuter", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(item_vibrant_catalyst_stone, 0, new ModelResourceLocation("fundamentalchemistry:vibrant_catalyst_stone", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(item_atom_display, 0, new ModelResourceLocation("fundamentalchemistry:atom_display", "inventory"));
	  	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	  	.register(item_molecule_display, 0, new ModelResourceLocation("fundamentalchemistry:molecule_display", "inventory"));
	  	
	  	//tile entity renderers
	  	 
      ClientRegistry.bindTileEntitySpecialRenderer(TileLaserRelay.class, new TileLaserRelayRenderer());
    }
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent evt)
  {
  	// register ingredients per outputs from smelting recipes
  	
  	Map<ItemStack, ItemStack> smelts = FurnaceRecipes.instance().getSmeltingList();
  	for(Map.Entry<ItemStack, ItemStack> entry : smelts.entrySet()){
  		ItemStack out = entry.getValue();
  		ItemStack in = entry.getKey();
  		
			int amount = (int)Math.round(in.stackSize/(float)out.stackSize);
			if(amount > 0){
				ItemStack[] items = new ItemStack[1];
				items[0] = new ItemStack(in.getItem(), amount);
				
				ingredients.put(out.getItem(), items);
			}
  	}
  	
  	// register ingredients per outputs from crafting recipes
 
  	for(IRecipe recipe : CraftingManager.getInstance().getRecipeList()){
  		ItemStack out = recipe.getRecipeOutput();
  		ItemStack[] items = null;

  		boolean check = out != null && out.getItem() == Item.getItemFromBlock(Blocks.redstone_block);
  		
  		if(recipe instanceof ShapedRecipes)
  			items = ((ShapedRecipes)recipe).recipeItems;
  		else if(recipe instanceof ShapedOreRecipe){
  			Object[] objs = ((ShapedOreRecipe)recipe).getInput();
  			items = new ItemStack[objs.length];
    		for(int i = 0; i < objs.length; i++){
    			if(objs[i] instanceof ItemStack)
    				items[i] = (ItemStack)objs[i];
    		}
  		}
  		else if(recipe instanceof ShapelessRecipes){
  			List<ItemStack> litems = ((ShapelessRecipes)recipe).recipeItems;
    		items = new ItemStack[litems.size()];
    		for(int i = 0; i < litems.size(); i++)
    			items[i] = litems.get(i);
  	  }
  		else if(recipe instanceof ShapelessOreRecipe){
  			List<Object> litems = ((ShapelessOreRecipe)recipe).getInput();
    		items = new ItemStack[litems.size()];
    		for(int i = 0; i < litems.size(); i++){
    			if(litems.get(i) instanceof ItemStack)
    				items[i] = (ItemStack)litems.get(i);
    		}
  		}
  		
  		if(check){
  			
  		}
  		
  		if(items != null && out != null && out.getItem() != null){
  			//register ingredients divided by output
  			ItemStack[] _items = new ItemStack[items.length];
  			for(int i = 0; i < items.length; i++){
  				if(items[i] != null){
  					int amount = (int)Math.round(items[i].stackSize/(float)out.stackSize);
  					if(check)
  						System.out.println(i+" => "+amount);
  					if(amount > 0)
  						_items[i] = new ItemStack(items[i].getItem(), amount);
  				}
  			}
  			
  			ingredients.put(out.getItem(), _items);
  		}
  	}
  }
  
  @EventHandler
  public void serverLoad(FMLServerStartingEvent event){
  	event.registerServerCommand(new Command());
  }
}
