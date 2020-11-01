package io.shine.cxxuhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BugCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
		s.sendMessage("§9CXXUHC §7>> §cEn cas de §4BUGS/GLITCH ETC... §cTu peux les reports sous le lien suivant: §c§lshorturl.at/muBW0");
		return true;
	}

}
