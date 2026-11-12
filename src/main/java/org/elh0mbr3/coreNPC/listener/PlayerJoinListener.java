package org.elh0mbr3.coreNPC.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.elh0mbr3.coreNPC.CoreNPC;
import org.elh0mbr3.coreNPC.model.Npc;

public class PlayerJoinListener implements Listener {
    private final CoreNPC plugin;

    public PlayerJoinListener(CoreNPC plugin) {
        this.plugin = plugin;
    }

    // Shows every NPC in the player's world as soon as they join
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (Npc npc : plugin.getNpcManager().getAll()) {
            if (player.getWorld().equals(npc.getLocation().getWorld())) {
                plugin.getNpcRenderer().show(npc, player);
            }
        }
    }
}
