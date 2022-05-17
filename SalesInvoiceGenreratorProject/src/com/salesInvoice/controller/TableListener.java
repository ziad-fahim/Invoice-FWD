/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salesInvoice.controller;

import com.salesInvoice.model.HeaderModel;
import com.salesInvoice.model.InvoiceLineModel;
import com.salesInvoice.model.InvoiceLineTableModel;
import com.salesInvoice.view.MainView;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Mostafa Amin
 */
public class TableListener  implements ListSelectionListener {
     private MainView frame;

    public TableListener(MainView frame) {
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvoiceIndex = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            HeaderModel selectedInv = frame.getInvoicesArray().get(selectedInvoiceIndex);
            ArrayList<InvoiceLineModel> lines = selectedInv.getLines();
            InvoiceLineTableModel lineTableModel = new InvoiceLineTableModel(lines);
            frame.setLinesArray(lines);
            frame.getInvoiceLinesTbl().setModel(lineTableModel);
            frame.getCustNameLbl().setText(selectedInv.getCustomerName());
            frame.getInvNumLbl().setText("" + selectedInv.getInvoiceNum());
            frame.getInvTotalIbl().setText("" + selectedInv.getInvoiceTotal());
            frame.getInvDateLbl().setText(MainView.dateFormat.format(selectedInv.getInvoiceDate()));
        }
    }
}
