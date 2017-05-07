/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.natrolite.cause;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public final class NamedCause {

  public static final String BLOCK_EVENT = "BlockEvent";

  public static final String BLOCK_PROTECTED = "BlockProtected";

  public static final String DECAY = "Decay";

  public static final String FAKE_PLAYER = "FakePlayer";

  public static final String FIRE_SPREAD = "FireSpread";

  public static final String HIT_TARGET = "HitTarget";

  public static final String IGNITER = "Igniter";

  public static final String LIQUID_FLOW = "LiquidFlow";

  public static final String NOTIFIER = "Notifier";

  public static final String OWNER = "Owner";

  public static final String PHYSICAL = "Physical";

  public static final String PISTON_EXTEND = "PistonExtend";

  public static final String PISTON_RETRACT = "PistonRetract";

  public static final String PLAYER_BREAK = "PlayerBreak";

  public static final String PLAYER_PLACE = "PlayerPlace";

  public static final String PLAYER_SIMULATED = "PlayerSimulated";

  public static final String SOURCE = "Source";

  public static final String THROWER = "Thrower";
  private final String name;
  private final Object object;

  private NamedCause(String name, Object object) {
    this.name = checkNotNull(name);
    this.object = checkNotNull(object);
  }

  /**
   * Creates a new {@link NamedCause} with the object being "named"
   * as {@link NamedCause#SOURCE}.
   *
   * @param object The object being the source
   * @return The new named cause
   */
  public static NamedCause source(Object object) {
    return of(SOURCE, object);
  }

  /**
   * Creates a new {@link NamedCause} with the object being "named"
   * as {@link NamedCause#OWNER}.
   *
   * @param object The object being the owner
   * @return The new named cause
   */
  public static NamedCause owner(Object object) {
    return of(OWNER, object);
  }

  /**
   * Creates a new {@link NamedCause} with the object being "named"
   * as {@link NamedCause#NOTIFIER}.
   *
   * @param object The object being the notifier
   * @return The new named cause
   */
  public static NamedCause notifier(Object object) {
    return of(NOTIFIER, object);
  }

  /**
   * Creates a new {@link NamedCause} with the object being "named"
   * as {@link NamedCause#HIT_TARGET}.
   *
   * @param object The object being the hit target
   * @return The new named cause
   */
  public static NamedCause hitTarget(Object object) {
    return of(HIT_TARGET, object);
  }

  /**
   * Creates a new {@link NamedCause} with the object being "named"
   * as a {@link NamedCause#PLAYER_SIMULATED} player.
   *
   * @param object The simulated player object
   * @return The new named cause
   */
  public static NamedCause simulated(Object object) {
    checkArgument(object instanceof Player || object instanceof OfflinePlayer,
        "Invalid object provided for player simulated methods");
    return of(PLAYER_SIMULATED, object);
  }

  /**
   * Creates a new {@link NamedCause} with the object being "named"
   * as a {@link NamedCause#PLAYER_SIMULATED} player.
   *
   * @param player The simulated player
   * @return The new named cause
   */
  public static NamedCause simulated(Player player) {
    return of(PLAYER_SIMULATED, player);
  }

  public static NamedCause simulated(OfflinePlayer offlinePlayer) {
    return of(PLAYER_SIMULATED, offlinePlayer);
  }

  /**
   * Constructs a new {@link NamedCause} where the {@code name} is not
   * {@code null} or {@link String#isEmpty() empty}. The object as well
   * can not be another instance of a {@link NamedCause}.
   *
   * @param name   The name of the object
   * @param object The object itself
   * @return The new named cause
   */
  public static NamedCause of(String name, Object object) {
    checkNotNull(name, "Cannot have a null name!");
    checkNotNull(object, "Cannot have a null object!");
    checkArgument(!name.isEmpty(), "The name cannot be empty!");
    checkArgument(!(object instanceof NamedCause), "Cannot nest a named cause in a named cause!");
    return new NamedCause(name, object);
  }

  /**
   * Gets the name of this {@link NamedCause}.
   *
   * @return The name of this cause
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the {@link Object} of this {@link NamedCause}.
   *
   * @return The object
   */
  public Object getCauseObject() {
    return this.object;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.name, this.object);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final NamedCause other = (NamedCause) obj;
    return Objects.equal(this.name, other.name)
        && Objects.equal(this.object, other.object);
  }

  @Override
  @SuppressWarnings("deprecation") //Bukkit uses Guava 17 and Spigot Guava 18
  public String toString() {
    return Objects.toStringHelper(this)
        .add("name", this.name)
        .add("object", this.object)
        .toString();
  }
}
