package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalFilter;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.Constants;

public class TileLaserRelay extends TileEntity {
	public Set<BlockPos> inputs;
	public Set<BlockPos> outputs;
	protected Random rand;
	
	public TileLaserRelay()
	{
		inputs = new HashSet<BlockPos>();
		outputs = new HashSet<BlockPos>();
		rand = new Random();
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
		fetch(out, null, null, mode, new HashSet<BlockPos>(), true);
	}
	
	// get the maximum of chemicals possible in the network, following the given mode and filter
	public void fetch(ChemicalStorage out, LaserRelayFetch mode, ChemicalFilter filter)
	{
		fetch(out, null, filter, mode, new HashSet<BlockPos>(), true);
	}
	
	// get the maximum of chemicals possible in the network, trying to fulfill the request
	public void fetch(ChemicalStorage out, ChemicalStorage request)
	{
		fetch(out, new ChemicalStorage(request), null, LaserRelayFetch.REQUEST, new HashSet<BlockPos>(), true);
	}

	
	public void fetch(ChemicalStorage out, ChemicalStorage request, ChemicalFilter filter, LaserRelayFetch mode, Set<BlockPos> dones, boolean first_ignore_storage)
	{
		dones.add(pos); //prevents circular fetch
		
		TileChemicalStorage storage = getAttachedStorage();
		
		if(storage != null && !first_ignore_storage){ 
			ChemicalStorage chems = storage.storage;
			
			//fetch storage
			if(mode == LaserRelayFetch.ALL){
				ChemicalStorage transfer = new ChemicalStorage();
				transfer.add(chems);
				transfer.applyFilter(filter);
				chems.add(out.add(chems.take(transfer))); //take everything, return overflow to origin
			}
			else if(mode == LaserRelayFetch.ATOMS){
				ChemicalStorage transfer = new ChemicalStorage();
				transfer.addAtoms(chems);
				transfer.applyFilter(filter);
				chems.add(out.add(chems.take(transfer))); //take atoms, return overflow to origin
			}
			else if(mode == LaserRelayFetch.MOLECULES){
				ChemicalStorage transfer = new ChemicalStorage();
				transfer.addMolecules(chems);
				transfer.applyFilter(filter);
				chems.add(out.add(chems.take(transfer))); //take molecules, return overflow to origin
			}
			else if(mode == LaserRelayFetch.ENERGY){
				ChemicalStorage transfer = new ChemicalStorage();
				transfer.addEnergy(chems);
				transfer.applyFilter(filter);
				chems.add(out.add(chems.take(transfer))); //take energy, return overflow to origin
			}
			else if(mode == LaserRelayFetch.ANY_ATOM){
				ChemicalStorage transfer = new ChemicalStorage();
				
				if(out.atoms.isEmpty() && !chems.atoms.isEmpty()){
					//take one atom randomly
					int index = rand.nextInt(chems.atoms.size());

					Iterator<Entry<Integer, Integer>> it = chems.atoms.entrySet().iterator();
					for(int i = 0; i < index; i++)
						it.next();
						
					transfer.atoms.put(it.next().getKey(), 1);
					transfer.applyFilter(filter);
					chems.add(out.add(chems.take(transfer))); //take any/one atom, return overflow to origin
				}
			}
			else if(mode == LaserRelayFetch.ANY_MOLECULE){
				ChemicalStorage transfer = new ChemicalStorage();

				if(out.molecules.isEmpty() && !chems.molecules.isEmpty()){
					//take one molecule randomly
					int index = rand.nextInt(chems.molecules.size());

					Iterator<Entry<Molecule, Integer>> it = chems.molecules.entrySet().iterator();
					for(int i = 0; i < index; i++)
						it.next();
						
					transfer.molecules.put(it.next().getKey(), 1);
					transfer.applyFilter(filter);
					chems.add(out.add(chems.take(transfer))); //take any/one molecule, return overflow to origin
				}
			}
			else if(mode == LaserRelayFetch.REQUEST && request != null){
				ChemicalStorage taken = chems.take(request);
				request.take(taken); //part of the request done, sub what is taken
				chems.add(out.add(taken)); //return request overflow to origin
			}
		}
		else{ // continue
			for(BlockPos pos : inputs){
				TileEntity te = this.worldObj.getTileEntity(pos);
				if(te != null && te instanceof TileLaserRelay && !dones.contains(pos)){
					TileLaserRelay ent = (TileLaserRelay)te;
					ent.fetch(out, request, filter, mode, dones, false);
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
	
	public void check() //called to check the connections (ex: disconnect on obstruction)
	{	
		if(!this.worldObj.isRemote){
			Set<BlockPos> rmlist = new HashSet<BlockPos>();
			
			for(BlockPos pos : inputs){
				//raytrace input laser
				
				Vec3 start = new Vec3(pos).addVector(0.5, FundamentalChemistry.LASER_HEIGHT, 0.5);
				Vec3 end = new Vec3(this.pos).addVector(0.5, FundamentalChemistry.LASER_HEIGHT, 0.5);
				
				MovingObjectPosition r = this.worldObj.rayTraceBlocks(start, end, false, true, false);
				if(r != null) //not equal to this laser relay
					rmlist.add(pos);
			}
			
			for(BlockPos pos : rmlist)
				toggleConnectFrom(pos);
		}
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
  	
  	
  	//save inputs pos
  	NBTTagList list = new NBTTagList();
  	
  	for(BlockPos pos : inputs){
  		int[] coords = new int[3];
  		coords[0] = pos.getX();
  		coords[1] = pos.getY();
  		coords[2] = pos.getZ();
  		list.appendTag(new NBTTagIntArray(coords));
  	}
  	
  	tag.setTag("in", list);
  	
  	//save outputs pos
  	list = new NBTTagList();
  	
  	for(BlockPos pos : outputs){
  		int[] coords = new int[3];
  		coords[0] = pos.getX();
  		coords[1] = pos.getY();
  		coords[2] = pos.getZ();
  		list.appendTag(new NBTTagIntArray(coords));
  	}
  	
  	tag.setTag("out", list);
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
    
    // read outputs pos
    
    list = tag.getTagList("out", Constants.NBT.TAG_INT_ARRAY);
    for(int i = 0; i < list.tagCount(); i++){
    	int[] coords = list.getIntArrayAt(i);
    	
    	if(coords.length == 3)
    		outputs.add(new BlockPos(coords[0], coords[1], coords[2]));
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
