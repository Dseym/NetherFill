package tiktok.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import ru.dseymo.utils.Chat;
import tiktok.Main;
import tiktok.tiktok.Video;

public class UpdateVideoRunnable extends BukkitRunnable {
	
	private Video lastVideo;
	private String lastUrl = "";
	
	@Override
	public void run() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Main main = Main.getInstance();
				Video video = new Video(Main.getInstance().url);
				if(!lastUrl.equals(Main.getInstance().url))
					lastVideo = null;
				
				if(lastVideo != null && video != null) {
					main.countActions += (video.comments-lastVideo.comments) + (video.shares-lastVideo.shares);
					if(main.countActions < 0)
						main.countActions = 0;
					
					Chat.NO_PREFIX.sendAll("&6» &7Video stats updated. Now actions - " + main.countActions);
				}
				
				lastVideo = video;
				lastUrl = Main.getInstance().url;
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						main.forceUpdate();
					}
				}.runTask(main);
			}
			
		}).start();
	}

}
