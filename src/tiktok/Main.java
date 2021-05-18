package tiktok;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import tiktok.cmd.SetVideoExecutor;
import tiktok.runnable.ReplaceBlocksRunnable;
import tiktok.runnable.UpdateVideoRunnable;

public class Main extends JavaPlugin {
	@Getter
	private static Main instance;
	public final static List<Material> NETHER_BLOCKS =
			Arrays.asList(Material.NETHERRACK, Material.CRIMSON_STEM, Material.NETHER_WART_BLOCK, Material.LAVA,
						  Material.OBSIDIAN);
	public String url = "https://www.tiktok.com/@anth.n.y2/video/6962563252064652550";
	
	
	public HashMap<Block, Material> blockQueue = new HashMap<>();
	public long countActions = 0;
	
	@Override
	public void onEnable() {
		new UpdateVideoRunnable().runTaskTimer(this, 200, 200);
		new ReplaceBlocksRunnable().runTaskTimer(this, 200, 1);
		
		new SetVideoExecutor();
		
		instance = this;
		getLogger().info("Enabled!");
	}
	
	public void forceUpdate() {
		List<Chunk> chunks = Arrays.asList(Bukkit.getWorld("world").getLoadedChunks());
		Collections.shuffle(chunks);
		for(Chunk chunk: chunks) {
			for(int x = 0; x < 16; x++)
				for(int y = 21; y < 151; y++)
					for(int z = 0; z < 16; z++) {
						Block block = chunk.getBlock(x, y, z);
						if(Main.NETHER_BLOCKS.contains(block.getType()) && countActions > 0) {
							replaceBlock(block.getRelative(BlockFace.UP));
							replaceBlock(block.getRelative(BlockFace.DOWN));
							replaceBlock(block.getRelative(BlockFace.SOUTH));
							replaceBlock(block.getRelative(BlockFace.EAST));
							replaceBlock(block.getRelative(BlockFace.NORTH));
							replaceBlock(block.getRelative(BlockFace.WEST));
						}
					}
			
			chunk.unload();
		}
	}
	
	public void replaceBlock(Block block) {
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
					
				case WATER: case OBSIDIAN:
					blockQueue.put(block, Material.LAVA);
					return;
	
				default:
					countActions++;
					break;
			}
		}
	}
	
}
