package imagicthecat.fundamentalchemistry.shared.tileentity;

import java.util.Map;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;


public class TileItemAnalyzer extends TileSimpleMachine implements IInventory{

	protected ItemStack pstack;
	
	public TileItemAnalyzer()
	{
		this.storage.max_atoms = 0;
		this.storage.max_molecules = 0;
	}
	
	//do machine work
	public void tick()
	{

		
		//analyze one item of the input stack
		ItemStack stack = this.getStackInSlot(0);
		if(stack != pstack){
			this.buffer.clear();
			this.pstack = stack;
			
			if(stack != null){
				//fetch energy
				TileLaserRelay relay = this.getAttachedRelay();
				if(relay != null){
					ChemicalStorage request = new ChemicalStorage();
					request.addEnergy(5);
					request.take(buffer);
					
					relay.fetch(buffer, request);
				}
				
				if(this.buffer.containsEnergy(new ChemicalStorage(null, null, 5))){
					//find composition
					Map<Molecule, Integer> molecules = FundamentalChemistry.getItemComposition(stack.getItem());
					if(molecules != null)
						this.buffer.addMolecules(new ChemicalStorage(null, molecules));
				}
			}
			
			this.update();
		}
	}

	@Override
	public String getName() {
		return "container.item_analyzer";
	}
}
