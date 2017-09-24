package imagicthecat.fundamentalchemistry.shared.block;

import java.util.Random;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ForgeGuiHandler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileMoleculeBreaker;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMoleculeBreaker extends Block implements ITileEntityProvider{
	public BlockMoleculeBreaker() {
	  super(Material.ROCK);
	  this.setHardness(1.5f);
	  this.setUnlocalizedName("molecule_breaker");
	  this.setRegistryName("molecule_breaker");
	  this.setTickRandomly(true);
	  this.setCreativeTab(FundamentalChemistry.tab);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMoleculeBreaker();
	}
	
	@Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		
		if(player.isSneaking())
  		player.openGui(FundamentalChemistry.instance, ForgeGuiHandler.CHEMICAL_STORAGE, world, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleUpdate(pos, this, FundamentalChemistry.RELAY_TICKS);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		world.scheduleUpdate(pos, this, FundamentalChemistry.RELAY_TICKS); //schedule next update
		
		TileMoleculeBreaker ent = (TileMoleculeBreaker)world.getTileEntity(pos);
		ent.tick();
	}
}
