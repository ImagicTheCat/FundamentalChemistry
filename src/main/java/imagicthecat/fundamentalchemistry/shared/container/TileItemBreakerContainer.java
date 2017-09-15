package imagicthecat.fundamentalchemistry.shared.container;

import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemBreaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class TileItemBreakerContainer extends Container {
	private TileItemBreaker ent;
	
	public TileItemBreakerContainer(IInventory player_inv, TileItemBreaker ent)
	{
		this.ent = ent;

		// input
    this.addSlotToContainer(new Slot(ent, 0, 80, 35));

    // player inventory, 9-35
    for (int y = 0; y < 3; ++y) {
      for (int x = 0; x < 9; ++x)
        this.addSlotToContainer(new Slot(player_inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
    }

    // player inventory, 0-8
    for (int x = 0; x < 9; ++x)
      this.addSlotToContainer(new Slot(player_inv, x, 8 + x * 18, 142));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return ent.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = null;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			// custom behaviour
			if(fromSlot == 0){
				// ent -> player
				if(!this.mergeItemStack(current, 1, 37, true))
					return null;
			}
			else {
				// player -> ent
				if(!this.mergeItemStack(current, 0, 1, false))
					return null;
			}
		// end custom behaviour

			if (current.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (current.stackSize == previous.stackSize)
				return null;
			slot.onPickupFromSlot(playerIn, current);
		}
		
		return previous;
	}
}
