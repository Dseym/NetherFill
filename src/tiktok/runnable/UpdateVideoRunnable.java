package tiktok.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import ru.dseymo.utils.Chat;
import tiktok.Main;
import tiktok.tiktok.Video;

public class UpdateVideoRunnable extends BukkitRunnable {
	
	private Video lastVideo;
	
	@Override
	public void run() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Video video = new Video("anth.n.y2", "6962563252064652550");
				if(lastVideo != null && video != null) {
					Main.countActions += (video.comments-lastVideo.comments) + (video.shares-lastVideo.shares);
					Chat.NO_PREFIX.sendAll("&6» &7Video stats updated. Now actions - " + Main.countActions);
				}
				
				lastVideo = video;
			}
			
		}).start();
	}

}
