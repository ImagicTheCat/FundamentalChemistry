package imagicthecat.fundamentalchemistry.shared;

import imagicthecat.fundamentalchemistry.shared.properties.PlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
	public ForgeEventHandler()
	{

	}
	
	@SubscribeEvent
	public void entityConstruct(EntityEvent.EntityConstructing e){
		//add custom properties to players
	  if(e.entity instanceof EntityPlayer){
	    if(e.entity.getExtendedProperties(PlayerProperties.ID) == null){
	      e.entity.registerExtendedProperties(PlayerProperties.ID, new PlayerProperties());
	    }
	  }
	}
}
