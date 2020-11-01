package io.shine.cxxuhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SayCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
		if(s.isOp()) {
			if(args.length == 0) {
				s.sendMessage("§9CXXUhc §7>> §cErreur de syntaxe: /say [Texte]");
				return false;
			}else {
				StringBuilder stb = new StringBuilder("");
				for(String ss : args) {
					stb.append(ss+" ");
				}
				Bukkit.broadcastMessage("§f[§e"+s.getName()+"§f] §6"+stb.toString());
				return true;
			}
		}else {
			s.sendMessage("§9CXXUhc §7>> §cVous devez être OP pour executer cette commande !");
			return false;
		}
	}

}
