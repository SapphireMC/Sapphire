/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.file;

import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.NBTContainer;
import io.sapphiremc.sapphire.api.nbt.exceptions.NBTException;
import io.sapphiremc.sapphire.api.nbt.NBTFile;
import io.sapphiremc.sapphire.testplugin.SapphireTestPlugin;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;

import java.io.File;
import java.nio.file.Files;

public class FileTest implements NBTTest {

    @Override
    public void test() throws Exception {
        SapphireTestPlugin.getInstance().getDataFolder().mkdirs();
        File testFile = new File(SapphireTestPlugin.getInstance().getDataFolder(), "test.nbt");
        Files.deleteIfExists(testFile.toPath());
        NBTFile file = new NBTFile(testFile);
        file.addCompound("testcomp").setString("test1", "ok");
        NBTCompound comp = file.getCompound("testcomp");
        comp.setString("test2", "ok");
        file.setLong("time", System.currentTimeMillis());
        file.setString("test", "test");
        NBTCompound chunks = file.addCompound("chunks");
        NBTCompound chunk = chunks.addCompound("somechunk");
        NBTCompound block = chunk.addCompound("someblock");
        block.setString("type", "wool");
        file.save();

        if (!"wool".equals(block.getString("type"))) {
            throw new NBTException("SubCompounds did not work!");
        }

        NBTFile fileLoaded = new NBTFile(testFile);
        if (!fileLoaded.getString("test").equals("test")) {
            throw new NBTException("Wasn't able to load NBT File with the correct content!");
        }
        Files.deleteIfExists(fileLoaded.getFile().toPath());
        // String
        String str = fileLoaded.toString();
        NBTContainer rebuild = new NBTContainer(str);
        if (!str.equals(rebuild.toString())) {
            throw new NBTException("Wasn't able to parse NBT from a String!");
        }
    }
}
