package watch.main;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import watch.commands.Buywatch;
import watch.commands.Watch;
import watch.events.Events;
import watch.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SpigotPlugin extends JavaPlugin {
    public File buyers;
    public Economy e;
    public FileConfiguration configuration;
    public Utils utils = new Utils(this);
    public HashMap<Player, Integer> tasks = new HashMap<>();

    private void init() {
        RegisteredServiceProvider<Economy> reg = Bukkit.getServicesManager().getRegistration(Economy.class);
        e = reg.getProvider();
    }

    public void onEnable() {
        init();
        getCommand("watch").setExecutor(new Watch(this));
        getCommand("buywatch").setExecutor(new Buywatch(this));
        Bukkit.getPluginManager().registerEvents(new Events(this), this);
        buyers = new File(getDataFolder() + File.separator + "buyers.yml");
        if (!buyers.exists()) {
            try {
                buyers.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(buyers);
    }
}