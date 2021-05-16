package tiktok;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import tiktok.runnable.ForceUpdateRunnable;
import tiktok.runnable.ReplaceBlocksRunnable;
import tiktok.runnable.UpdateVideoRunnable;

public class Main extends JavaPlugin implements Listener {
	public final static List<Material> NETHER_BLOCKS = Arrays.asList(Material.NETHERRACK, Material.CRIMSON_STEM, Material.NETHER_WART_BLOCK, Material.LAVA);
	
	
	public static HashMap<Block, Material> blockQueue = new HashMap<>();
	public static long countActions = 0;
	
	@Override
	public void onEnable() {
		new UpdateVideoRunnable().runTaskTimer(this, 200, 200);
		new ReplaceBlocksRunnable().runTaskTimer(this, 200, 1);
		new ForceUpdateRunnable().runTaskTimer(this, 100, 200);
		
		Bukkit.getPluginManager().registerEvents(this, this);
		
		getLogger().info("Enabled!");
	}
	
	@EventHandler
	public void tick(BlockPhysicsEvent e) {
		if(NETHER_BLOCKS.contains(e.getSourceBlock().getType()))
			replaceBlock(e.getBlock());
		else if(NETHER_BLOCKS.contains(e.getBlock().getType()))
			replaceBlock(e.getSourceBlock());
	}

	@EventHandler
	public void tick(BlockPlaceEvent e) {
		e.getBlockReplacedState().update();
	}
	
	@EventHandler
	public void tick(BlockBreakEvent e) {
		e.getBlock().getState().update();
	}
	
	public static void replaceBlock(Block block) {
		if(countActions > 0 && !blockQueue.containsKey(block)) {
			countActions--;
			switch(block.getType()) {
				case DIRT: case GRASS_BLOCK: case SAND:
					blockQueue.put(block, Material.NETHERRACK);
					return;
					
				case ACACIA_LOG: case BIRCH_LOG: case JUNGLE_LOG: case DARK_OAK_LOG: case OAK_LOG: case SPRUCE_LOG:
					blockQueue.put(block, Material.CRIMSON_STEM);
					return;
					
				case ACACIA_LEAVES: case BIRCH_LEAVES: case JUNGLE_LEAVES: case DARK_OAK_LEAVES: case OAK_LEAVES: case SPRUCE_LEAVES:
					blockQueue.put(block, Material.NETHER_WART_BLOCK);
					return;
					
				case GRASS: case TALL_GRASS:
					blockQueue.put(block, Material.AIR);
					return;
					
				case WATER:
					blockQueue.put(block, Material.LAVA);
					return;
	
				default:
					countActions++;
					break;
			}
		}
	}
	
}
