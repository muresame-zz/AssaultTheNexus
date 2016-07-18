package com.gmail.lynx7478.anni.anniGame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.bobacadodl.imgmessage.ImageChar;
import com.bobacadodl.imgmessage.ImageMessage;
import com.gmail.lynx7478.anni.anniMap.GameMap;
import com.gmail.lynx7478.anni.announcementBar.AnnounceBar;
import com.gmail.lynx7478.anni.announcementBar.Announcement;
import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.gmail.lynx7478.anni.main.Lang;
import com.gmail.lynx7478.anni.utils.Loc;
import com.gmail.lynx7478.anni.utils.ShopMenu;
import com.gmail.lynx7478.anni.voting.ScoreboardAPI;

public class StandardPhaseHandler implements Runnable
{
	private static Map<Integer,ImageMessage> images;
	public StandardPhaseHandler()
	{
		images = new HashMap<Integer,ImageMessage>();
		for(int x = 1; x < 6; x++)
		{
			try
			{
				BufferedImage image = ImageIO.read(AnnihilationMain.getInstance().getResource("Images/Phase"+x+".png"));
				ImageMessage message =  new ImageMessage(image, 10, ImageChar.MEDIUM_SHADE.getChar());
				message.appendTextToLine(5,Lang.PHASESTART.toStringReplacement(x));
				if(x == 1)
					message.appendTextToLines(6,Lang.PHASE1MESSAGE.toStringArray());
				else if(x==2)
					message.appendTextToLines(6,Lang.PHASE2MESSAGE.toStringArray());
				else if(x==3)
					message.appendTextToLines(6,Lang.PHASE3MESSAGE.toStringArray());
				else if(x==4)
					message.appendTextToLines(6,Lang.PHASE4MESSAGE.toStringArray());
				else if(x==5)
					message.appendTextToLines(6,Lang.PHASE5MESSAGE.toStringArray());
				images.put(x, message);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		sendImage(1);
	}
	
	private static void sendImage(int x)
	{
		ImageMessage m = images.get(x);
//		for(AnniPlayer p : AnniPlayer.getPlayers().values())
//		{
//			Player player = p.getPlayer();
//			if(player != null)
//				m.sendToPlayer(player);
//		}
		for(Player player : Bukkit.getOnlinePlayers())
		{
			m.sendToPlayer(player);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void run()
	{
		if(Game.getGameMap() != null)
		{
			GameMap map = Game.getGameMap();
			int newPhase = map.getCurrentPhase()+1;
            String m = Lang.PHASEBAR.toStringReplacement(newPhase) + " - {#}";
            Announcement ann = new Announcement(Lang.PHASEBAR.toStringReplacement(newPhase) + " - {#}");
			switch(newPhase)
			{
				case 2:
					map.setCanDamageNexus(true);
                    ann.setTime(map.getPhaseTime()).setCallback(this);
					break;
				case 3:
					for(Loc loc : map.getDiamondLocations())
					{
						Location l = loc.toLocation();
						l.getWorld().getBlockAt(l).setType(Material.DIAMOND_ORE);
					}
                    ann.setTime(map.getPhaseTime()).setCallback(this);
					break;
				case 4:
					ShopMenu.addGunPowder();
                    ann.setTime(map.getPhaseTime()).setCallback(this);
                    if(map.getBoss1Class() != null && map.getBoss2Class() != null)
                    {
                    	new Boss();
                    }
					break;
				case 5:
					map.setDamageMultiplier(2);
					//MessageBar.permanentBar(Lang.PHASEBAR.toStringReplacement(newPhase));
                    ann.setPermanent(true).setMessage(Lang.PHASEBAR.toStringReplacement(newPhase));
					break;
			}
            AnnounceBar.getInstance().countDown(ann);
			map.setPhase(map.getCurrentPhase()+1);
			ScoreboardAPI.updatePhase();
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"Phase "+ChatColor.AQUA+map.getCurrentPhase()+ChatColor.DARK_PURPLE+" has begun!");
			sendImage(map.getCurrentPhase());
			//Game.broadcastMessage(message);
		}
	}
	
	/** public static void performChange(final int phase)
	{
		if(Game.getGameMap() != null)
		{
			
			Bukkit.getScheduler().runTask(AnnihilationMain.getInstance(), new Runnable()
			{
				@SuppressWarnings("unused")
				public void run()
				{
					GameMap map = Game.getGameMap();
					map.setPhase(phase);
		            String m = Lang.PHASEBAR.toStringReplacement(phase) + " - {#}";
		            Announcement ann = new Announcement(Lang.PHASEBAR.toStringReplacement(phase) + " - {#}");
					switch(phase)
					{
						case 1:
							map.setCanDamageNexus(false);
							if(hasDiamonds)
							{
								for(Loc loc : map.getDiamondLocations())
								{
									Location l = loc.toLocation();
									l.getWorld().getBlockAt(l).setType(Material.COBBLESTONE);
								}
							}
							if(hasBlaze)
							{
								ShopMenu.removeGunPowder();
							}
							if(hasMultiplier)
							{
								map.setDamageMultiplier(1);
							}
						case 2:
							map.setCanDamageNexus(true);
		                    ann.setTime(map.getPhaseTime()).setCallback(this);
							if(hasDiamonds)
							{
								for(Loc loc : map.getDiamondLocations())
								{
									Location l = loc.toLocation();
									l.getWorld().getBlockAt(l).setType(Material.COBBLESTONE);
								}
							}
							if(hasBlaze)
							{
								ShopMenu.removeGunPowder();
							}
							if(hasMultiplier)
							{
								map.setDamageMultiplier(1);
							}
							break;
						case 3:
							for(Loc loc : map.getDiamondLocations())
							{
								Location l = loc.toLocation();
								l.getWorld().getBlockAt(l).setType(Material.DIAMOND_ORE);
							}
							hasDiamonds = true;
							if(hasBlaze)
							{
								ShopMenu.removeGunPowder();
							}
							if(hasMultiplier)
							{
								map.setDamageMultiplier(1);
							}
		                    ann.setTime(map.getPhaseTime()).setCallback(this);
							break;
						case 4:
							ShopMenu.addGunPowder();
							hasBlaze = true;
		                    ann.setTime(map.getPhaseTime()).setCallback(this);
							if(hasMultiplier)
							{
								map.setDamageMultiplier(1);
							}
		                    if(map.getBoss1Loc() != null && map.getBoss2Loc() != null)
		                    {
		                    	new Boss();
		                    }
							break;
						case 5:
							map.setDamageMultiplier(2);
							hasMultiplier = true;
							//MessageBar.permanentBar(Lang.PHASEBAR.toStringReplacement(newPhase));
		                    ann.setPermanent(true).setMessage(Lang.PHASEBAR.toStringReplacement(phase));
							break;
					}
		            AnnounceBar.getInstance().countDown(ann);
					ScoreboardAPI.updatePhase();
					Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"Phase "+ChatColor.AQUA+phase+ChatColor.DARK_PURPLE+" has begun!");
					sendImage(phase);
					//Game.broadcastMessage(message);
				}
			});
		}
	} **/
}
