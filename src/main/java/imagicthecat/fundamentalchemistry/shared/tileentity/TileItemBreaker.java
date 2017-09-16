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
	}
	
	//do machine work
	public void tick()
	{
		//break one item of the input stack
		ItemStack stack = decrStackSize(0,1);
		if(stack != null && stack.stackSize == 1){
			//find composition
			Map<Molecule, Integer> molecules = FundamentalChemistry.item_compositions.get(stack.getItem());
			if(molecules != null)
				this.storage.add(new ChemicalStorage(null, molecules));
		}
	}

	@Override
	public String getName() {
		return "container.item_breaker";
	}
}
