package com.example.moderartui;


import static android.graphics.Color.rgb;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.course.components.MoreInfoDialog;
//import android.course.modernui.FullscreenActivity.ColorBox;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<ColorBox> colorBoxes = colorBoxes();
        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        
//        final Button menuButton = (Button) findViewById(R.id.menuButton);
//        menuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) { openOptionsMenu(); }
//        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for (ColorBox colorBox : colorBoxes) {
                    int red = colorBox.getRed();
                    int green = colorBox.getGreen();
                    int blue = colorBox.getBlue();
                    if (red == green && red == blue) {
                        continue;
                    }

                    colorBox.setBackgroundColor(rgb(
                            inverse(red, progress),
                            inverse(green, progress),
                            inverse(blue, progress)));
                    colorBox.invalidate();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    static int inverse(int color, int progress) {
        return color + (int) ((255f - (2 * color)) * (progress / 100f));
    }

    private List<ColorBox> colorBoxes() {
        List<ColorBox> colorBoxes = new ArrayList<ColorBox>();
        colorBoxes.add(new ColorBox(findViewById(R.id.tv1)));
        colorBoxes.add(new ColorBox(findViewById(R.id.tv2)));
        colorBoxes.add(new ColorBox(findViewById(R.id.tv3)));
        colorBoxes.add(new ColorBox(findViewById(R.id.tv4)));
        colorBoxes.add(new ColorBox(findViewById(R.id.tv5)));

        return colorBoxes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu, menu );
        return true;
    }

    public void showDialog(MenuItem item) {
        MoreInfoDialog dialog = new MoreInfoDialog();
        dialog.show(getFragmentManager(), TAG);
    }

    private class ColorBox {
        private final View view;
        private final int red;
        private final int green;
        private final int blue;

        public ColorBox(View view) {
            this.view = view;

            int color = ((ColorDrawable) view.getBackground()).getColor();
            red = Color.red (color);
            green = Color.green(color);
            blue = Color.blue(color);
        }

        public void setBackgroundColor(int color) {
            view.setBackgroundColor(color);
        }

        public void invalidate() {
            view.invalidate();
        }

        public int getRed() {
            return red;
        }

        public int getGreen() {
            return green;
        }

        public int getBlue() {
            return blue;
        }
    }
}
