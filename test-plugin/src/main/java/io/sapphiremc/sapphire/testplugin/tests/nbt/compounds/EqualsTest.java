/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.compounds;

import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.NBTContainer;
import io.sapphiremc.sapphire.api.nbt.NBTItem;
import io.sapphiremc.sapphire.api.nbt.exceptions.NBTException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EqualsTest implements NBTTest {

    @Override
    public void test() throws Exception {
        NBTContainer cont = new NBTContainer();
        cont.setString("hello", "world");
        cont.setInteger("theAnswer", 42);
        cont.addCompound("sub").setString("me", "too");
        cont.getStringList("somelist").addAll(Arrays.asList("a", "b", "c"));
        ItemStack item = new ItemStack(Material.STONE);
        NBTItem nbti = new NBTItem(item);
        NBTCompound customData = nbti.addCompound("customData");
        // reverse order
        customData.addCompound("sub").setString("me", "too");
        customData.setInteger("theAnswer", 42);
        customData.setString("hello", "world");
        customData.getStringList("somelist").addAll(Arrays.asList("a", "b", "c"));
        if (!customData.equals(cont)) {
            throw new NBTException("Compounds did not match! " + customData + " " + cont);
        }

        // empty test

        if (!new NBTContainer().equals(new NBTContainer())) {
            throw new NBTException("Two empty tags did not match!");
        }

        // not equal test
        NBTContainer part1 = new NBTContainer();
        part1.setString("a", "a");
        part1.setString("b", "b");
        NBTContainer part2 = new NBTContainer();
        part2.setString("a", "a");
        part2.setString("b", "a");
        if (part1.equals(part2)) {
            throw new NBTException("Missmatched nbt did match!");
        }
    }
}
