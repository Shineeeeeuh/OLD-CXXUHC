package io.shine.cxxuhc.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.shine.cxxuhc.utils.GuiScreen;
import io.shine.cxxuhc.utils.Guis;

public class GuiEvents implements Listener {

	@EventHandler
	public void onInventoryInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(p == null) return;
		ItemStack it = e.getCurrentItem();
		if(it == null || it.getType() == Material.AIR) return;
		Inventory inv = e.getInventory();
		if(inv.getType() != InventoryType.CHEST || inv.getName() == null) return;
		if(Guis.isPlayerInGui(p.getName()) == false) return;
		GuiScreen gui = Guis.getPlayerGui(p.getName());
		e.setCancelled(true);
		gui.onClick(e);
		return;
	}

	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if(p == null) return;
		if(Guis.isPlayerInGui(p.getName()) == false) return;
		Guis.closeGui(p);
		return;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(p == null) return;
		ItemStack it = e.getItem();
		if(it == null || it.getType() == Material.AIR) return;
		Action act = e.getAction();
		if(act == null) return;
		if(act == Action.LEFT_CLICK_BLOCK || act == Action.LEFT_CLICK_AIR) return;
		if(it.getItemMeta().getDisplayName() == null) return;
		if(it.getItemMeta().getDisplayName().contains("§dConfiguration") && p.isOp()) {
			Guis.loadGui(p, "config");
			return;
		}else {
			return;
		}
	}

}
