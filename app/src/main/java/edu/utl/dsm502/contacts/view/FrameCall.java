package edu.utl.dsm502.contacts.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.utl.dsm502.contacts.databinding.FragmentFrameCallBinding;

public class FrameCall extends Fragment {

    private FragmentFrameCallBinding binding;

    public FrameCall() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrameCallBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }
}