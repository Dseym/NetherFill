package ru.dseymo.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Executor implements CommandExecutor {
	
	protected boolean onlyPlayer;
	protected String permission;
	protected int minArgs;
	
	public Executor(String command, boolean onlyPlayer, String permission, int minArgs) {
		
		this.onlyPlayer = onlyPlayer;
		this.permission = permission;
		this.minArgs = minArgs;
		
		Bukkit.getPluginCommand(command).setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(onlyPlayer && !(sender instanceof Player)) {
			Chat.FAIL.send(sender, "Only player");
			return false;
		} else if(permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
			Chat.NO_PERM.send(sender);
			return false;
		} else if(args.length < minArgs && notEnoughArgs(sender, args))
			return false;
		
		return execute(sender, args);
	}
	
	public boolean notEnoughArgs(CommandSender sender, String[] args) {
		Chat.FAIL.send(sender, "Not enough arguments");
		return true;
	}
	
	public abstract boolean execute(CommandSender sender, String[] args);
	
	public boolean isOnlyPlayer() {
		return onlyPlayer;
	}
	
	public String getPerm() {
		return permission;
	}
	
	public int getMinArgs() {
		return minArgs;
	}
	
}
