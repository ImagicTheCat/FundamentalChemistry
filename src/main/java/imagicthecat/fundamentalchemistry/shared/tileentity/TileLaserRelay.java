package imagicthecat.fundamentalchemistry.shared.tileentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.util.Constants;

public class TileLaserRelay extends TileEntity {
	public Set<BlockPos> inputs;
	
	public TileLaserRelay()
	{
		inputs = new HashSet<BlockPos>();
	}
	
	//try to toggle connect the laser relay to another one, return true on success
	public boolean toggleConnect(BlockPos pos)
	{
		TileEntity te = this.getWorld().getTileEntity(pos);
		if(te != null && te instanceof TileLaserRelay){
			TileLaserRelay ent = (TileLaserRelay)te;
			return ent.toggleConnectFrom(this.pos);
		}
		
		return false;
	}
	
	//try to toggle connect from another laser relay to this one, return true on success
	public boolean toggleConnectFrom(BlockPos pos)
	{
		TileEntity te = this.getWorld().getTileEntity(pos);
		if(te != null && te instanceof TileLaserRelay){
			if(!pos.equals(this.pos)){ //prevents self linking
				if(!inputs.contains(pos))
					inputs.add(pos);
				else
					inputs.remove(pos);
				
				this.worldObj.markBlockForUpdate(this.pos);
				this.markDirty();
				
				return true;
			}
		}
		
		return false;
	}
	
	//check inputs, remove if invalid
	public void check()
	{
		System.out.println("check!");
		
		List<BlockPos> rmlist = new ArrayList<BlockPos>();
		for(BlockPos pos : inputs){
			TileEntity te = this.getWorld().getTileEntity(pos);
			if(te == null || !(te instanceof TileLaserRelay))
				rmlist.add(pos);
		}
		
		for(BlockPos pos : rmlist)
			inputs.remove(pos);
		
		if(rmlist.size() > 0){
			this.worldObj.markBlockForUpdate(this.pos);
			this.markDirty();
		}
	}
	
  @Override
  public void writeToNBT(NBTTagCompound tag) 
  {
  	super.writeToNBT(tag);
  	NBTTagList list = new NBTTagList();
  	
  	//save inputs pos
  	
  	for(BlockPos pos : inputs){
  		int[] coords = new int[3];
  		coords[0] = pos.getX();
  		coords[1] = pos.getY();
  		coords[2] = pos.getZ();
  		list.appendTag(new NBTTagIntArray(coords));
  	}
  	
  	tag.setTag("in", list);
  }
  
  @Override
  public void readFromNBT(NBTTagCompound tag)
  {
    super.readFromNBT(tag);
    inputs.clear();
    
    // read inputs pos
    
    NBTTagList list = tag.getTagList("in", Constants.NBT.TAG_INT_ARRAY);
    for(int i = 0; i < list.tagCount(); i++){
    	int[] coords = list.getIntArrayAt(i);
    	
    	if(coords.length == 3)
    		inputs.add(new BlockPos(coords[0], coords[1], coords[2]));
    }
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
}
