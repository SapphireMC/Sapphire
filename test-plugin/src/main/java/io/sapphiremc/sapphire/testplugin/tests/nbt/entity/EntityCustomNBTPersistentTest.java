/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.entity;

import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.NBTEntity;
import io.sapphiremc.sapphire.api.nbt.exceptions.NBTException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

public class EntityCustomNBTPersistentTest implements NBTTest {

    @Override
    public void test() throws Exception {
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                if (!world.getEntitiesByClasses(Animals.class, Monster.class).isEmpty()) {
                    Entity ent = world.getEntitiesByClasses(Animals.class, Monster.class).iterator().next();
                    NBTEntity nbtEnt = ent.getNBT();
                    NBTCompound comp = nbtEnt.getPersistentDataContainer();
                    comp.setString("Hello", "World");
                    NBTEntity nbtent = ent.getNBT();
                    if (!nbtent.toString().contains("Hello:\"World\"")) {
                        throw new NBTException("Custom Data did not save to the Entity!");
                    }
                    comp.removeKey("Hello");

                }
            } catch (Exception ex) {
                throw new NBTException("Wasn't able to use NBTEntities!", ex);
            }
        }
    }
}
