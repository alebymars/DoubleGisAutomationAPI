package models;

import java.util.ArrayList;


public class Message {
    public Integer total;
    public ArrayList<Item> items;

    public Message(Integer total, ArrayList<Item> items) {
        this.total = total;
        this.items = items;
    }

    public Integer getTotal() {
        return total;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}