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
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

public class TileItemBreaker extends TileChemicalStorage implements IInventory{
	private ItemStack input;
	
	public TileItemBreaker()
	{
		
	}
	
	//do machine work
	public void tick()
	{
		//break one item of the input stack
		ItemStack stack = decrStackSize(0,1);
		if(stack != null && stack.stackSize == 1){
			//find composition
			Map<Molecule, Integer> molecules = FundamentalChemistry.item_compositions.get(stack.getItem());
			if(molecules != null)
				this.storage.add(new ChemicalStorage(null, molecules));
		}
	}
	
  @Override
  public void writeToNBT(NBTTagCompound tag) 
  {
  	super.writeToNBT(tag);

  	// write input stack
  	ItemStack items = this.getStackInSlot(0);
  	if(items != null){
  		NBTTagCompound tinput = new NBTTagCompound();
  		items.writeToNBT(tinput);
  		tag.setTag("items", tinput);
  	}
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
  public Packet getDescriptionPacket() 
  {
    NBTTagCompound tag = new NBTTagCompound();
    writeToNBT(tag);
    return new S35PacketUpdateTileEntity(this.pos, 1, tag);
  }
  
  @Override
  public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) 
  {
    readFromNBT(pkt.getNbtCompound());
  }

	@Override
	public String getName() {
		return "container.item_breaker";
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
	public int getSizeInventory(){
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if(index == 0)
			return input;
		else
			return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack items = getStackInSlot(index);
		if(items != null){
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
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(index == 0){
			if(stack != null){
				if(stack.stackSize == 0) // remove if empty stack
					stack = null;
				else if(stack.stackSize > getInventoryStackLimit()) // clamp
					stack.stackSize = getInventoryStackLimit();
			}
			
			input = stack;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
    return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
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
    for (int i = 0; i < this.getSizeInventory(); i++)
      this.setInventorySlotContents(i, null);
	}
}
