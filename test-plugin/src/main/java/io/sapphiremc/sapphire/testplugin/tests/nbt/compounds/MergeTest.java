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

public class MergeTest implements NBTTest {

    @Override
    public void test() throws Exception {
        NBTContainer test1 = new NBTContainer();
        test1.setString("test1", "test");
        NBTContainer test2 = new NBTContainer();
        test2.setString("test2", "test");
        test2.addCompound("test").setLong("time", System.currentTimeMillis());
        test1.mergeCompound(test2);
        if (!test1.getString("test1").equals(test1.getString("test2"))) {
            throw new NBTException("Wasn't able to merge Compounds!");
        }
    }
}
