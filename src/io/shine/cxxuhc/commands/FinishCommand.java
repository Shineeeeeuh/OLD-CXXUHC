package io.shine.cxxuhc.commands;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.utils.HostGame;
import io.shine.cxxuhc.utils.ItemBuilder;

public class FinishCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
		if(s.isOp()) {
			if(CXXUhc.INSTANCE.editinginv.contains(s.getName())) {
				Player p = (Player) s;
				HostGame.setInventory(p.getInventory().getContents());
				HostGame.setArmor(p.getInventory().getArmorContents());
				p.setGameMode(GameMode.ADVENTURE);
				s.sendMessage("§9CXXUhc §7>> §eVous avez changer l'inventaire avec succée !");
				p.getInventory().clear();
				CXXUhc.INSTANCE.editinginv.remove(p.getName());
				p.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setName("§dConfiguration").build());
				return true;
			}else {
				s.sendMessage("§9CXXUhc §7>> Vous devez être en mode édition de l'inventaire pour executer cette commande !");
				return false;
			}
		}else {
			s.sendMessage("§9CXXUhc §7>> §cVous devez être OP pour executer cette commande !");
			return false;
		}
	}

}
