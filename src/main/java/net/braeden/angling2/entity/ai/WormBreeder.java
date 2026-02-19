package net.braeden.angling2.entity.ai;

/**
 * Implemented by entities that can breed with worms.
 * Tracks a countdown timer set when the entity is fed a worm.
 */
public interface WormBreeder {
    int getWormBredTimer();
    void setWormBredTimer(int timer);

    default boolean wantsToBreed() {
        return getWormBredTimer() > 0;
    }

    default void clearWormBred() {
        setWormBredTimer(0);
    }
}
