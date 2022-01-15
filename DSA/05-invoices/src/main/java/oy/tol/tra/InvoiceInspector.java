package oy.tol.tra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceInspector {

   /** List of invoices sent to customer. */
   Invoice[] invoices = null;
   /** List of payments received from customers. */
   Payment[] payments = null;
   /**
    * Based on invoices and payments, a list of new invoices to be sent to
    * customers. DO NOT use Java containers in your implementation, it is used ONLY here
    * to store the invoices to collect. Use plain Java arrays {@code Invoice[]} and {@code Payment[]} 
    */
   List<Invoice> toCollect = new ArrayList<>();

   /**
    * Reads the invoices and payments to memory from csv text files.
    * 
    * @param invoicesFile The file containing the invoice data.
    * @param paymentsFile The file containing the payments data.
    * @throws IOException If file thing goes wrong, there will be exception.
    */
   public void readInvoicesAndPayments(String invoicesFile, String paymentsFile) throws IOException {
      BufferedReader invoiceReader = new BufferedReader(new FileReader(invoicesFile));
      String line = null;
      line = invoiceReader.readLine();
      int itemCount = 0;
      if (null != line && line.length() > 0) {
         itemCount = Integer.parseInt(line);
         invoices = new Invoice[itemCount];
      } else {
         invoiceReader.close();
         throw new IOException("Could not read the invoice file");
      }
      itemCount = 0;
      while ((line = invoiceReader.readLine()) != null && line.length() > 0) {
         String[] items = line.split(",");
         invoices[itemCount++] = new Invoice(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
      }
      invoiceReader.close();
      BufferedReader paymentsReader = new BufferedReader(new FileReader(paymentsFile));
      line = paymentsReader.readLine();
      itemCount = 0;
      if (null != line && line.length() > 0) {
         itemCount = Integer.parseInt(line);
         payments = new Payment[itemCount];
      } else {
         paymentsReader.close();
         throw new IOException("Could not read the invoice file");
      }
      itemCount = 0;
      while ((line = paymentsReader.readLine()) != null && line.length() > 0) {
         String[] items = line.split(",");
         payments[itemCount++] = new Payment(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
      }
      paymentsReader.close();
   }

   /**
    * A naive simple implementation handling the creation of new invoices based on
    * old invoices and payments received from customers.
    * 
    * @throws IOException
    */
   public void handleInvoicesAndPaymentsSlow() throws IOException {
      for (int counter = 0; counter < invoices.length; counter++) {
         Invoice invoice = invoices[counter];
         boolean noPaymentForInvoiceFound = true;
         for (int paymentCounter = 0; paymentCounter < payments.length; paymentCounter++) {
            Payment payment = payments[paymentCounter];
            if (invoice.number.compareTo(payment.number) == 0) {
               noPaymentForInvoiceFound = false;
               if (invoice.sum.compareTo(payment.sum) > 0) {
                  toCollect.add(new Invoice(invoice.number, invoice.sum - payment.sum));
                  break;
               }
            }
         }
         if (noPaymentForInvoiceFound) {
            toCollect.add(invoice);
         }
      }
      int i = toCollect.size()-1;
      while (i > 0) {
         for (int index = i - 1; index >= 0; index--) {
            if (toCollect.get(i).getID() < toCollect.get(index).getID()) {
               Invoice tmp = toCollect.get(i);
               toCollect.set(i, toCollect.get(index));
               toCollect.set(index, tmp);
            }
         }
         i--;
      }
   }

   public void saveNewInvoices(String outputFile) throws IOException {
      BufferedWriter toCollectWriter = new BufferedWriter(new FileWriter(outputFile));
      for (Invoice invoice : toCollect) {
         toCollectWriter.write(invoice.number.toString() + "," + invoice.sum.toString());
         toCollectWriter.newLine();
      }
      toCollectWriter.close();
   }


   /**
    * Sorts using recursive quicksort amd searches with binarysearch
    * @throws IOException
    */
   public void handleInvoicesAndPaymentsFast() {
      quicksort(invoices, 0, invoices.length-1);
      quicksort(payments, 0, payments.length-1);
      
      for(int i = 0; i < invoices.length; i++){
         Invoice invoice = invoices[i];
         int x = binarySearch(payments, invoice); //returns payment array index
         if(x < 0){
            toCollect.add(invoice);
            continue;
         }
         Payment payment = payments[x];
         if(invoice.sum.compareTo(payment.sum) > 0){
            toCollect.add(new Invoice(invoice.number, invoice.sum - payment.sum));
         }   
      }   
   }

   private void quicksort(Identifiable[] array, int low, int high) {
      if (low < high){
         int pivot = partition(array, low, high);
         quicksort(array, low, pivot-1);
         quicksort(array, pivot+1, high);
      }
   }

   private int partition(Identifiable[] array, int low, int high) {
      Identifiable x = array[high];
      int i = low - 1;
   
      for(int j = low; j < high; j++){
         if(x.compareTo(array[j]) > 0){
            i++;
            Identifiable tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
         }
      }
      Identifiable tmp = array[i+1];
      array[i+1] = array[high];
      array[high] = tmp;
      return i + 1;
   }
   
   private static int binarySearch(Identifiable[] array, Identifiable number) {
      int left = 0;
      int right = array.length-1;
      
      while(left <= right){
         int mid = left + (right - left) / 2;
         Identifiable x = array[mid];
         int a = x.getID();
         if(a == number.getID()){
            return mid;
         }
         if(a < number.getID()){
            left = mid + 1;
         } else {
            right = mid - 1;
         }
      }
      return -1;
   }
}
