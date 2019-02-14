package logbook.plugin.checkpowerup.gui;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import logbook.bean.Ship;
import logbook.bean.ShipCollection;
import logbook.bean.ShipMst;
import logbook.internal.gui.Tools.Tables;
import logbook.internal.gui.Tools.Conrtols;
import logbook.internal.gui.WindowController;
import logbook.internal.LoggerHolder;
import logbook.internal.Ships;

/**
 * 近代化改修可能な艦娘のコントローラー
 *
 */
public class ModernizableShipController extends WindowController {

    @FXML
    private TableView<ModernizableShipItem> table;

    /** 行番号 */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> row;

    /** ID */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> id;

    /** 艦娘 */
    @FXML
    private TableColumn<ModernizableShipItem, Ship> ship;

    /** Lv */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> lv;

    /** 火力 */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> karyoku;

    /** 雷装 */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> raisou;

    /** 対空 */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> taiku;

    /** 装甲 */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> soukou;

    /** 運 */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> lucky;

    /** 耐久 */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> taikyu;

    /** 対潜 */
    @FXML
    private TableColumn<ModernizableShipItem, Integer> taisen;

    private ObservableList<ModernizableShipItem> item = FXCollections.observableArrayList();

    private int modernizableShipHashCode;

    private Timeline timeline;

    @FXML
    void initialize() {
        Tables.setVisible(this.table, this.getClass().toString() + "#" + "table");
        Tables.setWidth(this.table, this.getClass().toString() + "#" + "table");
        Tables.setSortOrder(this.table, this.getClass().toString() + "#" + "table");

        // カラムとオブジェクトのバインド
        this.row.setCellFactory(p -> new RowNumberCell());
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.ship.setCellValueFactory(new PropertyValueFactory<>("ship"));
        this.ship.setCellFactory(p -> new ShipImageCell());
        this.lv.setCellValueFactory(new PropertyValueFactory<>("lv"));
        this.karyoku.setCellValueFactory(new PropertyValueFactory<>("karyoku"));
        this.raisou.setCellValueFactory(new PropertyValueFactory<>("raisou"));
        this.taiku.setCellValueFactory(new PropertyValueFactory<>("taiku"));
        this.soukou.setCellValueFactory(new PropertyValueFactory<>("soukou"));
        this.lucky.setCellValueFactory(new PropertyValueFactory<>("lucky"));
        this.taikyu.setCellValueFactory(new PropertyValueFactory<>("taikyu"));
        this.taisen.setCellValueFactory(new PropertyValueFactory<>("taisen"));

        SortedList<ModernizableShipItem> sortedList = new SortedList<>(this.item);
        this.table.setItems(this.item);
        sortedList.comparatorProperty().bind(this.table.comparatorProperty());
        this.table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.table.setOnKeyPressed(Tables::defaultOnKeyPressedHandler);

        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(1),
                this::update));
        this.timeline.play();

        this.update(null);
    }

    /**
     * 画面の更新
     *
     * @param e ActionEvent
     */
    @FXML
    void update(ActionEvent e) {
        List<Ship> modernizable = ShipCollection.get()
                .getShipMap()
                .values()
                .stream()
                .filter(this::filter)
                .collect(Collectors.toList());
        if (this.modernizableShipHashCode == modernizable.hashCode()) {
            return;
        }
        this.item.clear();
        modernizable.stream()
                .sorted(Comparator.comparing(Ship::getLv, Comparator.reverseOrder()))
                .map(ModernizableShipItem::toShipItem)
                .forEach(this.item::add);
        this.modernizableShipHashCode = modernizable.hashCode();
    }

    /**
     * クリップボードにコピー
     */
    @FXML
    void copy() {
        Tables.selectionCopy(this.table);
    }

    /**
     * すべてを選択
     */
    @FXML
    void selectAll() {
        Tables.selectAll(this.table);
    }

    /**
     * テーブル列の表示・非表示の設定
     */
    @FXML
    void columnVisible() {
        try {
            Tables.showVisibleSetting(this.table, this.getClass().toString() + "#" + "table",
                    this.getWindow());
        } catch (Exception e) {
            LoggerHolder.get().error("FXMLの初期化に失敗しました", e);
        }
    }

    /**
     * フィルター
     * @param ship 艦娘
     * @return フィルタ結果
     */
    private boolean filter(Ship ship) {
        List<Integer> kyouka = ship.getKyouka();
        boolean result = false;

        if (this.karyoku.isVisible()) {
            result |= kyouka.get(0) < Ships.shipMst(ship).map(ShipMst::getHoug).map(mst -> mst.get(1) - mst.get(0)).orElse(0);
        }
        if (this.raisou.isVisible()) {
            result |= kyouka.get(1) < Ships.shipMst(ship).map(ShipMst::getRaig).map(mst -> mst.get(1) - mst.get(0)).orElse(0);
        }
        if (this.taiku.isVisible()) {
            result |= kyouka.get(2) < Ships.shipMst(ship).map(ShipMst::getTyku).map(mst -> mst.get(1) - mst.get(0)).orElse(0);
        }
        if (this.soukou.isVisible()) {
            result |= kyouka.get(3) < Ships.shipMst(ship).map(ShipMst::getSouk).map(mst -> mst.get(1) - mst.get(0)).orElse(0);
        }
        if (this.lucky.isVisible()) {
            result |= kyouka.get(4) < Ships.shipMst(ship).map(ShipMst::getLuck).map(mst -> mst.get(1) - mst.get(0)).orElse(0);
        }
        if (this.taikyu.isVisible()) {
            result |= ship.getMaxhp() < Ships.shipMst(ship).map(ShipMst::getTaik).map(mst -> mst.get(1)).orElse(0) && kyouka.get(5) < 2;
        }
        if (this.taisen.isVisible()) {
            result |= ship.getTaisen().get(1) > 0 && kyouka.get(6) < 9;
        }
        return result;
    }

    /**
     * 行番号のセル
     *
     */
    private static class RowNumberCell extends TableCell<ModernizableShipItem, Integer> {
        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (!empty) {
                TableRow<?> currentRow = this.getTableRow();
                this.setText(Integer.toString(currentRow.getIndex() + 1));
            } else {
                this.setText(null);
            }
        }
    }

    /**
     * 艦娘画像のセル
     *
     */
    private static class ShipImageCell extends TableCell<ModernizableShipItem, Ship> {
        @Override
        protected void updateItem(Ship ship, boolean empty) {
            super.updateItem(ship, empty);

            if (!empty) {
                this.setGraphic(Conrtols.zoomImage(new ImageView(Ships.shipWithItemImage(ship))));
                this.setText(Ships.shipMst(ship)
                        .map(ShipMst::getName)
                        .orElse(""));
            } else {
                this.setGraphic(null);
                this.setText(null);
            }
        }
    }
}
