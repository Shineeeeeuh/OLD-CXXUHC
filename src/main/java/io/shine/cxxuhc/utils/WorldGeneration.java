package io.shine.cxxuhc.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.scheduler.BukkitTask;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.enums.GameState;
import net.minecraft.server.v1_8_R3.BiomeBase;

public class WorldGeneration {

  private static int cx;
  private static int cy;

  public static HashSet<Chunk> generateMap() {

    Field biomesField = null;
    try {
      biomesField = BiomeBase.class.getDeclaredField("biomes");
      biomesField.setAccessible(true);
      if (biomesField.get(null) instanceof BiomeBase[]) {
        BiomeBase[] biomes = (BiomeBase[]) biomesField.get(null);
        biomes[BiomeBase.DEEP_OCEAN.id] = BiomeBase.PLAINS;
        biomes[BiomeBase.OCEAN.id] = BiomeBase.FOREST;
        biomes[BiomeBase.SWAMPLAND.id] = BiomeBase.SWAMPLAND;
        biomes[BiomeBase.DESERT.id] = BiomeBase.DESERT;
        biomes[BiomeBase.DESERT_HILLS.id] = BiomeBase.DESERT_HILLS;
        biomes[BiomeBase.PLAINS.id] = BiomeBase.PLAINS;
        biomes[BiomeBase.JUNGLE.id] = BiomeBase.JUNGLE;
        biomes[BiomeBase.JUNGLE_EDGE.id] = BiomeBase.JUNGLE_EDGE;
        biomes[BiomeBase.JUNGLE_HILLS.id] = BiomeBase.JUNGLE_HILLS;
        biomes[BiomeBase.EXTREME_HILLS.id] = BiomeBase.EXTREME_HILLS;
        biomes[BiomeBase.EXTREME_HILLS_PLUS.id] = BiomeBase.EXTREME_HILLS_PLUS;
        biomes[BiomeBase.MESA.id] = BiomeBase.MESA;
        biomes[BiomeBase.MESA_PLATEAU.id] = BiomeBase.MESA_PLATEAU;
        biomes[BiomeBase.MESA_PLATEAU_F.id] = BiomeBase.MESA_PLATEAU_F;
        biomes[BiomeBase.MUSHROOM_ISLAND.id] = BiomeBase.MUSHROOM_ISLAND;
        biomes[BiomeBase.MUSHROOM_SHORE.id] = BiomeBase.MUSHROOM_SHORE;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    String worldname = "uhcworld";
    World world = Bukkit.createWorld(new WorldCreator(worldname));
    world.setGameRuleValue("naturalRegeneration", "false");
    World nether = Bukkit.createWorld(new WorldCreator(worldname + "_nether").environment(Environment.NETHER));
    nether.setGameRuleValue("naturalRegeneration", "false");

    int worldSize = 512;

    HashSet<Chunk> chunkSet = new HashSet<>();

    for (int x = 0; x < worldSize / 2; x += 16) {
      for (int z = 0; z < worldSize / 2; z += 16) {
        chunkSet.add(world.getChunkAt(x, z));
        world.loadChunk(x, z);
        chunkSet.add(world.getChunkAt(-x, -z));
        world.loadChunk(-x, -z);
        chunkSet.add(world.getChunkAt(-x, z));
        world.loadChunk(-x, z);
        chunkSet.add(world.getChunkAt(x, -z));
        world.loadChunk(x, -z);
      }
    }

    HostGame.setState(GameState.WAITING);
    setSpwanPlat("uhcworld");

    WorldBorder worldborder = world.getWorldBorder();
    worldborder.setCenter(0, 0);
    worldborder.setSize(1000);
    worldborder.setDamageAmount(0.5);
    worldborder.setDamageBuffer(5.0);
    worldborder.setWarningDistance(20);

    WorldBorder netherworldborder = nether.getWorldBorder();
    netherworldborder.setCenter(0, 0);
    netherworldborder.setSize(1000);
    netherworldborder.setDamageAmount(0.5);
    netherworldborder.setDamageBuffer(5.0);
    netherworldborder.setWarningDistance(20);

    return chunkSet;
  }

  @SuppressWarnings("deprecation")
  private static void setSpwanPlat(String worldname) {
    Location la = new Location(Bukkit.getWorld(worldname), 0 - 10, 236 - 2, 0 - 10);
    Location lb = new Location(Bukkit.getWorld(worldname), 0 + 10, 236 + 2, 0 + 10);
    List<Integer> s = new ArrayList<Integer>(14);
    for (int i = 0; i < 15; i++) {
      if (i != 12)
        s.add(i);
    }

    for (int i = (int) la.getX(); i <= lb.getX(); i++) {
      for (int j = (int) la.getZ(); j <= lb.getZ(); j++) {
        for (int k = (int) la.getY(); k <= lb.getY(); k++) {
          if (k == la.getY() || k == lb.getY()) {
            Bukkit.getWorld(worldname).getBlockAt(i, k, j).setType(Material.STAINED_GLASS);
            int r = new Random().nextInt(14);
            r = s.get(r);
            if (r != 0)
              Bukkit.getWorld(worldname).getBlockAt(i, (int) la.getY(), j).setData((byte) r);
          } else if (j == la.getZ() || j == lb.getZ() || i == la.getX() || i == lb.getX()) {
            Bukkit.getWorld(worldname).getBlockAt(i, k, j).setType(Material.STAINED_GLASS_PANE);
            int r = new Random().nextInt(14);
            r = s.get(r);
            if (r != 0)
              Bukkit.getWorld(worldname).getBlockAt(i, (int) la.getY(), j).setData((byte) r);
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
        } else {
          files[i].delete();
        }
      }
    }
    return path.delete();
  }

}
