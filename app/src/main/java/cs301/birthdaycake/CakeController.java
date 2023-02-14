package cs301.birthdaycake;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {

    private CakeView cakeView;
    private CakeModel cakeModel;

    public CakeController(CakeView view) {
        cakeView = view;
        cakeModel = view.getCakeModel();
    }

    @Override
    public void onClick(View v) {
        boolean should_draw = !cakeModel.get_lit_candles();
        cakeModel.set_lit_candles(should_draw);

        View newView = v;
        Button lit_action = newView.findViewById(R.id.blow_out);
        if(should_draw == false) {
            lit_action.setText("Re-Light");
        } else {
            lit_action.setText("Blow Out");
        }
        cakeView.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        cakeModel.set_has_candles(isChecked);
        cakeView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        cakeModel.set_num_candles(progress);
        cakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        boolean rtn = true;

        if(e.getAction() == MotionEvent.ACTION_DOWN) {
            cakeModel.setTouchX(e.getX());
            cakeModel.setTouchY(e.getY());
            cakeView.invalidate();
        }

        return rtn;
    }
}
