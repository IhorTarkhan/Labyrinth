package ua.knu.labyrinth;

import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.List;

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
        TableLayout tableView = view.findViewById(R.id.layouttable_set_ships);

        System.out.println(getArguments().getString("level"));

        Map map = Map.generateMap(10);
        map.generateBordersHard();

        int cellSize = (screenWidth - 100) / map.getMatrix().size();
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

        View ball = view.findViewById(R.id.ball);

        view.findViewById(R.id.button_down).setOnClickListener(
                v -> ball.setY(ball.getY() + cellSize));

        view.findViewById(R.id.button_up).setOnClickListener(
                v -> ball.setY(ball.getY() - cellSize));

        view.findViewById(R.id.button_left).setOnClickListener(
                v -> ball.setX(ball.getX() - cellSize));

        view.findViewById(R.id.button_right).setOnClickListener(
                v -> ball.setX(ball.getX() + cellSize));
    }
}