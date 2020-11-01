package io.shine.cxxuhc.guis;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.enums.GameState;
import io.shine.cxxuhc.task.TimerTask;
import io.shine.cxxuhc.utils.GuiScreen;
import io.shine.cxxuhc.utils.Guis;
import io.shine.cxxuhc.utils.HostGame;
import io.shine.cxxuhc.utils.ItemBuilder;

public class ConfigGui extends GuiScreen{

	public ConfigGui() {
		super("§eConfiguration", 6);
	}

	@Override
	public void draw(Player p, Inventory inv) {
		for(int i = 0; i < 6*9; i++) {
			inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 6).setName("§d").build());
		}
		inv.setItem(13, new ItemBuilder(Material.WATCH).setName("§2Configurer les events").build());
		inv.setItem(19, new ItemBuilder(Material.BOOK).setName("§c§lSOON....").build());
		inv.setItem(21, new ItemBuilder(Material.SKULL_ITEM, (short) 3).setName("§c§lSOON....").build());
		inv.setItem(23, new ItemBuilder(Material.REDSTONE).setName("§cChanger la vie de départ").addLore("§fActuellement: §d"+HostGame.getHeal()+" vie(s)").addLore("§f(2 vies = 1 coeur)").build());
		inv.setItem(25, new ItemBuilder(Material.BARRIER).setName("§cRégler les interdictions").build());
		inv.setItem(31, new ItemBuilder(Material.CHEST).setName("§6Changer l'inventaire").build());
		inv.setItem(49, new ItemBuilder(Material.EMERALD).setName("§eStart la game").build());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(InventoryClickEvent e) {
		ItemStack it = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		if(it.getItemMeta().getDisplayName().contains("§cRégler les interdictions")) {
			p.closeInventory();
			Bukkit.getScheduler().scheduleAsyncDelayedTask(JavaPlugin.getPlugin(CXXUhc.class), new BukkitRunnable() {

				@Override
				public void run() {
					Guis.loadGui(p, "disallowconfig");
				}
			},3);
			return;
		}
		if(it.getItemMeta().getDisplayName().contains("§2Configurer les events")) {
			p.closeInventory();
			Bukkit.getScheduler().scheduleAsyncDelayedTask(JavaPlugin.getPlugin(CXXUhc.class), new BukkitRunnable() {

				@Override
				public void run() {
					Guis.loadGui(p, "eventconfig");
				}
			},3);
			return;
		}
		if(it.getItemMeta().getDisplayName().contains("§cChanger la vie de départ")) {
			p.closeInventory();
			p.sendMessage("§9CXXUhc §7>> §eVeuillez écrire la vie de départ dans le chat !");
			CXXUhc.INSTANCE.editingheal.add(p.getName());
			return;
		}
		if(it.getItemMeta().getDisplayName().contains("§6Changer l'inventaire")) {
			p.closeInventory();
			p.getInventory().clear();
			p.setGameMode(GameMode.CREATIVE);
			p.getInventory().setArmorContents(null);
			p.sendMessage("§9CXXUhc §7>> §eVeuillez faire l'inventaire de base et faire §a§l/finish §epour changer l'inventaire de base !");
			CXXUhc.INSTANCE.editinginv.add(p.getName());
			return;
		}
		if(it.getItemMeta().getDisplayName().contains("§eStart la game")) {
			if(HostGame.getState() == GameState.TIMER) {
				p.closeInventory();
				p.sendMessage("§9CXXUHC §7>> Le timer est déjà lancer !");
				return;
			}else {
				p.closeInventory();
				HostGame.setState(GameState.TIMER);
				TimerTask.run(CXXUhc.INSTANCE, 5);
			}
		}
	}


}
