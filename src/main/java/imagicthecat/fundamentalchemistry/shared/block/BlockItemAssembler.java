package imagicthecat.fundamentalchemistry.shared.block;

import java.util.Random;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ForgeGuiHandler;
import imagicthecat.fundamentalchemistry.shared.tileentity.LaserRelayFetch;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemAssembler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemBreaker;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileMoleculeAssembler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileSimpleMachine;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemAssembler extends Block implements ITileEntityProvider{

	public BlockItemAssembler() {
	  super(Material.ROCK);
	  this.setHardness(1.5f);
	  this.setUnlocalizedName("item_assembler");
	  this.setRegistryName("item_assembler");
	  this.setTickRandomly(true);
	  this.setCreativeTab(FundamentalChemistry.tab);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileItemAssembler();
	}
	
	@Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
    if (!world.isRemote){
			if(player.isSneaking())
    		player.openGui(FundamentalChemistry.instance, ForgeGuiHandler.CHEMICAL_STORAGE, world, pos.getX(), pos.getY(), pos.getZ());
			else
				player.openGui(FundamentalChemistry.instance, ForgeGuiHandler.SIMPLE_MACHINE, world, pos.getX(), pos.getY(), pos.getZ());
    }
		
    return true;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
	  InventoryHelper.dropInventoryItems(world, pos, (TileSimpleMachine)world.getTileEntity(pos));
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
