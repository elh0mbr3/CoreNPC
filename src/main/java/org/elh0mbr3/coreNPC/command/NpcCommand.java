package org.elh0mbr3.coreNPC.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elh0mbr3.coreNPC.CoreNPC;
import org.elh0mbr3.coreNPC.model.Npc;

public class NpcCommand implements CommandExecutor {
    private final CoreNPC plugin;

    // Passes the main plugin instance here so we can access our NpcManager
    public NpcCommand(CoreNPC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensures the command is sent by a real player, not the server console
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can spawn NPCs.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0 && args[0].equalsIgnoreCase("spawn")) {
            // Creates the NPC blueprint at the player's exact location
            Npc npc = new Npc("TestNPC", player.getLocation());

            // Save it to our manager
            plugin.getNpcManager().addNpc(npc);

            player.sendMessage("NPC created in memory with ID: " + npc.getEntityId());
            return true;
        }

        player.sendMessage("Usage: /npc spawn");
        return true;
    }
}
