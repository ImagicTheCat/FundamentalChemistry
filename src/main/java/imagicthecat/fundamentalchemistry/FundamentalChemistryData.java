package imagicthecat.fundamentalchemistry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class FundamentalChemistryData {
	public static void register()
	{
  	// catalysts
  	
  	C(Item.getItemFromBlock(Blocks.cobblestone), 1);
  	C(Items.redstone, 2);
  	C(Items.iron_ingot, 3);
  	C(Items.gold_ingot, 4);
  	C(Items.diamond, 5);
  	
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
  	
  	// item compositions
  	
  	//organic
  	I(Blocks.log, "2 cellulose", "1 lignin"); //wood
  	I(Blocks.log2, "2 cellulose", "1 lignin"); //wood
  	I(Blocks.planks, "1 cellulose"); //planks
  	I(Blocks.leaves, "1 chlorophyll_a"); //leaf
  	I(Blocks.sapling, "1 chlorophyll_a", "1 lignin"); //sapling
  	I(Blocks.red_flower, "1 chlorophyll_b");
  	I(Blocks.yellow_flower, "1 chlorophyll_b");
  	I(Blocks.double_plant, "1 chlorophyll_b", "1 chlorophyll_a");
  	I(Items.reeds, "1 glucose", "1 cellulose", "1 chlorophyll_a");
  	I(Items.sugar, "1 glucose");
  	
  	//ore
  	I(Blocks.cobblestone, "1 calcium_oxide"); //cobble
  	I(Blocks.stone, "1 silica", "1 aluminium_oxide", "1 potassium_oxide", "1 sodium_oxide", "1 calcium_oxide"); //stone ore
  	I(Items.quartz, "10 silica");
  	I(Blocks.sand, "2 silica");
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


