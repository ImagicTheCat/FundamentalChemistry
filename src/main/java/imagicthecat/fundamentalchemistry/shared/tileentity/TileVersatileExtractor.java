
package imagicthecat.fundamentalchemistry.shared.tileentity;

import java.util.Random;

import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class TileVersatileExtractor extends TileChemicalStorage {
	
	private Random rand;
	
	public TileVersatileExtractor()
	{
		this.storage.max_molecules = 100;
		rand = new Random();
	}
	
	//do work
	public void tick()
	{
		//extract air
		if(hasAir()){
			//nitrogen
			if(chance(80))
				this.storage.addMolecule(Molecule.fromNotation("N2"), 1);
			
			//oxygen
			if(chance(20))
				this.storage.addMolecule(Molecule.fromNotation("O2"), 1);
			
			//carbon dioxide
			if(chance(1))
				this.storage.addMolecule(Molecule.fromNotation("CO2"), 1);
		}
		
		//extract water
		BlockPos wpos = findWater();
		if(wpos != null){
			this.storage.addMolecule(Molecule.fromNotation("H2O"), 1);
			this.worldObj.setBlockToAir(wpos);
		}
	}
	
	// chance test by percentage
	public boolean chance(int percent)
	{
		return rand.nextInt(100)+1 <= percent;
	}
	
	
	BlockPos findWater()
	{
		if(isStaticWater(this.pos.add(1,0,0)))
			return this.pos.add(1,0,0);
		if(isStaticWater(this.pos.add(-1,0,0)))
			return this.pos.add(-1,0,0);
		if(isStaticWater(this.pos.add(0,0,1)))
			return this.pos.add(0,0,1);
		if(isStaticWater(this.pos.add(0,0,-1)))
			return this.pos.add(0,0,-1);
		
		return null;
	}
	
	public boolean isStaticWater(BlockPos pos)
	{
		IBlockState state = this.worldObj.getBlockState(pos);
		return state.getBlock() == Blocks.water && state.getValue(BlockLiquid.LEVEL) == 0;
	}
	
  //check if the block is in contact with air (not up and down)
	public boolean hasAir()
	{
		if(this.worldObj.getBlockState(this.pos.add(1,0,0)).getBlock() == Blocks.air)
			return true;
		if(this.worldObj.getBlockState(this.pos.add(-1,0,0)).getBlock() == Blocks.air)
			return true;
		if(this.worldObj.getBlockState(this.pos.add(0,0,1)).getBlock() == Blocks.air)
			return true;
		if(this.worldObj.getBlockState(this.pos.add(0,0,-1)).getBlock() == Blocks.air)
			return true;
		
		return false;
	}
}
