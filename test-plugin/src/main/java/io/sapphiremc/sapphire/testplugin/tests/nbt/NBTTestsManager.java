/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt;

import io.sapphiremc.sapphire.testplugin.SapphireTestPlugin;
import io.sapphiremc.sapphire.testplugin.tests.nbt.blocks.BlockTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.chunks.ChunkNBTPersistentTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.EqualsTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.ForEachTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.GetterSetterTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.GsonTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.IteratorTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.ListTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.MergeTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.RemovingKeysTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.StreamTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.SubCompoundsTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.compounds.TypeTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.entity.EntityCustomNBTPersistentTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.entity.EntityTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.file.FileTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.gameprofile.GameProfileTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.item.DirectApplyTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.item.EmptyItemTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.item.ItemConvertionTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.item.ItemMergingTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.tiles.TileTest;
import io.sapphiremc.sapphire.testplugin.tests.nbt.tiles.TilesCustomNBTPersistentTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class NBTTestsManager {

    private final SapphireTestPlugin plugin;
    private final List<NBTTest> nbtTests = new ArrayList<>();
    private final Map<NBTTest, Exception> results = new HashMap<>();

    private boolean success = true;

    public NBTTestsManager(SapphireTestPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadTests() {
        if (plugin == null) return;

        // NBTCompound tests
        nbtTests.add(new EqualsTest());
        nbtTests.add(new ForEachTest());
        nbtTests.add(new GetterSetterTest());
        nbtTests.add(new GsonTest());
        nbtTests.add(new IteratorTest());
        nbtTests.add(new ListTest());
        nbtTests.add(new MergeTest());
        nbtTests.add(new RemovingKeysTest());
        nbtTests.add(new StreamTest());
        nbtTests.add(new SubCompoundsTest());
        nbtTests.add(new TypeTest());

        // NBTBlock test
        nbtTests.add(new BlockTest());

        // NBTChunk test
        nbtTests.add(new ChunkNBTPersistentTest());

        // NBTEntity tests
        nbtTests.add(new EntityTest());
        nbtTests.add(new EntityCustomNBTPersistentTest());

        // NBTFile test
        nbtTests.add(new FileTest());

        // NBTGameProfile test
        nbtTests.add(new GameProfileTest());

        // NBTItem tests
        nbtTests.add(new DirectApplyTest());
        nbtTests.add(new EmptyItemTest());
        nbtTests.add(new ItemConvertionTest());
        nbtTests.add(new ItemMergingTest());

        // NBTTileEntity tests
        nbtTests.add(new TileTest());
        nbtTests.add(new TilesCustomNBTPersistentTest());
    }

    public void runTests() {
        for (NBTTest nbtTest : nbtTests) {
            try {
                nbtTest.test();
                results.put(nbtTest, null);
                plugin.getLogger().info("Test " + nbtTest.getClass().getSimpleName() + " passed!");
            } catch (Exception ex) {
                results.put(nbtTest, ex);
                plugin.getLogger().log(Level.WARNING, "Error during '" + nbtTest.getClass().getSimpleName() + "' test!", ex);
            } catch (NoSuchFieldError ex) {
                plugin.getLogger().severe("Severe error during '" + nbtTest.getClass().getSimpleName() + "' test!");
                plugin.getLogger().severe("This version of Item-NBT-API seems to be broken! Canceled the other tests!");
                throw ex;
            }
        }

        for (Map.Entry<NBTTest, Exception> entry : results.entrySet()) {
            if (entry.getValue() != null) {
                this.success = false;
                plugin.getLogger().info(entry.getKey().getClass().getSimpleName() + ": " + entry.getValue().getMessage());
            }
        }
    }

    public boolean isSuccess() {
        return this.success;
    }
}
