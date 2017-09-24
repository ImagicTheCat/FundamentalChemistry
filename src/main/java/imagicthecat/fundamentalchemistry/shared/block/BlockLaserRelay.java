package imagicthecat.fundamentalchemistry.shared.block;

import java.util.Random;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.capability.IPlayerCapability;
import imagicthecat.fundamentalchemistry.shared.capability.PlayerProvider;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockLaserRelay extends Block implements ITileEntityProvider{
  public BlockLaserRelay()
  {
    super(Material.ROCK);
    this.setHardness(1.5f);
    this.setUnlocalizedName("laser_relay");
    this.setRegistryName("laser_relay");
    this.setTickRandomly(true);
    this.setCreativeTab(FundamentalChemistry.tab);
  }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileLaserRelay();
	}
	
	@Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileLaserRelay ent = (TileLaserRelay)world.getTileEntity(pos);
		IPlayerCapability pcap = player.getCapability(PlayerProvider.PLAYER_CAPABILITY, null);
		
		if(pcap != null){
			if(pcap.getLinkPos() != null){ // already linking, try to link to this one
				if(!world.isRemote)
					ent.toggleConnectFrom(pcap.getLinkPos());
					
				pcap.setLinkPos(null);
			}
			else
				pcap.setLinkPos(pos);
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) // event when block is destroyed and before tile entity is removed
	{
		TileLaserRelay ent = (TileLaserRelay)world.getTileEntity(pos);
		ent.destroy();
		
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
