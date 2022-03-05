/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.compounds;

import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.NBTContainer;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;

public class SubCompoundsTest implements NBTTest {

    private static final String COMP_TEST_KEY = "componentTest";
    private static final String STRING_TEST_KEY = "stringTest";
    private static final String INT_TEST_KEY = "intTest";
    private static final String DOUBLE_TEST_KEY = "doubleTest";
    private static final String BOOLEAN_TEST_KEY = "booleanTest";
    private static final String STRING_TEST_VALUE = "TestString";
    private static final int INT_TEST_VALUE = 42;
    private static final double DOUBLE_TEST_VALUE = 1.5d;
    private static final boolean BOOLEAN_TEST_VALUE = true;

    @Override
    public void test() throws Exception {
        NBTContainer cont = new NBTContainer();

        NBTCompound comp = cont.addCompound(COMP_TEST_KEY);
        comp.setString(STRING_TEST_KEY, STRING_TEST_VALUE + "2");
        comp.setInteger(INT_TEST_KEY, INT_TEST_VALUE * 2);
        comp.setDouble(DOUBLE_TEST_KEY, DOUBLE_TEST_VALUE * 2);

        if (cont.getCompound("invalide") != null) {
            throw new NbtApiException("An invalide compound did not return null!");
        }

        comp = null;

        comp = cont.getCompound(COMP_TEST_KEY);
        if (comp == null) {
            throw new NbtApiException("Wasn't able to get the NBTCompound!");
        }
        if (!comp.hasKey(STRING_TEST_KEY)) {
            throw new NbtApiException("Wasn't able to check a compound key!");
        }
        if (!(STRING_TEST_VALUE + "2").equals(comp.getString(STRING_TEST_KEY))
            || comp.getInteger(INT_TEST_KEY) != INT_TEST_VALUE * 2
            || comp.getDouble(DOUBLE_TEST_KEY) != DOUBLE_TEST_VALUE * 2
            || comp.getBoolean(BOOLEAN_TEST_KEY) == BOOLEAN_TEST_VALUE) {
            throw new NbtApiException("One key does not equal the original compound value!");
        }

        // Using getOrCreateCompound twice
        comp.getOrCreateCompound("someName").setString("test", "abc");
        if (!comp.getOrCreateCompound("someName").getString("test").equals("abc")) {
            throw new NbtApiException("getOrCreateCompound did not return the same compound!");
        }
    }
}
