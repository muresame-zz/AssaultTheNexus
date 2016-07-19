package com.gmail.lynx7478.anni.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.lynx7478.anni.anniGame.AnniPlayer;
import com.gmail.lynx7478.anni.anniGame.AnniTeam;
import com.gmail.lynx7478.anni.anniGame.Game;

public class ChangeTeamCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command changeteam, String s, String[] args) 
	{
		if(changeteam.getName().equalsIgnoreCase("changeteam"))
		{
			if(!(sender instanceof Player))
			{
				return false;
			}
			Player p = (Player) sender;
			if(args.length != 2)
			{
				p.sendMessage(ChatColor.RED+"Usage: /changeteam <Player> <Team>");
				return false;
			}
			Player t = Bukkit.getPlayer(args[0]);
			if(t == null)
			{
				p.sendMessage(ChatColor.RED+args[0]+" is not a valid player or is not online.");
				return false;
			}
			AnniPlayer aP = AnniPlayer.getPlayer(t.getUniqueId());
			AnniTeam team;
			if(args[1].equalsIgnoreCase("leave"))
			{
				if(aP.getTeam() == null)
				{
					p.sendMessage(ChatColor.RED + t.getName() + " does not have a team.");
					return false;
				}
				team = aP.getTeam();
				team.leaveTeam(aP);
				Game.LobbyMap.sendToSpawn(t);
				p.sendMessage(ChatColor.GREEN+"Successfully removed "+ChatColor.AQUA+t.getName()+ChatColor.GREEN+" from team "+team.getColoredName()+ChatColor.GREEN+"!");
				return true;
			}
			switch(args[1])
			{
			case "red":
				team = AnniTeam.Red;
				break;
			case "yellow":
				team = AnniTeam.Yellow;
				break;
			case "green":
				team = AnniTeam.Green;
				break;
			case "blue":
				team = AnniTeam.Blue;
				break;
			default:
				team = null;
				break;
			}
			if(team == null)
			{
				p.sendMessage(ChatColor.RED+args[1]+" is not a valid team.");
				return false;
			}
			aP.getTeam().leaveTeam(aP);
			team.joinTeam(aP);
			t.setHealth(0.0);
			p.sendMessage(ChatColor.GREEN + "Successfully put " + ChatColor.AQUA + t.getName() + ChatColor.GREEN + " in team " + team.getColoredName() + ChatColor.GREEN+"!");
			
		}
		return false;
	}

}
