package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class CreditCardPayTest {

    /** 
     * inputCreditCardNumber() test
     *   inputCreditCardNumberTest: Checks that the same card number input is returned
    */
    @Test
    public void inputCreditCardNumberTest() {
        CustomerInterface test = new CustomerInterface();
        String input = "40691";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scan = new Scanner(in);
        assertEquals("40691", test.inputCreditCardNumber(scan));
    }

    /** 
     * inputCreditCardName() test
     *   inputCreditCardNameTest: Checks that the same card name input is returned
    */
    // @Test
    // public void inputCreditCardNameTest() {
    //     CustomerInterface test = new CustomerInterface();
    //     String input = "Charles";
    //     InputStream in = new ByteArrayInputStream(input.getBytes());
    //     System.setIn(in);
    //     assertEquals("Charles", test.inputCreditCardName());
    // }

    /** 
     * usePreSavedCardDetails() test
     *   noSavedCardDetails: Checks that when customer has no saved credit card details, should return false
     *   useSavedCardDetails: Customer has saved credit card details and wishes to use them, should return true
     *   dontUseSavedCardDetails: Customer has saved credit card details but does not wish to use them, should return false
    */
    @Test
    public void noSavedCardDetails() {
        CustomerInterface test = new CustomerInterface();
        Customer customer = new Customer("fjkdslfjdkls", "password");
        test.setCustomer(customer);
        Scanner scan = new Scanner(System.in);
        assertFalse(test.usePreSavedCardDetails(scan));
    }

    @Test
    public void useSavedCardDetails() {
        CustomerInterface test = new CustomerInterface();
        Customer customer = new Customer("fjkdslfjdkls", "password");
        customer.setCreditCardName("fjkdslfjdkls");
        customer.setCreditCardNum("40691");
        test.setCustomer(customer);
        String input = "y";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scan = new Scanner(in);
        assertTrue(test.usePreSavedCardDetails(scan));
    }

    @Test
    public void dontUseSavedCardDetails() {
        CustomerInterface test = new CustomerInterface();
        Customer customer = new Customer("fjkdslfjdkls", "password");
        customer.setCreditCardName("fjkdslfjdkls");
        customer.setCreditCardNum("40691");
        test.setCustomer(customer);
        String input = "N";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scan = new Scanner(in);
        assertFalse(test.usePreSavedCardDetails(scan));
    }

    /** 
     * askSaveCreditCard() test
     *   dontSaveCardDetails: Customer does not wish to save card details, should return false and not update customer's attributes
     *   doSaveCardDetails: Customer does wish to save card details, should return true and update customer's attributes
    */
    @Test
    public void dontSaveCardDetails() {
        CustomerInterface test = new CustomerInterface();
        Customer customer = new Customer("fjkdslfjdkls", "password");
        test.setCustomer(customer);
        String input = "N";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scan = new Scanner(in);
        assertFalse(test.askSaveCreditCard(scan, "cardName", "40691"));
        assertNull(customer.getCreditCardName());
        assertNull(customer.getCreditCardNum());
    }

    @Test
    public void doSaveCardDetails() {
        CustomerInterface test = new CustomerInterface();
        Customer customer = new Customer("fjkdslfjdkls", "password");
        test.setCustomer(customer);
        String input = "Y";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scan = new Scanner(in);
        assertNull(customer.getCreditCardName());
        assertNull(customer.getCreditCardNum());
        assertTrue(test.askSaveCreditCard(scan, "cardName", "40691"));
        assertEquals("cardName", customer.getCreditCardName());
        assertEquals("40691", customer.getCreditCardNum());
    }

    /** 
     * payWithCreditCard() test
     *   paySavedDetailsValid: Customer pays for tickets with valid saved card details, should return true
     *   paySavedDetailsInvalidNum: Customer pays for tickets with invalid saved card number, should return false
     *   paySavedDetailsInvalidName: Customer pays for tickets with invalid saved card name, should return false
    */
    @Test
    public void paySavedDetailsValid() {
        CustomerInterface test = new CustomerInterface();
        Customer customer = new Customer("fjkdslfjdkls", "password");
        customer.setCreditCardName("Charles");
        customer.setCreditCardNum("40691");
        test.setCustomer(customer);
        String input = "Y";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scan = new Scanner(in);
        assertTrue(test.payWithCreditCard(scan));
    }

    @Test
    public void paySavedDetailsInvalidNum() {
        CustomerInterface test = new CustomerInterface();
        Customer customer = new Customer("fjkdslfjdkls", "password");
        customer.setCreditCardName("Charles");
        customer.setCreditCardNum("00110");
        test.setCustomer(customer);
        String input = "Y";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scan = new Scanner(in);
        assertFalse(test.payWithCreditCard(scan));
    }

    @Test
    public void paySavedDetailsInvalidName() {
        CustomerInterface test = new CustomerInterface();
        Customer customer = new Customer("fjkdslfjdkls", "password");
        customer.setCreditCardName("fjdsklfjd");
        customer.setCreditCardNum("40691");
        test.setCustomer(customer);
        String input = "Y";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scan = new Scanner(in);
        assertFalse(test.payWithCreditCard(scan));
    }


}
