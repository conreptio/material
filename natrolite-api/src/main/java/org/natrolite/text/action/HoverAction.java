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

package org.natrolite.text.action;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.xnity.odium.Odium;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.natrolite.Identifiable;
import org.natrolite.text.Text;

/**
 * Represents a {@link TextAction} that responds to hovers.
 *
 * @param <R> The type of the result of the action
 */
public abstract class HoverAction<R> extends TextAction<R> {

  /**
   * Constructs a new {@link HoverAction} with the given result.
   *
   * @param result The result of the hover action
   */
  public HoverAction(R result) {
    super(result);
  }

  @Override
  public void applyTo(Text.Builder builder) {
    builder.onHover(this);
  }

  /**
   * Shows some text.
   */
  public static final class ShowText extends HoverAction<Text> {

    ShowText(Text text) {
      super(text);
    }

    @Override
    public void apply(JsonObject object, JsonSerializationContext context) {
      object.addProperty("action", "show_text");
      object.add("value", context.serialize(result));
    }
  }

  /**
   * Shows information about an item.
   */
  public static final class ShowItem extends HoverAction<ItemStack> {

    /**
     * Constructs a new {@link ShowItem} instance that will show information
     * about an item when it is hovered.
     *
     * @param item The item to display
     */
    ShowItem(ItemStack item) {
      super(item);
    }

    @Override
    public void apply(JsonObject object, JsonSerializationContext context) {
      object.addProperty("action", "show_item");
      object.addProperty("value", Odium.itemToJson(getResult()).orElse("{}"));
    }
  }

  /**
   * Shows information about an entity.
   */
  public static final class ShowEntity extends HoverAction<ShowEntity.Ref> {

    /**
     * Constructs a new {@link ShowEntity} that will show information about
     * an entity when it is hovered.
     *
     * @param ref The reference to the entity to display
     */
    ShowEntity(Ref ref) {
      super(ref);
    }

    @Override
    public void apply(JsonObject object, JsonSerializationContext context) {
      JsonObject tmp = new JsonObject();
      tmp.addProperty("id", getResult().getUniqueId().toString());
      tmp.addProperty("name", getResult().getName());
      getResult().getType().ifPresent(type -> {
        if (type.getName() != null) {
          tmp.addProperty("type", "minecraft:" + type.getName());
        }
      });
      object.addProperty("action", "show_entity");
      object.addProperty("value", tmp.toString());
    }

    /**
     * Represents a reference to an entity, used in the underlying JSON of
     * the show entity action.
     */
    public static final class Ref implements Identifiable {

      private final UUID uuid;
      private final String name;
      private final Optional<EntityType> type;

      /**
       * Constructs a Ref to an entity.
       *
       * @param uuid The UUID of the entity
       * @param name The name of the entity
       * @param type The type of the entity
       */
      public Ref(UUID uuid, String name, @Nullable EntityType type) {
        this(uuid, name, Optional.ofNullable(type));
      }

      /**
       * Constructs a Ref to an entity.
       *
       * @param uuid The UUID of the entity
       * @param name The name of the entity
       */
      public Ref(UUID uuid, String name) {
        this(uuid, name, Optional.empty());
      }

      /**
       * Constructs a Ref, given an {@link Entity}.
       *
       * @param entity The entity
       * @param name   The name of the entity
       */
      public Ref(Entity entity, String name) {
        this(entity.getUniqueId(), name, entity.getType());
      }

      /**
       * Constructs a Ref directly.
       *
       * @param uuid The UUID
       * @param name The name
       * @param type The type
       */
      protected Ref(UUID uuid, String name, Optional<EntityType> type) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
      }

      /**
       * Retrieves the UUID that this {@link Ref} refers to.
       *
       * @return The UUID
       */
      @Override
      public UUID getUniqueId() {
        return this.uuid;
      }

      /**
       * Retrieves the name that this {@link Ref} refers to.
       *
       * @return The name
       */
      public String getName() {
        return this.name;
      }

      /**
       * Retrieves the type that this {@link Ref} refers to, if it exists.
       *
       * @return The type, or {@link Optional#empty()}
       */
      public Optional<EntityType> getType() {
        return this.type;
      }

      @Override
      public boolean equals(Object obj) {
        if (super.equals(obj)) {
          return true;
        }
        if (!(obj instanceof Ref)) {
          return false;
        }
        Ref that = (Ref) obj;
        return this.uuid.equals(that.uuid)
            && this.name.equals(that.name)
            && this.type.equals(that.type);
      }

      @Override
      public int hashCode() {
        return Objects.hashCode(this.uuid, this.name, this.type);
      }

      @Override
      public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("uuid", this.uuid)
            .add("name", this.name)
            .add("type", this.type)
            .toString();
      }
    }
  }
}
