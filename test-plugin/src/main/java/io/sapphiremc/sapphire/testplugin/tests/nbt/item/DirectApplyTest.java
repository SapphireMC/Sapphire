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

public class DirectApplyTest implements NBTTest {

    @Override
    public void test() throws Exception {
        ItemStack baseItem = new ItemStack(Material.STONE);
        NBTItem nbti = baseItem.getNBT(true);
        nbti.setString("SomeKey", "SomeValue");
        if (!baseItem.equals(nbti.getItem()) || !baseItem.getNBT().hasKey("SomeKey")) {
            throw new NBTException("The item's where not equal!");
        }
    }
}
