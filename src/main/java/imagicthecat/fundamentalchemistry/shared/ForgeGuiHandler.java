package imagicthecat.fundamentalchemistry.shared;


import imagicthecat.fundamentalchemistry.client.gui.TileSimpleMachineGui;
import imagicthecat.fundamentalchemistry.shared.container.TileSimpleMachineContainer;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemBreaker;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileSimpleMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ForgeGuiHandler implements IGuiHandler {
	
	public static final int SIMPLE_MACHINE = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == SIMPLE_MACHINE)
			return new TileSimpleMachineContainer(player.inventory, (TileSimpleMachine)world.getTileEntity(new BlockPos(x,y,z)));

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == SIMPLE_MACHINE)
			return new TileSimpleMachineGui(player.inventory, (TileSimpleMachine)world.getTileEntity(new BlockPos(x,y,z)));

		return null;
	}

}
