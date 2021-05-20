package tiktok.runnable;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import tiktok.Main;

public class ReplaceBlocksRunnable extends BukkitRunnable {

	@Override
	public void run() {
		Main main = Main.getInstance();
		
		int cycle = 0;
		for(Entry<Block, Material> block: new HashMap<>(main.blockQueue).entrySet()) {
			if(++cycle > 5)
				break;
			
			block.getKey().setType(block.getValue());
			
			main.blockQueue.remove(block.getKey());
		}
	}

}
