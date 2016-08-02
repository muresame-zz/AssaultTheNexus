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
			if(!p.hasPermission("Anni.ChangeTeam"))
			{
				p.sendMessage(Lang.NO_PERMISSION.toString().replaceAll("%COMMAND%", "/"+changeteam.getName()));
				return false;
			}
			if(args.length != 2)
			{
				p.sendMessage(Lang.CHANGETEAM_USAGE.toString());
				return false;
			}
			Player t = Bukkit.getPlayer(args[0]);
			if(t == null)
			{
				p.sendMessage(Lang.INVALID_PLAYER.toString().replaceAll("%PLAYER%", args[0]));
				return false;
			}
			AnniPlayer aP = AnniPlayer.getPlayer(t.getUniqueId());
			AnniTeam team;
			if(args[1].equalsIgnoreCase("leave"))
			{
				if(aP.getTeam() == null)
				{
					p.sendMessage(Lang.NO_TEAM.toString().replaceAll("%PLAYER%", t.getName()));
					return false;
				}
				team = aP.getTeam();
				team.leaveTeam(aP);
				Game.LobbyMap.sendToSpawn(t);
				p.sendMessage(Lang.REMOVED_TEAM.toString().replaceAll("%PLAYER%", t.getName()).replaceAll("%TEAM%", team.getColoredName()));
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
			p.sendMessage(Lang.CHANGED_TEAM.toString().replaceAll("%PLAYER%", t.getName()).replaceAll("%TEAM%", team.getColoredName()));
			
		}
		return false;
	}

}
