/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 2;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
//        Log.v("MainActivity","Has Whipped Cream : " + hasWhippedCream);

        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name);
        String name = nameEditText.getText().toString();

        int price=calculatePrice(quantity, hasWhippedCream , hasChocolate);
        String message=createOrderSummary(price , hasWhippedCream , hasChocolate,name);
        String subject = "JustJava order for " + name;
        composeEmail(message,subject);
        //displayMessage(message);
    }

    public void composeEmail(String body, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     *Create Summary Of Order
     *@param price of the order
     *@param addWhippedCream is whether user wants whipped cream or not
     * @return textsummary*/

    private String createOrderSummary(int price, boolean addWhippedCream , boolean addChocolate, String customerName)
    {
        String message="Name: " + customerName + "\nAdd Whipped Cream? " + addWhippedCream  ;
        message += "\nAdd Chocolate? " + addChocolate;
        message += "\nQuantity: "+ quantity + "\nTotal: " + price + "\nThank You!";
        return message;
    }
    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity , boolean addwhippedCream, boolean addChocolate)
    {
        int base = 5;
        if(addwhippedCream)
        {
            base += 1;
        }
        if(addChocolate)
        {
            base+=2;
        }
        int price = quantity * base;
        return price;
    }

    public void increment(View view)
    {

        if(quantity==100)
        {
            Toast.makeText(this , "You Cannot have more than 100 coffee!", Toast.LENGTH_LONG).show();
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }

    public void decrement(View view)
    {
        if(quantity==1)
        {
            Toast.makeText(this , "You Cannot have less than 1 coffee!", Toast.LENGTH_LONG).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffee) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffee);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}