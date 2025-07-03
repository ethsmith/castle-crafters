package dev.ethans.castlecrafters.wave;

import org.bukkit.Material;
import org.bukkit.inventory.ItemType;

import java.util.Map;

public record Wave(int waveNumber, Map<ItemType, Integer> items) {

    public int getAmountLeft(ItemType type) {
        return items.getOrDefault(type, 0);
    }

    public void subtractAmount(ItemType type, int amountToDecrement) {
        int amount = getAmountLeft(type) - amountToDecrement;

        if (amount > 0) {
            items.put(type, amount);
            return;
        }

        items.remove(type);
    }
}
