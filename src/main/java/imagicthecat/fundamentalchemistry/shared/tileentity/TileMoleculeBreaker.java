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
	public TileMoleculeBreaker()
	{
		this.storage.max_atoms = 100;
		this.storage.max_molecules = 100;
	}
	
	//do machine work
	public void tick()
	{
		TileLaserRelay relay = this.getAttachedRelay();
		if(relay != null){
			//fetch a molecule from the network
			
			if(this.buffer.molecules.isEmpty()) // nothing in the buffer, request any molecule
				relay.fetch(buffer, LaserRelayFetch.ANY_MOLECULE);
			
			if(!this.buffer.molecules.isEmpty()){
				Molecule m = this.buffer.molecules.entrySet().iterator().next().getKey();
				ChemicalStorage reagents = new ChemicalStorage();
				reagents.addMolecule(m, 1);
				ChemicalStorage products = new ChemicalStorage(m.atoms, null);
				
				//compute energy
				ChemicalStorage required_energy = new ChemicalStorage();
				required_energy.addEnergy(m.countProtons());
				
				//fetch energy
				ChemicalStorage request = new ChemicalStorage();
				request.addEnergy(required_energy);
				request.take(buffer);
				relay.fetch(buffer, request);

			  //break the molecule
				if(this.buffer.containsEnergy(required_energy) &&  //check energy
						new ChemicalStorage(this.storage).addAtoms(products).isEmpty()){ //check storage no overflow
					//output
					this.buffer.take(reagents);
					this.buffer.take(required_energy);
					this.storage.addAtoms(products);
					this.markDirty();
				}
			}
		}
	}
}

