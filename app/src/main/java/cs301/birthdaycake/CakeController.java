package cs301.birthdaycake;

import android.view.View;
import android.widget.CompoundButton;

public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private CakeView cakeView;
    private CakeModel cakeModel;

    public CakeController(CakeView view) {
        cakeView = view;
        cakeModel = view.getCakeModel();
    }

    @Override
    public void onClick(View v) {
        cakeModel.set_lit_candles(false);
        cakeView.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        cakeModel.set_has_candles(isChecked);
        cakeView.invalidate();
    }
}
