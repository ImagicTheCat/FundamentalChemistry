package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import net.minecraft.tileentity.TileEntity;

public class TileChemicalStorage extends TileEntity  {
	public ChemicalStorage storage;
	
	public TileChemicalStorage()
	{
		storage = new ChemicalStorage();
	}
	
	// return the relay attached to the storage, or null (currently above the storage)
	public TileLaserRelay getAttachedRelay()
	{
		TileEntity te = this.worldObj.getTileEntity(this.pos.add(0, 1, 0));
		if(te != null && te instanceof TileLaserRelay)
			return (TileLaserRelay)te;
		
		return null;
	}
}
