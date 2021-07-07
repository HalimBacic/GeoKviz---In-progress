package geokviz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import geokviz.activity.R;

public class MyView extends View {

    String usernamePoints;
    String level;
    Bitmap image;
    Double percent;


    public MyView(Context context,String usernamePoints,Double percent) {
        super(context);
        if (percent >= 0 && percent < 20) {
            level = getResources().getString(R.string.level1);
            image = BitmapFactory.decodeResource(getResources(),R.drawable.globe1);
        }
        else if (percent >= 20 && percent < 40) {
            level = getResources().getString(R.string.level2);
            image = BitmapFactory.decodeResource(getResources(),R.drawable.globe2);
        }
        else if(percent >=40 && percent <60) {
            level = getResources().getString(R.string.level3);
            image = BitmapFactory.decodeResource(getResources(),R.drawable.globe3);
        }
        else if(percent >=60 && percent <80) {
            level = getResources().getString(R.string.level4);
            image = BitmapFactory.decodeResource(getResources(),R.drawable.globe4);
        }
        else if(percent >=80 && percent <100) {
            level = getResources().getString(R.string.level5);
            image = BitmapFactory.decodeResource(getResources(),R.drawable.globe5);
        }
        this.percent = percent;
        this.usernamePoints = usernamePoints;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.WHITE);
        canvas.drawRect(0, 0, 950, 1100, pBackground);
        Paint pText = new Paint();
        pText.setColor(Color.BLACK);
        pText.setTextSize(100);
        canvas.drawText(usernamePoints, 100, 100, pText);
        canvas.drawBitmap(image,0,0,pBackground);
        canvas.drawText(level, 100, 1000, pText);
    }
}
