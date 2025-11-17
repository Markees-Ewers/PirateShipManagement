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

/**
 * The Class ViewStockChangesCodeBehind.
 * 
 * @author Markees Ewers
 * @version Fall 2025
 */
public class ViewStockChangesCodeBehind {

	@FXML
	private ListView<StockItem> inventoryListView;

	@FXML
	private ListView<StockChange> changesListView;

	@FXML
	private TextArea detailsTextArea;

	@FXML
	private Button backButton;

	/** The view model. */
	private ViewStockViewModel viewModel;

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
		// create and populate the view model
		this.viewModel = new ViewStockViewModel(InventoryRepository.getInstance());
		this.viewModel.refresh();

		ObservableList<StockItem> inventory = this.viewModel.getInventory();
		ObservableList<StockChange> changes = this.viewModel.getChanges();

		this.inventoryListView.setItems(inventory);
		this.changesListView.setItems(changes);

		this.stockItemCell();
		this.changeListView();

		// selection listeners
		this.inventoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
			if (newV == null) {
				this.detailsTextArea.clear();
				return;
			}
			// clear other selection
			this.changesListView.getSelectionModel().clearSelection();
			this.detailsTextArea.setText(ViewStockChangesCodeBehind.this.viewModel.formatStockItem(newV));
		});
		this.changesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
			if (newV == null) {
				this.detailsTextArea.clear();
				return;
			}
			this.inventoryListView.getSelectionModel().clearSelection();
			this.detailsTextArea.setText(this.viewModel.formatStockChange(newV));
		});

		// Back handling: pop if possible, otherwise replace with main menu
		this.backButton.setOnAction(evt -> {
			if (NavigationManager.canGoBack()) {
				NavigationManager.goBack();
			} else {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
					NavigationManager.navigateReplace(loader);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		});
	}

	private void stockItemCell() {
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
	}

	private void changeListView() {
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

							setText(ViewStockChangesCodeBehind.this.viewModel.formatStockChange(change).split("\\n")[0]);
						}
					}
				};
			}
		});
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		this.viewModel.refresh();
	}
}
