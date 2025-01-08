package org.example.controller;

import org.example.Worker;
import org.example.model.ParcelModel;
import org.example.view.ParcelView;

public class ParcelController {
    private ParcelModel model;
    private ParcelView view;
    private Worker worker;

    public ParcelController(ParcelModel model, ParcelView view) {
        this.model = model;
        this.view = view;
        this.worker = new Worker(model.getParcelMap());

        view.getProcessButton().addActionListener(e -> processNextCustomer());
    }

    private void processNextCustomer() {
        model.processNextCustomer();
        if (model.getCurrentCustomer() != null) {
            worker.processCustomer(model.getCurrentCustomer());
        }
    }
}
