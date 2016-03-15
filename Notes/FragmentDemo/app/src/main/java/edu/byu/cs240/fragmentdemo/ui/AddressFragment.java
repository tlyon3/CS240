package edu.byu.cs240.fragmentdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import edu.byu.cs240.fragmentdemo.R;
import edu.byu.cs240.fragmentdemo.model.Address;


public class AddressFragment extends Fragment {

    private static final String ARG_TITLE = "title";

    private String title;

    private TextView titleTextView;
    private EditText streetEditText;
    private EditText cityEditText;
    private EditText stateEditText;
    private EditText zipCodeEditText;

    public AddressFragment() {
        // Required empty public constructor
    }

    public static AddressFragment newInstance(String title) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_address, container, false);

        titleTextView = (TextView)v.findViewById(R.id.titleTextView);
        titleTextView.setText(title);

        streetEditText = (EditText)v.findViewById(R.id.streetEditText);

        cityEditText = (EditText)v.findViewById(R.id.cityEditText);

        stateEditText = (EditText)v.findViewById(R.id.stateEditText);

        zipCodeEditText = (EditText)v.findViewById(R.id.zipCodeEditText);

        return v;
    }

    public Address getAddress() {
        Address addr = new Address();
        addr.setStreet(streetEditText.getText().toString());
        addr.setCity(cityEditText.getText().toString());
        addr.setState(stateEditText.getText().toString());
        addr.setZipCode(zipCodeEditText.getText().toString());

        return addr;
    }
}
