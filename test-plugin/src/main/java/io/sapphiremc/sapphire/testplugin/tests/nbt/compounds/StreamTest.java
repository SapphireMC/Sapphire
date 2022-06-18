/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.compounds;

import io.sapphiremc.sapphire.api.nbt.NBTContainer;
import io.sapphiremc.sapphire.api.nbt.exceptions.NBTException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class StreamTest implements NBTTest {

    @Override
    public void test() throws Exception {
        NBTContainer base = new NBTContainer();
        base.addCompound("sub").setString("hello", "world");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        base.getCompound("sub").writeCompound(outStream);
        byte[] data = outStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        NBTContainer container = new NBTContainer(inputStream);
        if (!container.toString().equals(base.getCompound("sub").toString())) {
            throw new NBTException("Component content did not match! " + base.getCompound("sub") + " " + container);
        }
    }
}
