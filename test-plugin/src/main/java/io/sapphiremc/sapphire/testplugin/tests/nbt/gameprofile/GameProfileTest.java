/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package io.sapphiremc.sapphire.testplugin.tests.nbt.gameprofile;

import com.mojang.authlib.GameProfile;
import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.api.nbt.NBTGameProfile;
import io.sapphiremc.sapphire.testplugin.tests.nbt.NBTTest;

import java.util.UUID;

public class GameProfileTest implements NBTTest {

    @Override
    public void test() throws Exception {
        UUID uuid = UUID.randomUUID();
        GameProfile profile = new GameProfile(uuid, "random");
        NBTCompound nbt = NBTGameProfile.toNBT(profile);
        profile = null;
        profile = NBTGameProfile.fromNBT(nbt);
        if (profile == null || !profile.getId().equals(uuid)) {
            throw new NbtApiException("Error when converting a GameProfile from/to NBT!");
        }
    }
}
