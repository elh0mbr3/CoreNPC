package org.elh0mbr3.coreNPC;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.elh0mbr3.coreNPC.command.NpcCommand;
import org.elh0mbr3.coreNPC.core.NpcManager;

public final class CoreNPC extends JavaPlugin {

    // Holds the connection to ProtocolLib so fake packets can be sent later
    private ProtocolManager protocolManager;
    private NpcManager npcManager;

    @Override
    public void onEnable() {
        // Ensures ProtocolLib is actually installed on the server
        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            getLogger().severe("ProtocolLib is missing! Please install ProtocolLib. Disabling CoreNPC");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Hooks into ProtocolLib
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.npcManager = new NpcManager();

        getCommand("npc").setExecutor(new NpcCommand(this));
        getLogger().info("CoreNPC has successfully started.");
    }

    // Executes the method when a server is shutting down
    @Override
    public void onDisable() {
        getLogger().info("CoreNPC is shutting down.");
    }

    // Returns protocolManager
    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public NpcManager getNpcManager() {
        return npcManager;
    }
}
