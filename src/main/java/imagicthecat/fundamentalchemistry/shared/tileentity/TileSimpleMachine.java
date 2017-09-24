package imagicthecat.fundamentalchemistry.shared.tileentity;

import java.util.Map;
import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;

public class TileSimpleMachine extends TileChemicalStorage implements IInventory{
	protected ItemStack input;
	protected ItemStack[] sbuffer;
	public int buffer_energy;
	
	public TileSimpleMachine()
	{
		sbuffer = new ItemStack[12]; //buffer display
		buffer_energy = 0;
	}
	
	//do machine work
	public void tick()
	{
	}
	
	// update the sbuffer inventory based on the chemical storage
	public void update()
	{
		sbuffer = new ItemStack[12];
		buffer.toItemStacks(sbuffer);
	}
	
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) 
  {
  	super.writeToNBT(tag);

  	// write input stack
  	ItemStack items = this.getStackInSlot(0);
  	if(items != null){
  		NBTTagCompound tinput = new NBTTagCompound();
  		items.writeToNBT(tinput);
  		tag.setTag("items", tinput);
  	}
  	
  	return tag;
  }
  
  @Override
  public void readFromNBT(NBTTagCompound tag)
  {
    super.readFromNBT(tag);
    
    // read input stack
    if(tag.hasKey("items"))
    	this.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(tag.getCompoundTag("items")));
  }

	@Override
	public String getName() {
		return "container.simple_machine";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory(){
		return 13;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if(index == 0)
			return input;
		else if(index < this.getSizeInventory())
			return sbuffer[index-1];
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack items = getStackInSlot(index);
		if(items != null && index == 0){
			if(items.stackSize <= count){
				setInventorySlotContents(index, null);
				return items;
			}
			else{
				ItemStack r = items.splitStack(count);
				if(items.stackSize <= 0)
					setInventorySlotContents(index, null);
				else
					setInventorySlotContents(index, items);
				
				return r;
			}
		}
		
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if(index == 0){
			ItemStack stack = getStackInSlot(index);
			setInventorySlotContents(index, null);
			return stack;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(index >= 0 && index < this.getSizeInventory()){
			if(stack != null){
				if(stack.stackSize == 0) // remove if empty stack
					stack = null;
				else if(stack.stackSize > getInventoryStackLimit()) // clamp
					stack.stackSize = getInventoryStackLimit();
			}
			
			if(index == 0)	
				input = stack;
			else
				sbuffer[index-1] = stack;
				
			this.markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
    return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0;
	}

	@Override
	public int getField(int id) {
		if(id == 0) return buffer.energy;
			
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if(id == 0) buffer_energy = value;
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {
    for (int i = 0; i < this.getSizeInventory(); i++)
      this.setInventorySlotContents(i, null);
	}
}

