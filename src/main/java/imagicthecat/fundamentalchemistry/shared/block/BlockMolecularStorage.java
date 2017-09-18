package imagicthecat.fundamentalchemistry.shared.block;

import java.util.Random;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.ForgeGuiHandler;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import imagicthecat.fundamentalchemistry.shared.properties.PlayerProperties;
import imagicthecat.fundamentalchemistry.shared.tileentity.LaserRelayFetch;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileMolecularStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileSimpleMachine;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockMolecularStorage extends Block implements ITileEntityProvider {

	public BlockMolecularStorage() {
	  super(Material.rock);
	  this.setHardness(1.5f);
	  this.setUnlocalizedName("molecular_storage");
	  this.setTickRandomly(true);
	  this.setCreativeTab(FundamentalChemistry.tab);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMolecularStorage();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos,
			IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ) 
	{
		TileChemicalStorage ent = (TileChemicalStorage)world.getTileEntity(pos);
		
		if(!world.isRemote){
			if(player.isSneaking()){ //storage
				player.addChatMessage(new ChatComponentText(ent.storage.toString()));
	    	player.openGui(FundamentalChemistry.instance, ForgeGuiHandler.CHEMICAL_STORAGE, world, pos.getX(), pos.getY(), pos.getZ());
			}
			else //machine
		    player.openGui(FundamentalChemistry.instance, ForgeGuiHandler.SIMPLE_MACHINE, world, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return super.onBlockActivated(world, pos, state, player, side, hitX, hitY,
				hitZ);
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
