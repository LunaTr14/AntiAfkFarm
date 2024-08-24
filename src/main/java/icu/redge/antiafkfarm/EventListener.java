package icu.redge.antiafkfarm;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;


public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public final void onMobDeath(EntityDeathEvent e) {

        FileConfiguration config = Antiafkfarm.instance.getConfig();

        List<String> entityList = config.getStringList("list");
        Set<String> lowerCaseEntitySet = new HashSet<>();
        for (String entity : entityList) {
            lowerCaseEntitySet.add(entity.toLowerCase());
        }

        boolean isBlackListMode = config.getBoolean("isBlackList", true);

        String entityName = e.getEntity().getType().name().toLowerCase();
        boolean isEntityInList = lowerCaseEntitySet.contains(entityName);

        boolean isPlayerKilled = e.getEntity().getKiller() instanceof org.bukkit.entity.Player;
        if (!isBlackListMode) return;
        if (!isEntityInList) return;
        if (!isPlayerKilled) return;
        e.setDroppedExp(0);
        e.getDrops().clear();
    }
}