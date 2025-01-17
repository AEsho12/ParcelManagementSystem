package org.example.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueOfCustomers {
    private Queue<Customer> customerQueue;

    public QueueOfCustomers() {
        customerQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer) {
        customerQueue.offer(customer);
    }

    public Customer removeCustomer() {
        return customerQueue.poll();
    }

    public boolean isEmpty() {
        return customerQueue.isEmpty();
    }

    public int size() {
        return customerQueue.size();
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customerQueue);
    }
}
