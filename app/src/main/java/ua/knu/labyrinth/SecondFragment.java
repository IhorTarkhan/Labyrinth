package ua.knu.labyrinth;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AtomicInteger ballX = new AtomicInteger();
        AtomicInteger ballY = new AtomicInteger();

        Map map = Map.generateMap(7);
        switch (getArguments().getString("level")) {
            case "easy":
                map.generateBordersEasy();
                break;
            case "medium":
                map.generateBordersEasy(); // ToDo
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

        view.findViewById(R.id.button_down).setOnClickListener(
                v -> {
                    if (map.getPoint(ballX.get(), ballY.get()).isBorderBottom()) {
                        showError(v);
                    } else {
                        ball.setY(ball.getY() + cellSize);
                        ballY.getAndIncrement();
                    }
                });

        view.findViewById(R.id.button_up).setOnClickListener(
                v -> {
                    if (map.getPoint(ballX.get(), ballY.get()).isBorderTop()) {
                        showError(v);
                    } else {
                        ball.setY(ball.getY() - cellSize);
                        ballY.getAndDecrement();
                    }
                });

        view.findViewById(R.id.button_left).setOnClickListener(
                v -> {
                    if (map.getPoint(ballX.get(), ballY.get()).isBorderLeft()) {
                        showError(v);
                    } else {
                        ball.setX(ball.getX() - cellSize);
                        ballX.getAndDecrement();
                    }
                });

        view.findViewById(R.id.button_right).setOnClickListener(
                v -> {
                    if (map.getPoint(ballX.get(), ballY.get()).isBorderRight()) {
                        showError(v);
                    } else {
                        ball.setX(ball.getX() + cellSize);
                        ballX.getAndIncrement();
                    }
                });
    }

    private void showError(View v) {
        Snackbar snackbar = Snackbar.make(v, R.string.app_name, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.RED);
        snackbar.show();
    }

    private void drawMap(@NonNull View view, Map map) {
        int cellSize = (screenWidth - 100) / map.getMatrix().size();
        TableLayout tableView = view.findViewById(R.id.layouttable_set_ships);
        for (List<Point> row : map.getMatrix()) {
            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            for (Point point : row) {
                ImageView imageView = new ImageView(getActivity());
                ArrayList<Drawable> layers = new ArrayList<>();
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