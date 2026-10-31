package org.elh0mbr3.coreNPC.model;

import org.bukkit.Location;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Npc {

    // Starts at 100,000 so our holograms never share an ID with real server entities
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(100000);

    private final int entityId;
    private final UUID uuid;
    private final String name;
    private Location location;

    public Npc(String name, Location location) {
        // Automatically assigns the next available number (100001, 100002, etc.)
        this.entityId = ID_GENERATOR.incrementAndGet();
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.location = location;
    }

    public int getEntityId() {
        return entityId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
