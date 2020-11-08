package io.shine.cxxuhc.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.enums.GameState;
import io.shine.cxxuhc.events.custom.GameStartEvent;
import io.shine.cxxuhc.events.custom.GameWinEvent;
import io.shine.cxxuhc.task.FinishTask;
import io.shine.cxxuhc.task.GameEventTask;
import io.shine.cxxuhc.utils.HostGame;
import io.shine.cxxuhc.utils.ScoreboardSign;

public class GameEvents implements Listener {

  @EventHandler
  public void onPortal(PlayerPortalEvent e) {
    Player p = e.getPlayer();

    if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
      e.useTravelAgent(true);
      e.getPortalTravelAgent().setCanCreatePortal(true);
      Location l;
      if (p.getWorld() == Bukkit.getWorld("uhcworld")) {
        l = new Location(Bukkit.getWorld("uhcworld_nether"), e.getFrom().getBlockX() / 8, e.getFrom().getBlockY(),
            e.getFrom().getBlockZ() / 8);
      } else {
        l = new Location(Bukkit.getWorld("uhcworld"), e.getFrom().getBlockX() * 8, e.getFrom().getBlockY(),
            e.getFrom().getBlockZ() * 8);
      }
      e.setTo(e.getPortalTravelAgent().findOrCreate(l));
    }
  }

  @EventHandler
  public void onCraft(CraftItemEvent e) {
    ItemStack r = e.getRecipe().getResult();
    if (r.getType() == Material.GOLDEN_APPLE) {
      if (r.getDurability() == (short) 1) {
        if (HostGame.isNotchAppleAllowed())
          return;
        e.setCancelled(true);
      }
    }
    if (r.getType() == Material.FISHING_ROD) {
      if (HostGame.isLavaAllowed())
        return;
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onPvP(EntityDamageByEntityEvent e) {
    if (HostGame.getPvP() == true)
      return;
    if (e.getDamager() instanceof Player) {
      if (e.getEntity() instanceof Player) {
        e.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent e) {
    if (e.getEntityType() == EntityType.PLAYER) {
      if (HostGame.getState() == GameState.START) {
        e.getEntity().spigot().respawn();
        HostGame.getPlayers().remove(e.getEntity().getName());
        updateSB();
        if (e.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {
          if (e.getEntity().getKiller() != null) {
            String name = e.getEntity().getKiller().getName();
            ScoreboardSign sb = CXXUhc.INSTANCE.scoreboards.get(name);
            if (HostGame.getKSHashMap().containsKey(name)) {
              sb.setLine(9, "§cKill(s): §7" + (HostGame.getKS(name) + 1));
            } else {
              sb.setLine(9, "§cKill(s): §71");
            }
            HostGame.addKS(name);
          }
        }
        if (HostGame.getPlayers().size() == 1) {
          Bukkit.getPluginManager()
              .callEvent(new GameWinEvent(Bukkit.getWorld("uhcworld"), Bukkit.getPlayer(HostGame.getPlayers().get(0))));
        }
      }
    }
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent e) {
    Player p = e.getPlayer();
    p.setGameMode(GameMode.SPECTATOR);
    if (p.getKiller() != null) {
      p.teleport(p.getKiller());
      return;
    } else {
      p.teleport(new Location(Bukkit.getWorld("uhcworld"), 0, 150, 0));
      return;
    }
  }

  @EventHandler
  public void onStart(GameStartEvent e) {
    HostGame.setPvP(false);
    e.getWorld().setTime(0L);
    e.getWorld().setGameRuleValue("naturalRegeneration", "false");
    e.getWorld().setStorm(false);
    e.getWorld().setThundering(false);

    for (Location l : CXXUhc.INSTANCE.lbtp) {
      l.getBlock().setType(Material.AIR);
    }
    for (ScoreboardSign sb : CXXUhc.INSTANCE.scoreboards.values()) {
      sb.destroy();
    }
    for (Player p : Bukkit.getOnlinePlayers()) {
      p.getInventory().setContents(HostGame.getInventory());
      p.getInventory().setArmorContents(HostGame.getArmor());
      p.setSaturation(20F);
      p.setGameMode(GameMode.SURVIVAL);
      p.setHealth(HostGame.getHeal());
      ScoreboardSign sb = new ScoreboardSign(p, "§9CXXUhc");
      sb.create();
      sb.setLine(0, "§7§m------------------");
      sb.setLine(1, "§c");
      sb.setLine(2, "§eStatus: §6En jeu");
      sb.setLine(3, "§7");
      sb.setLine(4, "§cInvicibilité: §aActiver");
      sb.setLine(5, "§cPVP: §f" + HostGame.getPVPEventTime() + "minute(s)");
      sb.setLine(6, "§cBordure: §f" + HostGame.getWallEventTime() + " minute(s)");
      sb.setLine(7, "§a");
      sb.setLine(8, "§cJoueurs en vie: §7" + HostGame.getPlayers().size() + "/20");
      sb.setLine(9, "§cKill(s): §70");
      sb.setLine(10, "§d");
      sb.setLine(11, "§8§7§m------------------");
      CXXUhc.INSTANCE.scoreboards.put(p.getName(), sb);
    }
    GameEventTask.run(CXXUhc.INSTANCE);
  }

  public void updateSB() {
    for (ScoreboardSign sb : CXXUhc.INSTANCE.scoreboards.values()) {
      sb.setLine(8, "§cJoueurs en vie: §7" + HostGame.getPlayers().size() + "/20");
    }
  }

  @EventHandler
  public void onMove(PlayerMoveEvent e) {
    if ((e.getTo().getY() < 190.0d || e.getTo().getX() < 190.0d) && HostGame.getState() == GameState.TIMER) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    if (HostGame.getState() == GameState.START) {
      if (HostGame.getPlayers().contains(p.getName())) {
        HostGame.getPlayers().remove(p.getName());
        for (ItemStack itemStack : p.getInventory().getContents()) {
          if (itemStack == null || itemStack.getType() == Material.AIR)
            continue;
          p.getWorld().dropItemNaturally(p.getLocation(), itemStack);
        }
        for (ItemStack itemStack : p.getInventory().getArmorContents()) {
          if (itemStack == null || itemStack.getType() == Material.AIR)
            continue;
          p.getWorld().dropItemNaturally(p.getLocation(), itemStack);
        }
        updateSB();
        if (HostGame.getPlayers().size() == 1) {
          Bukkit.getPluginManager()
              .callEvent(new GameWinEvent(Bukkit.getWorld("uhcworld"), Bukkit.getPlayer(HostGame.getPlayers().get(0))));
        }
      }
    }
  }

  @EventHandler
  public void onWin(GameWinEvent e) {
    Player p = e.getWinner();
    HostGame.setState(GameState.WIN);
    Bukkit.broadcastMessage("§9CXXUhc §7>> §6" + p.getName() + " §eà gagner cette partie d'UHC !");
    FinishTask.run(CXXUhc.INSTANCE);
    HostGame.setNoDamage(true);
    p.setAllowFlight(true);
    p.setFlying(true);
  }

}
