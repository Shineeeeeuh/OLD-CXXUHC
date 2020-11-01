package io.shine.cxxuhc;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import io.shine.cxxuhc.commands.BugCommand;
import io.shine.cxxuhc.commands.FinishCommand;
import io.shine.cxxuhc.commands.SayCommand;
import io.shine.cxxuhc.events.ConfigEvents;
import io.shine.cxxuhc.events.GameEvents;
import io.shine.cxxuhc.events.GuiEvents;
import io.shine.cxxuhc.events.LobbyEvents;
import io.shine.cxxuhc.guis.ConfigDisallowGui;
import io.shine.cxxuhc.guis.ConfigEventsGui;
import io.shine.cxxuhc.guis.ConfigGui;
import io.shine.cxxuhc.utils.Guis;
import io.shine.cxxuhc.utils.HostGame;
import io.shine.cxxuhc.utils.ScoreboardSign;
import io.shine.cxxuhc.utils.WorldGeneration;

public class CXXUhc extends JavaPlugin{
	
	public static CXXUhc INSTANCE;
	public HashMap<String, ScoreboardSign> scoreboards;
	public ArrayList<String> editingheal,editinginv,editingtime;
	public ArrayList<Location> lbtp;
	public HashMap<String, Location> spawns;
	public static ScoreboardManager sbm;
	public static Scoreboard sb;
	public static Objective lifesb;
	public boolean pvp;
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		HostGame.init();
		WorldGeneration.generateMap();
		registerEvents();
		initStockage();
		registerGuis();
		registerCommands();
	}
	
	public void setPvp(boolean pvp) {
		this.pvp = pvp;
	}
	
	public void initStockage() {
		scoreboards = new HashMap<>();
		editingheal = new ArrayList<>();
		editinginv = new ArrayList<>();
		lbtp = new ArrayList<>();
		spawns = new HashMap<>();
		editingtime = new ArrayList<>();
		pvp = false;
		sbm = getServer().getScoreboardManager();
		sb = sbm.getNewScoreboard();
		lifesb = sb.registerNewObjective("health", "health");
		lifesb.setDisplaySlot(DisplaySlot.PLAYER_LIST);
	}
	
	public void registerCommands() {
		getCommand("finish").setExecutor(new FinishCommand());
		getCommand("say").setExecutor(new SayCommand());
		getCommand("bug").setExecutor(new BugCommand());
	}
	
	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new LobbyEvents(), this);
		getServer().getPluginManager().registerEvents(new GuiEvents(), this);
		getServer().getPluginManager().registerEvents(new ConfigEvents(), this);
		getServer().getPluginManager().registerEvents(new GameEvents(), this);
	}
	
	public void registerGuis() {
		Guis.registerGui("config", new ConfigGui());
		Guis.registerGui("disallowconfig", new ConfigDisallowGui());
		Guis.registerGui("eventconfig", new ConfigEventsGui());
	}

}
