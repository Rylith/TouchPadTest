package rylith.dismissoverlaytest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;
    private TextView pos;

    private Canvas board;
    private Bitmap sheet;
    private Paint paint;
    private ImageView image;
    private float downx = 0, downy = 0, upx = 0, upy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pos = (TextView) findViewById(R.id.pos);
        image = (ImageView) this.findViewById(R.id.image);

        // Obtain the DismissOverlayView element
        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismissOverlay.setIntroText("Long Press to dismiss");
        mDismissOverlay.showIntroIfNecessary();

        //Creating canvas for drawing
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(screenSize);
        sheet = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        board = new Canvas(sheet);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        image.setImageBitmap(sheet);

        // Configure a gesture detector
        mDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public void onLongPress(MotionEvent event) {

                    mDismissOverlay.show();
                }

                    @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                        float distanceY) {
                    pos.setText("Scroll:\n" +"X: "+ e1.getX()+"\nY: "+e1.getY());
                    board.drawLine(e1.getX(),e1.getY(),e2.getX(),e2.getY(),paint);
                    image.invalidate();
                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent event) {
                    pos.setText("Pos:\n" +"X: "+ event.getX()+"\nY: "+event.getY());
                    paint.setColor(Color.BLUE);
                    board.drawPoint(event.getX(),event.getY(),paint);
                    paint.setColor(Color.GREEN);
                    image.invalidate();
                    return true;
                }
            }
        );
    }

    // Capture long presses
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }
}
