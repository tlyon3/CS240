package edu.byu.cs240.fragmentdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.byu.cs240.fragmentdemo.R;
import edu.byu.cs240.fragmentdemo.model.Order;

public class MainActivity extends Activity {

    private final int REQ_CODE_ORDER_INFO = 1;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onButtonClicked();
            }
        });
    }

    private void onButtonClicked() {
        Intent intent = new Intent(this, OrderInfoActivity.class);
        startActivityForResult(intent, REQ_CODE_ORDER_INFO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_ORDER_INFO && resultCode == RESULT_OK && data != null) {
            Order order = OrderInfoActivity.getResult(data);

            Log.i("FragmentDemo", order.toString());
        }
    }
}
