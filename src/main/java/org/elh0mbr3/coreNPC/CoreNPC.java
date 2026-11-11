package org.elh0mbr3.coreNPC;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.elh0mbr3.coreNPC.command.NpcCommand;
import org.elh0mbr3.coreNPC.core.NpcManager;

public final class CoreNPC extends JavaPlugin {

    // Holds the connection to PacketEvents so fake packets can be sent later
    private PacketEventsAPI<?> packetEvents;
    private NpcManager npcManager;

    @Override
    public void onEnable() {

        // Hooks into PacketEvents
        this.packetEvents = PacketEvents.getAPI();
        this.npcManager = new NpcManager();

        getCommand("npc").setExecutor(new NpcCommand(this));
        getLogger().info("CoreNPC has successfully started.");
    }

    // Executes the method when a server is shutting down
    @Override
    public void onDisable() {
        getLogger().info("CoreNPC is shutting down.");
    }

    // Returns Packet Events
    public PacketEventsAPI<?> getPacketEvents() {
        return packetEvents;
    }

    public NpcManager getNpcManager() {
        return npcManager;
    }
}
