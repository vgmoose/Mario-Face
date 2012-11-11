package com.vgmoose.lipsync;

import com.vgmoose.lipsync.R;

import android.app.Activity;
import android.app.AlertDialog;
//import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.res.Resources;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
//import android.widget.Toast;
//import android.os.Build;


public class lipsync extends Activity {
	/** Called when the activity is first created. */
	public static final String PREFS_NAME = "voicebox";

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event ) {
		
	if(keycode == KeyEvent.KEYCODE_BACK)
	{
		//if (constants.qs)
		//{
		if (constants.inverse)
			cycle();
		else
			settings();
		return true;
		//}
		//else
			//return super.onKeyDown(keycode,event);  
	}
	
	else if(keycode == KeyEvent.KEYCODE_MENU)
	 {
		 if (constants.inverse)
			 settings();
		 else
			 cycle();
		return true;

	 }
	 else
	 return super.onKeyDown(keycode,event);  
	}
	
	private void settings() {
		Resources res = getResources();
		final CharSequence[] items = res.getStringArray(R.array.options);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.optitle));
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	if (item==0){
		    		cycle();
		    		if (constants.growl)
		    		{
			        	constants.growl=false;
			        	Context context = getApplicationContext();
		        		CharSequence text;
	        			text=getString(R.string.qsoff);
	    	        	int duration = Toast.LENGTH_SHORT;
	
	    	        	Toast toast = Toast.makeText(context, text, duration);
	    	        	toast.show();
		    		}
		    	}
		        if (item==1)
		        {
		        	constants.scaleType++;
		        	if (constants.scaleType>=4) constants.scaleType=0;
		        	
		        	setScale();
		        	
		        	Context context = getApplicationContext();
	        		CharSequence text;
	        		
	        		if (constants.scaleType==0)
	        			text="Default";
	        		else if (constants.scaleType==1)
	        			text="Zoom";
	        		else if (constants.scaleType==2)
	        			text="Stretch";
	        		else
	        			text="No background";
	        	
	        	int duration = Toast.LENGTH_SHORT;

	        	Toast toast = Toast.makeText(context, text, duration);
	        	toast.show();
		        }
		        else if (item==2)
		        {
		        	onStop();
		        	System.exit(0);
		        }
		        else if (item==4)
		        {
		        	constants.qs=!constants.qs;
		        	Context context = getApplicationContext();
	        		CharSequence text;
	        		
	        		if (constants.qs)
	        			text=getString(R.string.qsoff);
	        		else
	        			text=getString(R.string.qson);
	        	
	        	int duration = Toast.LENGTH_SHORT;

	        	Toast toast = Toast.makeText(context, text, duration);
	        	toast.show();
		        }
		        else if (item==3)
		        {
		        	constants.inverse=!constants.inverse;
		        	Context context = getApplicationContext();
		        		CharSequence text;
		        		
		        		if (constants.inverse)
		        			text="Press BACK to swap faces, MENU for settings.";
		        		else
		        			text="Press MENU to swap faces, BACK for settings.";
		        	
		        	int duration = Toast.LENGTH_LONG;

		        	Toast toast = Toast.makeText(context, text, duration);
		        	toast.show();
		        }
		    }
		});
		AlertDialog alert = builder.create();
	    //lp.height = WindowManager.LayoutParams.FILL_PARENT;
		alert.show();

	}

	@Override
    protected void onStop(){
       super.onStop();
       
       // We need an Editor object to make preference changes.
       // All objects are from android.context.Context
       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       SharedPreferences.Editor editor = settings.edit();
       editor.putInt("MODE", constants.MODE);
       editor.putBoolean("inverse", constants.inverse);
       editor.putInt("scaleType", constants.scaleType);
       editor.putBoolean("qs",constants.qs);
       editor.putInt("version", constants.version);
       editor.putBoolean("first", constants.firstrun);
       editor.putBoolean("growl",constants.growl);

       // Commit the edits!
       editor.commit();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Restore preferences
	       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	       constants.MODE = settings.getInt("MODE", 3);
	       constants.inverse = settings.getBoolean("inverse", true);
	       constants.scaleType = settings.getInt("scaleType",0);
	       constants.qs = settings.getBoolean("qs", true);
	       constants.version = settings.getInt("version", 10);
	       constants.firstrun = settings.getBoolean("first", true);
	       constants.growl = settings.getBoolean("growl", true);
	       	       
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//super.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

		drawFace();
		
		//if (Build.VERSION.SDK_INT>=11)
			//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);

		if (constants.FULLSCREEN)
		{
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

		setContentView(R.layout.main);

		final View view = (View) findViewById(R.id.imageView1);
		final ImageView iview = (ImageView) findViewById(R.id.imageView1);

		if (constants.MODE==3) view.setBackgroundColor(Color.argb(255, 229, 159, 117));
		else if (constants.MODE==1) view.setBackgroundColor(Color.argb(255, 185, 93, 39));
		else if (constants.MODE==0) view.setBackgroundColor(Color.argb(255, 247, 247, 247));
		else view.setBackgroundColor(Color.argb(255, 220, 177, 159));

		constants.mopen = R.drawable.boo_open+2*(constants.MODE);
		constants.mclosed = R.drawable.boo_closed+2*(constants.MODE);
		
		iview.setImageResource(R.drawable.boo_closed+2*(constants.MODE));
		
		view.setOnTouchListener(new View.OnTouchListener() {
						
			final ImageView iview = (ImageView) findViewById(R.id.imageView1);
			
			public boolean onTouch(View v, MotionEvent event) {
				
				switch(event.getAction())
				{
					case 2:
					{
						iview.setImageDrawable(constants.open);
						break;
					}
					case 0:
					{
						iview.setImageDrawable(constants.open);
						//if (Build.VERSION.SDK_INT>=11)
							//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
						break;
	
					}
					case 1:
					{
						iview.setImageDrawable(constants.closed);
						break;
	
					}
					default:
					{
						iview.setImageDrawable(constants.open);
						break;
					}
				}       
				//System.out.println();
				return true;


			}
		}
		);

		setScale();
		if (constants.firstrun || constants.version!=11)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.introtitle));
			builder.setMessage(getString(R.string.introtext));
			OnClickListener arg1 = null;
			builder.setNeutralButton(getString(R.string.yougotit), arg1);
			AlertDialog alert = builder.create();
			alert.show();
			constants.firstrun=false;
			constants.version=11;
		}

	}
	
	private void setScale()
	{
		final ImageView iview = (ImageView) findViewById(R.id.imageView1);

		switch (constants.scaleType)
		{
			case 0:
				iview.setScaleType(ImageView.ScaleType.FIT_CENTER);
				if (constants.MODE==3) iview.setBackgroundColor(Color.argb(255, 229, 159, 117));
			 		else if (constants.MODE==1) iview.setBackgroundColor(Color.argb(255, 185, 93, 39));
			 		else if (constants.MODE==0) iview.setBackgroundColor(Color.argb(255, 247, 247, 247));
			 		else iview.setBackgroundColor(Color.argb(255, 220, 177, 159));
			break;
			
			case 1:
				iview.setScaleType(ImageView.ScaleType.CENTER_CROP);
			break;
			
			case 2:
				iview.setScaleType(ImageView.ScaleType.FIT_XY);
			break;
			
			case 3:
				iview.setScaleType(ImageView.ScaleType.FIT_CENTER);
				iview.setBackgroundColor(Color.argb(255, 0, 0, 0));
			break;

		}
			
	}
	
	private void cycle()
	{
		if (constants.MODE==4) constants.MODE = -1;
		 constants.MODE+=1; 
		 if (constants.MODE==2) constants.MODE = 3;
		 
		 final ImageView iview = (ImageView) findViewById(R.id.imageView1);
		 
		 if (constants.scaleType!=3)
		 {
			 if (constants.MODE==3) iview.setBackgroundColor(Color.argb(255, 229, 159, 117));
			 	else if (constants.MODE==1) iview.setBackgroundColor(Color.argb(255, 185, 93, 39));
			 	else if (constants.MODE==0) iview.setBackgroundColor(Color.argb(255, 247, 247, 247));
			 	else iview.setBackgroundColor(Color.argb(255, 220, 177, 159));
		 }
		 else
			 iview.setBackgroundColor(Color.argb(255, 0, 0, 0));
		 
		 drawFace();
		 
		 iview.setImageResource(R.drawable.boo_closed+2*(constants.MODE));
		 
		constants.mopen = R.drawable.boo_open+2*(constants.MODE);
		constants.mclosed = R.drawable.boo_closed+2*(constants.MODE);	
	}
	
	private void drawFace() {
		switch (constants.MODE)
		{
		case 0:
		{
			constants.open = constants.getImageByName("boo_open", lipsync.this);
			constants.closed = constants.getImageByName("boo_closed", lipsync.this);
			break;
		}
		case 1:
		{
			constants.open = constants.getImageByName("goomba_open", lipsync.this);
			constants.closed = constants.getImageByName("goomba_closed", lipsync.this);
			break;
		}
		case 2:
		case 4:
		{
			constants.open = constants.getImageByName("peach_open", lipsync.this);
			constants.closed = constants.getImageByName("peach_closed", lipsync.this);
			constants.MODE=4;
			break;
		}
		default:
		{
			constants.open = constants.getImageByName("mario_open", lipsync.this);
			constants.closed = constants.getImageByName("mario_closed", lipsync.this);
			break;
		}
		
		}

		
		
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    int action = event.getAction();
	    int keyCode = event.getKeyCode();
		final ImageView iview = (ImageView) findViewById(R.id.imageView1);

	        switch (keyCode) {
	        case KeyEvent.KEYCODE_VOLUME_UP:
	            if (action == KeyEvent.ACTION_UP) {
	            	iview.setImageDrawable(constants.closed);
	            }
	            if (action == KeyEvent.ACTION_DOWN) {
	            	iview.setImageDrawable(constants.open);
	            	//if (Build.VERSION.SDK_INT>=11)
						//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
	            }
	            return true;
	        case KeyEvent.KEYCODE_VOLUME_DOWN:
	        	if (action == KeyEvent.ACTION_UP) {
	        		iview.setImageDrawable(constants.closed);
	            }
	            if (action == KeyEvent.ACTION_DOWN) {
	            	iview.setImageDrawable(constants.open);
	            	//if (Build.VERSION.SDK_INT>=11)
						//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
	            }
	            return true;
	        case KeyEvent.KEYCODE_CAMERA:
	            if (action == KeyEvent.ACTION_UP) {
	            	iview.setImageDrawable(constants.closed);
	            }
	            if (action == KeyEvent.ACTION_DOWN) {
	            	iview.setImageDrawable(constants.open);
	            	//if (Build.VERSION.SDK_INT>=11)
						//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
	            }
	            return true;
	        default:
	            return super.dispatchKeyEvent(event);
	        }
	    }

}