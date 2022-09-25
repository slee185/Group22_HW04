/*
 * Group 22 Homework 03
 * ViewDrinksFragment.java
 * Ken Stanley & Stephanie Karp
 */
package edu.uncc.hw04.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.uncc.hw04.Drink;
import edu.uncc.hw04.Drink.AlcoholPercentComparator;
import edu.uncc.hw04.Drink.DateAddedComparator;
import edu.uncc.hw04.R;
import edu.uncc.hw04.ViewDrinksRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ViewDrinksFragment extends Fragment implements ViewDrinksRecyclerAdapter.iViewDrinks {
    int currentDrinkNumber = 0;
    ArrayList<Drink> drinks;
    LinearLayoutManager layoutManager;
    ViewDrinksRecyclerAdapter adapter;

    AlcoholPercentComparator alcPercentComp = new AlcoholPercentComparator();
    DateAddedComparator dateAddedComp = new DateAddedComparator();

    public ViewDrinksFragment(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewdrinks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView viewDrinksRecyclerView = view.findViewById(R.id.viewDrinksRecyclerView);
        viewDrinksRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        viewDrinksRecyclerView.setLayoutManager(layoutManager);
        adapter = new ViewDrinksRecyclerAdapter(getActivity(), drinks, this);
        viewDrinksRecyclerView.setAdapter(adapter);

        updateView(drinks);

        view.findViewById(R.id.viewDrinksCloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.viewDrinksButtonCloseClicked(drinks);
            }
        });

        view.findViewById(R.id.viewDrinksSortAlcoholPercentAscButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByAlcoholPercentAsc(drinks);
            }
        });

        view.findViewById(R.id.viewDrinksSortAlcoholPercentDescButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByAlcoholPercentDesc(drinks);
            }
        });

        view.findViewById(R.id.viewDrinksSortDateAddedAscButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByDateAddedAsc(drinks);
            }
        });

        view.findViewById(R.id.viewDrinksSortDateAddedDescButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByDateAddedDesc(drinks);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof iListener) {
            mListener = (iListener) context;
        } else {
            throw new RuntimeException(context + getString(R.string.listener_throw_message));
        }
    }

    public void updateView(ArrayList<Drink> drinks) {
        Drink currentDrink = drinks.get(currentDrinkNumber);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);

        Date drinkDate = Calendar.getInstance().getTime();
        drinkDate.setTime(currentDrink.dateTime);
    }

    public void sortByAlcoholPercentAsc(ArrayList<Drink> drinks) {
        Collections.sort(drinks, alcPercentComp);
        adapter.notifyDataSetChanged();
    }

    public void sortByAlcoholPercentDesc(ArrayList<Drink> drinks) {

    }

    public void sortByDateAddedAsc(ArrayList<Drink> drinks) {
        Collections.sort(drinks, dateAddedComp);
        adapter.notifyDataSetChanged();
    }

    public void sortByDateAddedDesc(ArrayList<Drink> drinks) {

    }

    iListener mListener;

    @Override
    public void trashButtonClicked(int currentDrinkNumber) {
        drinks.remove(currentDrinkNumber);
        if (drinks.isEmpty()) {
            mListener.viewDrinksButtonCloseClicked(drinks);
        } else {
            adapter.notifyItemRemoved(currentDrinkNumber);
        }
    }

    public interface iListener {
        void viewDrinksButtonCloseClicked(ArrayList<Drink> drinks);
    }
}
