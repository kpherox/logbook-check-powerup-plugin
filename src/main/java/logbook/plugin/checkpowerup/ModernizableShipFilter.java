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

    @Builder
    public static class ModernizedFilter implements ModernizableShipFilter {

        /** 火力 */
        private boolean karyoku;

        /** 雷装 */
        private boolean raisou;

        /** 対空 */
        private boolean taiku;

        /** 装甲 */
        private boolean soukou;

        /** 運 */
        private boolean lucky;

        /** 耐久 */
        private boolean taikyu;

        /** 対潜 */
        private boolean taisen;

        @Override
        public boolean test(ModernizableShipItem ship) {
            if (ship == null)
                return false;

            return this.karyoku && ship.getKaryoku() > 0
                || this.raisou && ship.getRaisou() > 0
                || this.taiku && ship.getTaiku() > 0
                || this.soukou && ship.getSoukou() > 0
                || this.lucky && ship.getLucky() > 0
                || this.taikyu && ship.getTaikyu() > 0
                || this.taisen && ship.getTaisen() > 0;
        }

    }

}
