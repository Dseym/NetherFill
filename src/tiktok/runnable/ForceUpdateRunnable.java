package tiktok.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import tiktok.Main;

public class ForceUpdateRunnable extends BukkitRunnable {

	@Override
	public void run() {
		for(Chunk chunk: Bukkit.getWorld("world").getLoadedChunks())
			for(int x = 0; x < 16; x++)
				for(int y = 30; y < 180; y++)
					for(int z = 0; z < 16; z++) {
						Block block = chunk.getBlock(x, y, z);
						if(Main.NETHER_BLOCKS.contains(block.getType())) {
							Material mat = block.getType();
							block.setType(Material.BARRIER);
							block.getState().update();
							block.setType(mat);
						}
					}
	}

}
