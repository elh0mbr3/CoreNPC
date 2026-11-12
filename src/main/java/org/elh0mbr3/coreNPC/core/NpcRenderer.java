package org.elh0mbr3.coreNPC.core;

import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.entity.Player;
import org.elh0mbr3.coreNPC.model.Npc;

public class NpcRenderer {
    private final PacketEventsAPI<?> packetEvents;

    public NpcRenderer(PacketEventsAPI<?> packetEvents) {
        this.packetEvents = packetEvents;
    }

    // Sends the packets that make one NPC appear for one player
    public void show(Npc npc, Player viewer) {
        // Tells the client this player profile exists (not added to the tab list)
        UserProfile profile = new UserProfile(npc.getUuid(), npc.getName());
        WrapperPlayServerPlayerInfoUpdate info = new WrapperPlayServerPlayerInfoUpdate(
                WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER,
                new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(profile));
        // Spawns a player-type entity using that same UUID
        float yaw = npc.getLocation().getYaw();
        WrapperPlayServerSpawnEntity spawn = new WrapperPlayServerSpawnEntity(npc.getEntityId(),
                npc.getUuid(),
                EntityTypes.PLAYER,
                SpigotConversionUtil.fromBukkitLocation(npc.getLocation()), yaw, 0, null);
        // Points the head the same way as the body
        WrapperPlayServerEntityHeadLook head = new WrapperPlayServerEntityHeadLook(npc.getEntityId(), yaw);

        packetEvents.getPlayerManager().sendPacket(viewer, info);
        packetEvents.getPlayerManager().sendPacket(viewer, spawn);
        packetEvents.getPlayerManager().sendPacket(viewer, head);
    }

    // Sends the packets that make one NPC disappear for one player
    public void hide(Npc npc, Player viewer) {
        WrapperPlayServerDestroyEntities destroy = new WrapperPlayServerDestroyEntities(npc.getEntityId());
        WrapperPlayServerPlayerInfoRemove info = new WrapperPlayServerPlayerInfoRemove(npc.getUuid());
        packetEvents.getPlayerManager().sendPacket(viewer, destroy);
        packetEvents.getPlayerManager().sendPacket(viewer, info);

    }
}
