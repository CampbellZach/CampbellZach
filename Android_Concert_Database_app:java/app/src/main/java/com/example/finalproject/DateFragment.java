package com.example.finalproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener onDateSetListener;

    public DateFragment(DatePickerDialog.OnDateSetListener onDateSetListener){
        this.onDateSetListener = onDateSetListener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date, container, false);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this.onDateSetListener,year,month,day);
        return datePickerDialog;

    }
}
