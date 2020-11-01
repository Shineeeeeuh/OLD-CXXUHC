package io.shine.cxxuhc.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiScreen {
	
	private int row;
	private String name;
	
	public GuiScreen(String name, int row) {
		this.row = row;
		this.name = name;
	}
	
	public void draw(Player p, Inventory inv) {
		
	}
	
	public void onClick(InventoryClickEvent e) {
		
	}
	
	public int getRow() {
		return row;
	}
	
	public String getName() {
		return name;
	}
}
