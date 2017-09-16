package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileMoleculeBreaker extends TileChemicalStorage {
	ChemicalStorage buffer;
	
	public TileMoleculeBreaker()
	{
		buffer = new ChemicalStorage();
		
		this.storage.max_atoms = 100;
		this.storage.max_molecules = 100;
	}
	
	//do machine work
	public void tick()
	{
		TileLaserRelay relay = this.getAttachedRelay();
		if(relay != null){
			//fetch a molecule from the network
			
			if(buffer.isEmpty()) // nothing in the buffer, request any molecule
				relay.fetch(buffer, LaserRelayFetch.ANY_MOLECULE);
			
			if(!buffer.molecules.isEmpty()){
				//break the molecule
				Molecule m = buffer.molecules.entrySet().iterator().next().getKey();
				
				if(new ChemicalStorage(this.storage).addAtoms(new ChemicalStorage(m.atoms, null)).isEmpty()){ //check storage overflow first
					//output
					this.storage.addAtoms(new ChemicalStorage(m.atoms, null));
					this.markDirty();
					buffer.clear();
				}
			}
		}
	}
}

