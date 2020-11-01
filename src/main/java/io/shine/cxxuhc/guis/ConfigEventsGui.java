package io.shine.cxxuhc.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.utils.GuiScreen;
import io.shine.cxxuhc.utils.HostGame;
import io.shine.cxxuhc.utils.ItemBuilder;

public class ConfigEventsGui extends GuiScreen{

	public ConfigEventsGui() {
		super("§2Configuration des events", 4);
	}

	public void draw(Player p, Inventory inv) {
		for(int i = 0; i < 4*9; i++) {
			inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 14).setName("§c").build());
		}
		inv.setItem(13, new ItemBuilder(Material.DIAMOND_SWORD).setName("§6Activation du PVP").addLore("§eActuellement: §f"+HostGame.getPVPEventTime()+" minute(s)").addLore("§eClique pour changer").build());
		inv.setItem(21, new ItemBuilder(Material.REDSTONE).setName("§6Vulnerabilité").addLore("§eActuellement: §f"+HostGame.getDamageEventTime()+" minute(s)").addLore("§eClique pour changer").build());
		inv.setItem(23, new ItemBuilder(Material.BARRIER).setName("§cRéduction de la bordure").addLore("§eActuellement: §f"+HostGame.getWallEventTime()+" minute(s)").addLore("§eClique pour changer").build());
	}

	@Override
	public void onClick(InventoryClickEvent e) {
		ItemStack it = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		if(it.getType() == Material.DIAMOND_SWORD) {
			p.closeInventory();
			p.sendMessage("§9CXXUhc §7>> §eVeuillez écrire le temps en minute de l'action de l'évènement PvP dans le chat !");
			CXXUhc.INSTANCE.editingtime.add(p.getName()+" pvpedit");
			return;
		}
		if(it.getType() == Material.REDSTONE) {
			p.closeInventory();
			p.sendMessage("§9CXXUhc §7>> §eVeuillez écrire le temps en minute de l'action de l'évènement Vulnérabilité dans le chat !");
			CXXUhc.INSTANCE.editingtime.add(p.getName()+" damageedit");
			return;
		}
		if(it.getType() == Material.BARRIER) {
			p.closeInventory();
			p.sendMessage("§9CXXUhc §7>> §eVeuillez écrire le temps en minute de l'action de l'évènement Réduction de Bordure dans le chat !");
			CXXUhc.INSTANCE.editingtime.add(p.getName()+" borderedit");
			return;
		}
	}



}
