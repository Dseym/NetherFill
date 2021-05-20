package tiktok.cmd;

import org.bukkit.command.CommandSender;

import ru.dseymo.utils.Chat;
import ru.dseymo.utils.Executor;
import tiktok.Main;

public class SetVideoExecutor extends Executor {

	public SetVideoExecutor() {
		super("setvideo", false, "netherfill.admin", 1);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		String url = args[0];
		if(!(url.contains("https://www.tiktok.com/@") && url.contains("/video/"))) {
			Chat.FAIL.send(sender, "Enter the correct link.", "Example:&l https://www.tiktok.com/@anth.n.y2/video/6962563252064652550");
			return true;
		}
		
		try {
			Main.getInstance().updateVideo.changeVideo(url);
			Chat.SUCCESS.send(sender, "Success");
		} catch (Exception e) {
			e.printStackTrace();
			Chat.FAIL.send(sender, "Enter the correct link.", "Example:&l https://www.tiktok.com/@anth.n.y2/video/6962563252064652550");
		}
		
		return true;
	}

}
