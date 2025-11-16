package edu.westga.cs3211.pirateship.model;

public class StockItem {

    private String Id;

    private String name;

    private ItemCategory category;

    private int currentQuantity;

    private String unit;



    public StockItem(String name, ItemCategory category, int currentQuantity, String unit) {
        this.name = name;
        this.category = category;
        this.currentQuantity = currentQuantity;
        this.unit = unit;
        this.Id = java.util.UUID.randomUUID().toString();

    }

    // Getters and Setters


    public String getName() { return name; }

    public ItemCategory getCategory() { return category; }

    public int getCurrentQuantity() { return currentQuantity; }

    public String getUnit() { return unit; }

    public void setCurrentQuantity(int quantity) { this.currentQuantity = quantity; }


	public String getId() {
		return Id;
	}

}