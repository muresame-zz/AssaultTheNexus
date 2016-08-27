package com.gmail.lynx7478.anni.enderFurnace.api;

public interface IFurnace
{	
	void tick();
	
	void open();
	
	FurnaceData getFurnaceData();
	
	void load(FurnaceData data);
}
