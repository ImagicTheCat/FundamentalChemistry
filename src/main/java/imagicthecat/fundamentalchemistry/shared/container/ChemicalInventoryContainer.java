
package imagicthecat.fundamentalchemistry.shared.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ChemicalInventoryContainer extends Container{
	IInventory inventory;
	
	public ChemicalInventoryContainer(IInventory inv)
	{
		inventory = inv;
    for (int y = 0; y < 6; ++y) {
      for (int x = 0; x < 9; ++x)
        this.addSlotToContainer(new Slot(inv, x + y * 9, 8 + x * 18, 52 + y * 18));
    }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return inventory.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		return null;
	}
}
