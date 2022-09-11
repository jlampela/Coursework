package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Testing invoice checking algorigthm")
public class InvoicesPaymentsLargeTests {

   @Test
   void newInvoicesFromPaymentsSlowTest() {
      try {
         System.out.println("Starting to handle the invoices the slow way...");
         InvoiceInspector inspector = new InvoiceInspector();
         inspector.readInvoicesAndPayments("l-invoices.txt", "l-payments.txt");
         long start = System.nanoTime();
         inspector.handleInvoicesAndPaymentsSlow();
         long duration = System.nanoTime() - start;
         inspector.saveNewInvoices("l-to-collect-slow.txt");
         System.out.println("Handling the large file took " + duration / 1000000 + " ms");
         assertTrue(checkFileHash("l-to-collect-slow.txt", "45e1826bcc9f14492cad21adab06f8d2fbc71741"), () -> "Wrong hash code for the file.");
      } catch (IOException e) {
         fail("Failed to manage the invoice and payments files");
      }
   }

   @Test
   void newInvoicesFromPaymentsFastTest() {
      try {
         System.out.println("Starting to handle the invoices the faster way...");
         InvoiceInspector inspector = new InvoiceInspector();
         inspector.readInvoicesAndPayments("l-invoices.txt", "l-payments.txt");
         long start = System.nanoTime();
         inspector.handleInvoicesAndPaymentsFast();
         long duration = System.nanoTime() - start;
         inspector.saveNewInvoices("l-to-collect.txt");
         System.out.println("Handling the large file took " + duration / 1000000 + " ms");
         // bcf5b1db6341098985a694eddaf360aeb54f973c5b31b091c23817d9f51865cef90c1e3d2cadeedde30661f4e043e03b7b9008ea62dd27a68e2e34239b2d26f6
         assertTrue(checkFileHash("l-to-collect.txt", "45e1826bcc9f14492cad21adab06f8d2fbc71741"), () -> "Wrong hash code for the file.");
      } catch (IOException e) {
         fail("Failed to manage the invoice and payments files");
      }
   }

   private boolean checkFileHash(String fileName, String correctHash) {
      try {
         // Use SHA for hashing, does not need to be secure.
         MessageDigest md = MessageDigest.getInstance("SHA");
         // Use buffered reader since we need to read explicitly using UTF-8.
         BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

         // Read the lines from the line to a String.
         String line = null;
         StringBuilder sb = new StringBuilder();
         while ((line = br.readLine()) != null) {
            sb.append(line);
         }
         br.close();
         // Calculate the SHA digest (hash) from the string
         byte [] mdbytes = md.digest(sb.toString().getBytes("UTF-8"));

         // Convert the hash into a String.
         StringBuilder hashStringBuilder = new StringBuilder();
         for (int i = 0; i < mdbytes.length; i++) {
            hashStringBuilder.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
         }
         String calculatedHash = hashStringBuilder.toString();
         System.out.println("\nHash is " + calculatedHash.toString());
         // Check if the hash matches with the expected correct one.
         boolean matches = calculatedHash.equals(correctHash);
         if (!matches) {
            System.out.println("Correct has is: " + correctHash);
         }
         return matches;
      } catch (NoSuchAlgorithmException | IOException e) {
         System.out.println("Could not check file hash: " + e.getMessage());
      }
      return false;
   }
}
