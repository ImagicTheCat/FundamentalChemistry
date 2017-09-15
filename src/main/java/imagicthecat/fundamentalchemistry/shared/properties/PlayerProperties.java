package imagicthecat.fundamentalchemistry.shared.properties;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerProperties implements IExtendedEntityProperties {
	public static String ID = "fundamentalchemistry:player";
	
	public BlockPos p_link; //if linking two elements, contains the blockpos of the first element (the source)
	
	public PlayerProperties()
	{
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub

	}
}
