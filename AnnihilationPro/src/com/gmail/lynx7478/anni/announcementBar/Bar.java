package com.gmail.lynx7478.anni.announcementBar;

import org.bukkit.entity.Player;

public interface Bar
{
    void sendToPlayer(Player player, String message, float percentOfTotal);
}
