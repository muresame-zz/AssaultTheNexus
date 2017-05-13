package com.gmail.lynx7478.anni.anniGame;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;

import com.gmail.lynx7478.anni.main.AnnihilationMain;

public class GameVars
{
	private static GameMode defaultGamemode = GameMode.ADVENTURE;
	private static String endGameCommand = "stop";
	
	private static boolean AutoStart = false;
	private static int PlayerstoStart = 4;
	private static int CountdowntoStart = 30;
	
	private static boolean Voting = true;
	private static int maxVotingMaps = 3;
	private static String Map = "";
	
	private static boolean AutoReStart = false;
	private static int PlayersToRestart = 0;
	private static int CountdowntoRestart = 15;
	
	private static boolean useMOTD = false;
	
	private static boolean killOnLeave = false;
	
	private static boolean useEC = false;
	private static boolean useECC = false;
	private static int ecSlots = 9;
	
	private static boolean hideTags = true;
	
	// Private brewing stands.
	private static boolean privateBrewingStands = true;
	
	// For 1.8 adventure fix.
	private static boolean useSpecificBreaking = true;
	
	private static boolean useTeamBalance = true;
	private static int balanceTolerance = 2;


	private static int endOfGameCountdown = 120;
	
	private static boolean revealOnBreak;
	private static boolean revealOnHit;
	private static boolean revealOnDamage;
	
	private static boolean useCivilianKit = true;
	
	private static boolean useMetrics = true;
	
	//TODO: Hardcore mode.
	private static boolean hardcore;
	
	// PEX Hook.
	private static boolean usePEX;

	public static int getEndOfGameCountdown()
	{
		return endOfGameCountdown;
	}
	
	public static boolean getHideTags()
	{
		return hideTags;
	}
	
	public static boolean getRevealOnBreak()
	{
		return revealOnBreak;
	}
	
	public static boolean getRevealOnHit()
	{
		return revealOnHit;
	}
	
	public static boolean getRevealOnDamage()
	{
		return revealOnDamage;
	}
	
	public static boolean getUseEC(){
		return useEC;
	}
	
	public static boolean getUseECC(){
		return useECC;
	}
	
	public static int getECSlots(){
		return ecSlots;
	}
	
	public static boolean getUseCivilianKit()
	{
		return useCivilianKit;
	}
	
	public static int getMaxMapsForVoting()
	{
		return maxVotingMaps;
	}
	
	public static boolean getUseSpecificBreaking()
	{
		return useSpecificBreaking;
	}
	
	public static boolean getKillOnLeave(){
		return killOnLeave;
	}
	
	public static int getBalanceTolerance()
	{
		return balanceTolerance;
	}

	public static boolean useTeamBalance()
	{
		return useTeamBalance;
	}
	
	public static GameMode getDefaultGamemode()
	{
		return defaultGamemode;
	}
	
	public static String getEndOfGameCommand()
	{
		return endGameCommand;
	}
	
	public static boolean useMOTD()
	{
		return useMOTD;
	}
	
	public static boolean getAutoRestart()
	{
		return AutoReStart;
	}
	
	public static int getCountdownToRestart()
	{
		return CountdowntoRestart;
	}
	
	public static int getPlayersToRestart()
	{
		return PlayersToRestart;
	}
	
	public static boolean getAutoStart()
	{
		return AutoStart;
	}
	
	public static int getPlayersToStart()
	{
		return PlayerstoStart;
	}
	
	public static int getCountdownToStart()
	{
		return CountdowntoStart;
	}
	
	public static boolean getVoting()
	{
		return Voting;
	}
	
	public static File getMap()
	{
		return new File(Map);
	}
	
	public static boolean getUseMetrics()
	{
		return useMetrics;
	}
	
	public static boolean isHardcore()
	{
		return hardcore;
	}
	
	public static boolean usePEX()
	{
		return usePEX;
	}
	
	public static boolean usePrivateBrewingStands()
	{
		return privateBrewingStands;
	}
	
	public static void loadGameVars(ConfigurationSection config)
	{
		if(config != null)
		{
			useMOTD = config.getBoolean("useMOTD");
			endOfGameCountdown = config.getInt("End-Of-Game-Countdown");
			String command = config.getString("EndGameCommand");
			useEC = config.getBoolean("Use-EnderChest");
			useECC = config.getBoolean("Use-EnderChest-Command");
			ecSlots = config.getInt("EnderChest-Slots");
			hideTags = config.getBoolean("Hide-Other-Teams-Tag");
			useSpecificBreaking = config.getBoolean("Use-Specific-Breaking");
			useMetrics = config.getBoolean("Use-Metrics");
			usePEX = config.getBoolean("Use-PEX");
			if(useEC){
				if(!(ecSlots % 9 == 0)){
					Bukkit.getLogger().log(Level.SEVERE, "The enderchest slots must be a multiple of 9!!");
					Bukkit.getLogger().log(Level.SEVERE, "Please disable the enderchest or change the value of slots to a valid number.");
					AnnihilationMain.getInstance().getPluginLoader().disablePlugin(AnnihilationMain.getInstance());
				}
			}
			if(command != null)
				endGameCommand = command.trim();
			ConfigurationSection gameVars = config.getConfigurationSection("GameVars");
			if(gameVars != null)
			{
				hardcore = gameVars.getBoolean("Hardcore");
				ConfigurationSection auto = gameVars.getConfigurationSection("AutoStart");
				AutoStart = auto.getBoolean("On");//auto.set("On", false);
				PlayerstoStart = auto.getInt("PlayersToStart");//auto.set("PlayersToStart", 4);
				CountdowntoStart = auto.getInt("CountdownTime");
				
				ConfigurationSection mapload = gameVars.getConfigurationSection("MapLoading");
				Voting = mapload.getBoolean("Voting");//mapload.set("Voting", true);
				maxVotingMaps = mapload.getInt("Max-Maps-For-Voting");
				Map = mapload.getString("UseMap");//mapload.set("UseMap", "plugins/Annihilation/Worlds/Test");
				
				ConfigurationSection autorestart = gameVars.getConfigurationSection("AutoRestart");
				AutoReStart = autorestart.getBoolean("On");//autorestart.set("On", false);
				PlayersToRestart = autorestart.getInt("PlayersToAutoRestart");//autorestart.set("PlayersToAutoRestart", 0);
				CountdowntoRestart = autorestart.getInt("CountdownTime");
				
				ConfigurationSection balance = gameVars.getConfigurationSection("Team-Balancing");
				useTeamBalance = balance.getBoolean("On");//autorestart.set("On", false);
				balanceTolerance = balance.getInt("Tolerance");//autorestart.set("PlayersToAutoRestart", 0);
				
				ConfigurationSection invis = gameVars.getConfigurationSection("Invisibles");
				revealOnBreak = invis.getBoolean("Reveal-On-Block-Break");
				revealOnHit = invis.getBoolean("Reveal-On-Hit");
				revealOnDamage = invis.getBoolean("Reveal-On-Damage");
				
				useCivilianKit = gameVars.getBoolean("Use-Civilian-Kit");
				
				privateBrewingStands = gameVars.getBoolean("Use-Private-Brewing-Stands");
				
			
				String gamemode = gameVars.getString("DefaultGameMode");
				if(gamemode != null)
				{
					try
					{
						defaultGamemode = GameMode.valueOf(gamemode.toUpperCase());
					}
					catch(Exception e)
					{
						defaultGamemode = GameMode.ADVENTURE;
					}
				}
			}
		}
		File worldFolder = new File(AnnihilationMain.getInstance().getDataFolder().getAbsolutePath()+"/Worlds");
		if(!worldFolder.exists())
			worldFolder.mkdir();
//		tempWorldPath = AnnihilationMain.getInstance().getDataFolder().getAbsolutePath()+"/TempWorld";
//		worldFolder = new File(tempWorldPath);
//		if(!worldFolder.exists())
//			worldFolder.mkdir();
	}
}
