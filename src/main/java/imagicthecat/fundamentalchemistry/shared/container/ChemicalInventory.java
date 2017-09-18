package imagicthecat.fundamentalchemistry.shared.container;

import java.util.Map;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class ChemicalInventory implements IInventory {
	protected ItemStack[] slots;
	
	// build chemical inventory (for display only, limited to 54 elements) from chemical storage
	public ChemicalInventory(ChemicalStorage storage)
	{
		slots = new ItemStack[this.getSizeInventory()];
		
		if(storage != null){
			int index = 0;
			
			for(Map.Entry<Integer, Integer> entry : storage.atoms.entrySet()){
				if(index < this.getSizeInventory()){ //add atom stack
					ItemStack stack = new ItemStack(FundamentalChemistry.item_atom_display, 1);
					NBTTagCompound tag = stack.getTagCompound();
					if(tag == null){
						tag = new NBTTagCompound();
						stack.setTagCompound(tag);
					}
					
					tag.setInteger("atomic_number", entry.getKey());
					tag.setInteger("quantity", entry.getValue());
					
					slots[index] = stack;
					index++;
				}
			}
			
			for(Map.Entry<Molecule, Integer> entry : storage.molecules.entrySet()){
				if(index < this.getSizeInventory()){ //add atom stack
					ItemStack stack = new ItemStack(FundamentalChemistry.item_molecule_display, 1);
					NBTTagCompound tag = stack.getTagCompound();
					if(tag == null){
						tag = new NBTTagCompound();
						stack.setTagCompound(tag);
					}
					
					String name = FundamentalChemistry.molecules.invget(entry.getKey());
					if(name == null)
						name = "unknown";
					
					tag.setString("molecule_name", name);
					tag.setInteger("quantity", entry.getValue());
					
					slots[index] = stack;
					index++;
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "container.chemical_storage";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return 54;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if(index >= 0 && index < slots.length)
			return slots[index];
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(index >= 0 && index < slots.length)
			slots[index] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
	}
}
