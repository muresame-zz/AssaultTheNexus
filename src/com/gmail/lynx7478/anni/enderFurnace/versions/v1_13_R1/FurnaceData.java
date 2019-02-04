package com.gmail.lynx7478.anni.enderFurnace.versions.v1_13_R1;

import net.minecraft.server.v1_13_R1.TileEntityFurnace;

class FurnaceData extends com.gmail.lynx7478.anni.enderFurnace.api.FurnaceData
{
	
	private static TileEntityFurnace furnace;
	
    public FurnaceData(Furnace_v1_13_R1 furnace_v1_13_R1)
    {
        super(asBukkitCopy(getStacksAsCollection()),furnace_v1_13_R1.getProperty(0),furnace_v1_13_R1.getProperty(1),furnace_v1_13_R1.getProperty(2));
        FurnaceData.furnace = furnace_v1_13_R1;
    }

    private static org.bukkit.inventory.ItemStack[] asBukkitCopy(net.minecraft.server.v1_13_R1.ItemStack[] stacks)
    {
        org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[stacks.length];
        for(int i = 0; i < items.length; i++)
        {
            items[i] = org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack.asBukkitCopy(stacks[i]);
        }
        return items;
    }
    
    private static net.minecraft.server.v1_13_R1.ItemStack[] getStacksAsCollection()
    {
    	net.minecraft.server.v1_13_R1.ItemStack[] stacks = new net.minecraft.server.v1_13_R1.ItemStack[furnace.getContents().size()];
    	int num = 0;
    	for(net.minecraft.server.v1_13_R1.ItemStack i : furnace.getContents())
    	{
    		if(num>furnace.getContents().size())
    		{
    			break;
    		}else
    		{
    			stacks[num] = i;
    		}
    	}
    	return stacks;
    }
}
