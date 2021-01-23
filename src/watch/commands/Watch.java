package watch.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import watch.enums.SkullNumber;
import watch.main.SpigotPlugin;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Watch implements CommandExecutor {
    private SpigotPlugin plugin;

    public Watch(SpigotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (plugin.configuration.getStringList("buyers").contains(p.getName())) {
                Inventory i = Bukkit.createInventory(null, 27, "Время");
                LocalTime time = LocalTime.now();
                char[] hour = String.valueOf(time.getHour() < 10 ? "0" + time.getHour() : time.getHour()).toCharArray();
                char[] minute = String.valueOf(time.getMinute() < 10 ? "0" + time.getMinute() : time.getMinute()).toCharArray();
                List<ItemStack> date = new ArrayList<>();
                for (char chr : hour) {
                    String url = null;
                    for (SkullNumber num : SkullNumber.values()) {
                        if (num.number == Integer.parseInt(String.valueOf(chr))) url = num.url;
                    }
                    date.add(plugin.utils.getSkullAsUrl(String.valueOf(chr), url));
                }
                for (char chr : minute) {
                    String url = null;
                    for (SkullNumber num : SkullNumber.values()) {
                        if (num.number == Integer.parseInt(String.valueOf(chr))) url = num.url;
                    }
                    date.add(plugin.utils.getSkullAsUrl(String.valueOf(chr), url));
                }
                i.setItem(11, date.get(0));
                i.setItem(12, date.get(1));
                i.setItem(13, plugin.utils.getSkullAsUrl(":", "http://textures.minecraft.net/texture/ccbee28e2c79db138f3977ba472dfae6b11a9bb82d5b3d7f25479338fff1fe92"));
                i.setItem(14, date.get(2));
                i.setItem(15, date.get(3));
                p.openInventory(i);
                int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    LocalTime time1 = LocalTime.now();
                    char[] hour1 = String.valueOf(time1.getHour() < 10 ? "0" + time1.getHour() : time1.getHour()).toCharArray();
                    char[] minute1 = String.valueOf(time1.getMinute() < 10 ? "0" + time1.getMinute() : time1.getMinute()).toCharArray();
                    List<ItemStack> date1 = new ArrayList<>();
                    for (char chr : hour1) {
                        String url = null;
                        for (SkullNumber num : SkullNumber.values()) {
                            if (num.number == Integer.parseInt(String.valueOf(chr))) url = num.url;
                        }
                        date1.add(plugin.utils.getSkullAsUrl(String.valueOf(chr), url));
                    }
                    for (char chr : minute1) {
                        String url = null;
                        for (SkullNumber num : SkullNumber.values()) {
                            if (num.number == Integer.parseInt(String.valueOf(chr))) url = num.url;
                        }
                        date1.add(plugin.utils.getSkullAsUrl(String.valueOf(chr), url));
                    }
                    i.setItem(11, date1.get(0));
                    i.setItem(12, date1.get(1));
                    i.setItem(13, plugin.utils.getSkullAsUrl(":", "http://textures.minecraft.net/texture/ccbee28e2c79db138f3977ba472dfae6b11a9bb82d5b3d7f25479338fff1fe92"));
                    i.setItem(14, date1.get(2));
                    i.setItem(15, date1.get(3));
                    p.openInventory(i);
                }, 20L*60, 20L*60);
                plugin.tasks.put(p, id);
                return true;
            } else {
                p.sendMessage(ChatColor.RED + "Вы еще не купили часы!");
                return true;
            }
        } else return false;
    }
}