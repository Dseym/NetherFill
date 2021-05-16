package tiktok.runnable;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import tiktok.Main;

public class ReplaceBlocksRunnable extends BukkitRunnable {

	@Override
	public void run() {
		Main.replaceBlock(Bukkit.getWorld("world").getSpawnLocation().getBlock().getRelative(BlockFace.DOWN));
		
		if(Main.blockQueue.size() == 0)
			return;
		
		Entry<Block, Material> block = Main.blockQueue.entrySet().iterator().next();
		
		block.getKey().setType(block.getValue());
		Main.blockQueue.remove(block.getKey());
	}

}
