package io.shine.cxxuhc.utils;

import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;

public class NMSUtils {

    public static void sendChunk(Player p, Chunk c) {
        PacketPlayOutMapChunk pc = new PacketPlayOutMapChunk(((CraftChunk) c).getHandle(), true, 65535);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(pc);
    }

}
