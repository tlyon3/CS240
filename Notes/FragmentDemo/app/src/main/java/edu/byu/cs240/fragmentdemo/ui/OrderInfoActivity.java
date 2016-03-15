package edu.byu.cs240.fragmentdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import edu.byu.cs240.fragmentdemo.R;
import edu.byu.cs240.fragmentdemo.model.Address;
import edu.byu.cs240.fragmentdemo.model.Order;

public class OrderInfoActivity extends FragmentActivity {

    public static String EXTRA_RESULT = "result";

    public static Order getResult(Intent intent) {
        return (Order)intent.getSerializableExtra(EXTRA_RESULT);
    }

    private AddressFragment shippingAddressFragment;
    private AddressFragment billingAddressFragment;
    private CheckBox checkBox;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        checkBox = (CheckBox)findViewById(R.id.checkBox);
        checkBox.setChecked(true);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckChanged();
            }
        });

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked();
            }
        });

        FragmentManager fm = this.getSupportFragmentManager();
        shippingAddressFragment = (AddressFragment)fm.findFragmentById(R.id.shippingFrameLayout);
        if (shippingAddressFragment == null) {
            shippingAddressFragment = AddressFragment.newInstance("SHIPPING ADDRESS");
            fm.beginTransaction()
                .add(R.id.shippingFrameLayout, shippingAddressFragment)
                .commit();
        }

        // Don't add billingAddressFragment, because by default the billing address is the
        // same as the shipping address (see the onCheckChanged method)
    }

    private void onCheckChanged() {
        FragmentManager fm = this.getSupportFragmentManager();

        if (checkBox.isChecked()) {
            // Remove the billingAddressFragment, because we no longer need it
            // (because the billing address is the same as the shipping address)

            fm.beginTransaction()
                .remove(billingAddressFragment)
                .commit();
        }
        else {
            // Create the billingAddressFragment, because the billing address is now
            // different than the shipping address

            billingAddressFragment = AddressFragment.newInstance("BILLING ADDRESS");
            fm.beginTransaction()
                    .add(R.id.billingFrameLayout, billingAddressFragment)
                    .commit();
        }
    }

    private void onButtonClicked() {
        returnResult();
        this.finish();
    }

    private void returnResult() {
        Address shippingAddress = shippingAddressFragment.getAddress();

        Address billingAddress = null;
        if (!checkBox.isChecked()) {
            billingAddress = billingAddressFragment.getAddress();
        }

        Order order = new Order();
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, order);
        setResult(RESULT_OK, intent);
    }

}
