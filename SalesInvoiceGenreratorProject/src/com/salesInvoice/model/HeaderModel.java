/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salesInvoice.model;

import java.util.ArrayList;
import java.util.Date;

public class HeaderModel {
      private int invoiceNum;
      private String customerName;
      private Date invoiceDate;
      private ArrayList<InvoiceLineModel> Lines;
    public HeaderModel() {
    }

    public HeaderModel(int invoiceNumber, String customerName, Date invoiceDate) {
        this.invoiceNum = invoiceNumber;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
    }

    public int getInvoiceNum() {

        return invoiceNum;
    }

    public void setInvoiceNumber(int invoiceNumber) {

        this.invoiceNum = invoiceNumber;
    }

    public String getCustomerName() {

        return customerName;
    }

    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }

    public Date getInvoiceDate() {

        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {

        this.invoiceDate = invoiceDate;
    }

    public ArrayList<InvoiceLineModel> getLines() {

        if (Lines == null) {
            Lines = new ArrayList<>();
        }
        return Lines;
    }
    

    public void setItems(ArrayList<InvoiceLineModel> items) {

        this.Lines = items;
    }


  public double getInvoiceTotal(){
    double total=0.0;

    for (int i=0; i< Lines.size(); i++){
        total+=Lines.get(i).getLineTotal();
        }
    return total;

    }
}
