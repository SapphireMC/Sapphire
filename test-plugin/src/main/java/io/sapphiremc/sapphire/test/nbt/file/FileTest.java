/*
 * Copyright (c) 2022 DenaryDev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package io.sapphiremc.sapphire.test.nbt.file;

import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.NBTContainer;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.api.nbt.NBTFile;
import io.sapphiremc.sapphire.test.SapphireTestPlugin;
import io.sapphiremc.sapphire.test.nbt.NBTTest;
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
            throw new NbtApiException("SubCompounds did not work!");
        }

        NBTFile fileLoaded = new NBTFile(testFile);
        if (!fileLoaded.getString("test").equals("test")) {
            throw new NbtApiException("Wasn't able to load NBT File with the correct content!");
        }
        Files.deleteIfExists(fileLoaded.getFile().toPath());
        // String
        String str = fileLoaded.asNBTString();
        NBTContainer rebuild = new NBTContainer(str);
        if (!str.equals(rebuild.asNBTString())) {
            throw new NbtApiException("Wasn't able to parse NBT from a String!");
        }
    }
}
