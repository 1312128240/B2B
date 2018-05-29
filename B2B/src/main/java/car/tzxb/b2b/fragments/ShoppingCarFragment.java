package car.tzxb.b2b.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import car.tzxb.b2b.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCarFragment extends Fragment {


    public ShoppingCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_car, container, false);
    }

}
