/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.blocks;

import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockTest implements NBTTest {

    @Override
    public void test() throws Exception {
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                if (world.getLoadedChunks().length > 1) {
                    Chunk chunk = world.getLoadedChunks()[0];
                    Block block = chunk.getBlock(0, 254, 0);
                    NBTCompound data = block.getNBTCompound();
                    data.removeKey("Too");
                    if (data.hasKey("Too")) {
                        throw new NbtApiException("Unable to remove key from Block!");
                    }
                    data.setString("Too", "Bar");
                    if (!block.getNBTCompound().getString("Too").equals("Bar")) {
                        throw new NbtApiException("Custom Data did not save to a Block!");
                    }
                }
            } catch (Exception ex) {
                throw new NbtApiException("Wasn't able to use NBTBlocks!", ex);
            }
        }
    }
}
