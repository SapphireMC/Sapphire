/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.test.nbt.item;

import com.google.common.collect.Lists;
import io.sapphiremc.sapphire.api.nbt.NBTContainer;
import io.sapphiremc.sapphire.api.nbt.NBTItem;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.test.nbt.NBTTest;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemConvertionTest implements NBTTest {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Lists.newArrayList("Firest Line", "Second Line"));
        item.setItemMeta(meta);
        String nbt = NBTItem.convertItemtoNBT(item).toString();
        if (!nbt.contains("Firest Line") || !nbt.contains("Second Line"))
            throw new NbtApiException("The Item nbt '" + nbt + "' didn't contain the lore");
        ItemStack rebuild = NBTItem.convertNBTtoItem(new NBTContainer(nbt));
        if (!item.isSimilar(rebuild))
            throw new NbtApiException("Rebuilt item did not match the original!");

        NBTContainer cont = new NBTContainer();
        cont.setItemStack("testItem", item);
        if (!cont.getItemStack("testItem").isSimilar(item))
            throw new NbtApiException("Rebuilt item did not match the original!");
    }
}
