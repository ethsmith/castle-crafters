package dev.ethans.castlecrafters.wave;

import org.bukkit.Material;

import java.util.Map;

public record Wave(int waveNumber, Map<Material, Integer> items) {

    public int getAmountLeft(Material material) {
        return items.getOrDefault(material, 0);
    }

    public void decrementAmountLeft(Material material) {
        int amount = getAmountLeft(material);
        amount--;

        if (amount > 0) {
            items.put(material, amount - 1);
            return;
        }

        items.remove(material);
    }
}
