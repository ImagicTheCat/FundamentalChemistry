
package imagicthecat.fundamentalchemistry.shared.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChemicalInventoryContainer extends Container{
	protected ChemicalInventory inventory;
	public int[] fields;
	
	public ChemicalInventoryContainer(ChemicalInventory inv)
	{
		inventory = inv;
    for (int y = 0; y < 6; ++y) {
      for (int x = 0; x < 9; ++x)
        this.addSlotToContainer(new Slot(inv, x + y * 9, 8 + x * 18, 52 + y * 18));
    }
    
    fields = new int[inv.getFieldCount()];
    for(int i = 0; i < fields.length; i++)
    	fields[i] = inventory.getField(i);
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
	
	@Override
  public void addListener(IContainerListener listener)
  {
    super.addListener(listener);
    listener.sendAllWindowProperties(this, inventory);
  }
	
	@Override
	public void detectAndSendChanges()
	{
		inventory.update();
		for(int i = 0; i < fields.length; i++){
			int new_field = inventory.getField(i);
			if(new_field != fields[i]){
				fields[i] = new_field;
				
				//send change
				for(int j = 0; j < this.listeners.size(); ++j){
					IContainerListener listener = (IContainerListener)this.listeners.get(j);
					listener.sendProgressBarUpdate(this, i, new_field);
				}
			}
		}
		
		super.detectAndSendChanges();
	}
	
  @SideOnly(Side.CLIENT)
  public void updateProgressBar(int id, int data)
  {
     this.inventory.setField(id, data);
  }
}
