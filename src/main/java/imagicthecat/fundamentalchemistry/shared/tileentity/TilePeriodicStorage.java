package imagicthecat.fundamentalchemistry.shared.tileentity;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
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
			ChemicalStorage request = buildRequest();
			if(request != null)
				relay.fetch(this.storage, request);
			else
				relay.fetch(this.storage, LaserRelayFetch.ATOMS);
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
							Integer an = FundamentalChemistry.elements.get(line.replaceAll("[^a-zA-Z]", ""));
							if(an != null)
								request.addAtom(an, this.storage.max_atoms);
						}
					}
				}
				
				return request;
			}
		}
		
		return null;
	}
}
