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
	
	//do machine work
	public void tick()
	{
		TileLaserRelay relay = this.getAttachedRelay();
		if(relay != null){
			//fetch a molecule from the network
			
			ChemicalStorage taken = new ChemicalStorage();
			relay.fetch(taken, LaserRelayFetch.ANY_MOLECULE);
			
			if(!taken.molecules.isEmpty()){
				//break the molecule
				Molecule m = taken.molecules.entrySet().iterator().next().getKey();
				this.storage.addAtoms(new ChemicalStorage(m.atoms, null));
				this.markDirty();
			}
		}
	}
}

