package me.itsmas.dailyrewards.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class for easily creating custom items
 *
 * @author Sam
 * @since 6/02/17
 * @version 1.1.0
 */
public class CustomItem extends ItemStack
{
    /**
     * Constructor for a custom item
     * @param material The item's material
     */
    public CustomItem(Material material)
    {
        this(material, 1);
    }

    /**
     * Constructor for a custom item
     * @param material The item's material
     * @param amount The size of the stack
     */
    public CustomItem(Material material, int amount)
    {
        super(material, amount);
    }

    /**
     * Sets the durability of the item
     * @param damage The durability
     * @return This class instance
     */
    public CustomItem setDamage(short damage)
    {
        setDurability(damage);

        return this;
    }

    /**
     * Sets the item's name
     * @param name The name
     * @return This class instance
     */
    public CustomItem setName(String name)
    {
        executeMetaMethod(meta -> meta.setDisplayName(name));

        return this;
    }

    /**
     * Sets the item's lore
     * @param lore The lore
     * @return This class instance
     */
    public CustomItem setLore(String... lore)
    {
        setLore(Arrays.asList(lore));

        return this;
    }

    /**
     * Sets the item's lore
     * @param lore The lore
     * @return This class instance
     */
    public CustomItem setLore(List<String> lore)
    {
        executeMetaMethod(meta -> meta.setLore(lore));

        return this;
    }

    /**
     * Enchants the item
     * @param enchantment The enchantment type
     * @param level The enchantment level
     * @return This class instance
     */
    public CustomItem addEnchant(Enchantment enchantment, int level)
    {
        addUnsafeEnchantment(enchantment, level);

        return this;
    }

    /**
     * Adds the enchantment 'glow' to the item
     * @return This class instance
     */
    public CustomItem addGlow()
    {
        addEnchant(Enchantment.DURABILITY, 999);

        executeMetaMethod(meta -> meta.addItemFlags(ItemFlag.HIDE_ENCHANTS));

        return this;
    }

    /**
     * Adds item flags to the item
     * @param flags The item flags to add
     * @return This class instance
     */
    public CustomItem addFlags(ItemFlag... flags)
    {
        executeMetaMethod(meta -> meta.addItemFlags(flags));

        return this;
    }

    /**
     * Makes the item unbreakable
     * @return This class instance
     */
    public CustomItem makeUnbreakable()
    {
        executeMetaMethod(meta -> meta.setUnbreakable(true));

        return this;
    }

    public CustomItem dupe()
    {
        return (CustomItem) clone();
    }

    /**
     * Executes a task on the ItemMeta
     * @param consumer What to execute on the ItemMeta
     */
    private void executeMetaMethod(Consumer<ItemMeta> consumer)
    {
        ItemMeta meta = getItemMeta();
        consumer.accept(meta);
        setItemMeta(meta);
    }
}

