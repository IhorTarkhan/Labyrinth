package ua.knu.labyrinth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ua.knu.labyrinth.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    public static final int BALL_STEP = 10;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentSecondBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        View ball = view.findViewById(R.id.ball);

        view.findViewById(R.id.button_down).setOnClickListener(
                v -> ball.setY(ball.getY() + BALL_STEP));

        view.findViewById(R.id.button_up).setOnClickListener(
                v -> ball.setY(ball.getY() - BALL_STEP));

        view.findViewById(R.id.button_left).setOnClickListener(
                v -> ball.setX(ball.getX() - BALL_STEP));

        view.findViewById(R.id.button_right).setOnClickListener(
                v -> ball.setX(ball.getX() + BALL_STEP));
    }
}