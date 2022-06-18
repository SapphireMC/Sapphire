/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.item;

import io.sapphiremc.sapphire.api.nbt.NBTItem;
import io.sapphiremc.sapphire.api.nbt.exceptions.NBTException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EmptyItemTest implements NBTTest {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        NBTItem nbti = item.getNBT();
        if (nbti.getBoolean("test") == null || nbti.getString("test") == null)
            throw new NBTException("Getters return null instead of the default value");

        try {
            Material barrel = Material.valueOf("BARREL");
            item = new ItemStack(barrel);
            nbti = item.getNBT();
        } catch (IllegalArgumentException ex) {
            // old version
        }
    }
}
