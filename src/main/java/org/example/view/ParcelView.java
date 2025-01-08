package org.example.view;

import org.example.model.*;

import javax.swing.*;
import java.awt.*;

public class ParcelView extends JFrame implements ModelListener {
    private ParcelModel model;
    private JTextArea queueArea;
    private JTextArea parcelArea;
    private JTextArea currentProcessingArea;
    private JButton processButton;

    public ParcelView(ParcelModel model) {
        this.model = model;
        model.addListener(this);

        setTitle("Parcel Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createQueuePanel();
        createParcelPanel();
        createProcessingPanel();
        createControlPanel();

        pack();
        setLocationRelativeTo(null);
    }

    private void createQueuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        queueArea = new JTextArea(10, 30);
        queueArea.setEditable(false);
        panel.add(new JScrollPane(queueArea), BorderLayout.CENTER);
        add(panel, BorderLayout.WEST);
    }

    private void createParcelPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Parcels"));
        parcelArea = new JTextArea(10, 30);
        parcelArea.setEditable(false);
        panel.add(new JScrollPane(parcelArea), BorderLayout.CENTER);
        add(panel, BorderLayout.EAST);
    }

    private void createProcessingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Current Processing"));
        currentProcessingArea = new JTextArea(5, 40);
        currentProcessingArea.setEditable(false);
        panel.add(new JScrollPane(currentProcessingArea), BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
    }

    private void createControlPanel() {
        JPanel panel = new JPanel();
        processButton = new JButton("Process Next Customer");
        panel.add(processButton);
        add(panel, BorderLayout.SOUTH);
    }

    public JButton getProcessButton() {
        return processButton;
    }

    @Override
    public void modelChanged() {
        updateDisplay();
    }

    private void updateDisplay() {
        Customer currentCustomer = model.getCurrentCustomer();
        Parcel currentParcel = model.getCurrentParcel();

        if (currentCustomer != null && currentParcel != null) {
            currentProcessingArea.setText(
                    "Processing Customer: " + currentCustomer.getName() + "\n" +
                            "Parcel ID: " + currentParcel.getId() + "\n" +
                            "Weight: " + currentParcel.getWeight() + "\n" +
                            "Size: " + currentParcel.getSize()
            );
        } else {
            currentProcessingArea.setText("No customers in queue\nAll parcels processed");
        }

        StringBuilder queueText = new StringBuilder("Customers in Queue:\n\n");
        QueueOfCustomers queue = model.getCustomerQueue();
        for (Customer customer : queue.getCustomers()) {
            queueText.append(customer.getName())
                    .append(" - Parcel: ")
                    .append(customer.getParcelId())
                    .append("\n");
        }
        queueArea.setText(queueText.toString());

        StringBuilder parcelsText = new StringBuilder("Available Parcels:\n\n");
        ParcelMap parcelMap = model.getParcelMap();
        for (Parcel parcel : parcelMap.getAllParcels()) {
            parcelsText.append("ID: ").append(parcel.getId())
                    .append(", Weight: ").append(parcel.getWeight())
                    .append(", Size: ").append(parcel.getSize())
                    .append("\n");
        }
        parcelArea.setText(parcelsText.toString());
    }
}
