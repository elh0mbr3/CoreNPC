package org.elh0mbr3.coreNPC.command;

import org.bukkit.Bukkit;
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
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0 && args[0].equalsIgnoreCase("spawn")) {
            // Creates the NPC blueprint at the player's exact location
            Npc npc = new Npc("TestNPC", player.getLocation());

            // Saves it and shows it to the players
            plugin.getNpcManager().addNpc(npc);
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.getWorld().equals(npc.getLocation().getWorld())) {
                    plugin.getNpcRenderer().show(npc, online);
                }
            }

            player.sendMessage("NPC created with ID: " + npc.getEntityId());
            return true;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("remove")) {
            if (args.length < 2) {
                player.sendMessage("Usage: /npc remove <id>");
                return true;
            }
            int id;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage("That's not a number: " + args[1]);
                return true;
            }
            Npc npc = plugin.getNpcManager().getNpc(id);
            if (npc == null) {
                player.sendMessage("No NPC with ID " + id);
                return true;
            }

            // Hides it for everyone, since players without it simply ignore the packet
            for (Player online : Bukkit.getOnlinePlayers()) {
                plugin.getNpcRenderer().hide(npc, online);
            }
            plugin.getNpcManager().removeNpc(id);

            player.sendMessage("NPC " + id + " removed.");
            return true;
        }

        player.sendMessage("Usage: /npc spawn|remove");
        return true;
    }
}
