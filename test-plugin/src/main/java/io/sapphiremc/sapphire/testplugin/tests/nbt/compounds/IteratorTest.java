/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.compounds;

import io.sapphiremc.sapphire.api.nbt.NBTContainer;
import io.sapphiremc.sapphire.api.nbt.NBTList;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorTest implements NBTTest {

    @Override
    public void test() throws Exception {
        NBTList<Integer> testList;

        testList = initIntegerList();
        testIterator(testList.iterator());

        testList = initIntegerList();
        testIterator(testList.listIterator());
    }

    private NBTList<Integer> initIntegerList() {
        NBTContainer comp = new NBTContainer();
        NBTList<Integer> list = comp.getIntegerList("test");
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        return list;
    }

    private static void testIterator(Iterator<Integer> iterator) {
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() == 1);
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() == 2);
        iterator.remove();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() == 3);
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() == 4);
        testNoMoreElements(iterator);
    }

    private static void testNoMoreElements(Iterator<Integer> iterator) {
        assertTrue(!iterator.hasNext());
        try {
            iterator.next();
        } catch (NoSuchElementException expected) {
            return;
        } catch (Exception e) {
            throw new NbtApiException("iterator threw wrong exception: " + e);
        }
        throw new NbtApiException("iterator did not throw exception");
    }

    private static void assertTrue(boolean condition) {
        if (!condition) {
            throw new NbtApiException("iterator test failed");
        }
    }
}
