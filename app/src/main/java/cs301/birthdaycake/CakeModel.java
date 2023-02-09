package cs301.birthdaycake;

public class CakeModel {

    private boolean lit_candles = true;
    private int num_lit_candles = 2;
    private boolean has_frosting = true;
    private boolean has_candles = true;

    public void set_lit_candles(Boolean b) {
        lit_candles = b;
    }

    public boolean get_lit_candles() {
        return lit_candles;
    }
}
