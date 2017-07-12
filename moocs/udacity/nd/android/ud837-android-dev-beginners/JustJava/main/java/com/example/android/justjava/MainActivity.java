package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    int basicPrice = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText cN = (EditText) findViewById(R.id.customer_name);
        String customerName = cN.getText().toString();
        CheckBox whippedCreamBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamBox.isChecked();
/*        Log.v("MainActivity", "check box is ticked: " + hasWhippedCream ); */
        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(customerName, price, hasWhippedCream, hasChocolate);
//        displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        String subject = getString(R.string.email_subject,customerName);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * This method returns a summary message.
     *
     * @param priceOfOrder,   the total cost
     * @param hasWhippedCream is whether or not the customer is ordering whipped cream
     * @param hasChocolate    is whether or not the customer is ordering chocolate
     * @param customerName    is the name of the customer
     * @return text summary
     */
    private String createOrderSummary(String customerName, int priceOfOrder, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage;
        priceMessage = getString(R.string.customer_name,customerName);
        priceMessage = getString(R.string.customer_name,customerName);
        priceMessage += "\n" + getString(R.string.add_whipped_cream,hasWhippedCream);
        priceMessage += "\n" + getString(R.string.add_chocolate,hasChocolate);
        priceMessage += "\n" + getString(R.string.quantity_java,quantity);
        priceMessage += "\n" + getString(R.string.total,String.valueOf(priceOfOrder));
//        priceMessage += "\n" + getString(R.string.total,NumberFormat.getCurrencyInstance().format(priceOfOrder));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream tells whether whipped cream is also ordered
     * @param hasChocolate    tells whether chocolate is also ordered
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = basicPrice;
        if (hasWhippedCream) {
            price = price + 1;
        }
        if (hasChocolate) {
            price = price + 2;
        }
        return quantity * price;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Log.v("MainActivity", "Attempt made to go over upper limit of 100");
            String upperLimitComment = getString(R.string.toast_upper_limit);
            Toast.makeText(this,upperLimitComment, Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Log.v("MainActivity", "Attempted made to go over lower limit of 1");
            String lowerLimitComment = getString(R.string.toast_lower_limit);
            Toast.makeText(this,lowerLimitComment, Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


}