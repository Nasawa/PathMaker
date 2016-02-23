package com.anigeek.pathmaker;

import android.app.*;
import android.os.*;
import android.view.View.*;
import android.view.*;

public class MainActivity extends Activity
{
	private Handler frame = new Handler();
	private static final int FRAME_RATE = 20;
	private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		canvasView = (CanvasView)findViewById(R.id.canvas);

		findViewById(R.id.prev).setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					canvasView.setLayer(true);
				}
			});

		findViewById(R.id.next).setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					canvasView.setLayer(false);
				}
			});

		canvasView.setOnTouchListener(new OnTouchListener()
			{
				public boolean onTouch(View v, MotionEvent m)
				{
					canvasView.addPoint((int)m.getX(), (int)m.getY());

					return true;
				}
			});

		Handler h = new Handler();

		h.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					initGfx();
				}
			}, 1000);
	}

	synchronized public void initGfx()
	{
		frame.removeCallbacks(frameUpdate);
		frame.postDelayed(frameUpdate, FRAME_RATE);
	}

	private Runnable frameUpdate = new Runnable()
	{
		@Override
		synchronized public void run()
		{
			frame.removeCallbacks(frameUpdate);
			canvasView.invalidate();
			frame.postDelayed(frameUpdate, FRAME_RATE);
		}
	};
}

