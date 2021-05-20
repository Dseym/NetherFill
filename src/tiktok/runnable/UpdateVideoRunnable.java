package tiktok.runnable;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import ru.dseymo.tiktokapi.api.TikTok;
import ru.dseymo.tiktokapi.api.builders.TikTokBuilder;
import ru.dseymo.tiktokapi.api.entities.Video;
import ru.dseymo.utils.Chat;
import tiktok.Main;

public class UpdateVideoRunnable extends BukkitRunnable {
	
	private TikTok tiktok = new TikTokBuilder().build();
	private Video video;
	
	public void changeVideo(String url) throws Exception {
		video = tiktok.getVideo(url.split("/@")[1].split("/video/")[0], url.split("/video/")[1]);
	}
	
	@Override
	public void run() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(video == null)
					return;
				Main main = Main.getInstance();
				
				long lastActions = video.getCommentCount() + video.getShareCount();
				video.update();
				main.countActions += (video.getCommentCount() + video.getShareCount()) - lastActions;
				if(main.countActions < 0)
					main.countActions = 0;
				
				Chat.NO_PREFIX.sendAll("&6» &7Video stats updated. Actions: " + main.countActions);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						main.replaceBlock(Bukkit.getWorld("world").getSpawnLocation().getBlock().getRelative(BlockFace.DOWN));
						main.forceUpdate();
					}
					
				}.runTask(main);
			}
			
		}).start();
	}

}
