package org.natrolite.event.game;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.natrolite.cause.Cause;
import org.natrolite.event.NatroliteEvent;
import org.natrolite.game.Game;
import org.natrolite.game.GameState;

public class GameStateChangedEvent extends Event implements NatroliteEvent {

  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final Cause cause;
  private final Game game;
  private final GameState from;
  private final GameState to;

  public GameStateChangedEvent(Cause cause, Game game, GameState from, GameState to) {
    this.cause = Preconditions.checkNotNull(cause);
    this.game = Preconditions.checkNotNull(game);
    this.from = Preconditions.checkNotNull(from);
    this.to = Preconditions.checkNotNull(to);
  }

  public Game getGame() {
    return game;
  }

  public GameState getFrom() {
    return from;
  }

  public GameState getTo() {
    return to;
  }

  @Override
  public Cause getCause() {
    return cause;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GameStateChangedEvent that = (GameStateChangedEvent) o;
    return Objects.equals(cause, that.cause) &&
        Objects.equals(game, that.game) &&
        Objects.equals(from, that.from) &&
        Objects.equals(to, that.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cause, game, from, to);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("cause", cause)
        .add("game", game)
        .add("from", from)
        .add("to", to)
        .toString();
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }
}
