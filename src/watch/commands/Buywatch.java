package watch.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import watch.main.SpigotPlugin;

import java.time.LocalTime;
import java.util.List;

public class Buywatch implements CommandExecutor {
    private SpigotPlugin plugin;

    public Buywatch(SpigotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (plugin.e.getBalance(p) >= 100000 && !plugin.configuration.getStringList("buyers").contains(p.getName())) {
                List<String> players = plugin.configuration.getStringList("buyers");
                players.add(p.getName());
                plugin.e.withdrawPlayer(p, 100000);
                plugin.configuration.set("buyers", players);
                plugin.utils.save();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eВы успешно купили себе часы! Посмотреть время - &a/watch"));
                return true;
            } else {
                p.sendMessage(ChatColor.RED + "На вашем счету недостаточно средств! Требуется 100.000$");
                return true;
            }
        } else return false;
    }
}