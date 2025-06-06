package edu.uncc.assignment06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import edu.uncc.assignment06.databinding.FragmentSelectTaskDateBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectTaskDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectTaskDateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectTaskDateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectTaskDateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTaskDateFragment newInstance(String param1, String param2) {
        SelectTaskDateFragment fragment = new SelectTaskDateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentSelectTaskDateBinding binding;
    SelectTaskDateFragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectTaskDateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Task Date");

        binding.datePicker.setMaxDate(System.currentTimeMillis());

        binding.buttonCancel.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        binding.buttonSubmit.setOnClickListener(v -> {
            DatePicker dp = binding.datePicker;
            Calendar cal = Calendar.getInstance();
            cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
            Date selectedDate = cal.getTime();
            if (selectedDate.after(new Date())) {
                Toast.makeText(getActivity(), "Please select a valid date", Toast.LENGTH_SHORT).show();
            } else {
                if (listener != null) {
                    listener.setSelectedDate(selectedDate);
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (SelectTaskDateFragmentListener) context;
    }

    interface SelectTaskDateFragmentListener {
        void setSelectedDate(Date date);
    }

}