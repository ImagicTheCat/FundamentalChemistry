package imagicthecat.fundamentalchemistry.shared.block;

import java.util.Random;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.shared.Molecule;
import imagicthecat.fundamentalchemistry.shared.properties.PlayerProperties;
import imagicthecat.fundamentalchemistry.shared.tileentity.LaserRelayFetch;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileChemicalStorage;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;
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
	  this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		TileChemicalStorage ent = new TileChemicalStorage();
		return ent;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos,
			IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ) 
	{
		TileChemicalStorage ent = (TileChemicalStorage)world.getTileEntity(pos);
		
		if(!world.isRemote){
			if(player.isSneaking())
				player.addChatMessage(new ChatComponentText(ent.storage.toString()));
			else{
				ent.storage.molecules.put(Molecule.fromNotation("H2O"),100);
			}
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
		
		// take all atoms 
		TileChemicalStorage ent = (TileChemicalStorage)world.getTileEntity(pos);
		TileLaserRelay relay = ent.getAttachedRelay();
		if(relay != null)
			relay.fetch(ent.storage, LaserRelayFetch.MOLECULES);
	}
}