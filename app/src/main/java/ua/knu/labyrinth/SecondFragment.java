package ua.knu.labyrinth;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import ua.knu.labyrinth.databinding.FragmentSecondBinding;
import ua.knu.labyrinth.game.Map;
import ua.knu.labyrinth.game.Point;

public class SecondFragment extends Fragment {

    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentSecondBinding.inflate(inflater, container, false).getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AtomicInteger ballX = new AtomicInteger();
        AtomicInteger ballY = new AtomicInteger();
        AtomicInteger steps = new AtomicInteger();


        int size = Integer.parseInt(getArguments().getString("size"));

        Map map = Map.generateMap(size);
        switch (getArguments().getString("level")) {
            case "easy":
                map.generateBordersEasy();
                break;
            case "medium":
                map.generateBordersMedium();
                break;
            case "hard":
                map.generateBordersHard();
                break;
        }
        int cellSize = (screenWidth - 100) / map.getMatrix().size();

        drawMap(view, map);

        View ball = view.findViewById(R.id.ball);
        ball.getLayoutParams().height = (int) (cellSize * 0.8) + 50 + (int) (cellSize * 0.1);
        ball.getLayoutParams().width = (int) (cellSize * 0.8) + 50 + (int) (cellSize * 0.1);
        ball.setPadding(50 + (int) (cellSize * 0.1), 50 + (int) (cellSize * 0.1), 0, 0);
        TextView stepsText = view.findViewById(R.id.steps);

        view.findViewById(R.id.button_help).setOnClickListener(v -> {
            view.findViewById(R.id.button_down).setVisibility(View.GONE);
            new Thread(() -> {
                try {

                    float downX = hideButton(view, R.id.button_down);
                    float upX = hideButton(view, R.id.button_up);
                    float leftX = hideButton(view, R.id.button_left);
                    float rightX = hideButton(view, R.id.button_right);
                    float helpX = hideButton(view, R.id.button_help);
                    map.goToExit(ballX.get(), ballY.get())
                            .forEach(d -> {
                                switch (d) {
                                    case LEFT:
                                        ball.setX(ball.getX() - cellSize);
                                        ballX.getAndDecrement();
                                        break;
                                    case RIGHT:
                                        ball.setX(ball.getX() + cellSize);
                                        ballX.getAndIncrement();
                                        break;
                                    case BOTTOM:
                                        ball.setY(ball.getY() + cellSize);
                                        ballY.getAndIncrement();
                                        break;
                                    case TOP:
                                        ball.setY(ball.getY() - cellSize);
                                        ballY.getAndDecrement();
                                        break;
                                }
                                steps.getAndIncrement();
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                    showButton(view, R.id.button_down, downX);
                    showButton(view, R.id.button_up, upX);
                    showButton(view, R.id.button_left, leftX);
                    showButton(view, R.id.button_right, rightX);
                    showButton(view, R.id.button_help, helpX);
                    Snackbar snackbar = Snackbar.make(v, "Helped", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(Color.rgb(205, 220, 57));
                    snackbar.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            view.findViewById(R.id.button_down).setVisibility(View.VISIBLE);
            stepsText.setText("Steps: " + (steps.get() + map.goToExit(ballX.get(), ballY.get()).size()));
        });
        view.findViewById(R.id.button_down).setOnClickListener(v -> {
            if (map.getPoint(ballX.get(), ballY.get()).isBorderBottom()) {
                showError(v);
            } else if (ballX.get() == map.getMatrix().size() - 1
                    && ballY.get() + 1 == map.getMatrix().size() - 1) {
                win(v, steps.get());
            } else {
                ball.setY(ball.getY() + cellSize);
                ballY.getAndIncrement();
                steps.getAndIncrement();
                stepsText.setText("Steps: " + steps.get());
            }
        });
        view.findViewById(R.id.button_up).setOnClickListener(v -> {
            if (map.getPoint(ballX.get(), ballY.get()).isBorderTop()) {
                showError(v);
            } else if (ballX.get() == map.getMatrix().size() - 1
                    && ballY.get() - 1 == map.getMatrix().size() - 1) {
                win(v, steps.get());
            } else {
                ball.setY(ball.getY() - cellSize);
                ballY.getAndDecrement();
                steps.getAndIncrement();
                stepsText.setText("Steps: " + steps.get());
            }
        });
        view.findViewById(R.id.button_left).setOnClickListener(v -> {
            if (map.getPoint(ballX.get(), ballY.get()).isBorderLeft()) {
                showError(v);
            } else if (ballX.get() - 1 == map.getMatrix().size() - 1
                    && ballY.get() == map.getMatrix().size() - 1) {
                win(v, steps.get());
            } else {
                ball.setX(ball.getX() - cellSize);
                ballX.getAndDecrement();
                steps.getAndIncrement();
                stepsText.setText("Steps: " + steps.get());
            }
        });
        view.findViewById(R.id.button_right).setOnClickListener(v -> {
            if (map.getPoint(ballX.get(), ballY.get()).isBorderRight()) {
                showError(v);
            } else if (ballX.get() + 1 == map.getMatrix().size() - 1
                    && ballY.get() == map.getMatrix().size() - 1) {
                win(v, steps.get());
            } else {
                ball.setX(ball.getX() + cellSize);
                ballX.getAndIncrement();
                steps.getAndIncrement();
                stepsText.setText("Steps: " + steps.get());
            }
        });
    }

    private void showButton(View view, @IdRes int id, float originX) {
        View viewById = view.findViewById(id);
        viewById.setX(originX);
    }

    private float hideButton(@NonNull View view, @IdRes int id) {
        View viewById = view.findViewById(id);
        float originX = viewById.getX();
        viewById.setX(-1000);
        return originX;
    }

    private void win(View v, int steps) {
        Snackbar snackbar = Snackbar.make(v, "Win in " + ++steps + " steps", Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.rgb(76, 175, 80));
        snackbar.show();
        findNavController(this).navigate(R.id.action_SecondFragment_to_FirstFragment);

    }

    private void showError(View v) {
        Snackbar snackbar = Snackbar.make(v, R.string.error, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.RED);
        snackbar.show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void drawMap(@NonNull View view, Map map) {
        int cellSize = (screenWidth - 100) / map.getMatrix().size();
        TableLayout tableView = view.findViewById(R.id.layouttable_set_ships);
        for (int i = 0; i < map.getMatrix().size(); i++) {
            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < map.getMatrix().get(i).size(); j++) {
                ImageView imageView = new ImageView(getActivity());
                ArrayList<Drawable> layers = new ArrayList<>();
                Point point = map.getMatrix().get(i).get(j);
                if (point.isBorderBottom()) {
                    layers.add(getResources().getDrawable(R.drawable.border_bottomt));
                }
                if (point.isBorderTop()) {
                    layers.add(getResources().getDrawable(R.drawable.border_top));
                }
                if (point.isBorderLeft()) {
                    layers.add(getResources().getDrawable(R.drawable.border_left));
                }
                if (point.isBorderRight()) {
                    layers.add(getResources().getDrawable(R.drawable.border_right));
                }
                if (i == map.getMatrix().size() - 1 && j == map.getMatrix().size() - 1) {
                    layers.add(getResources().getDrawable(R.drawable.star));
                }
                LayerDrawable layerDrawable = new LayerDrawable(layers.toArray(new Drawable[0]));
                imageView.setImageDrawable(layerDrawable);
                tr.addView(imageView);

                imageView.getLayoutParams().height = cellSize;
                imageView.getLayoutParams().width = cellSize;
            }
            tableView.addView(tr);
        }
    }
}