package cs301.birthdaycake;

public class CakeModel {

    private boolean lit_candles = true;
    private int num_candles = 2;
    private boolean has_frosting = true;
    private boolean has_candles = true;
    private float touchX;
    private float touchY;

    public void set_lit_candles(Boolean b) {
        lit_candles = b;
    }

    public boolean get_lit_candles() {
        return lit_candles;
    }

    public void set_has_candles(Boolean b) {
        has_candles = b;
    }

    public boolean get_has_candles() {
        return has_candles;
    }

    public void set_num_candles(int i) {
        num_candles = i;
    }

    public int get_num_candles() {
        return num_candles;
    }

    public void setTouchX(float x) {
        touchX = x;
    }

    public float getTouchX() {
        return touchX;
    }

    public void setTouchY(float y) {
        touchY = y;
    }

    public float getTouchY() {
        return touchY;
    }

    public String toString() {
        String rtn = "X: " + getTouchX() + ", Y: " + getTouchY();
        return rtn;
    }
}
