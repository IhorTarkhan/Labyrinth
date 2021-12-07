package ua.knu.labyrinth;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.Slider;

import ua.knu.labyrinth.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentFirstBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_easy_level).setOnClickListener(
                v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("level", "easy");
                    bundle.putString("size", String.valueOf((int) view.<Slider>findViewById(R.id.continuousSlider).getValue()));
                    findNavController(this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                });
        view.findViewById(R.id.button_medium_level).setOnClickListener(
                v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("level", "medium");
                    bundle.putString("size", String.valueOf((int) view.<Slider>findViewById(R.id.continuousSlider).getValue()));
                    findNavController(this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                });
        view.findViewById(R.id.button_hard_level).setOnClickListener(
                v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("level", "hard");
                    bundle.putString("size", String.valueOf((int) view.<Slider>findViewById(R.id.continuousSlider).getValue()));
                    findNavController(this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                });
    }
}