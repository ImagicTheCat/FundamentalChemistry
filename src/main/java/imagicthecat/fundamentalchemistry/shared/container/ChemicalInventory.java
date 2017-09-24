package imagicthecat.fundamentalchemistry.shared.container;


import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ChemicalInventory implements IInventory {
	protected ItemStack[] slots;
	protected ChemicalStorage storage;
	
	public int max_energy;
	public int max_atoms;
	public int max_molecules;
	public int energy;
	
	// build chemical inventory (for display only, limited to 54 elements) from chemical storage
	public ChemicalInventory(ChemicalStorage storage)
	{
		this.storage = storage;
		
		this.max_energy = -1;
		this.max_atoms = -1;
		this.max_molecules = -1;
		this.energy = 0;
		
		update();
	}
	
	// update the inventory based on the registered chemical storage
	public void update()
	{
		slots = new ItemStack[this.getSizeInventory()];
		
		if(storage != null){
			this.max_energy = storage.max_energy;
			this.max_molecules = storage.max_molecules;
			this.max_atoms = storage.max_atoms;
			this.energy = storage.energy;
			
			storage.toItemStacks(slots);
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
	public ITextComponent getDisplayName() {
		return new TextComponentString(this.getName());
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
		switch(id){
			case 0: return max_energy;
			case 1: return max_atoms;
			case 2: return max_molecules;
			case 3: return energy;
		}
		
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch(id){
			case 0: max_energy = value; break;
			case 1: max_atoms = value; break;
			case 2: max_molecules = value; break;
			case 3: energy = value; break;
		}
	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
	}
}
