package imagicthecat.fundamentalchemistry.shared.block;

import java.util.Random;
import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ForgeGuiHandler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileNegativeNuclearTransmuter;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileSimpleMachine;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockNegativeNuclearTransmuter extends Block implements ITileEntityProvider{

	public BlockNegativeNuclearTransmuter() {
	  super(Material.rock);
	  this.setHardness(1.5f);
	  this.setUnlocalizedName("negative_nuclear_transmuter");
	  this.setTickRandomly(true);
	  this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileNegativeNuclearTransmuter();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos,
			IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ) 
	{
    if (!world.isRemote)
      player.openGui(FundamentalChemistry.instance, ForgeGuiHandler.SIMPLE_MACHINE, world, pos.getX(), pos.getY(), pos.getZ());
		
		return super.onBlockActivated(world, pos, state, player, side, hitX, hitY,
				hitZ);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		TileSimpleMachine ent = (TileSimpleMachine) world.getTileEntity(pos);
	  InventoryHelper.dropInventoryItems(world, pos, ent);
	  
	  super.breakBlock(world, pos, blockstate);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleUpdate(pos, this, FundamentalChemistry.RELAY_TICKS);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		world.scheduleUpdate(pos, this, FundamentalChemistry.RELAY_TICKS); //schedule next update
		
		TileSimpleMachine ent = (TileSimpleMachine)world.getTileEntity(pos);
		ent.tick();
	}
}
