package logbook.plugin.checkpowerup.gui;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.control.textfield.TextFields;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.Duration;

import logbook.bean.Ship;
import logbook.bean.ShipCollection;
import logbook.bean.ShipMst;
import logbook.internal.gui.ShipItem;
import logbook.internal.gui.SuggestSupport;
import logbook.internal.gui.Tools.Tables;
import logbook.internal.gui.Tools.Conrtols;
import logbook.internal.gui.WindowController;
import logbook.internal.LoggerHolder;
import logbook.internal.Operator;
import logbook.internal.Ships;

import logbook.plugin.checkpowerup.ModernizableShipFilter;

/**
 * 近代化改修可能な艦娘のコントローラー
 *
 */
public class ModernizableShipController extends WindowController {

    /** ロックフィルター */
    @FXML
    private ToggleSwitch lockedFilter;

    /** ロック */
    @FXML
    private CheckBox lockedValue;

    /** レベルフィルター */
    @FXML
    private ToggleSwitch levelFilter;

    /** レベル */
    @FXML
    private TextField levelValue;

    /** レベル条件 */
    @FXML
    private ChoiceBox<Operator> levelType;

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

    private ObservableList<ModernizableShipItem> items = FXCollections.observableArrayList();

    private FilteredList<ModernizableShipItem> filteredItems = new FilteredList<>(this.items);

    private int modernizableShipHashCode;

    private Timeline timeline;

    @FXML
    void initialize() {
        Tables.setVisible(this.table, this.getClass().toString() + "#" + "table");
        Tables.setWidth(this.table, this.getClass().toString() + "#" + "table");
        Tables.setSortOrder(this.table, this.getClass().toString() + "#" + "table");

        // フィルターのバインド
        this.lockedFilter.selectedProperty().addListener((ob, ov, nv) -> {
            this.lockedValue.setDisable(!nv);
        });
        this.levelFilter.selectedProperty().addListener((ob, ov, nv) -> {
            this.levelValue.setDisable(!nv);
            this.levelType.setDisable(!nv);
        });
        this.levelValue.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        TextFields.bindAutoCompletion(this.levelValue,
                                      new SuggestSupport("100", "99", "90", "80", "75", "70", "65", "60", "55", "50", "40", "30", "20"));
        this.levelValue.setText("98");
        this.levelType.setItems(FXCollections.observableArrayList(Operator.values()));
        this.levelType.getSelectionModel().select(Operator.GE);

        this.levelFilter.selectedProperty().addListener(this::filterAction);
        this.levelValue.textProperty().addListener(this::filterAction);
        this.levelType.getSelectionModel().selectedItemProperty().addListener(this::filterAction);
        this.lockedFilter.selectedProperty().addListener(this::filterAction);
        this.lockedValue.selectedProperty().addListener(this::filterAction);

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

        SortedList<ModernizableShipItem> sortedItems = new SortedList<>(this.filteredItems);
        this.table.setItems(sortedItems);
        sortedItems.comparatorProperty().bind(this.table.comparatorProperty());
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
                .filter(this::modernizableFilter)
                .collect(Collectors.toList());
        if (this.modernizableShipHashCode == modernizable.hashCode()) {
            return;
        }
        this.items.clear();
        modernizable.stream()
                .sorted(Comparator.comparing(Ship::getLv, Comparator.reverseOrder()))
                .map(ModernizableShipItem::toShipItem)
                .forEach(this.items::add);
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
    private boolean modernizableFilter(Ship ship) {
        List<Integer> kyouka = ship.getKyouka();
        Function<List<Integer>, Integer> getLimit = (mst) -> { return mst.get(1) - mst.get(0); };
        boolean result = kyouka.get(0) < Ships.shipMst(ship).map(ShipMst::getHoug).map(getLimit).orElse(0)
                      || kyouka.get(1) < Ships.shipMst(ship).map(ShipMst::getRaig).map(getLimit).orElse(0)
                      || kyouka.get(2) < Ships.shipMst(ship).map(ShipMst::getTyku).map(getLimit).orElse(0)
                      || kyouka.get(3) < Ships.shipMst(ship).map(ShipMst::getSouk).map(getLimit).orElse(0)
                      || kyouka.get(4) < Ships.shipMst(ship).map(ShipMst::getLuck).map(getLimit).orElse(0)
                      || ship.getMaxhp() < Ships.shipMst(ship).map(ShipMst::getTaik).map(mst -> mst.get(1)).orElse(0) && kyouka.get(5) < 2
                      || ship.getTaisen().get(1) > 0 && kyouka.get(6) < 9;

        return result;
    }

    /**
     * フィルターを設定する
     */
    private void filterAction(ObservableValue<?> observable, Object oldValue, Object newValue) {
        this.updateFilter();
    }
    private void updateFilter() {
        Predicate<ModernizableShipItem> filter = this.createFilter();
        this.filteredItems.setPredicate(filter);
        //this.saveConfig();
    }

    /**
     * 艦娘フィルターを作成する
     * @return 艦娘フィルター
     */
    private Predicate<ModernizableShipItem> createFilter() {
        Predicate<ModernizableShipItem> filter = null;

        if (this.levelFilter.isSelected()) {
            String level = this.levelValue.getText().isEmpty() ? "0" : this.levelValue.getText();

            filter = ModernizableShipFilter.LevelFilter.builder()
                    .value(Integer.parseInt(level))
                    .type(this.levelType.getValue())
                    .build();
        }
        if (this.lockedFilter.isSelected()) {
            ModernizableShipFilter newFilter = ModernizableShipFilter.LockedFilter.builder()
                    .locked(this.lockedValue.isSelected())
                    .build();
            filter = this.filterAnd(filter, newFilter);
        }

        return filter;
    }

    private <T extends Predicate<ModernizableShipItem>> Predicate<ModernizableShipItem> filterAnd(T base, T add) {
        if (base != null) {
            return base.and(add);
        }
        return add;
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
