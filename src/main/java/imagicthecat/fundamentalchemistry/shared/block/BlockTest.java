package imagicthecat.fundamentalchemistry.shared.block;


import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class BlockTest extends Block implements ITileEntityProvider{
  public BlockTest()
  {
    super(Material.rock);
    this.setHardness(1.5f);
    this.setUnlocalizedName("test");
    this.setCreativeTab(FundamentalChemistry.tab);
  }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return null;
	}
}
