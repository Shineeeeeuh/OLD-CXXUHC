package io.shine.cxxuhc.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.utils.BorderManager;
import io.shine.cxxuhc.utils.HostGame;
import io.shine.cxxuhc.utils.ScoreboardSign;

public class GameEventTask {

	private static BukkitTask eventtimer;
	private static int time;

	public static void run(Plugin pl) {
		Bukkit.broadcastMessage("§9CXXUhc §7>> §eVous êtes invincible pendant §61 minute §e!");
		time = 0;
		eventtimer = new BukkitRunnable() {

			@Override
			public void run() {
				time++;
				if(time == HostGame.getPVPEventTime()) {
					Bukkit.broadcastMessage("§9CXXUhc §7>> §eLe §6PVP §ea été §6activer §e!");
					for(ScoreboardSign sb : CXXUhc.INSTANCE.scoreboards.values()) {
						sb.setLine(5, "§cPVP: §aActiver");
					}
					CXXUhc.INSTANCE.setPvp(true);
				}
				if(time == HostGame.getWallEventTime()) {
					Bukkit.broadcastMessage("§9CXXUhc §7>> §eAttention ! §6§lLa map se rétrécie §e!");
					for(ScoreboardSign sb : CXXUhc.INSTANCE.scoreboards.values()) {
						sb.setLine(6, "§cBordure: §aActiver");
					}
					BorderManager.moveWorldBorder(50, 50, 15, 600);
					this.cancel();
				}
				if(time == HostGame.getDamageEventTime()) {
					HostGame.setNoDamage(false);
					Bukkit.broadcastMessage("§9CXXUhc §7>> §eVous êtes maintenant §6§lvulnérable §e!");
					for(ScoreboardSign sb : CXXUhc.INSTANCE.scoreboards.values()) {
						sb.setLine(4, "§cInvicibilité: §cDésactiver");
					}
				}
				for(ScoreboardSign sb : CXXUhc.INSTANCE.scoreboards.values()) {
					if(time < HostGame.getWallEventTime()) {
						sb.setLine(6, "§cBordure: §f"+(HostGame.getWallEventTime()-time)+" minute(s)");
					}
					if(time < HostGame.getPVPEventTime()) {
						sb.setLine(5, "§cPVP: §f"+(HostGame.getPVPEventTime()-time)+" minute(s)");
					}
				}


			}
		}.runTaskTimer(pl, 1200L, 1200L);
	}



}
