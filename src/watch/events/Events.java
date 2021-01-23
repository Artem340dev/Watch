package watch.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import watch.main.SpigotPlugin;

public class Events implements Listener {
    private SpigotPlugin plugin;

    public Events(SpigotPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equals("Время")) e.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getName().equals("Время")) {
            int id = plugin.tasks.get(e.getPlayer());
            Bukkit.getScheduler().cancelTask(id);
            plugin.tasks.remove(e.getPlayer());
        }
    }
}