package me.mjdawson.inventorydroptoggle;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.*;

public final class Inventorydroptoggle extends JavaPlugin implements Listener {

    private static Inventorydroptoggle instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(PlayerDataManager.getInstance(), this);
    }

    @Override
    public void onDisable() {
        PlayerDataManager.getInstance().saveAllData();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
        if (command.getName().equalsIgnoreCase("inventoryToggle")) {
            try {
                if (Inventorydroptoggle.checkEquals(PlayerDataManager.getInstance()
                        .getData(((org.bukkit.OfflinePlayer) (Object) commandSender), "drop"), "False")) {
                    PlayerDataManager.getInstance().setData(((org.bukkit.OfflinePlayer) (Object) commandSender), "drop",
                            "True");
                    ((org.bukkit.entity.Player) (Object) commandSender).spigot()
                            .sendMessage(net.md_5.bungee.chat.ComponentSerializer
                                    .parse("{\"text\":\"Keep inventory disabled.\",\"color\":\"green\"}"));
                } else {
                    PlayerDataManager.getInstance().setData(((org.bukkit.OfflinePlayer) (Object) commandSender), "drop",
                            "False");
                    ((org.bukkit.entity.Player) (Object) commandSender).spigot()
                            .sendMessage(net.md_5.bungee.chat.ComponentSerializer
                                    .parse("{\"text\":\"Keep inventory enabled.\",\"color\":\"green\"}"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return true;
    }

    public static void procedure(String procedure, List procedureArgs) throws Exception {
    }

    public static Object function(String function, List functionArgs) throws Exception {
        return null;
    }

    public static List createList(Object obj) {
        if (obj instanceof List) {
            return (List) obj;
        }
        List list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            int length = java.lang.reflect.Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                list.add(java.lang.reflect.Array.get(obj, i));
            }
        } else if (obj instanceof Collection<?>) {
            list.addAll((Collection<?>) obj);
        } else if (obj instanceof Iterator) {
            ((Iterator<?>) obj).forEachRemaining(list::add);
        } else {
            list.add(obj);
        }
        return list;
    }

    public static void createResourceFile(String path) {
        Path file = getInstance().getDataFolder().toPath().resolve(path);
        if (Files.notExists(file)) {
            try (InputStream inputStream = Inventorydroptoggle.class.getResourceAsStream("/" + path)) {
                Files.createDirectories(file.getParent());
                Files.copy(inputStream, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Inventorydroptoggle getInstance() {
        return instance;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void event1(org.bukkit.event.entity.PlayerDeathEvent event) throws Exception {
        event.setKeepInventory(true);
        if (Inventorydroptoggle.checkEquals(PlayerDataManager.getInstance().getData(((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Entity) event.getEntity())), "drop"), "True")) {
            Player player = event.getEntity();
            Location location = player.getLocation();

            // drop all items in the player's inventory
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    location.getWorld().dropItemNaturally(location, item);
                }
            }

            // drop all items in the player's armor slots
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    location.getWorld().dropItemNaturally(location, item);
                }
            }
            player.getInventory().clear();
        }
    }

    public static boolean checkEquals(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1 instanceof Number && o2 instanceof Number
                ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue()
                : o1.equals(o2);
    }
}
