package watch.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import watch.main.SpigotPlugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

public class Utils {
    private SpigotPlugin plugin;

    public Utils(SpigotPlugin plugin) {
        this.plugin = plugin;
    }

    public void save() {
        try {
            plugin.configuration.save(plugin.buyers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ItemStack getSkullAsUrl(String name, String url) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] data = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(data)));
        Field field = null;
        try {
            field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l"+name));
        meta.setLore(null);
        skull.setItemMeta(meta);
        return skull;
    }
}