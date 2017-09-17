
package imagicthecat.fundamentalchemistry.shared.tileentity;

import java.util.Random;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class TileNuclearTransmuter extends TileSimpleMachine {
	protected int factor; // direction/factor of the transmutation
	private Random rand;
	
	public TileNuclearTransmuter(int factor)
	{
		this.factor = factor;
		
		this.storage.max_atoms = 100;
		rand = new Random();
	}
	
	//do work
	@Override
	public void tick()
	{
		TileLaserRelay relay = this.getAttachedRelay();
		if(relay != null){
			//fetch an atom from the network
			
			if(this.buffer.atoms.isEmpty()) // nothing in the buffer, request any molecule
				relay.fetch(buffer, LaserRelayFetch.ANY_ATOM);
			
			ItemStack stack = this.decrStackSize(0, 1);
			if(stack != null && stack.stackSize == 1){
				Integer catalyst_power = FundamentalChemistry.nuclear_transmuter_catalysts.get(stack.getItem());
				boolean consume = false; 
						
				if(!this.buffer.atoms.isEmpty() && catalyst_power != null){ //available atom, available catalyst
					//prepare request
					ChemicalStorage required = new ChemicalStorage();
					required.addAtom(this.buffer.atoms.entrySet().iterator().next().getKey(), 1); //add processed atom
					required.addEnergy(catalyst_power); //required energy
					
					//fetch energy
					ChemicalStorage request = new ChemicalStorage();
					request.add(required);
					request.take(this.buffer);
					relay.fetch(this.buffer, request);
					
					Integer new_atom = buffer.atoms.entrySet().iterator().next().getKey()+catalyst_power*factor;
					
					if(this.buffer.contains(required) && //check required chemicals (atom and energy)
							new ChemicalStorage(this.storage).addAtom(new_atom, 1) == 0){ //check storage no overflow
						// do transmutation
						if(chance(Math.min(catalyst_power*10+50, 99))){ // operation chance check
							if(FundamentalChemistry.elements.invget(new_atom) != null){ // check if the new atom exists
								this.storage.addAtom(new_atom, 1);
								this.markDirty();
							}
						}
						else // consume catalyst on failure
							consume = true;
						
						this.buffer.take(required); //take reagents
					}
				}
				
				if(!consume){
					//reverse stack decrement
					ItemStack pstack = this.getStackInSlot(0);
					if(pstack != null)
						stack.stackSize += pstack.stackSize;

					this.setInventorySlotContents(0, stack);
				}
			}
		}
	}
	
	// chance test by percentage
	public boolean chance(int percent)
	{
		return rand.nextInt(100)+1 <= percent;
	}
}
