/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.test.nbt.compounds;

import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.NBTContainer;
import io.sapphiremc.sapphire.api.nbt.NBTType;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.test.nbt.NBTTest;

public class TypeTest implements NBTTest {

    @Override
    public void test() throws Exception {
        NBTCompound comp = new NBTContainer();
        comp.setString("s", "test");
        comp.setInteger("i", 42);
        comp.addCompound("c");
        if (comp.getType("s") != NBTType.NBTTagString || comp.getType("i") != NBTType.NBTTagInt
            || comp.getType("c") != NBTType.NBTTagCompound)
            throw new NbtApiException("One parsed type did not match what it should have been!");
    }
}
