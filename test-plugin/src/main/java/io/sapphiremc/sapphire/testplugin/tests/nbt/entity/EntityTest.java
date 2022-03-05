/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.entity;

import io.sapphiremc.sapphire.api.nbt.NBTEntity;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;

public class EntityTest implements NBTTest {

    @Override
    public void test() throws Exception {
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                if (!world.getEntitiesByClasses(Animals.class, Monster.class).isEmpty()) {
                    NBTEntity nbte = world.getEntitiesByClasses(Animals.class, Monster.class).iterator().next().getNBT();
                    nbte.setString("INVALIDEKEY", "test");
                }
            } catch (Exception ex) {
                throw new NbtApiException("Wasn't able to use NBTEntities!", ex);
            }
        }
    }
}
