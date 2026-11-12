package org.elh0mbr3.coreNPC.core;

import org.elh0mbr3.coreNPC.model.Npc;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NpcManager {
    // Thread-sade map storing our NPCs by their unique entity ID
    private final Map<Integer, Npc> npcById = new ConcurrentHashMap<>();

    public void addNpc(Npc npc) {
        npcById.put(npc.getEntityId(), npc);
    }

    public void removeNpc(int entityId) {
        npcById.remove(entityId);
    }

    public Npc getNpc(int entityId) {
        return npcById.get(entityId);
    }

    public Collection<Npc> getAll() {
        return npcById.values();
    }

    public void clear() {
        npcById.clear();
    }
}
