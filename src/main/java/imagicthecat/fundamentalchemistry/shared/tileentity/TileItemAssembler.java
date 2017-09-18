package imagicthecat.fundamentalchemistry.shared.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

public class TileItemAssembler extends TileSimpleMachine implements IInventory{
	public TileItemAssembler()
	{
	}
	
	//do machine work
	@Override
	public void tick()
	{
		// build molecules listed in the placed book using atoms from the network
		// each line of the book is a molecule

		ItemStack stack = this.getStackInSlot(0);
		TileLaserRelay relay = this.getAttachedRelay();
		if(stack != null && stack.stackSize > 0 && relay != null){
			//get item scheme
			Map<Molecule, Integer> scheme = FundamentalChemistry.getItemComposition(stack.getItem());
			if(scheme != null){
				//build molecules request
				ChemicalStorage request = new ChemicalStorage();
				ChemicalStorage required = new ChemicalStorage(null, scheme); //required molecules
				required.addEnergy(required.countAtoms()); //required energy
				
				//fetch required
				request.add(required); // base scheme
				request.take(this.buffer); // sub already buffered
				relay.fetch(this.buffer, request); // do request
				
				if(this.buffer.contains(required)){ // process
					this.buffer.take(required); // take reagents
					
					// output
					TileEntityChest chest = getAttachedChest();
					if(chest != null){ // output in chest
						ItemStack residual = TileEntityHopper.putStackInInventoryAllSlots(chest, new ItemStack(stack.getItem(), 1), EnumFacing.NORTH);
						//output residual
						if(residual != null && residual.stackSize > 0) 
							this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ(), residual));
						
					}
					else //output in world
						this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ(), new ItemStack(stack.getItem(), 1)));
				}
			}
		}
	}
	
	public TileEntityChest getAttachedChest()
	{
		TileEntity ent = null;
		ent = this.worldObj.getTileEntity(this.pos.add(1, 0, 0));
		if(ent != null && ent instanceof TileEntityChest)
			return (TileEntityChest)ent;
		ent = this.worldObj.getTileEntity(this.pos.add(-1, 0, 0));
		if(ent != null && ent instanceof TileEntityChest)
			return (TileEntityChest)ent;
		ent = this.worldObj.getTileEntity(this.pos.add(0, 0, 1));
		if(ent != null && ent instanceof TileEntityChest)
			return (TileEntityChest)ent;
		ent = this.worldObj.getTileEntity(this.pos.add(0, 0, -1));
		if(ent != null && ent instanceof TileEntityChest)
			return (TileEntityChest)ent;
		
		return null;
	}

	@Override
	public String getName() {
		return "container.item_assembler";
	}
}
