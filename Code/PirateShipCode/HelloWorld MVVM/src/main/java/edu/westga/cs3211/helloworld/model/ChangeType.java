package edu.westga.cs3211.helloworld.model;

public enum ChangeType {

    ADD_STOCK,      // New supplies added to inventory

    REMOVE_STOCK,   // Stock manually removed/transferred

    CONSUMED,       // Used in battle or daily operations

    SPOILED,        // Food/drink went bad

    DAMAGED,        // Items damaged in combat or storage

    SOLD,           // Traded or sold at port

    THEFT,          // Stolen by crew or enemies

    LOST_OVERBOARD  // Lost in rough seas

}