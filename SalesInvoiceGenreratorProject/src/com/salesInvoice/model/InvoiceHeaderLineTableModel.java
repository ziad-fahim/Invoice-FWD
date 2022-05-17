/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salesInvoice.model;
import com.salesInvoice.view.MainView;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceHeaderLineTableModel extends AbstractTableModel  {
     private ArrayList<HeaderModel> invoicesArray;
    private String[] columns = {"Invoice Num", "Invoice Date", "Customer Name"};

    public InvoiceHeaderLineTableModel(ArrayList<HeaderModel> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }

 @Override
    public int getRowCount() {

        return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {

        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HeaderModel inv = invoicesArray.get(rowIndex);
        switch (columnIndex) {
            case 0: return inv.getInvoiceNum();
            case 1: return MainView.dateFormat.format(inv.getInvoiceDate());
            case 2: return inv.getCustomerName();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {

        return columns[column];
    }

}
