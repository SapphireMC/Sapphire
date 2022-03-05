/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.item;

import io.sapphiremc.sapphire.api.nbt.NBTItem;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class ItemMergingTest implements NBTTest {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();
        bookMeta.setAuthor("Author");
        bookMeta.setDisplayName("name");
        item.setItemMeta(bookMeta);

        NBTItem nbti = item.getNBT();
        nbti.setString("author", "New Author");
        nbti.setString("test", "value");

        nbti.mergeCustomNBT(item);
        if (!item.getNBT().hasKey("test"))
            throw new NbtApiException("Couldn't merge custom NBT tag!");
        if ("New Author".equals(item.getNBT().getString("author")))
            throw new NbtApiException("Vanilla NBT tag was merged when shouldn't!");

        nbti.setString("test", "New Value");
        nbti.mergeNBT(item);
        if (!"New Author".equals(item.getNBT().getString("author")) || !"New Value".equals(item.getNBT().getString("test")))
            throw new NbtApiException("Couldn't replace NBT tag while merging!");

        ItemStack test = new ItemStack(Material.WRITTEN_BOOK);
        nbti.applyNBT(test);
        if (!item.isSimilar(test))
            throw new NbtApiException("ItemStacks didn't match! " + item.getNBT() + " " + new NBTItem(test));

        test = new ItemStack(Material.STONE);
        nbti.applyNBT(test);
        if (!nbti.hasKey("test"))
            throw new NbtApiException("Couldn't merge custom NBT tag!");
        if (!item.getItemMeta().getDisplayName().equals(test.getItemMeta().getDisplayName()))
            throw new NbtApiException("Couldn't merge vanilla NBT tag!");

        nbti.setBoolean("remove", true);
        nbti.clearCustomNBT();
        if (nbti.hasKey("remove"))
            throw new NbtApiException("Couldn't clear custom NBT tags!");
        if (!nbti.hasKey("author"))
            throw new NbtApiException("Vanilla tag was removed!");
    }
}
