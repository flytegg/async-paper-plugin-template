package gg.flyte.template.listener

import kotlinx.coroutines.delay
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import kotlin.time.Duration.Companion.seconds

class SuspendingJoinListener : Listener {

    @EventHandler
    suspend fun onJoin(event: PlayerJoinEvent) {
        delay(1.seconds)
        event.player.sendMessage("Hello, ${event.player.name}! You joined 1 second ago!")
    }

}