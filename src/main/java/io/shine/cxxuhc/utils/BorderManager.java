package io.shine.cxxuhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;

public class BorderManager {
	
	public static void moveWorldBorder(){
		WorldBorder wb = Bukkit.getWorld("uhcworld").getWorldBorder();
		wb.setSize(100, 900);
	}

}
