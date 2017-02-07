package com.example.android.justjava;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void submitOrder(View view) {
        EditText nameField= (EditText) findViewById(R.id.name_field);
        String name=nameField.getText().toString();
        Log.v("MainActivity","Name:" +name);
        CheckBox whippedCreamCheckBox= (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox ChocolateCheckBox=(CheckBox)findViewById(R.id.Chocolate);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        boolean hasChocolate=ChocolateCheckBox.isChecked();
        Log.v("Main Activity","has Whipped Cream :"+hasWhippedCream);
        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java app for coffee order for" +name);
        intent.putExtra(Intent.EXTRA_TEXT,"" +priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
      }

  //      displayMessage(priceMessage);
    }
    private String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolate){
        String priceMessage="Name :  \n"+ name ;
        priceMessage=priceMessage+"\n Add Whipped Cream?"  +  addWhippedCream;
        priceMessage=priceMessage+"\n Add Chocolate?"  +  addChocolate;
        priceMessage=priceMessage+"\n Quantity "+ quantity;
        priceMessage=priceMessage+ "\n Total: $ " + price;
        priceMessage=priceMessage+"\nThank you !!";
        return priceMessage;
    }

    private int calculatePrice(boolean addWhippedCream,boolean addChocolate){
        int basePrice=5;
        if(addWhippedCream){
            basePrice=basePrice+1;
        }
        if(addChocolate){
            basePrice=basePrice+2;
        }
        int price=quantity*basePrice;
        return price;
    }
    public void increment(View view){
        if(quantity==100){
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view){
        if(quantity==1){
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

}