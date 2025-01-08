package org.example.view;

import org.example.model.ModelListener;
import org.example.model.Parcel;
import org.example.model.ParcelModel;
import org.example.model.Customer;

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
            }
        }
}
