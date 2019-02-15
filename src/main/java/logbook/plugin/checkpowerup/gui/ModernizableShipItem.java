package logbook.plugin.checkpowerup.gui;

import java.util.function.Function;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

import logbook.bean.Ship;
import logbook.bean.ShipMst;
import logbook.internal.Ships;

/**
 * 改装レベル不足の艦娘テーブルの行
 *
 */
public class ModernizableShipItem {

    /** ID */
    @Getter @Setter
    private Integer id;

    /** 艦娘 */
    @Getter @Setter
    private Ship ship;

    /** Lv */
    @Getter @Setter
    private Integer lv;

    /** ロック */
    @Getter @Setter
    private Boolean locked;

    /** 近代化改修 */
    @Setter
    private List<Integer> kyouka;

    /** 火力 */
    @Setter
    private Integer karyoku;

    /** 火力上限 */
    @Setter
    private Integer karyokuLimit;

    /** 雷装 */
    @Setter
    private Integer raisou;

    /** 雷装上限 */
    @Setter
    private Integer raisouLimit;

    /** 対空 */
    @Setter
    private Integer taiku;

    /** 対空上限 */
    @Setter
    private Integer taikuLimit;

    /** 装甲 */
    @Setter
    private Integer soukou;

    /** 装甲上限 */
    @Setter
    private Integer soukouLimit;

    /** 運 */
    @Setter
    private Integer lucky;

    /** 運上限 */
    @Setter
    private Integer luckyLimit;

    /** 耐久 */
    @Setter
    private Integer taikyu;

    /** 耐久上限 */
    @Setter
    private Integer taikyuMax;

    /** 対潜 */
    @Setter
    private Integer taisen;

    /** 素の対潜の有無 */
    private Boolean hasTaisen;

    private static int VHP = 2;

    private static int VAS = 9;

    /**
     * 火力を取得します。
     * @param karyoku 火力
     * @return 火力
     */
    public Integer getKaryoku() {
        return this.karyokuLimit - this.kyouka.get(0);
    }

    /**
     * 雷装を取得します。
     * @param raisou 雷装
     * @return 雷装
     */
    public Integer getRaisou() {
        return this.raisouLimit - this.kyouka.get(1);
    }

    /**
     * 対空を取得します。
     * @param taiku 対空
     * @return 対空
     */
    public Integer getTaiku() {
        return this.taikuLimit - this.kyouka.get(2);
    }

    /**
     * 装甲を取得します。
     * @param soukou 装甲
     * @return 装甲
     */
    public Integer getSoukou() {
        return this.soukouLimit - this.kyouka.get(3);
    }

    /**
     * 運を取得します。
     * @param lucky 運
     * @return 運
     */
    public Integer getLucky() {
        return this.luckyLimit - this.kyouka.get(4);
    }

    /**
     * 耐久を取得します。
     * @param taikyu 耐久
     * @return 耐久
     */
    public Integer getTaikyu() {
        return this.taikyuLimit() - this.kyouka.get(5);
    }

    /**
     * 対潜を取得します。
     * @param taisen 対潜
     * @return 対潜
     */
    public Integer getTaisen() {
        return this.taisenLimit() - this.kyouka.get(6);
    }

    /**
     * 素の対潜の有無を設定します。
     * @param taisenMax 対潜の最大値?
     */
    public void setTaisenMax(Integer taisenMax) {
        this.hasTaisen = 0 < taisenMax;
    }


    /**
     * 改修の上限値
     */
    private Integer taikyuLimit() {
        Integer limit = this.taikyuMax - (this.taikyu - this.kyouka.get(5));

        return limit < 2 ? limit : this.VHP;
    }
    private Integer taisenLimit() {
        return this.hasTaisen ? this.VAS : 0;
    }

    /**
     * 近代化改修可能な艦娘テーブルの行を作成します
     *
     * @param ship 艦娘
     * @return 近代化改修可能な艦娘テーブルの行
     */
    public static ModernizableShipItem toShipItem(Ship ship) {
        ModernizableShipItem item = new ModernizableShipItem();
        Optional<ShipMst> shipMst = Ships.shipMst(ship);
        Function<List<Integer>, Integer> getLimit = (mst) -> { return mst.get(1) - mst.get(0); };

        item.setId(ship.getId());
        item.setShip(ship);
        item.setLv(ship.getLv());
        item.setLocked(ship.getLocked());

        item.setKyouka(ship.getKyouka());

        item.setKaryoku(ship.getKaryoku().get(0));
        item.setKaryokuLimit(shipMst.map(ShipMst::getHoug).map(getLimit).orElse(0));

        item.setRaisou(ship.getRaisou().get(0));
        item.setRaisouLimit(shipMst.map(ShipMst::getRaig).map(getLimit).orElse(0));

        item.setTaiku(ship.getTaiku().get(0));
        item.setTaikuLimit(shipMst.map(ShipMst::getTyku).map(getLimit).orElse(0));

        item.setSoukou(ship.getSoukou().get(0));
        item.setSoukouLimit(shipMst.map(ShipMst::getSouk).map(getLimit).orElse(0));

        item.setLucky(ship.getLucky().get(0));
        item.setLuckyLimit(shipMst.map(ShipMst::getLuck).map(getLimit).orElse(0));

        item.setTaikyu(ship.getMaxhp());
        item.setTaikyuMax(shipMst.map(ShipMst::getTaik).map(mst -> mst.get(1)).orElse(0));

        item.setTaisen(ship.getTaisen().get(0));
        item.setTaisenMax(ship.getTaisen().get(1));

        return item;
    }
}