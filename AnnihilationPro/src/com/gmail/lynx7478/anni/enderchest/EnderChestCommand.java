package com.gmail.lynx7478.anni.enderchest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.lynx7478.anni.anniGame.AnniPlayer;
import com.gmail.lynx7478.anni.anniGame.Game;
import com.gmail.lynx7478.anni.anniGame.GameVars;

public class EnderChestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command enderchest, String s, String[] args) {
		if(enderchest.getName().equalsIgnoreCase("enderchest")){
			if(!(sender instanceof Player)){
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Only players can execute this command.");
				return false;
			}
			Player p = (Player) sender;
			AnniPlayer a = AnniPlayer.getPlayer(p.getUniqueId());
			if(GameVars.getUseECC()){
				if(p.hasPermission("Anni.EC")){
					if(Game.isGameRunning()){
						if(a.getTeam() != null){
							EnderChest.getChestFor(p).open();
							return true;
						}else{
							p.sendMessage(ChatColor.RED + "You must be in a team to open your ender chest!");
							return false;
						}
					}else{
						p.sendMessage(ChatColor.RED + "Game must be running to open your ender chest!");
						return false;
					}
			}else{
				p.sendMessage(ChatColor.RED + "You do not have permission to open your ender chest via the command!");
			}
			}else
				return false;
		}
		return false;
	}

}
