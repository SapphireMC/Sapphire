/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.compounds;

import io.sapphiremc.sapphire.api.nbt.NBTCompoundList;
import io.sapphiremc.sapphire.api.nbt.NBTContainer;
import io.sapphiremc.sapphire.api.nbt.NBTListCompound;
import io.sapphiremc.sapphire.api.nbt.exceptions.NBTException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;

import java.util.ListIterator;

public class ForEachTest implements NBTTest {

    @SuppressWarnings("unused")
    @Override
    public void test() throws Exception {
        NBTContainer comp = new NBTContainer();
        NBTCompoundList compList = comp.getCompoundList("testkey");
        if (compList != null) {
            compList.addCompound().setInteger("id", 1);
            compList.addCompound().setInteger("id", 2);
            compList.addCompound().setInteger("id", 3);
            int count = 0;
            for (NBTListCompound listComp : compList) {
                count++;
            }
            if (count != compList.size())
                throw new NBTException("For loop did not get all Entries!");
            count = 0;
            ListIterator<NBTListCompound> lit = compList.listIterator();
            while (lit.hasNext()){
                lit.next();
                count++;
            }
            if (count != compList.size())
                throw new NBTException("ListIterator did not get all Entries!");
            count = 0;
            while (lit.hasPrevious()){
                lit.previous();
                count++;
            }
            if (count != compList.size())
                throw new NBTException("ListIterator previous did not get all Entries!");
        }
    }
}
