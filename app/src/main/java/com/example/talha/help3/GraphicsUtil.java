package com.example.talha.help3;

/**
 * Created by talha on 1/19/2018.
 */
import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import android.graphics.Rect;
import android.graphics.RectF;

/**
 * GraphicsUtil an utility class which convert the image in circular shape
 */
public class GraphicsUtil {

/*
 * Draw image in circular shape Note: change the pixel size if you want
 * image small or large
 */

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        // TODO Auto-generated method stub
        int targetWidth = 100;
        int targetHeight = 100;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth) / 2, ((float) targetHeight) / 2,
                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
                Path.Direction.CW);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        // paint.setStyle(Paint.Style.STROKE);
        Paint p = new Paint();
        p.setColor(color.white);
        p.setStyle(Paint.Style.STROKE);

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        canvas.drawOval(new RectF(0, 0, targetWidth, targetHeight), paint);
        // paint.setColor(Color.TRANSPARENT);
        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
                sourceBitmap.getHeight()), new RectF(0, 0, targetWidth,
                targetHeight), paint);

        return targetBitmap;
    }
}
