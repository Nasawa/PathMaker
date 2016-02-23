package com.anigeek.pathmaker;
import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import java.util.*;
import android.view.View.*;
import android.widget.*;

public class CanvasView extends View
{

	private Paint p;
	private ArrayList<ArrayList<Point>> pathList;
	private ArrayList<Point> activePath;
	private int activeLine, passiveLine, 
	activePoint, passivePoint;
	Context context;

	public CanvasView(Context context, AttributeSet attrib)
	{
		super(context, attrib);

		this.context = context;

		p = new Paint();
		pathList = new ArrayList<ArrayList<Point>>();

		activePath = new ArrayList<Point>();
		pathList.add(activePath);

		activeLine = Color.rgb(255,0,0);
		passiveLine = Color.rgb(100,100,100);
		activePoint = Color.rgb(0,0,255);
		passivePoint = Color.rgb(50,50,200);
	}

	public void addPoint(int x, int y)
	{
		activePath.add(new Point(x,y));
	}

	@Override
	synchronized public void onDraw(Canvas c)
	{
		p.setStyle(Paint.Style.FILL_AND_STROKE);
		p.setAlpha(255);
		p.setStrokeWidth(10);

		for(ArrayList<Point> tpath : pathList)
		{
			if(tpath != activePath)
			{
				p.setColor(passiveLine);
				for(int i = 1; i < tpath.size(); i++)
				{
					Point o = tpath.get(i - 1);
					Point n = tpath.get(i);
					c.drawLine(o.x, o.y, n.x, n.y, p);
				}

				p.setColor(passivePoint);
				for(Point tpoint : tpath)
				{
					c.drawPoint(tpoint.x, tpoint.y, p);
				}
			}
		}

		p.setColor(activeLine);
		for(int i = 1; i < activePath.size(); i++)
		{
			Point o = activePath.get(i - 1);
			Point n = activePath.get(i);
			c.drawLine(o.x, o.y, n.x, n.y, p);
		}

		p.setColor(activePoint);
		for(Point po : activePath)
		{
			c.drawPoint(po.x, po.y, p);
		}

	}

	public void setLayer(boolean left)
	{
		int ind = pathList.indexOf(activePath);
		if(left)
		{
			if(ind != 0)
				activePath = pathList.get(ind - 1);
		}
		else
		{
			if(ind == pathList.size() - 1)
			{
				activePath = new ArrayList<Point>();
				pathList.add(activePath);
			}
			else
				activePath = pathList.get(ind + 1);
		}
	}
}
