package io.shine.cxxuhc.task;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import io.shine.cxxuhc.enums.GameState;
import io.shine.cxxuhc.utils.HostGame;

public class FinishTask {
	
	private static BukkitTask stoptimer;
	private static int stoptime;
	
	public static void run(Plugin pl) {
		stoptime = 0;
		Bukkit.broadcastMessage("§9CXXUhc §7>> §c§lLe serveur se redémarre dans §42 minutes §c§l!");
		stoptimer =  new BukkitRunnable() {
			@Override
			public void run() {
				stoptime++;
				if(stoptime == 120) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.kickPlayer("§cRegénaration de la map...");
					}
					HostGame.setState(GameState.CREATEMAP);
				}
				if(stoptime == 122) {
					deleteWorld(Bukkit.getWorld("uhcworld").getWorldFolder());
					deleteWorld(Bukkit.getWorld("uhcworld_nether").getWorldFolder());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"restart");
				}
			}
			
		}.runTaskTimer(pl, 0L, 20L);
	}
	
	private static boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}

}
