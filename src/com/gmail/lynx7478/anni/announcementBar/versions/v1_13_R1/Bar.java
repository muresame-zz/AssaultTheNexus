package com.gmail.lynx7478.anni.announcementBar.versions.v1_13_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.gmail.lynx7478.anni.utils.VersionUtils;

public class Bar implements com.gmail.lynx7478.anni.announcementBar.Bar
{
    @Override
    public void sendToPlayer(final Player player, final String message, final float percentOfTotal)
    {
    	String ver = VersionUtils.getVersion();
    	 try {
             Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + ver + ".entity.CraftPlayer");
             Object craftPlayer = craftPlayerClass.cast(player);
             Object ppoc;
             Class<?> c4 = Class.forName("net.minecraft.server." + ver + ".PacketPlayOutChat");
             Class<?> c5 = Class.forName("net.minecraft.server." + ver + ".Packet");
             Class<?> c2 = Class.forName("net.minecraft.server." + ver + ".ChatComponentText");
             Class<?> c3 = Class.forName("net.minecraft.server." + ver + ".IChatBaseComponent");
             Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + ver + ".ChatMessageType");
             Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
             Object chatMessageType = null;
             for (Object obj : chatMessageTypes) {
                 if (obj.toString().equals("GAME_INFO")) {
                     chatMessageType = obj;
                 }
             }
             Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(cleanMessage(message));
             ppoc = c4.getConstructor(new Class<?>[]{c3, chatMessageTypeClass}).newInstance(o, chatMessageType);
             Method m1 = craftPlayerClass.getDeclaredMethod("getHandle");
             Object h = m1.invoke(craftPlayer);
             Field f1 = h.getClass().getDeclaredField("playerConnection");
             Object pc = f1.get(h);
             Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
             m5.invoke(pc, ppoc);
         } catch (Exception ex) {
             ex.printStackTrace();
 }
    }

    private static String cleanMessage(String message)
    {
        if (message.length() > 64)
            message = message.substring(0, 63);

        return message;
    }
}
