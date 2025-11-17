package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211.pirateship.model.InventoryRepository;
import edu.westga.cs3211.pirateship.model.StockChange;
import edu.westga.cs3211.pirateship.model.StockItem;
import edu.westga.cs3211pirateship.viewmodel.ViewStockViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

import java.io.IOException;

public class ViewStockChangesCodeBehind {

    @FXML
    private ListView<StockItem> inventoryListView;

    @FXML
    private ListView<StockChange> changesListView;

    @FXML
    private TextArea detailsTextArea;

    @FXML
    private Button backButton;

    private ViewStockViewModel viewModel;

    @FXML
    public void initialize() {
        // create and populate the view model
        this.viewModel = new ViewStockViewModel(InventoryRepository.getInstance());
        this.viewModel.refresh();

        ObservableList<StockItem> inventory = this.viewModel.getInventory();
        ObservableList<StockChange> changes = this.viewModel.getChanges();

        this.inventoryListView.setItems(inventory);
        this.changesListView.setItems(changes);

        // simple cell factories for readable summary
        this.inventoryListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<StockItem> call(ListView<StockItem> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(StockItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getName() + " — " + item.getCurrentQuantity());
                        }
                    }
                };
            }
        });

        this.changesListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<StockChange> call(ListView<StockChange> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(StockChange change, boolean empty) {
                        super.updateItem(change, empty);
                        if (empty || change == null) {
                            setText(null);
                        } else {
                            setText(viewModel.formatStockChange(change).split("\\n")[0]);
                        }
                    }
                };
            }
        });

        // selection listeners
        this.inventoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV == null) {
                detailsTextArea.clear();
                return;
            }
            // clear other selection
            this.changesListView.getSelectionModel().clearSelection();
            detailsTextArea.setText(this.viewModel.formatStockItem(newV));
        });

        this.changesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV == null) {
                detailsTextArea.clear();
                return;
            }
            this.inventoryListView.getSelectionModel().clearSelection();
            detailsTextArea.setText(this.viewModel.formatStockChange(newV));
        });

        // Back handling: pop if possible, otherwise replace with main menu
        this.backButton.setOnAction(evt -> {
            if (NavigationManager.canGoBack()) {
                NavigationManager.goBack();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                    NavigationManager.navigateReplace(loader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // allow external refresh if needed
    public void refresh() {
        this.viewModel.refresh();
    }
}
