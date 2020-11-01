package io.shine.cxxuhc.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.scheduler.BukkitTask;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.enums.GameState;

public class WorldGeneration {
	
	private static int cx;
	private static int cy;
	
	public static void generateMap() {
		String worldname = "uhcworld";
		World w = Bukkit.createWorld(new WorldCreator(worldname));
		w.setGameRuleValue("naturalRegeneration", "false");
		World nw = Bukkit.createWorld(new WorldCreator(worldname+"_nether").environment(Environment.NETHER));
		nw.setGameRuleValue("naturalRegeneration", "false");
		int cr = (100 / 16) + 4;
		cx = -1 * cr;
		cy = -1 * cr;
		CompletableFuture<BukkitTask> t = new CompletableFuture<>();
		t.complete(Bukkit.getScheduler().runTaskTimer(CXXUhc.INSTANCE, () -> {
			for (int i = 0; i < 25; ++i) {
				w.loadChunk(cx,cy);
				if (cx == cr && cy == cr) {
					try {
						HostGame.setState(GameState.WAITING);
						setSpwanPlat("uhcworld");
						WorldBorder wb = w.getWorldBorder();
						wb.setCenter(0, 0);
						wb.setSize(3000);
						wb.setDamageAmount(2.0);
						wb.setDamageBuffer(5.0);
						wb.setWarningDistance(20);
						WorldBorder nb = nw.getWorldBorder();
						nb.setCenter(0, 0);
						nb.setSize(3000);
						nb.setDamageAmount(2.0);
						nb.setDamageBuffer(5.0);
						nb.setWarningDistance(20);
						t.get().cancel();
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				} else if (cx == cr) {
					cx = -1 * cr;
					cy++;
				} else {
					cx++;
				}
			}
		}, 0, 1));
	}
	
	@SuppressWarnings("deprecation")
	private static void setSpwanPlat(String worldname) {
		Location la = new Location(Bukkit.getWorld(worldname), 0-10, 236-2, 0-10);
		Location lb = new Location(Bukkit.getWorld(worldname), 0+10, 236+2, 0+10);
		List<Integer> s = new ArrayList<Integer>(14);
		for (int i=0 ; i<15 ; i++) {
			if (i!=12) s.add(i);
		}
		
		for (int i=(int) la.getX(); i<=lb.getX() ; i++) {
			for (int j=(int) la.getZ() ; j<=lb.getZ() ; j++) {
				for (int k=(int) la.getY() ; k<=lb.getY() ; k++) {
					if (k==la.getY() || k==lb.getY()) {
						Bukkit.getWorld(worldname).getBlockAt(i, k, j).setType(Material.STAINED_GLASS);
						int r = new Random().nextInt(14);
						r = s.get(r);
						if (r!=0) Bukkit.getWorld(worldname).getBlockAt(i, (int) la.getY(), j).setData((byte) r);
					} else if (j==la.getZ() || j==lb.getZ() || i==la.getX() || i ==lb.getX()) {
						Bukkit.getWorld(worldname).getBlockAt(i, k, j).setType(Material.STAINED_GLASS_PANE);
						int r = new Random().nextInt(14);
						r = s.get(r);
						if (r!=0) Bukkit.getWorld(worldname).getBlockAt(i, (int) la.getY(), j).setData((byte) r);
					}
				}
				
			}
		}
	}
	
	private static boolean deleteWorld(File path) {
        if (path.exists()) {
            final File[] files = path.listFiles();
            for (int i = 0; i < files.length; ++i) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return path.delete();
    }

}
