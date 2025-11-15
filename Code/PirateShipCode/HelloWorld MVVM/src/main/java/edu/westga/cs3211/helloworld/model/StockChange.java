package edu.westga.cs3211.helloworld.model;

import java.util.UUID;

//StockChange.java

public class StockChange {

 private String id;
 private StockItem item;
 private int changeAmount;  // The only input we need
 private long timestamp;
 private User performedBy;
 private ChangeType changeType;


 public StockChange(StockItem item, int changeAmount, User performedBy, ChangeType changeType) {

     this.id = UUID.randomUUID().toString();

     this.item = item;

     this.changeAmount = changeAmount;

     this.timestamp = System.currentTimeMillis();

     this.performedBy = performedBy;

     this.changeType = changeType;


     // Update the item's quantity based on change amount
     item.setCurrentQuantity(item.getCurrentQuantity() + changeAmount);

 }



 // Getters

 public String getId() { return id; }

 public StockItem getItem() { return item; }

 public int getChangeAmount() { return changeAmount; }

 public long getTimestamp() { return timestamp; }

 public User getPerformedBy() { return performedBy; }

 public ChangeType getChangeType() { return changeType; }

 

 // Calculated properties - no need to store these!

 public int getPreviousQuantity() {
     return item.getCurrentQuantity() - changeAmount;
 }

 
 public int getNewQuantity() {
     return item.getCurrentQuantity();
 }
 

 public String getItemName() {

     return item.getName();

 }

}