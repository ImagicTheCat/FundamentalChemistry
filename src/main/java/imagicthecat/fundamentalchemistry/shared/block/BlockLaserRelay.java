package imagicthecat.fundamentalchemistry.shared.block;

import java.util.Random;

import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockLaserRelay extends Block implements ITileEntityProvider{
  public BlockLaserRelay()
  {
    super(Material.rock);
    this.setHardness(1.5f);
    this.setUnlocalizedName("laser_relay");
    this.setTickRandomly(true);
    this.setCreativeTab(CreativeTabs.tabBlock);
  }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileLaserRelay();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos,
			IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ) 
	{
		if(!world.isRemote){
			TileLaserRelay ent = (TileLaserRelay)world.getTileEntity(pos);
			
			NBTTagCompound tag = player.getEntityData();
			if(tag.hasKey("fch:link")){ // already linking, try to link to this one
				int[] coords = tag.getIntArray("fch:link");
				if(coords.length == 3){
					BlockPos ppos = new BlockPos(coords[0], coords[1], coords[2]);
					if(ent.toggleConnectFrom(ppos))
						player.addChatMessage(new ChatComponentText("Linked/unlinked "+ppos+" to "+pos+"."));
					else
						player.addChatMessage(new ChatComponentText("Couldn't link "+ppos+" to "+pos+"."));
				}
				
				tag.removeTag("fch:link");
			}
			else{ //not linking, add current coords
				int[] coords = new int[3];
				coords[0] = pos.getX();
				coords[1] = pos.getY();
				coords[2] = pos.getZ();
				
				tag.setIntArray("fch:link", coords);
			}
		}
		
		return super.onBlockActivated(world, pos, state, player, side, hitX, hitY,
				hitZ);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if(!world.isRemote){
			TileLaserRelay ent = (TileLaserRelay)world.getTileEntity(pos);
			ent.check();
		}
	}
}
