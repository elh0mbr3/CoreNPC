package org.elh0mbr3.coreNPC.storage;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.elh0mbr3.coreNPC.CoreNPC;
import org.elh0mbr3.coreNPC.core.NpcManager;
import org.elh0mbr3.coreNPC.model.Npc;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class NpcStorage {
    private final CoreNPC plugin;
    private final File file;

    public NpcStorage(CoreNPC plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "npcs.yml");
    }

    public void save(Collection<Npc> npcs) {
        YamlConfiguration config = new YamlConfiguration();
        int i = 0;
        for (Npc npc : npcs) {
            config.set("npcs." + i + ".name", npc.getName());
            config.set("npcs." + i + ".location", npc.getLocation());
            i++;
        }
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save NPCs: " + e.getMessage());
        }
    }

    public void load(NpcManager manager) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("npcs");
        if (section == null) {
            return; // no file yet, or no NPCs saved
        }
        for (String key : section.getKeys(false)) {
            String name = section.getString(key + ".name");
            Location location = section.getLocation(key + ".location");
            manager.addNpc(new Npc(name, location));
        }
    }
}
