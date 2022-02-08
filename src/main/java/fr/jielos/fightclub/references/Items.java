package fr.jielos.fightclub.references;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import fr.jielos.fightclub.components.ItemBuilder;

public enum Items {

	EQUIPEMENT(new ItemStack[]{
			new ItemStack(Material.EXP_BOTTLE, 16), new ItemStack(Material.INK_SACK, 8, (short) 4),
			new ItemBuilder(new ItemStack(Material.ENCHANTED_BOOK)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
			new ItemBuilder(new ItemStack(Material.ENCHANTED_BOOK)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
			new ItemBuilder(new ItemStack(Material.ENCHANTED_BOOK)).addEnchant(Enchantment.DAMAGE_ALL, 1).toItemStack(),
			new ItemBuilder(new ItemStack(Material.ENCHANTED_BOOK)).addEnchant(Enchantment.ARROW_DAMAGE, 1).toItemStack(),
			new ItemStack(Material.BOW, 1),
			new Potion(PotionType.INSTANT_DAMAGE, 1).splash().toItemStack(1), new Potion(PotionType.REGEN, 1).splash().toItemStack(1),
			new ItemStack(Material.GOLDEN_APPLE, 1), new ItemStack(Material.ARROW, 8)});
	
	final ItemStack[] items;
	Items(final ItemStack... items) {
		this.items = items;
	}
	
	public ItemStack[] getContent() {
		return items;
	}
}
