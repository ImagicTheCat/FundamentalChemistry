package imagicthecat.fundamentalchemistry.shared.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.Molecule;

public class ItemMoleculeDisplay extends Item {
	public ItemMoleculeDisplay()
	{
    this.setUnlocalizedName("molecule_display");
    this.setCreativeTab(FundamentalChemistry.tab);
	}
	
	public String getMoleculeName(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();
		String name = "unknown";
		int amount = 0;
		
		if(tag != null){
			name = tag.getString("molecule_name");
			amount = tag.getInteger("quantity");
		}
		
		return I18n.format("molecule."+name+".name")+" ("+amount+")";
	}
	
	@Override
  public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
  {
		NBTTagCompound tag = stack.getTagCompound();
		String name = "unknown";
		int amount = 0;
		
		if(tag != null){
			name = tag.getString("molecule_name");
			amount = tag.getInteger("quantity");
		}
		
		//notation
		Molecule m = FundamentalChemistry.molecules.get(name);
		if(m != null)
			tooltip.add(m.toNotation());
		else
			tooltip.add("???");
		
		// atoms and protons
		if(m != null){
			tooltip.add(m.countAtoms()+" atoms");
			tooltip.add(m.countProtons()+" protons");
		}
  }
	
	@Override
  public String getItemStackDisplayName(ItemStack stack)
  {
    return super.getItemStackDisplayName(stack)+" "+getMoleculeName(stack);
  }
}
