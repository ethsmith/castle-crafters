package dev.ethans.castlecrafters.event;

import dev.ethans.castlecrafters.state.InGameState;
import dev.ethans.castlecrafters.wave.WaveManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DepositListener implements Listener {

    private final WaveManager waveManager;

    public DepositListener(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    @EventHandler
    public void onDepositBarrel(InventoryMoveItemEvent event) {
        Inventory barrelInventory = event.getDestination();
        Inventory playerInventory = event.getSource();

        if (!(barrelInventory instanceof Barrel)) return;
        if (!(playerInventory instanceof PlayerInventory)) return;

        Player player = (Player) event.getSource().getHolder();
        ItemStack depositItem = event.getItem();
        Material depositItemType = depositItem.getType();

        int originalDepositAmount = depositItem.getAmount();
        int neededAmount = waveManager.getCurrentWave().getAmountLeft(depositItemType);
        int amountToRefund = originalDepositAmount - neededAmount;

        if (neededAmount <= 0) {
            event.setCancelled(true);
            return;
        }

        barrelInventory.close();
        barrelInventory.clear();

        int finalDepositAmount = Math.min(originalDepositAmount, neededAmount);
        waveManager.getCurrentWave().subtractAmount(depositItemType, finalDepositAmount);

        player.sendMessage(Component.text("You have deposited ", NamedTextColor.GREEN)
                .append(Component.text(finalDepositAmount, NamedTextColor.GREEN))
                .append(Component.text(" ", NamedTextColor.GREEN))
                .append(Component.text("items into the barrel.", NamedTextColor.GREEN)));

        if (amountToRefund <= 0) return;

        ItemStack refundItem = new ItemStack(depositItem.getType(), amountToRefund);
        player.getInventory().addItem(depositItem, refundItem);

        player.sendMessage(Component.text("You have been refunded ", NamedTextColor.RED)
                .append(Component.text(amountToRefund, NamedTextColor.RED))
                .append(Component.text(" ", NamedTextColor.RED))
                .append(Component.text("items.", NamedTextColor.RED)));
    }
}
