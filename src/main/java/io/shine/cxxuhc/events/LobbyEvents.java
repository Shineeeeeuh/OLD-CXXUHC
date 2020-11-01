package io.shine.cxxuhc.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.enums.GameState;
import io.shine.cxxuhc.utils.HostGame;
import io.shine.cxxuhc.utils.ItemBuilder;
import io.shine.cxxuhc.utils.ScoreboardSign;
import io.shine.cxxuhc.utils.TitleSender;

public class LobbyEvents implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.setAllowFlight(false);
		p.setFlying(false);
		p.setHealth(20D);
		p.setExp(0F);
		p.setLevel(0);
		p.setFoodLevel(20);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setGameMode(GameMode.ADVENTURE);
		p.teleport(new Location(Bukkit.getWorld("uhcworld"), 0, 235, 0));
		e.setJoinMessage("§6"+p.getName()+" §eà rejoint l'UHC !");
		if(p.isOp()) {
			p.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setName("§dConfiguration").build());
		}
		TitleSender.sendTitle(p, "§9CCXUHC", "§eBienvenu(e) :D !", 20);
		for (final Player all : Bukkit.getOnlinePlayers()) {
            p.showPlayer(all);
        }
		for(ScoreboardSign sb : CXXUhc.INSTANCE.scoreboards.values()) {
			sb.setLine(7, "§7Joueurs: §d"+Bukkit.getOnlinePlayers().size()+"/20");
		}
		loadSB(p);
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		if(HostGame.getState() != GameState.START) {
			e.setCancelled(true);
		}
	}

	private void loadSB(Player p) {
		ScoreboardSign sb = new ScoreboardSign(p, "§9CXXUHC");
		sb.create();
		sb.setLine(4, "§7§m------------------");
		sb.setLine(5, "§c");
		sb.setLine(6, "§7Pseudo: §b"+p.getName());
		sb.setLine(7, "§7Joueurs: §d"+Bukkit.getOnlinePlayers().size()+"/20");
		sb.setLine(8, "§d");
		sb.setLine(9, "§8§7§m------------------");
		CXXUhc.INSTANCE.scoreboards.put(p.getName(), sb);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage("§6"+p.getName()+" §eà quitter l'UHC !");
		CXXUhc.INSTANCE.scoreboards.remove(p.getName());
		if(HostGame.getState() != GameState.START) {
			for(ScoreboardSign sb : CXXUhc.INSTANCE.scoreboards.values()) {
				sb.setLine(7, "§7Joueurs: §d"+Bukkit.getOnlinePlayers().size()+"/20");
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(HostGame.getState() != GameState.START) {
			e.setCancelled(true);
		}else {
			if(HostGame.isNoDamage() == false) return;
			if(e.getEntityType() == EntityType.PLAYER) {
				e.setCancelled(true);
			}
		}
	}


	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(HostGame.getState() != GameState.START) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPickUP(PlayerPickupItemEvent e) {
		if(HostGame.getState() != GameState.START && !e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(HostGame.getState() != GameState.START && !e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(HostGame.getState() != GameState.START && !e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if(HostGame.getState() == GameState.CREATEMAP) {
			e.setKickMessage("§cLa map est en cours de regénération !\n§cVeuillez réessayer plus tard !");
			e.setResult(Result.KICK_WHITELIST);
			return;
		}else {
			if(HostGame.getState() == GameState.START) {
				e.setKickMessage("§cLa partie est déjà en cours !\n§cVeuillez réessayer plus tard !");
				e.setResult(Result.KICK_WHITELIST);
				return;
			}
			if(HostGame.getState() == GameState.TIMER) {
				e.setKickMessage("§cLa partie est déjà en cours !\n§cVeuillez réessayer plus tard !");
				e.setResult(Result.KICK_WHITELIST);
				return;
			}
		}
	}

}
