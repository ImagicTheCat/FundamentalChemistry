package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalFilter;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TilePeriodicStorage extends TileSimpleMachine {
	public TilePeriodicStorage()
	{
		this.storage.max_atoms = 1000;
	}
	
	@Override
	public void tick()
	{
		TileLaserRelay relay = this.getAttachedRelay();
		if(relay != null){
			relay.fetch(this.storage, LaserRelayFetch.ATOMS, buildFilter());
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
							
							Integer an = FundamentalChemistry.elements.get(line.replaceAll("[^a-zA-Z]", ""));
							if(an != null)
								filter.atoms.add(an);
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
		return "container.periodic_storage";
	}
}
