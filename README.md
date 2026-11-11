# CoreNPC
CoreNPC is a free Minecraft plugin designed to be a lightweight, modern alternative to traditional NPC 
engines like `Citizens`.

Instead of registering real entities on the server, CoreNPC uses network packet manipulation to render
"fake" entities directly to the players' game clients. Because the server doesn't simulate these NPCs
(no gravity, collisions or AI), each one costs far less than a real entity, so servers can host large
numbers of NPCs with very little impact on `TPS` (Ticks per second).

## Why PaperMC?
Other server engines (like Bukkit or Spigot) force almost all world interactions onto the
server's single main thread. CoreNPC requires **Paper**. 
- To eventually make NPCs walk, we need to calculate routes (A* pathfinding) across the map. CoreNPC will do this on background threads, using chunk snapshots (read-only copies of the world), so route calculation doesn't slow down the main thread. Paper's asynchronous chunk loading helps here because loading the chunks it needs won't freeze the server.
- Paper heavily optimises how network packets are dispatched to players, which helps develop our packet-based architecture.
- Paper natively includes the `Adventure` component library, which provides thread-safe and highly optimised text formatting for NPC names and holograms.

## Why Gradle?
For instance, Maven uses a rigid, XML-based configuration(`pom.xml`). We chose **Gradle (with Kotlin DSL)** 
for this project.
- CoreNPC will eventually use specialised libraries (like `fastutil`) to manage memory efficiently during pathfinding. Gradle's Shadow plugin makes it incredibly easy to hide these libraries inside our plugin so they don't conflict with other plugins.
- Gradle allows us to use the `run-paper` plugin. Instead of manually moving JAR files into a server folder, we can boot up a test server directly from our IDE with a single command.
- Gradle uses a daemon and incremental compilation, making the build process significantly faster when testing minor code changes.

## Requirements:
- **Server:** PaperMC 26.3 (or higher)
- **Dependencies:** ProtocolLib 5.3.0
- **Java:** Java 25