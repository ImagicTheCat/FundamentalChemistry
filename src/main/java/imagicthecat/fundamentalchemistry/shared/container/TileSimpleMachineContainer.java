package imagicthecat.fundamentalchemistry.shared.container;


import imagicthecat.fundamentalchemistry.shared.tileentity.TileSimpleMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileSimpleMachineContainer extends Container {
	private TileSimpleMachine ent;
	public int[] fields;
	
	public TileSimpleMachineContainer(IInventory player_inv, TileSimpleMachine ent)
	{
		this.ent = ent;
		
    fields = new int[ent.getFieldCount()];
    for(int i = 0; i < fields.length; i++)
    	fields[i] = ent.getField(i);

		// input
    this.addSlotToContainer(new Slot(ent, 0, 26, 35));
    
    // sbuffer display
    for (int y = 0; y < 2; ++y) {
      for (int x = 0; x < 6; ++x)
        this.addSlotToContainer(new Slot(ent, x + y * 9 + 1, 62 + x * 18, 35 + y * 18));
    }

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
  public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn)
  {
		if(slotId >= 1 && slotId < 13)
			return null;
		else
			return super.slotClick(slotId, clickedButton, mode, playerIn);
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
				if(!this.mergeItemStack(current, 13, 49, true))
					return null;
			}
			else if(fromSlot >= 13) {
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
	
	@Override
  public void onCraftGuiOpened(ICrafting listener)
  {
		ent.update();
    super.onCraftGuiOpened(listener);
    listener.sendAllWindowProperties(this, ent);
  }
	
	@Override
	public void detectAndSendChanges()
	{
		ent.update();
		for(int i = 0; i < fields.length; i++){
			int new_field = ent.getField(i);
			if(new_field != fields[i]){
				fields[i] = new_field;
				
				//send change
				for(int j = 0; j < this.crafters.size(); ++j){
					ICrafting icrafting = (ICrafting)this.crafters.get(j);
					icrafting.sendProgressBarUpdate(this, i, new_field);
				}
			}
		}
		
		super.detectAndSendChanges();
	}
	
  @SideOnly(Side.CLIENT)
  public void updateProgressBar(int id, int data)
  {
     this.ent.setField(id, data);
  }
}
