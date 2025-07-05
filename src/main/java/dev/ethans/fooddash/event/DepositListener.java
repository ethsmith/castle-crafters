package dev.ethans.fooddash.event;

import dev.ethans.fooddash.FoodDash;
import dev.ethans.fooddash.team.Team;
import dev.ethans.fooddash.wave.WaveManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

public class DepositListener implements Listener {

    private static final FoodDash plugin = FoodDash.getInstance();

    private final WaveManager waveManager;

    public DepositListener(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    @EventHandler
    public void onDepositBarrel(InventoryClickEvent event) {
        if (event.getAction() != InventoryAction.PLACE_ALL
                && event.getAction() != InventoryAction.PLACE_ONE) return;

        Inventory barrelInventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if (barrelInventory == null) return;
        if (!(barrelInventory.getType() == InventoryType.BARREL)) return;

        ItemStack depositItem = event.getCursor();
        Material depositMaterial = depositItem.getType();
        ItemType depositItemType = depositMaterial.asItemType();

        if (depositItemType == null) return;

        int originalDepositAmount = depositItem.getAmount();
        int neededAmount = waveManager.getCurrentWave().getAmountLeft(depositItemType);
        int amountToRefund = originalDepositAmount - neededAmount;

        if (neededAmount <= 0) {
            event.setCancelled(true);
            return;
        }

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            barrelInventory.close();
            barrelInventory.clear();

            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1, 1);

            int finalDepositAmount = Math.min(originalDepositAmount, neededAmount);
            waveManager.getCurrentWave().subtractAmount(depositItemType, finalDepositAmount);

            player.sendMessage(Component.text("You have deposited ", NamedTextColor.GREEN)
                    .append(Component.text(finalDepositAmount, NamedTextColor.GREEN))
                    .append(Component.text(" ", NamedTextColor.GREEN))
                    .append(Component.text("items into the barrel.", NamedTextColor.GREEN)));

            Team.DASHERS.addCoins(finalDepositAmount);
            waveManager.getWaveScoreboard().update();

            if (amountToRefund <= 0) return;

            ItemStack refundItem = new ItemStack(depositMaterial, amountToRefund);
            player.getInventory().addItem(refundItem);

            player.sendMessage(Component.text("You have been refunded ", NamedTextColor.RED)
                    .append(Component.text(amountToRefund, NamedTextColor.RED))
                    .append(Component.text(" ", NamedTextColor.RED))
                    .append(Component.text("items.", NamedTextColor.RED)));
        }, 1);
    }
}
