/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salesInvoice.controller;
import java.io.File;
import com.salesInvoice.model.HeaderModel;
import com.salesInvoice.model.InvoiceHeaderLineTableModel;
import com.salesInvoice.model.InvoiceLineModel;
import com.salesInvoice.model.InvoiceLineTableModel;
import com.salesInvoice.view.MainView;
import com.salesInvoice.view.HeaderDialogView;
import com.salesInvoice.view.LineDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class MainViewButtonsListener implements ActionListener {
    private MainView frame;
     private HeaderDialogView headerDialog;
    private LineDialogView lineDialog;

    public MainViewButtonsListener(MainView frame) {
        this.frame = frame;
    }
   


    @Override
    public void actionPerformed(ActionEvent e){
    switch(e.getActionCommand()){
        case "Load File":
              
                loadFilesInvoices();
                
                break;

        case"Save File":
               saveFiles();
            break;
        case"Create New Invoice":
            createNewInvoice();
            break;
        case"Delete Invoice":
             deleteInvoice();
            break;
       
        case "New Line":
                createNewLine();
                break;

        case "Delete Line":
                deleteLine();
                break;

        case "newInvoiceOK":
                newInvoiceDialogOK();
                break;

        case "newInvoiceCancel":
                newInvoiceDialogCancel();
                break;

        case "newLineCancel":
                newLineDialogCancel();
                break;

        case "newLineOK":
                newLineDialogOK();
                break;

        }
    }
    

    private void loadFilesInvoices() {
     
       JFileChooser fileChooser = new JFileChooser();
        try {
            System.out.println("Reading the files >>>");
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fileChooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                ArrayList<HeaderModel> invoiceHeaders = new ArrayList<>();
                System.out.println(headerLines);
                
                for (String headerLine : headerLines) {
                    String[] arr = headerLine.split(",");
                    String str1 = arr[0];
                    String str2 = arr[1];
                    String str3 = arr[2];
                    int code = Integer.parseInt(str1);
                    Date invoiceDate = MainView.dateFormat.parse(str2);
                    HeaderModel header = new HeaderModel(code, str3, invoiceDate);
                    
                    invoiceHeaders.add(header);
                }
                frame.setInvoicesArray(invoiceHeaders);

                result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fileChooser.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    ArrayList<InvoiceLineModel> invoiceLines = new ArrayList<>();
                   System.out.println(lineLines); 
                    for (String lineLine : lineLines) {
                        String[] arr = lineLine.split(",");
                        String str1 = arr[0];  
                        String str2 = arr[1];    
                        String str3 = arr[2];    
                        String str4 = arr[3];
                        int invCode = Integer.parseInt(str1);
                        double price = Double.parseDouble(str2);
                        int count = Integer.parseInt(str3);
                        HeaderModel inv = frame.getInvObject(invCode);
                        InvoiceLineModel line = new InvoiceLineModel(str4, price, count, inv);
                       
                        inv.getLines().add(line);
                    }
                }
                InvoiceHeaderLineTableModel headerTableModel = new InvoiceHeaderLineTableModel(invoiceHeaders);
                frame.setHeaderTableModel(headerTableModel);
                frame.getInvoiceHeaderTbl().setModel(headerTableModel);
                System.out.println("Done");
            }
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void createNewInvoice() {
        headerDialog = new HeaderDialogView(frame);
        headerDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedInvoiceIndex = frame. getInvoiceHeaderTbl().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            frame.getInvoicesArray().remove(selectedInvoiceIndex);
            frame.getHeaderLineTable().fireTableDataChanged();

            frame.getInvoiceLinesTbl().setModel(new InvoiceLineTableModel(null));
            frame.setLinesArray(null);
            frame.getCustNameLbl().setText("");
            frame.getInvNumLbl().setText("");
            frame.getInvTotalIbl().setText("");
            frame.getInvDateLbl().setText("");
        }
    }

    private void createNewLine() {
        lineDialog = new LineDialogView(frame);
        lineDialog.setVisible(true);
    }

    private void deleteLine() {
        int selectedLineIndex = frame.getInvoiceLinesTbl().getSelectedRow();
        int selectedInvoiceIndex = frame. getInvoiceHeaderTbl().getSelectedRow();
        if (selectedLineIndex != -1) {
            frame.getLinesArray().remove(selectedLineIndex);
            
            InvoiceLineTableModel lineTableModel = (InvoiceLineTableModel) frame.getInvoiceLinesTbl().getModel();
            lineTableModel.fireTableDataChanged();
            frame.getInvTotalIbl().setText(""+frame.getInvoicesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            frame.getHeaderLineTable().fireTableDataChanged();
            frame. getInvoiceHeaderTbl().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

    private void saveFiles() {
        ArrayList<HeaderModel> invoicesArray = frame.getInvoicesArray();
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                String headers = "";
                String lines = "";
                for (HeaderModel invoice : invoicesArray) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                    ArrayList<String> x=new ArrayList<>();
                    x.add(Integer.toString(invoice.getInvoiceNum()));
                    x.add(dateFormat.format(invoice.getInvoiceDate()).toString());
                    x.add(invoice.getCustomerName());
                    headers += Arrays.toString(x.toArray()).replace("[", "").replace("]", "");
                    System.out.println(headers);
                    headers += "\n";
                    for (InvoiceLineModel line : invoice.getLines()) {
                        ArrayList<String> y=new ArrayList<>();
                        y.add(Integer.toString(invoice.getInvoiceNum()));
                        y.add(Double.toString(line.getPrice()));
                        y.add(Integer.toString(line.getCount()));
                        y.add(line.getName());
                        lines += Arrays.toString(y.toArray()).replace("[", "").replace("]", "").replace(" ","");
                        lines += "\n";
                    }
                }
                headers = headers.substring(0, headers.length()-1);
                //System.out.println(headers);
                lines = lines.substring(0, lines.length()-1);
                result = fc.showSaveDialog(frame);
                File lineFile = fc.getSelectedFile();
                FileWriter lfw = new FileWriter(lineFile);
                hfw.write(headers);
                lfw.write(lines);
                hfw.close();
                lfw.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void newInvoiceDialogCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newInvoiceDialogOK() {
        headerDialog.setVisible(false);
        String custName = headerDialog.getCustNameField().getText();
        String str = headerDialog.getInvDateField().getText();
        Date d = new Date();
        try {
            d = MainView.dateFormat.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Can't parse date,", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (HeaderModel inv : frame.getInvoicesArray()) {
            if (inv.getInvoiceNum()> invNum) {
                invNum = inv.getInvoiceNum();
            }
        }
        invNum++;
        HeaderModel newInv = new HeaderModel(invNum, custName, d);
        frame.getInvoicesArray().add(newInv);
        frame.getHeaderLineTable().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newLineDialogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void newLineDialogOK() {
        lineDialog.setVisible(false);
        
        String name = lineDialog.getItemNameField().getText();
        String str1 = lineDialog.getItemCountField().getText();
        String str2 = lineDialog.getItemPriceField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Can't convert number", "Invalid number format.", JOptionPane.ERROR_MESSAGE);
        }
        
        try {
            price = Double.parseDouble(str2);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Can't convert price", "Invalid price format.", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (selectedInvHeader != -1) {
            HeaderModel invHeader = frame.getInvoicesArray().get(selectedInvHeader);
            InvoiceLineModel line = new InvoiceLineModel(name, price, count, invHeader);
          
            frame.getLinesArray().add(line);
            InvoiceLineTableModel lineTableModel = (InvoiceLineTableModel) frame.getInvoiceLinesTbl().getModel();
            lineTableModel.fireTableDataChanged();
            frame.getHeaderLineTable().fireTableDataChanged();
        }
        frame.getInvoiceHeaderTbl().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        lineDialog.dispose();
        lineDialog = null;
    }
}
