package dev.ethans.castlecrafters.state;

import dev.ethans.castlecrafters.CastleCrafters;
import dev.ethans.castlecrafters.state.base.GameState;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class WaitingState extends GameState {

    public WaitingState() {
        super(CastleCrafters.getInstance());
    }

    @Override
    public @NotNull Duration getDuration() {
        return Duration.ZERO;
    }

    @Override
    protected void onStart() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    protected void onEnd() {

    }

    @Override
    public boolean isReadyToEnd() {
        return true;
    }
}
