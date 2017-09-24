package imagicthecat.fundamentalchemistry.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import imagicthecat.fundamentalchemistry.FundamentalChemistry;
import imagicthecat.fundamentalchemistry.client.renderer.TileLaserRelayRenderer;
import imagicthecat.fundamentalchemistry.shared.ForgeRegisterHandler;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileLaserRelay;

public class RegisterHandler extends ForgeRegisterHandler{
	@Override
	public void register()
	{
		super.register();
		

  	//tile entity renderers
    ClientRegistry.bindTileEntitySpecialRenderer(TileLaserRelay.class, new TileLaserRelayRenderer());
	}
}
