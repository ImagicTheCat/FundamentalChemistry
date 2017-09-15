package imagicthecat.fundamentalchemistry.shared;

import imagicthecat.fundamentalchemistry.client.gui.TileItemBreakerGui;
import imagicthecat.fundamentalchemistry.shared.container.TileItemBreakerContainer;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemBreaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ForgeGuiHandler implements IGuiHandler {
	
	public static final int ITEM_BREAKER = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == ITEM_BREAKER)
			return new TileItemBreakerContainer(player.inventory, (TileItemBreaker)world.getTileEntity(new BlockPos(x,y,z)));

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == ITEM_BREAKER)
			return new TileItemBreakerGui(player.inventory, (TileItemBreaker)world.getTileEntity(new BlockPos(x,y,z)));

		return null;
	}

}
