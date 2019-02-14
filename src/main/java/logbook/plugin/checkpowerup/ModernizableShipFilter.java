package logbook.plugin.checkpowerup;

import java.util.function.Predicate;

import lombok.Builder;

import logbook.internal.Operator;

import logbook.plugin.checkpowerup.gui.ModernizableShipItem;

/**
 * 近代化改修可能な艦娘のフィルター
 *
 */
@FunctionalInterface
public interface ModernizableShipFilter extends Predicate<ModernizableShipItem> {

    @Builder
    public static class LevelFilter implements ModernizableShipFilter {

        /** レベル */
        private int value;

        /** レベル条件 */
        private Operator type;

        @Override
        public boolean test(ModernizableShipItem ship) {
            if (ship == null)
                return false;
            return this.type.compare(ship.getLv(), this.value);
        }

    }

    @Builder
    public static class LockedFilter implements ModernizableShipFilter {

        /** ロック */
        private boolean locked;

        @Override
        public boolean test(ModernizableShipItem ship) {
            if (ship == null)
                return false;
            return this.locked == ship.getLocked().booleanValue();
        }

    }

}
