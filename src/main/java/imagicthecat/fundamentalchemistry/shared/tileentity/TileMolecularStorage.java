package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileMolecularStorage extends TileSimpleMachine {
	public TileMolecularStorage()
	{
		this.storage.max_molecules = 250;
	}
	
	@Override
	public void tick()
	{
		TileLaserRelay relay = this.getAttachedRelay();
		if(relay != null){
			ChemicalStorage request = buildRequest();
			if(request != null)
				relay.fetch(this.storage, request);
			else
				relay.fetch(this.storage, LaserRelayFetch.MOLECULES);
			this.markDirty();
		}
	}
	
	//build storage request (return null if not filtered)
	public ChemicalStorage buildRequest()
	{
		//get book content
		ItemStack stack = this.getStackInSlot(0);
		if(stack != null && stack.stackSize >= 0){
			NBTTagCompound tag = stack.getTagCompound();
			if(tag != null && tag.hasKey("pages")){
				NBTTagList pages = tag.getTagList("pages", 8);
				ChemicalStorage request = new ChemicalStorage();
				
				// one atom per line
				for(int i = 0; i < pages.tagCount(); i++){
					String content = pages.getStringTagAt(i);
					if(content != null){
						for(String line : content.split("\n")){
							Molecule m = Molecule.fromNotation(line);
							if(m != null)
								request.addMolecule(m, this.storage.max_molecules);
						}
					}
				}
				
				return request;
			}
		}
		
		return null;
	}
}
