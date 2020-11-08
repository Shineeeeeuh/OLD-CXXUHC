package io.shine.cxxuhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.shine.cxxuhc.CXXUhc;
import io.shine.cxxuhc.task.FinishTask;

public class EndCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
    if (s.isOp()) {
      FinishTask.run(CXXUhc.INSTANCE);
      return true;
    } else {
      s.sendMessage("§9CXXUhc §7>> §cVous devez être OP pour executer cette commande !");
      return false;
    }
  }

}
