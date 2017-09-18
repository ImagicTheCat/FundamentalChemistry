package imagicthecat.fundamentalchemistry.shared.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import imagicthecat.fundamentalchemistry.FundamentalChemistry;

public class ItemAtomDisplay extends Item {
	public ItemAtomDisplay()
	{
    this.setUnlocalizedName("atom_display");
    this.setCreativeTab(FundamentalChemistry.tab);
	}
	
	public String getAtomName(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();
		int an = 0;
		int amount = 0;
		
		if(tag != null){
			an = tag.getInteger("atomic_number");
			amount = tag.getInteger("quantity");
		}
		
		String name = FundamentalChemistry.elements.invget(an);
		if(name != null)
			return name+" ("+amount+")";
		return an+" ("+amount+")";
	}
	
	@Override
  public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
  {
		NBTTagCompound tag = stack.getTagCompound();
		int an = 0;
		int amount = 0;
		
		if(tag != null){
			an = tag.getInteger("atomic_number");
			amount = tag.getInteger("quantity");
		}
		
		String name = FundamentalChemistry.elements.invget(an);
		if(name != null)
			tooltip.add(I18n.format("atom."+name+".name"));
		else
			tooltip.add(I18n.format("atom.unknown.name"));
		
		tooltip.add(an+"");
  }
	
	@Override
  public String getItemStackDisplayName(ItemStack stack)
  {
    return super.getItemStackDisplayName(stack)+" "+getAtomName(stack);
  }
}
