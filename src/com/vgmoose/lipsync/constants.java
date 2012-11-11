package com.vgmoose.lipsync;

import android.app.Activity;
//import com.vgmoose.lipsync;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
//import android.widget.ImageView;

class constants
{
	public static int MODE;
	public static boolean FULLSCREEN=true;
	public static int mopen;
	public static int mclosed;
	public static Drawable open,closed;
	public static boolean inverse;
	public static int scaleType;
	public static boolean qs;
	public static int version;
	public static boolean firstrun;
	public static boolean growl;
	//public static Drawable closed = new BitmapDrawable("boo_closed");
	//public static ImageView iview = (ImageView) findViewById(R.id.imageView1);
	
	public static Drawable getImageByName(String nameOfTheDrawable, Activity a) {
	    Drawable drawFromPath;
	    int path = a.getResources().getIdentifier(nameOfTheDrawable, 
	                                    "drawable", "com.vgmoose.lipsync"); 

	    Options options = new BitmapFactory.Options();
	    options.inScaled = false;
	    Bitmap source = BitmapFactory.decodeResource(a.getResources(), path, options);

	    drawFromPath = new BitmapDrawable(source);  

	    return drawFromPath;
	}

}


/*
 *  Mario = 3
 *  Peach = 4
 *  Boo = 0
 *  Goomba = 1
 */