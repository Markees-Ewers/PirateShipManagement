package edu.westga.cs3211.pirateship.model;

import java.util.UUID;

// TODO: Auto-generated Javadoc
//StockChange.java

/**
 * The Class StockChange.
 * @author Markees Ewers
 * @version Fall 2025
 */
public class StockChange {

	/** The id. */
	private String id;

	/** The item. */
	private StockItem item;

	/** The change amount. */
	private int changeAmount; 

	/** The timestamp. */
	private long timestamp;

	/** The performed by. */
	private User performedBy;

	/** The change type. */
	private ChangeType changeType;

	/**
	 * Instantiates a new stock change.
	 *
	 * @param item         the item
	 * @param changeAmount the change amount
	 * @param performedBy  the performed by
	 * @param changeType   the change type
	 */
	public StockChange(StockItem item, int changeAmount, User performedBy, ChangeType changeType) {

		this.id = UUID.randomUUID().toString();

		this.item = item;

		this.changeAmount = changeAmount;

		this.timestamp = System.currentTimeMillis();

		this.performedBy = performedBy;

		this.changeType = changeType;

		item.setCurrentQuantity(item.getCurrentQuantity() + changeAmount);

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public StockItem getItem() {
		return this.item;
	}

	/**
	 * Gets the change amount.
	 *
	 * @return the change amount
	 */
	public int getChangeAmount() {
		return this.changeAmount;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Gets the performed by.
	 *
	 * @return the performed by
	 */
	public User getPerformedBy() {
		return this.performedBy;
	}

	/**
	 * Gets the change type.
	 *
	 * @return the change type
	 */
	public ChangeType getChangeType() {
		return this.changeType;
	}

	// Calculated properties - no need to store these!

	/**
	 * Gets the previous quantity.
	 *
	 * @return the previous quantity
	 */
	public int getPreviousQuantity() {
		return this.item.getCurrentQuantity() - this.changeAmount;
	}

	/**
	 * Gets the new quantity.
	 *
	 * @return the new quantity
	 */
	public int getNewQuantity() {
		return this.item.getCurrentQuantity();
	}

	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	public String getItemName() {

		return this.item.getName();

	}

}