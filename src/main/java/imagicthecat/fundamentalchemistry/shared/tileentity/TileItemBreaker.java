package imagicthecat.fundamentalchemistry.shared.tileentity;

import java.util.Map;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

public class TileItemBreaker extends TileSimpleMachine implements IInventory{

	public TileItemBreaker()
	{
		this.storage.max_atoms = 100;
		this.storage.max_molecules = 25;
	}
	
	//do machine work
	public void tick()
	{
		//break one item of the input stack
		ItemStack stack = decrStackSize(0,1);
		if(stack != null && stack.stackSize == 1){
			boolean consume = false;
			
			//find composition
			Map<Molecule, Integer> molecules = FundamentalChemistry.getItemComposition(stack.getItem());
			if(molecules != null){
				//compute energy/products
				ChemicalStorage products = new ChemicalStorage(null, molecules);
				ChemicalStorage required_energy = new ChemicalStorage();
				required_energy.addEnergy(products.countAtoms());
				
				//fetch energy
				TileLaserRelay relay = this.getAttachedRelay();
				if(relay != null){
					ChemicalStorage request = new ChemicalStorage();
					request.addEnergy(required_energy);
					request.take(buffer);
					
					relay.fetch(buffer, request);
				}
				
				if(buffer.contains(required_energy) //check energy
						&& new ChemicalStorage(this.storage).add(products).isEmpty()){ //check storage no overflow 
					buffer.take(required_energy);
					this.storage.add(new ChemicalStorage(null, molecules));
					this.markDirty();
					consume = true;
				}
			}
			
			if(!consume){ //reverse stack decrement
				ItemStack pstack = this.getStackInSlot(0);
				if(pstack != null)
					stack.stackSize += pstack.stackSize;

				this.setInventorySlotContents(0, stack);
			}
		}
	}

	@Override
	public String getName() {
		return "container.item_breaker";
	}
}
