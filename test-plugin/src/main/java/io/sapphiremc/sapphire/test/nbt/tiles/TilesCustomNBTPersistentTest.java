/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.test.nbt.tiles;

import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.NBTTileEntity;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.test.nbt.NBTTest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class TilesCustomNBTPersistentTest implements NBTTest {

    @Override
    public void test() throws Exception {
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 250, world.getSpawnLocation().getBlockZ());
                if (block.getType() == Material.AIR) {
                    block.setType(Material.CHEST);
                    NBTTileEntity comp = new NBTTileEntity(block.getState());
                    NBTCompound persistentData = comp.getPersistentDataContainer();
                    persistentData.setString("Foo", "Bar");
                    if (!new NBTTileEntity(block.getState()).getPersistentDataContainer().getString("Foo").equals("Bar")) {
                        block.setType(Material.AIR);
                        throw new NbtApiException("Custom Data did not save to the Tile!");
                    }
                    block.setType(Material.AIR);
                }
            } catch (Exception ex) {
                throw new NbtApiException("Wasn't able to use NBTTiles!", ex);
            }
        }
    }
}
