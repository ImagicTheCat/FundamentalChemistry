package imagicthecat.fundamentalchemistry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class FundamentalChemistryData {
	public static void register()
	{
  	// catalysts
  	
  	C(Item.getItemFromBlock(Blocks.COBBLESTONE), 1);
  	C(Items.REDSTONE, 2);
  	C(Items.IRON_INGOT, 3);
  	C(Items.GOLD_INGOT, 4);
  	C(Items.DIAMOND, 5);
  	
  	// atoms
  	
  	A("H",1);
  	A("He",2);
  	A("Li",3);
  	A("Be",4);
  	A("B",5);
  	A("C",6);
  	A("N",7);
  	A("O",8);
  	A("F",9);
  	A("Ne",10);
  	A("Na",11);
  	A("Mg",12);
  	A("Al",13);
  	A("Si",14);
  	A("P",15);
  	A("S",16);
  	A("Cl",17);
  	A("Ar",18);
  	A("K",19);
  	A("Ca",20);
  	A("Sc",21);
  	A("Ti",22);
  	A("V",23);
  	A("Cr",24);
  	A("Mn",25);
  	A("Fe",26);
  	A("Co",27);
  	A("Ni",28);
  	A("Cu",29);
  	A("Zn",30);
  	A("Ga",31);
  	A("Ge",32);
  	A("As",33);
  	A("Se",34);
  	A("Br",35);
  	A("Kr",36);
  	A("Rb",37);
  	A("Sr",38);
  	A("Y",39);
  	A("Zr",40);
  	A("Nb",41);
  	A("Mo",42);
  	A("Tc",43);
  	A("Ru",44);
  	A("Rh",45);
  	A("Pd",46);
  	A("Ag",47);
  	A("Cd",48);
  	A("In",49);
  	A("Sn",50);
  	A("Sb",51);
  	A("Te",52);
  	A("I",53);
  	A("Xe",54);
  	A("Cs",55);
  	A("Ba",56);
  	A("La",57);
  	A("Ce",58);
  	A("Pr",59);
  	A("Nd",60);
  	A("Pm",61);
  	A("Sm",62);
  	A("Eu",63);
  	A("Gd",64);
  	A("Tb",65);
  	A("Dy",66);
  	A("Ho",67);
  	A("Er",68);
  	A("Tm",69);
  	A("Yb",70);
  	A("Lu",71);
  	A("Hf",72);
  	A("Ta",73);
  	A("W",74);
  	A("Re",75);
  	A("Os",76);
  	A("Ir",77);
  	A("Pt",78);
  	A("Au",79);
  	A("Hg",80);
  	A("Tl",81);
  	A("Pb",82);
  	A("Bi",83);
  	A("Po",84);
  	A("At",85);
  	A("Rn",86);
  	A("Fr",87);
  	A("Ra",88);
  	A("Ac",89);
  	A("Th",90);
  	A("Pa",91);
  	A("U",92);
  	A("Np",93);
  	A("Pu",94);
  	A("Am",95);
  	A("Cm",96);
  	A("Bk",97);
  	A("Cf",98);
  	A("Es",99);
  	A("Fm",100);
  	A("Md",101);
  	A("No",102);
  	A("Lr",103);
  	A("Rf",104);
  	A("Db",105);
  	A("Sg",106);
  	A("Bh",107);
  	A("Hs",108);
  	A("Mt",109);
  	A("Ds",110);
  	A("Rg",111);
  	A("Cn",112);
  	A("Nh",113);
  	A("Fl",114);
  	A("Mc",115);
  	A("Lv",116);
  	A("Ts",117);
  	A("Og",118);
                
  	// molecules
  	
  	M("water", "H2O");
  	M("dioxygen", "O2");
  	M("dinitrogen", "N2");
  	M("carbon_dioxide", "CO2");
  	M("cellulose", "C6H10O5");
  	M("phenylalanine", "C9H11NO2");
  	M("lignin", "C31H34O11");
  	M("chlorophyll_a", "C55H72O5N4Mg");
  	M("chlorophyll b", "C55H70O6N4Mg");
  	M("silica", "SiO2");
  	M("aluminium_oxide", "Al2O3");
  	M("potassium_oxide", "K2O");
  	M("sodium_oxide", "Na2O");
  	M("calcium_oxide", "CaO");
  	M("glucose", "C6H12O6");
  	M("glycerine", "C3H8O3");
  	M("glycine", "C2H5NO2");
  	M("proline", "C5H9NO2");
  	M("alanine", "C3H7NO2");
  	M("serine", "C3H7NO3");
  	M("kaolinite", "Al2Si2O9H4");
  	M("melanin", "C18H10N2O4");
  	M("hematite", "Fe2O3");
  	M("magnetite", "Fe3O4");
  	M("siderite", "FeCO3");
  	M("pyrite", "FeS2");
  	M("gold_cluster", "Au10");
  	M("diamond_lattice", "C8");
  	M("fluorescein", "C20H12O5");
  	M("acridine_yellow", "C15H15N3");
  	M("beryl", "Be3Al2Si6O18");
  	M("vanadium_pentoxide", "V2O5");
  	M("chromite", "FeCr2O4");
  	M("saltpetre", "KNO3");
  	M("cyclooctasulfur", "S8");
  	M("polyvinyl_acetate", "C4H6O2");
  	M("sodium_chloride", "NaCl");
  	
  	// item compositions
  	
  	//organic
  	I(Blocks.LOG, "4 cellulose", "1 lignin"); //wood
  	I(Blocks.LOG2, "4 cellulose", "1 lignin"); //wood
  	I(Blocks.PLANKS, "1 cellulose"); //planks
  	I(Blocks.LEAVES, "1 chlorophyll_a"); //leaf
  	I(Blocks.SAPLING, "1 chlorophyll_a", "1 cellulose"); //sapling
  	I(Blocks.RED_MUSHROOM, "1 cellulose", "1 glycerine"); //mush
  	I(Blocks.BROWN_MUSHROOM, "1 cellulose", "1 glycerine"); //mush
  	I(Blocks.TALLGRASS, "1 chlorophyll_a", "1 cellulose"); //tallgrass
  	I(Blocks.WATERLILY, "1 chlorophyll_a", "1 cellulose"); //lilypad
  	I(Blocks.RED_FLOWER, "1 chlorophyll_b");
  	I(Blocks.YELLOW_FLOWER, "1 chlorophyll_b");
  	I(Blocks.DOUBLE_PLANT, "1 chlorophyll_b", "1 chlorophyll_a");
  	I(Blocks.GRASS, "1 silica", "1 water", "1 cellulose", "1 chlorophyll_a");
  	I(Blocks.DIRT, "1 silica", "1 water", "1 cellulose");
  	I(Items.REEDS, "1 glucose", "1 cellulose", "1 chlorophyll_a", "1 water");
  	I(Blocks.CACTUS, "2 water", "1 cellulose", "1 chlorophyll_a");
  	I(Items.SUGAR, "1 glucose");
  	I(Items.CHICKEN, "1 glycerine", "1 proline", "1 glycine", "1 alanine", "2 water");
  	I(Items.BEEF, "1 glycerine", "1 proline", "1 glycine", "1 alanine", "2 water");
  	I(Items.RABBIT, "1 glycerine", "1 proline", "1 glycine", "1 alanine", "2 water");
  	I(Items.FISH, "1 glycerine", "1 proline", "1 glycine", "1 alanine", "2 water");
  	I(Items.PORKCHOP, "1 glycerine", "1 proline", "1 glycine", "1 alanine", "2 water");
  	I(Items.MUTTON, "1 glycerine", "1 proline", "1 glycine", "1 alanine", "2 water");
  	I(Items.ROTTEN_FLESH, "1 proline", "1 glycine", "1 alanine", "1 water");
  	I(Items.BONE, "1 proline", "1 glycine", "1 alanine");
  	I(Items.SKULL, "2 proline", "2 glycine", "2 alanine");
  	I(Items.DYE, "1 melanin", "1 water");
  	I(Items.LEATHER, "1 proline", "1 glycine", "1 alanine");
  	I(Items.RABBIT_HIDE, "1 proline", "1 glycine", "1 alanine");
  	I(Blocks.WOOL, "1 proline", "1 glycine", "1 alanine", "1 serine");
  	I(Items.STRING, "1 glycine", "1 alanine", "1 serine");
  	I(Items.WHEAT, "2 cellulose", "1 glycine");
  	I(Items.APPLE, "2 water", "2 glucose");
  	I(Items.CARROT, "1 water", "1 cellulose");
  	I(Items.MELON, "2 water", "2 glucose");
  	I(Blocks.PUMPKIN, "1 cellulose", "2 water", "1 glucose");
  	I(Items.EGG, "1 glycerine", "1 glycine", "1 alanine", "1 water");
  	I(Items.SPIDER_EYE, "1 glycerine", "1 glycine", "1 alanine", "1 proline");
  	I(Items.FEATHER, "1 glycine", "1 alanine", "1 serine");
  	I(Items.COAL, "4 cellulose", "1 lignin");
  	
  	//ore
  	I(Blocks.COBBLESTONE, "1 calcium_oxide"); //cobble
  	I(Blocks.STONE, "1 silica", "1 aluminium_oxide", "1 potassium_oxide", "1 sodium_oxide", "1 calcium_oxide"); //stone ore
  	I(Blocks.GRAVEL, "1 silica", "1 aluminium_oxide", "1 potassium_oxide", "1 sodium_oxide", "1 calcium_oxide");
  	I(Items.QUARTZ, "10 silica");
  	I(Blocks.SAND, "2 silica");
  	I(Blocks.CLAY, "1 kaolinite");
  	I(Items.IRON_INGOT, "2 hematite", "2 magnetite", "1 siderite", "1 pyrite");
  	I(Items.GOLD_INGOT, "2 gold_cluster");
  	I(Items.DIAMOND, "50 diamond_lattice");
  	I(Blocks.OBSIDIAN, "100 silica", "20 aluminium_oxide", "20 potassium_oxide", "20 sodium_oxide", "20 calcium_oxide");
  	I(Items.EMERALD, "1 beryl", "1 vanadium_pentoxide", "1 chromite");
  	I(Items.REDSTONE, "1 fluorescein");
  	I(Items.GLOWSTONE_DUST, "1 fluorescein", "1 acridine_yellow");
  	
  	//misc
  	I(Items.SNOWBALL, "1 water");
  	I(Items.PAPER, "1 cellulose");
  	I(Items.WATER_BUCKET, "1 water");
  	I(Items.LAVA_BUCKET, "1 silica", "1 aluminium_oxide", "1 potassium_oxide", "1 sodium_oxide", "1 calcium_oxide");
  	I(Items.MILK_BUCKET, "1 glycerine", "1 proline", "1 glycine", "1 alanine", "5 water");
  	I(Items.GUNPOWDER, "1 cellulose", "1 saltpetre", "1 cyclooctasulfur");
  	
  	//surnatural (imaginative composition)
  	I(Items.ENDER_PEARL, "100 silica", "10 diamond_lattice", "1 chlorophyll_a");
  	I(Blocks.NETHERRACK, "1 silica", "1 saltpetre");
  	I(Blocks.SOUL_SAND, "1 silica", "2 cyclooctasulfur");
  	I(Items.SLIME_BALL, "1 polyvinyl_acetate");
  	I(Items.GHAST_TEAR, "1 sodium_chloride", "1 water");
  	I(Items.BLAZE_ROD, "10 saltpetre", "50 cyclooctasulfur");
  	I(Items.NETHER_WART, "1 fluorescein", "1 lignin");
  	I(Items.PRISMARINE_CRYSTALS, "1 chlorophyll_a", "1 cellulose", "1 water");
  	I(Items.PRISMARINE_SHARD, "1 chlorophyll_a", "1 cellulose", "1 water");
	}
	
	//register catalyst
	public static void C(Item item, int power)
	{
		FundamentalChemistry.registerNuclearTransmuterCatalyst(item, power);
	}
	
	//register atom
	public static void A(String name, int an)
	{
		FundamentalChemistry.registerElement(name, an);
	}
	
	//register molecule
	public static void M(String name, String notation)
	{
		FundamentalChemistry.registerMolecule(name, notation);
	}
	
	//register item composition
	public static void I(Item item, String... strings)
	{
		FundamentalChemistry.registerItemComposition(item, strings);
	}
	
	public static void I(Block block, String... strings)
	{
		Item item = Item.getItemFromBlock(block);
		if(item != null)
			FundamentalChemistry.registerItemComposition(item, strings);
	}
}


