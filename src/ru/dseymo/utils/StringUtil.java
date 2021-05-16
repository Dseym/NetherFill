package ru.dseymo.utils;

import org.bukkit.ChatColor;

public class StringUtil {
	
	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
}
