/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.chunks;

import io.sapphiremc.sapphire.api.nbt.NBTChunk;
import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.exceptions.NBTException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

public class ChunkNBTPersistentTest implements NBTTest {

    @Override
    public void test() throws Exception {
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                if (world.getLoadedChunks().length > 1) {
                    Chunk chunk = world.getLoadedChunks()[0];
                    NBTChunk comp = new NBTChunk(chunk);
                    NBTCompound persistentData = comp.getPersistentDataContainer();
                    persistentData.removeKey("Foo");
                    if (persistentData.hasKey("Foo")) {
                        throw new NBTException("Unable to remove key from Chunk!");
                    }
                    persistentData.setString("Foo", "Bar");
                    if (!new NBTChunk(chunk).getPersistentDataContainer().getString("Foo").equals("Bar")) {
                        throw new NBTException("Custom Data did not save to the Chunk!");
                    }
                }
            } catch (Exception ex) {
                throw new NBTException("Wasn't able to use NBTChunks!", ex);
            }
        }
    }
}
