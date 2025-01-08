package org.example;

import org.example.controller.ParcelController;
import org.example.model.Customer;
import org.example.model.Log;
import org.example.model.Parcel;
import org.example.model.ParcelModel;
import org.example.view.ParcelView;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        ParcelModel model = new ParcelModel();


        ParcelView view = new ParcelView(model);
        ParcelController controller = new ParcelController(model, view);


        loadData(model, "src/main/resources/Custs.csv", "src/main/resources/Parcels.csv");


        view.setVisible(true);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Log log = Log.getInstance();
                log.addEntry("Application closed");
                try (PrintWriter writer = new PrintWriter("parcel_system_log.txt")) {
                    writer.print(log.getLog());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));
    }

    private static void loadData(ParcelModel model, String customerFile, String parcelFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(parcelFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Parcel parcel = new Parcel(
                        data[0],
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]),
                        Integer.parseInt(data[5])
                );
                model.addParcel(parcel);
                Log.getInstance().addEntry("Loaded parcel: " + parcel.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.getInstance().addEntry("Error loading parcels: " + e.getMessage());
        }


        try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Customer customer = new Customer(data[0], data[1]);
                model.addCustomer(customer);
                Log.getInstance().addEntry("Added customer to queue: " + customer.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.getInstance().addEntry("Error loading customers: " + e.getMessage());
        }
    }
}