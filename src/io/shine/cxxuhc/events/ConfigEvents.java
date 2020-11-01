package io.shine.cxxuhc.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.enums.GameState;
import io.shine.cxxuhc.utils.HostGame;

public class ConfigEvents implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(CXXUhc.INSTANCE.editingheal.contains(p.getName())) {
			e.setCancelled(true);
			if(isNumeric(e.getMessage())) {
				HostGame.setHeal(Double.parseDouble(e.getMessage()));
				CXXUhc.INSTANCE.editingheal.remove(p.getName());
				p.sendMessage("§9CXXUhc §7>> §aLa vie à été changer à §6"+e.getMessage()+" §avie(s) avec succées !");
				return;
			}else {
				p.sendMessage("§9CXXUhc §7>> §c"+e.getMessage()+" n'es pas un nombre !");
				return;
			}
		}
		if(CXXUhc.INSTANCE.editingtime.contains(p.getName()+" pvpedit")) {
			e.setCancelled(true);
			if(isNumeric(e.getMessage())) {
				HostGame.setPvpEventTime(Integer.parseInt(e.getMessage()));;
				CXXUhc.INSTANCE.editingtime.remove(p.getName()+" pvpedit");
				p.sendMessage("§9CXXUhc §7>> §aLe temps d'activation de l'évènement PvP à été changer à §6"+e.getMessage()+" §aminute(s) avec succées !");
				return;
			}else {
				p.sendMessage("§9CXXUhc §7>> §c"+e.getMessage()+" n'es pas un nombre !");
				return;
			}
		}
		if(CXXUhc.INSTANCE.editingtime.contains(p.getName()+" damageedit")) {
			e.setCancelled(true);
			if(isNumeric(e.getMessage())) {
				HostGame.setDamageEventTime(Integer.parseInt(e.getMessage()));;
				CXXUhc.INSTANCE.editingtime.remove(p.getName()+" damageedit");
				p.sendMessage("§9CXXUhc §7>> §aLe temps d'activation de l'évènement Vulnérabilité à été changer à §6"+e.getMessage()+" §aminute(s) avec succées !");
				return;
			}else {
				p.sendMessage("§9CXXUhc §7>> §c"+e.getMessage()+" n'es pas un nombre !");
				return;
			}
		}
		if(CXXUhc.INSTANCE.editingtime.contains(p.getName()+" borderedit")) {
			e.setCancelled(true);
			if(isNumeric(e.getMessage())) {
				HostGame.setWallEventTime(Integer.parseInt(e.getMessage()));;
				CXXUhc.INSTANCE.editingtime.remove(p.getName()+" borderedit");
				p.sendMessage("§9CXXUhc §7>> §aLe temps d'activation de l'évènement Réduction de la Bordure à été changer à §6"+e.getMessage()+" §aminute(s) avec succées !");
				return;
			}else {
				p.sendMessage("§9CXXUhc §7>> §c"+e.getMessage()+" n'es pas un nombre !");
				return;
			}
		}
		if(HostGame.getState() == GameState.START) {
			if(!HostGame.getPlayers().contains(e.getPlayer().getName())){
				e.setCancelled(true);
				p.sendMessage("§9CXXUhc §7>> Comme tu es spéctateur, tu ne peux parler !");
				return;
			}
		}
		if(p.isOp()) {
			e.setFormat("§c"+p.getName()+" §7>> §c"+e.getMessage().replaceAll("%", "%%"));
			return;
		}else {
			e.setFormat("§f"+p.getName()+" §7>> §f"+e.getMessage().replaceAll("%", "%%"));
			return;
		}
	}
	
	public boolean isNumeric(String s) {
	    try {
	        double d = Double.parseDouble(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	

}
