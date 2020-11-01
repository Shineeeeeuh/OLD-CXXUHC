package io.shine.cxxuhc.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.shine.cxxuhc.enums.GameState;

public class HostGame {
	
	private static ItemStack[] inv,armor;
	private static boolean allowroad, allowlava, allownotchapple, nodamage, pvp;
	private static double heal;
	private static GameState state;
	private static ArrayList<String> players;
	private static HashMap<String, Integer> ks;
	private static int pvpevent, wallevent, damageevent; 
	
	public static void init() {
		ks = new HashMap<>();
		inv = new ItemStack[0];
		armor = new ItemStack[0];
		allowroad = true;
		allowlava = true;
		allownotchapple = true;
		heal = 20D;
		state = GameState.CREATEMAP;
		players = new ArrayList<>();
		nodamage = true;
		pvpevent = 10;
		wallevent = 60;
		damageevent = 1;
		pvp = false;
	}
	
	public static void setPvP(boolean pvp) {
		HostGame.pvp = pvp;
	}
	
	public static boolean getPvP() {
		return pvp;
	}
	
	public static void setDamageEventTime(int damageevent) {
		HostGame.damageevent = damageevent;
	}
	
	public static void setWallEventTime(int wallevent) {
		HostGame.wallevent = wallevent;
	}
	public static void setPvpEventTime(int pvpevent) {
		HostGame.pvpevent = pvpevent;
	}
	
	public static int getWallEventTime() {
		return wallevent;
	}
	
	public static int getDamageEventTime() {
		return damageevent;
	}
	
	public static int getPVPEventTime() {
		return pvpevent;
	}
	
	public static HashMap<String, Integer> getKSHashMap() {
		return ks;
	}
	
	public static Integer getKS(String name) {
		return ks.get(name);
	}
	
	public static void addKS(String name) {
		if(ks.containsKey(name)) {
			ks.put(name, getKS(name)+1);
			return;
		}else {
			ks.put(name, 1);
		}
	}
	
	public static boolean isNoDamage() {
		return nodamage;
	}
	
	public static void setNoDamage(boolean nodamage) {
		HostGame.nodamage = nodamage;
	}
	
	public static ArrayList<String> getPlayers() {
		return players;
	}
	
	public static void addPlayer(Player p) {
		players.add(p.getName());
	}
	
	public static void removePlayer(Player p) {
		players.remove(p.getName());
	}
	
	public static boolean isNotchAppleAllowed() {
		return allownotchapple;
	}
	public static void setAllowNotchApple(boolean allownotchapple) {
		HostGame.allownotchapple = allownotchapple;
	}
	public static void setHeal(double heal) {
		HostGame.heal = heal;
	}
	public static boolean isLavaAllowed() {
		return allowlava;
	}
	
	public static boolean isRoadAllowed() {
		return allowroad;
	}
	
	public static ItemStack[] getArmor() {
		return armor;
	}
	
	public static ItemStack[] getInventory() {
		return inv;
	}
	
	public static void setInventory(ItemStack[] inv) {
		HostGame.inv = inv;
	}
	
	public static void setArmor(ItemStack[] armor) {
		HostGame.armor = armor;
	}
	
	public static void setAllowLava(boolean allowlava) {
		HostGame.allowlava = allowlava;
	}
	
	public static void setAllowRoad(boolean allowroad) {
		HostGame.allowroad = allowroad;
	}
	
	
	public static double getHeal() {
		return heal;
	}
	
	public static GameState getState() {
		return state;
	}
	
	public static void setState(GameState state) {
		HostGame.state = state;
	}
	
}
