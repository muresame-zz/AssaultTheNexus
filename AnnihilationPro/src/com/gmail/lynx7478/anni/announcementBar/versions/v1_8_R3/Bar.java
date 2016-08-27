package com.gmail.lynx7478.anni.announcementBar.versions.v1_8_R3;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Bar implements com.gmail.lynx7478.anni.announcementBar.Bar
{
    @Override
    public void sendToPlayer(final Player player, final String message, final float percentOfTotal)
    {
        IChatBaseComponent actionComponent = ChatSerializer.a("{\"text\":\"" + cleanMessage(message) + "\"}");
        PacketPlayOutChat actionPacket = new PacketPlayOutChat(actionComponent, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(actionPacket);
    }

    private static String cleanMessage(String message)
    {
        if (message.length() > 64)
            message = message.substring(0, 63);

        return message;
    }
}
