package dev.ethans.castlecrafters.wave;

import org.bukkit.Material;

import java.util.Map;

public record Wave(int waveNumber, Map<Material, Integer> items) {

    public int getAmountLeft(Material material) {
        return items.getOrDefault(material, 0);
    }

    public void subtractAmount(Material material, int amountToDecrement) {
        int amount = getAmountLeft(material) - amountToDecrement;

        if (amount > 0) {
            items.put(material, amount);
            return;
        }

        items.remove(material);
    }
}
