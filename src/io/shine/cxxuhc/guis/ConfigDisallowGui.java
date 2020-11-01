package io.shine.cxxuhc.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.shine.cxxuhc.utils.GuiScreen;
import io.shine.cxxuhc.utils.HostGame;
import io.shine.cxxuhc.utils.ItemBuilder;

public class ConfigDisallowGui extends GuiScreen{
	
	public ConfigDisallowGui() {
		super("§eConfig - Interdiction", 3);
	}
	
	@Override
	public void draw(Player p, Inventory inv) {
		for(int i = 0; i < 3*9; i++) {
			inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 14).setName("§c").build());
		}
		inv.setItem(10, new ItemBuilder(Material.FISHING_ROD).setName("§fRod: "+getStatus(HostGame.isRoadAllowed())).addLore("§eClique pour changer").build());
		inv.setItem(13, new ItemBuilder(Material.LAVA_BUCKET).setName("§fSaut de lave: "+getStatus(HostGame.isLavaAllowed())).addLore("§eClique pour changer").build());
		inv.setItem(16, new ItemBuilder(Material.GOLDEN_APPLE, (short) 1).setName("§fNotch Apple: "+getStatus(HostGame.isNotchAppleAllowed())).addLore("§eClique pour changer").build());
	}
	
	private String getStatus(boolean b) {
		if(b) {
			return "§aActivé";
		}else {
			return "§cDésactivé";
		}
	}
	
	private boolean reverseBoolean(boolean b) {
		if(b) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public void onClick(InventoryClickEvent e) {
		ItemStack it = e.getCurrentItem();
		Inventory inv = e.getInventory();
		if(it.getType() == Material.FISHING_ROD) {
			HostGame.setAllowRoad(reverseBoolean(HostGame.isRoadAllowed()));
			inv.setItem(10, new ItemBuilder(Material.FISHING_ROD).setName("§fRod: "+getStatus(HostGame.isRoadAllowed())).addLore("§eClique pour changer").build());
			return;
		}
		if(it.getType() == Material.LAVA_BUCKET) {
			HostGame.setAllowLava(reverseBoolean(HostGame.isLavaAllowed()));
			inv.setItem(13, new ItemBuilder(Material.LAVA_BUCKET).setName("§fSaut de lave: "+getStatus(HostGame.isLavaAllowed())).addLore("§eClique pour changer").build());
			return;
		}
		if(it.getType() == Material.GOLDEN_APPLE) {
			HostGame.setAllowNotchApple(reverseBoolean(HostGame.isNotchAppleAllowed()));
			inv.setItem(16, new ItemBuilder(Material.GOLDEN_APPLE, (short) 1).setName("§fNotch Apple: "+getStatus(HostGame.isNotchAppleAllowed())).addLore("§eClique pour changer").build());
			return;
		}
	}

}
