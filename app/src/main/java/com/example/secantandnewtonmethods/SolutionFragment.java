package com.example.secantandnewtonmethods;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.util.function.Function;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SolutionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SolutionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolutionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final double a = 9.5;
    private final double b = 10;
    private final double eps = 1e-3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SolutionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolutionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolutionFragment newInstance(String param1, String param2) {
        SolutionFragment fragment = new SolutionFragment();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.result);
        textView.append(Double.toString(solveWithNewtonsMethod()) + '\n');
        textView.append(Double.toString(solveWithSecantMethod()) + '\n');
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solution, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private double f(double x) {
        return 0.3 * Math.cos(x) * Math.cos(x) - Math.log(x) + 2;
    }

    private double f1(double x) {
        return -0.3 * Math.sin(2 * x) - 1/x;
    }

    private double f2(double x) {
        return -0.6 * Math.cos(2 * x) + 1/x/x;
    }

    private boolean checkIfF1EqualsZero() {
        for (double x = a; x <= b; x += eps) {
            if (Math.abs(f1(x)) < eps) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfF2ChangesSign() {
        for (double x = a + eps; x <= b; x += eps) {
            if (f2(x) * f2(x - eps) < 0) {
                return true;
            }
        }
        return false;
    }

    private double solveWithNewtonsMethod() {
        double xPrev = b;
        double xNext = xPrev - f(xPrev) / f1(xPrev);
        if (checkIfF1EqualsZero()
                || checkIfF2ChangesSign()
                || f2(xPrev) * f(xPrev) < 0) {
            return Double.NaN;
        }
        while (Math.abs(xPrev - xNext) >= eps) {
            xPrev = xNext;
            xNext = xPrev - f(xPrev) / f1(xPrev);
        }
        return xNext;
    }

    private double solveWithSecantMethod() {
        if (checkIfF2ChangesSign()
            || f(a) * f(b) > 0) {
            return Double.NaN;
        }
        double x1 = a;
        double x2 = b;
        double x3 = x1 - f(x1) * (x2 - x1) / (f(x2) - f(x1));
        while (Math.abs(x3 - x2) >= eps) {
            x1 = x2;
            x2 = x3;
            x3 = x1 - f(x1) * (x2 - x1) / (f(x2) - f(x1));
        }
        return x3;
    }
}
