package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CakeView extends SurfaceView {

    private CakeModel cakeModel;
    private final float DISPLAY_COORDS_X = 1350.0f;
    private final float DISPLAY_COORDS_Y = 760.0f;
    private final float BALLOON_STRING_LENGTH = 175.0f;
    private final float BALLOON_HORIZ_ADJUST = 50.0f;
    private final float BALLOON_VERT_ADJUST = 75.0f;
    private final float RECTANGLE_LENGTH = 25.0f;

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint coordinatePaint = new Paint();
    Paint balloonPaint = new Paint();
    Paint balloonStringPaint = new Paint();
    Paint bigRectangle = new Paint();
    Paint smallRectangle = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 40.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;


    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        cakeModel = new CakeModel();

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFFC755B5);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        coordinatePaint.setColor(Color.RED);
        coordinatePaint.setTextSize(75);
        coordinatePaint.setStyle(Paint.Style.FILL);
        balloonPaint.setColor(Color.BLUE);
        balloonPaint.setStyle(Paint.Style.FILL);
        balloonStringPaint.setColor(Color.BLACK);
        balloonStringPaint.setStyle(Paint.Style.FILL);
        bigRectangle.setColor(Color.RED);
        bigRectangle.setStyle(Paint.Style.FILL);
        smallRectangle.setColor(Color.GREEN);
        smallRectangle.setStyle(Paint.Style.FILL);

        setBackgroundColor(Color.WHITE);  //better than black default
    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        boolean draw_candles = cakeModel.get_has_candles();
        if(draw_candles == false) {
            return;
        }

        //draw the foundation
        canvas.drawRect(left, bottom - candleHeight, left + candleWidth, bottom, candlePaint);

        //draw the wick
        float wickLeft = left + candleWidth/2 - wickWidth/2;
        float wickTop = bottom - wickHeight - candleHeight;
        canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);

        //draw the flames or not
        boolean draw_flames = cakeModel.get_lit_candles();
        if(draw_flames == false) {
            return;
        }

        //draw the outer flame
        float flameCenterX = left + candleWidth/2;
        float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius/3;
        canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

        //draw the inner flame
        flameCenterY += outerFlameRadius/3;
        canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);
    }

    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas) {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        //Now the candles
        int num_candles = cakeModel.get_num_candles();
        for(int i = 1; i <= num_candles; i++) {
            drawCandle(canvas, cakeLeft + i * cakeWidth/(num_candles + 1) - candleWidth/2, cakeTop);
        }

        drawPatterns(canvas);
    }//onDraw

    public CakeModel getCakeModel() {
        return cakeModel;
    }

    public void drawPatterns(Canvas canvas) {
        //draw the coords
        if(cakeModel.getTouchX() == -5000 && cakeModel.getTouchY() == -5000) {
            canvas.drawText("X: 0.0, Y: 0.0", DISPLAY_COORDS_X, DISPLAY_COORDS_Y, coordinatePaint);
        } else {
            canvas.drawText(cakeModel.toString(), DISPLAY_COORDS_X, DISPLAY_COORDS_Y, coordinatePaint);
        }

        //draw the balloon
        canvas.drawLine(cakeModel.getTouchX(), cakeModel.getTouchY(), cakeModel.getTouchX(),
                cakeModel.getTouchY() + BALLOON_STRING_LENGTH, balloonStringPaint);
        canvas.drawOval(cakeModel.getTouchX() - BALLOON_HORIZ_ADJUST, cakeModel.getTouchY() - BALLOON_VERT_ADJUST,
                cakeModel.getTouchX() + BALLOON_HORIZ_ADJUST, cakeModel.getTouchY() + BALLOON_VERT_ADJUST,
                balloonPaint);

        //draw the balloon rectangle pattern
        canvas.drawRect(cakeModel.getTouchX() - RECTANGLE_LENGTH, cakeModel.getTouchY() - RECTANGLE_LENGTH,
                cakeModel.getTouchX() + RECTANGLE_LENGTH, cakeModel.getTouchY() + RECTANGLE_LENGTH,
                bigRectangle);
        canvas.drawRect(cakeModel.getTouchX() - RECTANGLE_LENGTH, cakeModel.getTouchY() - RECTANGLE_LENGTH,
                cakeModel.getTouchX(), cakeModel.getTouchY(), smallRectangle);
        canvas.drawRect(cakeModel.getTouchX(), cakeModel.getTouchY(),
                cakeModel.getTouchX() + RECTANGLE_LENGTH, cakeModel.getTouchY() + RECTANGLE_LENGTH,
                smallRectangle);
    }
}//class CakeView
