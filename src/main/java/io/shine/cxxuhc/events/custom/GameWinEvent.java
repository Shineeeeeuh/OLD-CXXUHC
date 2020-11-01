package io.shine.cxxuhc.events.custom;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameWinEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	private World w;
	private Player winner;
	
	public GameWinEvent(World w, Player winner) {
		this.w = w;
		this.winner = winner;
	}
	
	public Player getWinner() {
		return winner;
	}
	
	public World getWorld() {
		return w;
	}
	
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
