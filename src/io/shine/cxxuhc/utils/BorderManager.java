package io.shine.cxxuhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitScheduler;

import io.shine.cxxuhc.CXXUhc;

public class BorderManager {
	
	public static void moveWorldBorder(double x, double z, double r, int t){
		BukkitScheduler bs = Bukkit.getScheduler();
		WorldBorder wb = Bukkit.getWorld("uhcworld").getWorldBorder();
		int sp = t*20;
		int st = sp/5;
		double nX = 0;
		double nZ = 0;
		if(x < 0) nX = 0 - nX;
		else nX = x - 0;
		if(z < 0) nZ = 0 - nZ;
		else nZ = z - 0;
		double stepsX = (nX/st);
		double stepsZ = (nZ/st);
		wb.setSize(r, t);
		for(int i = 1; i <= st; i++){
			if(x < 0){
				double n = 0-(i*stepsX);
				bs.runTaskLaterAsynchronously(CXXUhc.INSTANCE, ()->{
					wb.setCenter(n, wb.getCenter().getZ());
				}, i*5);
			}else{
				double n = 0+(i*stepsX);
				bs.runTaskLaterAsynchronously(CXXUhc.INSTANCE, ()->{
					wb.setCenter(n, wb.getCenter().getZ());
				}, i*5);
			}
		}
		for(int i = 1; i <= st; i++){
			if(z < 0){
				double n = 0-(i*stepsZ);
				bs.runTaskLaterAsynchronously(CXXUhc.INSTANCE, ()->{
					wb.setCenter(wb.getCenter().getX(), n);
				}, i*5);
			}else{
				double n = 0+(i*stepsZ);
				bs.runTaskLaterAsynchronously(CXXUhc.INSTANCE, ()->{
					wb.setCenter(wb.getCenter().getX(), n);
				}, i*5);
			}
		}
	}

}
