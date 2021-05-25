/*-
 * LICENSE
 * CooKiePlugin
 * -------------
 * Copyright (C) 2021 YouKlike
 * -------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */

package cookie.main.cookieplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * TIP: final class (該類別不能被其他類別所繼承)
 *      final function (該方法不能被複寫)
 *      final (int, double, float) 該變數只能被指派一次
 */
public final class CookiePlugin
        extends JavaPlugin implements Listener {

    private final Map<Item, Player> playerItemMap = new HashMap<>();

    @Override
    public final void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public final void onDisable() {
    }

    @EventHandler
    public final void onPickupItem(final EntityPickupItemEvent event) {
        final LivingEntity entity = event.getEntity();
        final Item item = event.getItem();
        // LivingEntity 可能是 殭屍、苦力怕、玩家。因為下面的代碼有 強轉類型，所以必須先判斷是否可以強制轉換類型。
        if (entity instanceof Player) {
            final Player player = (Player) entity;

            final Player memoryTempPlayer = this.playerItemMap.get(item);
            if (memoryTempPlayer != null && player == memoryTempPlayer) return ;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public final void onDropItem(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final Item itemDrop = event.getItemDrop();
        this.playerItemMap.put(itemDrop, player);
    }

}
