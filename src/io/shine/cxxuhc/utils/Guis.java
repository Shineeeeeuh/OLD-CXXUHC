package io.shine.cxxuhc.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Guis {
	private static HashMap<String, GuiScreen> guis = new HashMap<>(); 
	private static HashMap<String, String> playeringui = new HashMap<>();
	
	public static void registerGui(String id, GuiScreen gui) {
		guis.put(id, gui);
	}
	
	public static void loadGui(Player p, String id) {
		GuiScreen gui = getGui(id);
		Inventory inv = Bukkit.createInventory(null, gui.getRow()*9, gui.getName());
		gui.draw(p, inv);
		p.openInventory(inv);
		playeringui.put(p.getName(), id);
	}
	
	public static GuiScreen getGui(String id) {
		return guis.get(id);
	}
	
	public static void closeGui(Player p) {
		playeringui.remove(p.getName());
	}
	
	public static boolean isPlayerInGui(String name) {
		if(!(playeringui.containsKey(name)) || playeringui.get(name) == null) return false;
		return true;
	}
	
	public static GuiScreen getPlayerGui(String name) {
		return getGui(playeringui.get(name));
	}
}