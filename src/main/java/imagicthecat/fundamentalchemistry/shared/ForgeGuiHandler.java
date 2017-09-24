package imagicthecat.fundamentalchemistry.shared;


import imagicthecat.fundamentalchemistry.client.gui.ChemicalInventoryGui;
import imagicthecat.fundamentalchemistry.client.gui.TileSimpleMachineGui;
import imagicthecat.fundamentalchemistry.shared.container.ChemicalInventory;
import imagicthecat.fundamentalchemistry.shared.container.ChemicalInventoryContainer;
import imagicthecat.fundamentalchemistry.shared.container.TileSimpleMachineContainer;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemBreaker;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileSimpleMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ForgeGuiHandler implements IGuiHandler {
	
	public static final int SIMPLE_MACHINE = 0;
	public static final int CHEMICAL_STORAGE = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == SIMPLE_MACHINE)
			return new TileSimpleMachineContainer(player.inventory, (TileSimpleMachine)world.getTileEntity(new BlockPos(x,y,z)));
		else if(ID == CHEMICAL_STORAGE)
			return new ChemicalInventoryContainer(new ChemicalInventory(((TileChemicalStorage)world.getTileEntity(new BlockPos(x,y,z))).storage));

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == SIMPLE_MACHINE)
			return new TileSimpleMachineGui(player.inventory, (TileSimpleMachine)world.getTileEntity(new BlockPos(x,y,z)));
		else if(ID == CHEMICAL_STORAGE)
			return new ChemicalInventoryGui(new ChemicalInventory(null)); // don't init with storage (display only)

		return null;
	}

}
