package com.gmail.lynx7478.anni.anniGame.autoRespawn.versions;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.lynx7478.anni.anniGame.autoRespawn.RespawnPacket;

import net.minecraft.server.v1_13_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_13_R2.PacketPlayInClientCommand.EnumClientCommand;

public class v1_13_R2Packet implements RespawnPacket
{
    private final PacketPlayInClientCommand packet;
    public v1_13_R2Packet()
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
