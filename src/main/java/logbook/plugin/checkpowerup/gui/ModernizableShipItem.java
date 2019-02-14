package logbook.plugin.checkpowerup.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /** 近代化改修 */
    private List<Integer> kyouka;

    /** 火力 */
    private Integer karyoku;

    /** マスタ火力 */
    private List<Integer> houg;

    /** 雷装 */
    private Integer raisou;

    /** マスタ雷装 */
    private List<Integer> raig;

    /** 対空 */
    private Integer taiku;

    /** マスタ対空 */
    private List<Integer> tyku;

    /** 装甲 */
    private Integer soukou;

    /** マスタ装甲 */
    private List<Integer> souk;

    /** 運 */
    private Integer lucky;

    /** マスタ運 */
    private List<Integer> luck;

    /** 耐久 */
    private Integer taikyu;

    /** マスタ耐久 */
    private List<Integer> taik;

    /** 対潜 */
    private Integer taisen;

    /** 素の対潜の有無 */
    private Boolean hasTaisen;

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
        return this.karyokuLimit() - this.kyouka.get(0);
    }
    public void setKaryoku(Integer karyoku) {
        this.karyoku = karyoku;
    }

    /**
     * マスタ火力を設定します。
     * @param houg マスタ火力
     */
    public void setHoug(List<Integer> houg) {
        this.houg = houg;
    }

    /**
     * 雷装のgetter/setter
     * @param raisou 雷装
     * @return 雷装
     */
    public Integer getRaisou() {
        return this.raisouLimit() - this.kyouka.get(1);
    }
    public void setRaisou(Integer raisou) {
        this.raisou = raisou;
    }

    /**
     * マスタ雷装を設定します。
     * @param raig マスタ雷装
     */
    public void setRaig(List<Integer> raig) {
        this.raig = raig;
    }

    /**
     * 対空のgetter/setter
     * @param taiku 対空
     * @return 対空
     */
    public Integer getTaiku() {
        return this.taikuLimit() - this.kyouka.get(2);
    }
    public void setTaiku(Integer taiku) {
        this.taiku = taiku;
    }

    /**
     * マスタ対空を設定します。
     * @param tyku マスタ対空
     */
    public void setTyku(List<Integer> tyku) {
        this.tyku = tyku;
    }

    /**
     * 装甲のgetter/setter
     * @param soukou 装甲
     * @return 装甲
     */
    public Integer getSoukou() {
        return this.soukouLimit() - this.kyouka.get(3);
    }
    public void setSoukou(Integer soukou) {
        this.soukou = soukou;
    }

    /**
     * マスタ装甲を設定します。
     * @param souk マスタ装甲
     */
    public void setSouk(List<Integer> souk) {
        this.souk = souk;
    }

    /**
     * 運のgetter/setter
     * @param lucky 運
     * @return 運
     */
    public Integer getLucky() {
        return this.luckyLimit() - this.kyouka.get(4);
    }
    public void setLucky(Integer lucky) {
        this.lucky = lucky;
    }

    /**
     * マスタ運を設定します。
     * @param luck マスタ運
     */
    public void setLuck(List<Integer> luck) {
        this.luck = luck;
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
     * マスタ耐久を設定します。
     * @param taik マスタ耐久
     */
    public void setTaik(List<Integer> taik) {
        this.taik = taik;
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
    private Integer karyokuLimit() {
        List<Integer> houg = this.houg;
        return houg.get(1) - houg.get(0);
    }

    private Integer raisouLimit() {
        List<Integer> raig = this.raig;
        return raig.get(1) - raig.get(0);
    }

    private Integer taikuLimit() {
        List<Integer> tyku = this.tyku;
        return tyku.get(1) - tyku.get(0);
    }

    private Integer soukouLimit() {
        List<Integer> souk = this.souk;
        return souk.get(1) - souk.get(0);
    }

    private Integer luckyLimit() {
        List<Integer> luck = this.luck;
        return luck.get(1) - luck.get(0);
    }

    private Integer taikyuLimit() {
        return 2;
    }

    private Integer taisenLimit() {
        return this.hasTaisen ? 9 : 0;
    }

    /**
     * 近代化改修可能な艦娘テーブルの行を作成します
     *
     * @param ship 艦娘
     * @return 近代化改修可能な艦娘テーブルの行
     */
    public static ModernizableShipItem toShipItem(Ship ship) {
        List<Integer> defaultList = new ArrayList<>(Arrays.asList(0, 0));
        ModernizableShipItem item = new ModernizableShipItem();
        item.setId(ship.getId());
        item.setShip(ship);
        item.setLv(ship.getLv());
        item.setKyouka(ship.getKyouka());
        item.setKaryoku(ship.getKaryoku().get(0));
        item.setHoug(Ships.shipMst(ship).map(ShipMst::getHoug).orElse(defaultList));
        item.setRaisou(ship.getRaisou().get(0));
        item.setRaig(Ships.shipMst(ship).map(ShipMst::getRaig).orElse(defaultList));
        item.setTaiku(ship.getTaiku().get(0));
        item.setTyku(Ships.shipMst(ship).map(ShipMst::getTyku).orElse(defaultList));
        item.setSoukou(ship.getSoukou().get(0));
        item.setSouk(Ships.shipMst(ship).map(ShipMst::getSouk).orElse(defaultList));
        item.setLucky(ship.getLucky().get(0));
        item.setLuck(Ships.shipMst(ship).map(ShipMst::getLuck).orElse(defaultList));
        item.setTaikyu(ship.getMaxhp());
        item.setTaik(Ships.shipMst(ship).map(ShipMst::getTaik).orElse(defaultList));

        item.setTaisenMax(ship.getTaisen().get(1));
        return item;
    }
}