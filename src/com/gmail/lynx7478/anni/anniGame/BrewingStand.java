package com.gmail.lynx7478.anni.anniGame;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class BrewingStand {
	
	private Location location;
	private AnniPlayer owner;
	
	public BrewingStand(Location loc, AnniPlayer owner)
	{
		this.location = loc;
		this.owner = owner;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public AnniPlayer getOwner()
	{
		return owner;
	}

}
