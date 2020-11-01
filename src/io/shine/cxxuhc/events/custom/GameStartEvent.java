package io.shine.cxxuhc.events.custom;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	private World w;
	
	public GameStartEvent(World w) {
		this.w = w;
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
