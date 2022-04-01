package com.example.quickcash.payment;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quickcash.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class PaymentActivity extends AppCompatActivity {

    ActivityResultLauncher activityResultLauncher;
    private static final int PAYPAL_REQUEST_CODE = 555;
    private static PayPalConfiguration config;
    private Button paymentButton;
    private EditText Amount;
    private String amountNum = "";
    private boolean isPaymentSuccessful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(Config.PAYPAL_CLIENT_ID);
        paymentButton = findViewById(R.id.paymentButton);
        Amount = findViewById(R.id.amount);
        init();
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment();
            }
        });
    }

    public void init(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==RESULT_OK){
                    PaymentConfirmation confirmation = result.getData().getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirmation!=null){
                        try {
                            String paymentDetails = confirmation.toJSONObject().toString(4);
                            JSONObject payObj = new JSONObject(paymentDetails);
                            String state = payObj.getJSONObject("response").getString("state");
                            setPaymentState(true);
                            showToastMessage("Payment " + state);
                        } catch (JSONException e) {
                            Log.e("Error", "Something going wrong: ", e);
                        }
                    }
                    showToastMessage("user canceled this payment");
                } else if (result.getResultCode() == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID){
                    Log.d(TAG,"Launcher Result Invalid");
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Log.d(TAG, "Launcher Result Cancelled");
                }
            }
        });
    }

    private void payment() {
        amountNum = Amount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amountNum)),"CAD","Payment of commission",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        activityResultLauncher.launch(intent);
    }

    private void showToastMessage(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private boolean getPaymentState(){
        return this.isPaymentSuccessful;
    }

    private void setPaymentState(boolean paymentState){
        this.isPaymentSuccessful = paymentState;
    }
}

