package com.salesInvoice.model;

public class InvoiceLineModel {
private String name;
private double price;
private int count;
private HeaderModel header;   

    public InvoiceLineModel(String name, double price, int count, HeaderModel header) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.header = header;
    }

    public InvoiceLineModel() {
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public int getCount() {

        return count;
    }

    public void setCount(int count) {

        this.count = count;
    }

    public HeaderModel getHeader() {

        return header;
    }

    public void setHeader(HeaderModel header) {

        this.header = header;
    }
    public double getLineTotal(){

        return price*count ;
    }
    @Override
    public String toString() {
        return "InvoiceLine{" + "item=" + name + ", price=" + price + ", count=" + count + ", header=" + header + '}';
    }
}
