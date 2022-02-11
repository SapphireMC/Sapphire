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
package io.sapphiremc.sapphire.test.nbt.gameprofile;

import com.mojang.authlib.GameProfile;
import io.sapphiremc.sapphire.api.nbt.NBTCompound;
import io.sapphiremc.sapphire.api.nbt.exceptions.NbtApiException;
import io.sapphiremc.sapphire.api.nbt.NBTGameProfile;
import io.sapphiremc.sapphire.test.nbt.NBTTest;
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
