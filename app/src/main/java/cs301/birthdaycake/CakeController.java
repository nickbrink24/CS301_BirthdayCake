package cs301.birthdaycake;

import android.util.Log;
import android.view.View;

public class CakeController implements View.OnClickListener {

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
}
