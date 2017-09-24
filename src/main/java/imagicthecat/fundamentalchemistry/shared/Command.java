package imagicthecat.fundamentalchemistry.shared;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
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
	public List<String> getTabCompletionOptions(MinecraftServer server,
			ICommandSender sender, String[] args, BlockPos pos) {
		return new ArrayList<String>();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender,
			String[] args) throws CommandException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canCommandSenderUseCommand(4, this.getCommandName());
	}
}
