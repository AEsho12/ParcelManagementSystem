package org.example.model;

public class Parcel {
    private String id;
    private int weight;
    private int fragility;
    private int size;
    private int urgency;
    private int deliveryTime;

    public Parcel(String id, int weight, int fragility, int size, int urgency, int deliveryTime) {
        this.id = id;
        this.weight = weight;
        this.fragility = fragility;
        this.size = size;
        this.urgency = urgency;
        this.deliveryTime = deliveryTime;
    }

    public String getId() { return id; }
    public int getWeight() { return weight; }
    public int getFragility() { return fragility; }
    public int getSize() { return size; }
    public int getUrgency() { return urgency; }
    public int getDeliveryTime() { return deliveryTime; }
}
