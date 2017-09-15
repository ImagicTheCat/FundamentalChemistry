package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.Constants;

public class TileLaserRelay extends TileEntity {
	public Set<BlockPos> inputs;
	public Set<BlockPos> outputs;
	
	public TileLaserRelay()
	{
		inputs = new HashSet<BlockPos>();
		outputs = new HashSet<BlockPos>();
	}
	
	public void toUpdate()
	{
		if(!this.worldObj.isRemote){
			this.worldObj.markBlockForUpdate(this.pos);
			this.markDirty();
		}
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
		if(te != null && te instanceof TileLaserRelay){ //is a laser relay
			TileLaserRelay ent = (TileLaserRelay)te;
			if(!pos.equals(this.pos) && this.pos.distanceSq(pos.getX(), pos.getY(), pos.getZ()) <= FundamentalChemistry.MAX_RELAY_DISTANCE){ //prevents self linking and check distance
				if(!inputs.contains(pos)){ 
					inputs.add(pos);
					ent.outputs.add(this.pos);
				}
				else{ //break the link
					inputs.remove(pos);
					ent.outputs.remove(this.pos);
				}
			
				this.toUpdate();
				ent.toUpdate();
				
				return true;
			}
		}
		
		return false;
	}
	
	// get the maximum of chemicals possible in the network, following the given mode
	public void fetch(ChemicalStorage out, LaserRelayFetch mode)
	{
		fetch(out, null, mode, new HashSet<BlockPos>());
	}
	
	// get the maximum of chemicals possible in the network, trying to fulfill the request
	public void fetch(ChemicalStorage out, ChemicalStorage request)
	{
		fetch(out, new ChemicalStorage(request), LaserRelayFetch.REQUEST, new HashSet<BlockPos>());
	}
	
	public void fetch(ChemicalStorage out, ChemicalStorage request, LaserRelayFetch mode, Set<BlockPos> dones)
	{
		dones.add(pos); //prevents circular fetch
		
		TileChemicalStorage storage = getAttachedStorage();
		
		if(storage != null && storage.storage != out){ 
			ChemicalStorage chems = storage.storage;
			
			//fetch storage
			if(mode == LaserRelayFetch.ALL){
				out.add(chems);
				chems.clear();
			}
			else if(mode == LaserRelayFetch.ATOMS){
				ChemicalStorage transfer = new ChemicalStorage();
				transfer.addAtoms(chems);
				out.add(chems.take(transfer));
			}
			else if(mode == LaserRelayFetch.MOLECULES){
				ChemicalStorage transfer = new ChemicalStorage();
				transfer.addMolecules(chems);
				out.add(chems.take(transfer));
			}
			else if(mode == LaserRelayFetch.REQUEST && request != null){
				ChemicalStorage taken = chems.take(request);
				request.take(taken); //part of the request done, sub what is taken
				out.add(taken);
			}
		}
		else{ // continue
			for(BlockPos pos : inputs){
				TileEntity te = this.worldObj.getTileEntity(pos);
				if(te != null && te instanceof TileLaserRelay && !dones.contains(pos)){
					TileLaserRelay ent = (TileLaserRelay)te;
					ent.fetch(out, request, mode, dones);
				}
			}
		}
	}
	
	// return the chemical storage attached to the relay, or null (currently under the laser relay)
	public TileChemicalStorage getAttachedStorage()
	{
		TileEntity te = this.worldObj.getTileEntity(this.pos.add(0, -1, 0));
		if(te != null && te instanceof TileChemicalStorage)
			return (TileChemicalStorage)te;
		
		return null;
	}
	
	public void destroy() //called when the relay is destroyed
	{
		//remove links
		
		//remove inputs
		for(BlockPos pos : inputs){
			TileEntity te = this.getWorld().getTileEntity(pos);
			if(te != null && te instanceof TileLaserRelay){ //is a laser relay
				TileLaserRelay ent = (TileLaserRelay)te;
				ent.outputs.remove(this.pos);
				ent.toUpdate();
			}
		}
		
		//remove outputs
		for(BlockPos pos : outputs){
			TileEntity te = this.getWorld().getTileEntity(pos);
			if(te != null && te instanceof TileLaserRelay){ //is a laser relay
				TileLaserRelay ent = (TileLaserRelay)te;
				ent.inputs.remove(this.pos);
				ent.toUpdate();
			}
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