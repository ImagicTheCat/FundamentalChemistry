package imagicthecat.fundamentalchemistry.shared;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ForgeCreativeTab extends CreativeTabs {
  public ForgeCreativeTab() 
  {
		super("fundamentalchemistry");
	}

	@Override public Item getTabIconItem() 
  {
    return Item.getItemFromBlock(FundamentalChemistry.block_test);
  }
}
