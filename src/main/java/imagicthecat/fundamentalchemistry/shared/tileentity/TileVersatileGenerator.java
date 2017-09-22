package imagicthecat.fundamentalchemistry.shared.tileentity;

import java.util.Map;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileVersatileGenerator extends TileSimpleMachine implements IInventory{

	public TileVersatileGenerator()
	{
		this.storage.max_energy = 1000;
	}
	
	//do machine work
	public void tick()
	{
		//consume one fuel item of the input stack
		
		ItemStack stack = decrStackSize(0,1);
		if(stack != null && stack.stackSize == 1){
			int burn_time = TileEntityFurnace.getItemBurnTime(stack);	
			int energy = (int) Math.ceil(burn_time/5.f);
			
			if(burn_time > 0 && new ChemicalStorage(this.storage).addEnergy(energy) == 0){ //check storage no overflow first
				this.storage.addEnergy(energy);
				this.markDirty();
			}
			else{ //reverse stack decrement
				ItemStack pstack = this.getStackInSlot(0);
				if(pstack != null)
					stack.stackSize += pstack.stackSize;

				this.setInventorySlotContents(0, stack);
			}
		}
		
		//wind
		if(hasAir())
			this.storage.addEnergy(10);
		
		//water movement
		this.storage.addEnergy(countMovingWater()*10);
		
		//heat source
		this.storage.addEnergy(getHeatSource()*10);
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
	
	//count adjacent moving water blocks
	public int countMovingWater()
	{
		int count = 0;
		
		if(isFlowingWater(this.pos.add(-1,0,0)))
			count++;
		if(isFlowingWater(this.pos.add(1,0,0)))
			count++;
		if(isFlowingWater(this.pos.add(0,0,-1)))
			count++;
		if(isFlowingWater(this.pos.add(0,0,1)))
			count++;
		
		return count;
	}
	
	public boolean isFlowingWater(BlockPos pos)
	{
		IBlockState state = this.worldObj.getBlockState(pos);
		return state.getBlock() == Blocks.water && state.getValue(BlockLiquid.LEVEL) > 0;
	}
	
	//get down heat source value
	public int getHeatSource()
	{
		Block block = this.worldObj.getBlockState(this.pos.add(0,-1,0)).getBlock();
		if(block == Blocks.fire)
			return 2;
		else if(block == Blocks.flowing_lava)
			return 2;
		else if(block == Blocks.lava)
			return 5;
		
		return 0;
	}
	

	@Override
	public String getName() {
		return "container.versatile_generator";
	}
}
