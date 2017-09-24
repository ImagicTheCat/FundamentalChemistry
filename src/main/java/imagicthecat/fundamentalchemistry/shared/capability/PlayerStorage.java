package imagicthecat.fundamentalchemistry.shared.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PlayerStorage implements IStorage<IPlayerCapability>
{

	@Override
	public NBTBase writeNBT(Capability<IPlayerCapability> capability,
			IPlayerCapability instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IPlayerCapability> capability,
			IPlayerCapability instance, EnumFacing side, NBTBase nbt) {
	}
}
