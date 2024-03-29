package io.shine.cxxuhc.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.enums.GameState;
import io.shine.cxxuhc.events.custom.GameStartEvent;
import io.shine.cxxuhc.utils.HostGame;
import io.shine.cxxuhc.utils.NMSUtils;
import io.shine.cxxuhc.utils.TitleSender;

public class TimerTask {

  private static int i;
  private static BukkitTask timer;

  @SuppressWarnings("deprecation")
  public static void run(Plugin pl, int time) {
    Bukkit.broadcastMessage("§9CXXUHC §7>> §fCréation des points de TP.");
    List<Location> r = new ArrayList<Location>();
    for (int i = 0; i < 20; i++) {
      Location l = generateLocation();
      r.add(l);
      CXXUhc.INSTANCE.lbtp.add(l);
    }

    for (Chunk c : CXXUhc.chunkSet) {
      c.load();
    }

    Bukkit.getScheduler().scheduleAsyncDelayedTask(pl, new BukkitRunnable() {

      @Override
      public void run() {

        Bukkit.broadcastMessage("§9CXXUHC §7>> §fTéléportation des joueurs...");
        for (Player p : Bukkit.getOnlinePlayers()) {
          Location l = r.stream().findAny().get();
          p.teleport(new Location(l.getWorld(), l.getX(), l.getY() + 5, l.getZ()));
          p.setScoreboard(CXXUhc.sb);
          CXXUhc.INSTANCE.spawns.put(p.getName(), l);
          r.remove(l);
        }
        i = time + 1;
        runTimer(pl);
      }
    }, 20);

  }

  private static void runTimer(Plugin pl) {
    timer = new BukkitRunnable() {

      @Override
      public void run() {
        i--;
        if (i == 0) {
          HostGame.setState(GameState.START);
          for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§9CXXUHC §7>> §eDémarrage maintenant ! §6Bonne chance :) !");
            TitleSender.sendTitle(p, "§eCommencement !", "GL :)", 20);
            HostGame.addPlayer(p);
            CXXUhc.INSTANCE.spawns.get(p.getName()).getBlock().setType(Material.AIR);
          }
          Bukkit.getPluginManager().callEvent(new GameStartEvent(Bukkit.getWorld("uhcworld")));
          this.cancel();
          return;
        } else {
          for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§9CXXUHC §7>> §eDébut dans §6" + i + " §eseconde(s) !");
          }
        }
      }
    }.runTaskTimer(pl, 0L, 20L);
  }

  private static Location generateLocation() {
    Random r = new Random();
    int x = r.nextInt(1000 / 2);
    int z = r.nextInt(1000 / 2);
    if (r.nextBoolean()) {
      x *= -1;
    }
    if (r.nextBoolean()) {
      z *= -1;
    }
    Location l = new Location(Bukkit.getWorld("uhcworld"), (double) x + 0.5, 200.0, (double) z + 0.5);
    l.getChunk().load(true);
    l.getBlock().setType(Material.STAINED_GLASS);
    return l;
  }

}
