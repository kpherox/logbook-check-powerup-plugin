package logbook.plugin.checkpowerup;

import java.util.List;
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

    @Builder
    public static class ModernizedFilter implements ModernizableShipFilter {

        /** 近代化改修項目 */
        private List<Boolean> modernizeds;

        @Override
        public boolean test(ModernizableShipItem ship) {
            if (ship == null)
                return false;

            return this.modernizeds.get(0) && ship.getKaryoku() > 0
                || this.modernizeds.get(1) && ship.getRaisou() > 0
                || this.modernizeds.get(2) && ship.getTaiku() > 0
                || this.modernizeds.get(3) && ship.getSoukou() > 0
                || this.modernizeds.get(4) && ship.getLucky() > 0
                || this.modernizeds.get(5) && ship.getTaikyu() > 0
                || this.modernizeds.get(6) && ship.getTaisen() > 0;
        }

    }

}
