
package imagicthecat.fundamentalchemistry.shared.capability;

import net.minecraft.util.math.BlockPos;

public class PlayerCapability implements IPlayerCapability{
	protected BlockPos link;
	
	@Override
	public BlockPos getLinkPos() {
		return link;
	}

	@Override
	public void setLinkPos(BlockPos pos) {
		if(pos != null)
			link = new BlockPos(pos);
		else
			link = null;
	}
}
