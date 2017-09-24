package imagicthecat.fundamentalchemistry.shared.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileMoleculeAssembler extends TileSimpleMachine implements IInventory{
	private int page_index;
	private int line_index;
	private String[] lines;
	
	public TileMoleculeAssembler()
	{
		page_index = 0;
		line_index = 0;
		lines = new String[0];
		
		this.storage.max_atoms = 100;
		this.storage.max_molecules = 25;
	}
	
	//do machine work
	@Override
	public void tick()
	{
		// build molecules listed in the placed book using atoms from the network
		// each line of the book is a molecule
		
		Molecule scheme = getNextScheme();
		TileLaserRelay relay = this.getAttachedRelay();
		
		if(scheme != null && relay != null 
				&& FundamentalChemistry.molecules.invget(scheme) != null){ // check if the molecule scheme is registered (is possible)
			//build atoms request
			ChemicalStorage request = new ChemicalStorage();
			ChemicalStorage required = new ChemicalStorage(scheme.atoms, null);
			required.addEnergy(scheme.countProtons()); //add required energy
			if(new ChemicalStorage(this.storage).addMolecule(scheme, 1) == 0){ //check storage no overflow	
				//fetch required chemicals
				request.add(required); // base scheme
				request.take(this.buffer); // sub already buffered
				relay.fetch(this.buffer, request); // do request
				
				if(this.buffer.contains(required)){ // process
					this.buffer.take(required); // take reagents
					// output
					this.storage.addMolecule(scheme, 1);
					this.markDirty();
				}
			}
		}
	}
	
	public Molecule getNextScheme()
	{
		ItemStack stack = this.getStackInSlot(0);
		if(stack != null && stack.stackSize >= 0){
			NBTTagCompound tag = stack.getTagCompound();
			if(tag != null && tag.hasKey("pages")){
				NBTTagList pages = tag.getTagList("pages", 8);
				
				if(line_index >= 0 && line_index < lines.length){ // next line
					Molecule m = Molecule.fromNotation(lines[line_index]);
					line_index++;
					return m;
				}
				else{ //book next page
					lines = new String[0];
					
					page_index++;
					if(page_index >= pages.tagCount())
						page_index = 0;
					
					if(page_index < pages.tagCount()){ // load page
						line_index = 0;
						String content = pages.getStringTagAt(page_index);
						if(content != null)
							lines = content.split("\n");
						
						if(line_index >= 0 && line_index < lines.length){ // next line right after book load (prevents machine slowdown)
							Molecule m = Molecule.fromNotation(lines[line_index]);
							line_index++;
							return m;
						}
					}
					else // re-init page index
						page_index = 0;
	  		}
			}
		}
		
		return null;
	}

	@Override
	public String getName() {
		return "container.molecule_assembler";
	}
}
