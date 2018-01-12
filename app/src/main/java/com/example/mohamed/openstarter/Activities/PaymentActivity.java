package com.example.mohamed.openstarter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohamed.openstarter.Data.DataSuppliers.PaymentDs;
import com.example.mohamed.openstarter.Helpers.Util.PaymentConfig;
import com.example.mohamed.openstarter.Models.Payment;
import com.example.mohamed.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class PaymentActivity extends AppCompatActivity {

    private EditText et_amount;
    private Button confirm;

    String amount;

    public static final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaymentConfig.PAYPAL_CLIENT_ID);

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Start PayPal service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);


        et_amount=findViewById(R.id.amount);
        confirm=findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    private void processPayment() {
        amount=et_amount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount))
                ,"USD","Donate for the project",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode==RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if ((confirmation!=null)){
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetailsActivity.class)
                        .putExtra("PaymentDetails",paymentDetails)
                        .putExtra("PaymentAmount",amount)
                        );

                        Log.d("paypall","confirmed ");
                        PaymentDs paymentDs = new PaymentDs();
                        paymentDs.paymentCreate(FirebaseAuth.getInstance().getCurrentUser().getEmail(), getIntent().getStringExtra("projectId"), Float.parseFloat(amount), new PaymentDs.CallbackAdd() {
                            @Override
                            public void onSuccessCreate(Payment createdPayment) {
                                Toast.makeText(PaymentActivity.this, "payment confirmed", Toast.LENGTH_SHORT).show();
                                Log.d("paypall","payment added to database");
                                finish();
                            }

                            @Override
                            public void onFail() {
                                Log.d("paypall","payment failed to add to database");

                            }
                        });

                        finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (resultCode== Activity.RESULT_CANCELED)
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }
        else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this, "INVALID", Toast.LENGTH_SHORT).show();
        }
    }
}
