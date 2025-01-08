package org.example;

import org.example.controller.ParcelController;
import org.example.model.Customer;
import org.example.model.Log;
import org.example.model.Parcel;
import org.example.model.ParcelModel;
import org.example.view.ParcelView;

import java.io.*;

public class Manager {
    private ParcelModel model;
    private ParcelView view;
    private ParcelController controller;

    public Manager() {
        model = new ParcelModel();
        view = new ParcelView(model);
        controller = new ParcelController(model, view);
    }

    public void loadData(String customerFile, String parcelFile) {
        loadParcels(parcelFile);
        loadCustomers(customerFile);
    }

    private void loadParcels(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Parcel parcel = new Parcel(
                        data[0],  // id
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]),
                        Integer.parseInt(data[5])
                );
                model.getParcelMap().addParcel(parcel);
                Log.getInstance().addEntry("Loaded parcel: " + parcel.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomers(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Customer customer = new Customer(data[0], data[1]);
                model.getCustomerQueue().addCustomer(customer);
                Log.getInstance().addEntry("Added customer to queue: " + customer.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        view.setVisible(true);
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.loadData("src/main/resources/Custs.csv", "src/main/resources/Parcels.csv");
        manager.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try (PrintWriter writer = new PrintWriter("parcel_system_log.txt")) {
                writer.print(Log.getInstance().getLog());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));
    }
}
