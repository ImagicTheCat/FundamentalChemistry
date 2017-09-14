package imagicthecat.fundamentalchemistry.shared;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Command implements ICommand {

	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "fch";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "fch ...";
	}

	@Override
	public List<String> getCommandAliases() {
		return new ArrayList<String>();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return (sender.getCommandSenderEntity() != null 
				&& FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(((EntityPlayerMP)sender.getCommandSenderEntity()).getGameProfile()));
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender,
			String[] args, BlockPos pos) {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
