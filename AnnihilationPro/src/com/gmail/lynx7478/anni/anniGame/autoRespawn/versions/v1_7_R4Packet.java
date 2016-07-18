package com.gmail.lynx7478.anni.anniGame.autoRespawn.versions;

import net.minecraft.server.v1_7_R4.EnumClientCommand;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.lynx7478.anni.anniGame.autoRespawn.RespawnPacket;

public class v1_7_R4Packet implements RespawnPacket
{
    private final PacketPlayInClientCommand packet;
    public v1_7_R4Packet()
    {
        packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
    }

    @Override
    public void sendToPlayer(final Player player)
    {
        CraftPlayer p = (CraftPlayer)player;
        p.getHandle().playerConnection.a(packet);
    }
}
