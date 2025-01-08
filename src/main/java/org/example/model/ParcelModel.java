package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ParcelModel {
    private QueueOfCustomers customerQueue;
    private ParcelMap parcelMap;
    private Customer currentCustomer;
    private Parcel currentParcel;
    private List<ModelListener> listeners;

    public ParcelModel() {
        customerQueue = new QueueOfCustomers();
        parcelMap = new ParcelMap();
        listeners = new ArrayList<>();
    }

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (ModelListener listener : listeners) {
            listener.modelChanged();
        }
    }

    public void processNextCustomer() {
        currentCustomer = customerQueue.removeCustomer();
        if (currentCustomer != null) {
            currentParcel = parcelMap.getParcel(currentCustomer.getParcelId());
            if (currentParcel != null) {
                parcelMap.removeParcel(currentParcel.getId());
            }
            notifyListeners();
        } else {
            currentCustomer = null;
            currentParcel = null;
            notifyListeners();
        }
    }

    public void addCustomer(Customer customer) {
        customerQueue.addCustomer(customer);
        notifyListeners();
    }

    public void addParcel(Parcel parcel) {
        parcelMap.addParcel(parcel);
        notifyListeners();
    }

    public Customer getCurrentCustomer() { return currentCustomer; }
    public Parcel getCurrentParcel() { return currentParcel; }
    public QueueOfCustomers getCustomerQueue() { return customerQueue; }
    public ParcelMap getParcelMap() { return parcelMap; }
}
