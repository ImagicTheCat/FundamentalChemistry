package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalFilter;
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
			relay.fetch(this.storage, LaserRelayFetch.MOLECULES, buildFilter());
			this.markDirty();
		}
	}
	
	//build storage filter (return null if not filtered)
	public ChemicalFilter buildFilter()
	{
		//get book content
		ItemStack stack = this.getStackInSlot(0);
		if(stack != null && stack.stackSize >= 0){
			NBTTagCompound tag = stack.getTagCompound();
			if(tag != null && tag.hasKey("pages")){
				NBTTagList pages = tag.getTagList("pages", 8);
				ChemicalFilter filter = new ChemicalFilter();
				
				// one atom per line
				for(int i = 0; i < pages.tagCount(); i++){
					String content = pages.getStringTagAt(i);
					if(content != null){
						for(String line : content.split("\n")){
							if(line.length() > 0 && line.charAt(0) == '-'){ //check if blacklist policy
								filter.policy = false;
								line = line.substring(1);
							}
							
							Molecule m = Molecule.fromNotation(line);
							if(m != null)
								filter.molecules.add(m);
						}
					}
				}
				
				return filter;
			}
		}
		
		return null;
	}
	
	@Override
	public String getName() {
		return "container.molecular_storage";
	}
}
