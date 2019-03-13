package com.jackathan.josefloadingapp;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;

import com.jackathan.josefloading.JosefLoading;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.AxisX)
    Switch axisX;
    @BindView(R.id.AxisY)
    Switch axisY;
    @BindView(R.id.JosefLoading)
    JosefLoading josefLoading;
    @BindView(R.id.Stiffness)
    SeekBar stiffness;
    @BindView(R.id.DampingRatio)
    SeekBar dampingRatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setStiffnessListener();
        setDampingRatioListener();
    }

    private void setStiffnessListener() {
        stiffness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        josefLoading.setStiffness(JosefLoading.STIFFNESS_VERY_LOW);
                        break;
                    case 1:
                        josefLoading.setStiffness(JosefLoading.STIFFNESS_LOW);
                        break;
                    case 2:
                        josefLoading.setStiffness(JosefLoading.STIFFNESS_MEDIUM);
                        break;
                    case 3:
                        josefLoading.setStiffness(JosefLoading.STIFFNESS_HIGH);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setDampingRatioListener() {
        dampingRatio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        josefLoading.setDampingRatio(JosefLoading.DAMPING_RATIO_NO_BOUNCY);
                        break;
                    case 1:
                        josefLoading.setDampingRatio(JosefLoading.DAMPING_RATIO_LOW_BOUNCY);
                        break;
                    case 2:
                        josefLoading.setDampingRatio(JosefLoading.DAMPING_RATIO_MEDIUM_BOUNCY);
                        break;
                    case 3:
                        josefLoading.setDampingRatio(JosefLoading.DAMPING_RATIO_HIGH_BOUNCY);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick(R.id.AxisX)
    public void switchClickedAxisX() {
        if (axisX.isChecked()) {
            josefLoading.setMoveOnAxisX(false);
        } else {
            josefLoading.setMoveOnAxisX(true);
        }
    }

    @OnClick(R.id.AxisY)
    public void switchClickedAxisY() {
        if (axisY.isChecked()) {
            josefLoading.setMoveOnAxisY(false);
        } else {
            josefLoading.setMoveOnAxisY(true);
        }
    }
}
