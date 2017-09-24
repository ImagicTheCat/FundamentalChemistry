package imagicthecat.fundamentalchemistry.shared.capability;

import net.minecraft.util.math.BlockPos;

public interface IPlayerCapability {
	public BlockPos getLinkPos();
	public void setLinkPos(BlockPos pos);
}
