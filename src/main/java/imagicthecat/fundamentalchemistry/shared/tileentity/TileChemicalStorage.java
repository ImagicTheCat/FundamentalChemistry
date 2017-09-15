package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import net.minecraft.tileentity.TileEntity;

public class TileChemicalStorage extends TileEntity  {
	public ChemicalStorage storage;
	
	public TileChemicalStorage()
	{
		storage = new ChemicalStorage();
	}
}
