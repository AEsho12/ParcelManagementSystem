package org.example;

import org.example.model.Customer;
import org.example.model.Log;
import org.example.model.Parcel;
import org.example.model.ParcelMap;

public class Worker {
    private ParcelMap parcelMap;
    private Log log;

    public Worker(ParcelMap parcelMap) {
        this.parcelMap = parcelMap;
        this.log = Log.getInstance();
    }

    public double calculateFee(Parcel parcel) {
        double fee = 5.0;
        fee += parcel.getWeight() * 0.5;
        fee += parcel.getFragility() * 1.0;
        fee += parcel.getSize() * 0.3;
        fee += parcel.getUrgency() * 0.8;

        return fee;
    }

    public void processCustomer(Customer customer) {
        Parcel parcel = parcelMap.getParcel(customer.getParcelId());
        if (parcel != null) {
            double fee = calculateFee(parcel);
            log.addEntry(String.format("Processing customer %s, Parcel ID: %s, Fee: £%.2f",
                    customer.getName(), parcel.getId(), fee));
        }
    }
}
