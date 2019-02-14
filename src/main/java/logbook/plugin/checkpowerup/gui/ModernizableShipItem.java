package logbook.plugin.checkpowerup.gui;

import java.util.function.Function;
import java.util.List;
import java.util.Optional;

import logbook.bean.Ship;
import logbook.bean.ShipMst;
import logbook.internal.Ships;

/**
 * 改装レベル不足の艦娘テーブルの行
 *
 */
public class ModernizableShipItem {

    /** ID */
    private Integer id;

    /** 艦娘 */
    private Ship ship;

    /** Lv */
    private Integer lv;

    /** ロック */
    private Boolean locked;

    /** 近代化改修 */
    private List<Integer> kyouka;

    /** 火力 */
    private Integer karyoku;

    /** 火力上限 */
    private Integer karyokuLimit;

    /** 雷装 */
    private Integer raisou;

    /** 雷装上限 */
    private Integer raisouLimit;

    /** 対空 */
    private Integer taiku;

    /** 対空上限 */
    private Integer taikuLimit;

    /** 装甲 */
    private Integer soukou;

    /** 装甲上限 */
    private Integer soukouLimit;

    /** 運 */
    private Integer lucky;

    /** 運上限 */
    private Integer luckyLimit;

    /** 耐久 */
    private Integer taikyu;

    /** 耐久上限 */
    private Integer taikyuMax;

    /** 対潜 */
    private Integer taisen;

    /** 素の対潜の有無 */
    private Boolean hasTaisen;

    private static int VHP = 2;

    private static int VAS = 9;

    /**
     * IDのgetter/setter
     * @param id ID
     * @return ID
     */
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 艦娘のgetter/setter
     * @param ship 艦娘
     * @return 艦娘
     */
    public Ship getShip() {
        return this.ship;
    }
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Lvのgetter/setter
     * @param lv Lv
     * @return Lv
     */
    public Integer getLv() {
        return this.lv;
    }
    public void setLv(Integer lv) {
        this.lv = lv;
    }

    /**
     * ロックのgetter/setter
     * @param locked ロック
     * @return ロック
     */
    public Boolean getLocked() {
        return this.locked;
    }
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * 近代化改修を設定します。
     * @param kyouka 近代化改修
     */
    public void setKyouka(List<Integer> kyouka) {
        this.kyouka = kyouka;
    }

    /**
     * 火力のgetter/setter
     * @param karyoku 火力
     * @return 火力
     */
    public Integer getKaryoku() {
        return this.karyokuLimit - this.kyouka.get(0);
    }
    public void setKaryoku(Integer karyoku) {
        this.karyoku = karyoku;
    }

    /**
     * 火力上限を設定します。
     * @param karyokuLimit 火力
     */
    public void setKaryokuLimit(Integer karyokuLimit) {
        this.karyokuLimit = karyokuLimit;
    }

    /**
     * 雷装のgetter/setter
     * @param raisou 雷装
     * @return 雷装
     */
    public Integer getRaisou() {
        return this.raisouLimit - this.kyouka.get(1);
    }
    public void setRaisou(Integer raisou) {
        this.raisou = raisou;
    }

    /**
     * 雷装上限を設定します。
     * @param raisouLimit 雷装
     */
    public void setRaisouLimit(Integer raisouLimit) {
        this.raisouLimit = raisouLimit;
    }

    /**
     * 対空のgetter/setter
     * @param taiku 対空
     * @return 対空
     */
    public Integer getTaiku() {
        return this.taikuLimit - this.kyouka.get(2);
    }
    public void setTaiku(Integer taiku) {
        this.taiku = taiku;
    }

    /**
     * 対空上限を設定します。
     * @param taikuLimit 対空
     */
    public void setTaikuLimit(Integer taikuLimit) {
        this.taikuLimit = taikuLimit;
    }

    /**
     * 装甲のgetter/setter
     * @param soukou 装甲
     * @return 装甲
     */
    public Integer getSoukou() {
        return this.soukouLimit - this.kyouka.get(3);
    }
    public void setSoukou(Integer soukou) {
        this.soukou = soukou;
    }

    /**
     * 装甲上限を設定します。
     * @param soukouLimit 装甲
     */
    public void setSoukouLimit(Integer soukouLimit) {
        this.soukouLimit = soukouLimit;
    }

    /**
     * 運のgetter/setter
     * @param lucky 運
     * @return 運
     */
    public Integer getLucky() {
        return this.luckyLimit - this.kyouka.get(4);
    }
    public void setLucky(Integer lucky) {
        this.lucky = lucky;
    }

    /**
     * 運上限を設定します。
     * @param luckyLimit 運
     */
    public void setLuckyLimit(Integer luckyLimit) {
        this.luckyLimit = luckyLimit;
    }

    /**
     * 耐久のgetter/setter
     * @param taikyu 耐久
     * @return 耐久
     */
    public Integer getTaikyu() {
        return this.taikyuLimit() - this.kyouka.get(5);
    }
    public void setTaikyu(Integer taikyu) {
        this.taikyu = taikyu;
    }

    /**
     * 耐久上限を設定します。
     * @param taikyuMax 耐久上限
     */
    public void setTaikyuMax(Integer taikyuMax) {
        this.taikyuMax = taikyuMax;
    }

    /**
     * 対潜のgetter/setter
     * @param taisen 対潜
     * @return 対潜
     */
    public Integer getTaisen() {
        return this.taisenLimit() - this.kyouka.get(6);
    }
    public void setTaisen(Integer taisen) {
        this.taisen = taisen;
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