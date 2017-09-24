package imagicthecat.fundamentalchemistry.shared.capability;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class PlayerProvider implements ICapabilityProvider{
	@CapabilityInject(IPlayerCapability.class)
	public static final Capability<IPlayerCapability> PLAYER_CAPABILITY = null;
	private IPlayerCapability instance;
	
	public PlayerProvider()
	{
		instance = PLAYER_CAPABILITY.getDefaultInstance();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == PLAYER_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		 return capability == PLAYER_CAPABILITY ? PLAYER_CAPABILITY.<T> cast(instance) : null;
	}
}
