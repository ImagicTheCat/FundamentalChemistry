package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileChemicalStorage extends TileEntity  {
	public ChemicalStorage storage;
	protected ChemicalStorage buffer; //used to store things when fetching required elements to process
	
	public TileChemicalStorage()
	{
		storage = new ChemicalStorage();
		buffer = new ChemicalStorage();
	}
	
	// return the relay attached to the storage, or null (currently above the storage)
	public TileLaserRelay getAttachedRelay()
	{
		TileEntity te = this.worldObj.getTileEntity(this.pos.add(0, 1, 0));
		if(te != null && te instanceof TileLaserRelay)
			return (TileLaserRelay)te;
		
		return null;
	}
	
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) 
  {
  	super.writeToNBT(tag);
  	
  	NBTTagCompound tstorage = new NBTTagCompound();
  	storage.write(tstorage);
  	
  	NBTTagCompound tbuffer = new NBTTagCompound();
  	buffer.write(tbuffer);
  	
  	tag.setTag("storage", tstorage);
  	tag.setTag("buffer", tbuffer);
  	
  	return tag;
  }
  
  @Override
  public void readFromNBT(NBTTagCompound tag)
  {
    super.readFromNBT(tag);
    
    NBTTagCompound tstorage = tag.getCompoundTag("storage");
    storage.read(tstorage);
    NBTTagCompound tbuffer = tag.getCompoundTag("buffer");
    buffer.read(tbuffer);
  }
  
  @Override
  public NBTTagCompound getUpdateTag()
  {
  	return writeToNBT(new NBTTagCompound());
  }
  
  @Override
  public void handleUpdateTag(NBTTagCompound tag)
  {
  	readFromNBT(tag);
  }
  
  @Override
  public SPacketUpdateTileEntity getUpdatePacket() 
  {
    return new SPacketUpdateTileEntity(this.pos, 1, writeToNBT(new NBTTagCompound()));
  }
  
  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) 
  {
    readFromNBT(pkt.getNbtCompound());
  }
}
