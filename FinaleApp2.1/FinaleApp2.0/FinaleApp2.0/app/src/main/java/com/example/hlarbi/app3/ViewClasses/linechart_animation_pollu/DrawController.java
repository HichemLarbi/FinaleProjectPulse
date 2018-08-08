package com.example.hlarbi.app3.ViewClasses.linechart_animation_pollu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;


import com.example.hlarbi.app3.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class DrawController {

	private Context context;
	private Chart chart;
	private AnimationValue value;
	private Paint frameLinePaint;
	private Paint frameInternalPaint;
	private Paint frameTextPaint;
	private Paint numberPaint;
	private Paint linePaint;
	private Paint strokePaint;
	private Paint fillPaint;


	public DrawController(@NonNull Context context, @NonNull Chart chart) {
		this.context = context;
		this.chart = chart;
		init();
	}

	public void updateTitleWidth() {
		int titleWidth = getTitleWidth();
		chart.setTitleWidth(titleWidth);
	}

	public void updateValue(@NonNull AnimationValue value) {
		this.value = value;
	}

	public void draw(@NonNull Canvas canvas) {
		drawFrame(canvas);
		drawChart(canvas);
	}

	private void drawFrame(@NonNull Canvas canvas) {
		drawChartHorizontal(canvas);
		drawFrameLines(canvas);
	}


	private void drawChartHorizontal(@NonNull Canvas canvas) {
		List<InputData> inputDataList = chart.getInputData();
		List<DrawData> drawDataList = chart.getDrawData();

		if (inputDataList == null || inputDataList.isEmpty() || drawDataList == null || drawDataList.isEmpty()) {
			return;
		}

		for (int i = 0; i < inputDataList.size(); i++) {

			InputData inputData = inputDataList.get(i);
			String date = DateUtils.format(inputData.getMillis());
			int dateWidth = (int) frameTextPaint.measureText(date);

			int x;
			if (drawDataList.size() > i) {
				DrawData drawData = drawDataList.get(i);
				x = drawData.getStartX();

				if (i > 0) {
					x -= (dateWidth / 2);
				}

			} else {
				x = drawDataList.get(drawDataList.size() - 1).getStopX() - dateWidth;
			}

			canvas.drawText(date, x, chart.getHeight(), frameTextPaint);
		}
	}

	private void drawFrameLines(@NonNull Canvas canvas) {
		int textSize = chart.getTextSize();
		int padding = chart.getPadding();
		int height = chart.getHeight() - textSize - padding;
		int width = chart.getWidth();
		int titleWidth = chart.getTitleWidth();
		int heightOffset = chart.getHeightOffset();

		canvas.drawLine(titleWidth, height, width-25, height, frameLinePaint);
	}

	private void drawChart(@NonNull Canvas canvas) {
		int runningAnimationPosition = value != null ? value.getRunningAnimationPosition() : AnimationManager.VALUE_NONE;
		for (int i = 0; i < runningAnimationPosition; i++) {
			drawChart(canvas, i, false);
		}

		if (runningAnimationPosition > AnimationManager.VALUE_NONE) {
			drawChart(canvas, runningAnimationPosition, true);
		}
	}

	private void drawChart(@NonNull Canvas canvas, int position, boolean isAnimation) {
		List<DrawData> dataList = chart.getDrawData();
		if (dataList == null || position > dataList.size() ) {
			return;
		}

		DrawData drawData = dataList.get(position);
		int startX = drawData.getStartX();
		int startY = drawData.getStartY();

		int stopX;
		int stopY;
		int alpha;
//if you want to creat an flash effect add "+1" to each terms
		if (isAnimation) {
			stopX = value.getX();
			stopY = value.getY();
			alpha = value.getAlpha();

		} else {
			stopX = drawData.getStopX();
			stopY = drawData.getStopY();
			alpha = AnimationManager.ALPHA_END;
		}

		drawChart(canvas, startX, startY, stopX, stopY, alpha, position);
	}

	private void drawChart(@NonNull Canvas canvas, int startX, int startY, int stopX, int stopY, int alpha, int position) {
		Resources res = context.getResources();
		List<InputData> val = chart.getInputData();
        ArrayList<String> ar = new ArrayList<String>();
        for (InputData h: val)
        {ar.add(String.valueOf(h.getValue()));
        }
        String a = ar.get(1);
		Paint p=new Paint();
		int radius = chart.getRadius();
		int inerRadius = chart.getInerRadius();
		canvas.drawLine(startX, startY, stopX, stopY, linePaint);
		Bitmap b=BitmapFactory.decodeResource(res, R.drawable.pollu_popup);
		b= b.createScaledBitmap(b,48,48,false);

		if (position >= 0) {


			strokePaint.setAlpha(alpha);
			canvas.drawBitmap(b,startX-20, startY+20,p);
			canvas.drawCircle(startX, startY, radius, strokePaint);
			canvas.drawCircle(startX, startY, inerRadius, fillPaint);
			canvas.drawText(ar.get(position),startX-10, startY+60,numberPaint);




		}

	}

	private int getTitleWidth() {
		List<InputData> valueList = chart.getInputData();
		if (valueList == null || valueList.isEmpty()) {
			return 0;
		}
		int titleWidth=0;
		return titleWidth+25 ;
	}

	private void init() {
		Resources res = context.getResources();
		chart.setHeightOffset((int) (res.getDimension(R.dimen.radius) + res.getDimension(R.dimen.line_width)));
		chart.setPadding((int) res.getDimension(R.dimen.frame_padding));
		chart.setTextSize((int) res.getDimension(R.dimen.frame_text_size));
		chart.setRadius((int) res.getDimension(R.dimen.radius));
		chart.setInerRadius((int) res.getDimension(R.dimen.iner_radius));

		//Paint X et Y  : color, width ...
		frameLinePaint = new Paint();
		frameLinePaint.setAntiAlias(true);
		frameLinePaint.setStrokeWidth(res.getDimension(R.dimen.frame_line_width));
		frameLinePaint.setColor(res.getColor(R.color.trans_white2));
//Interline

		frameTextPaint = new Paint();
		frameTextPaint.setAntiAlias(true);
		frameTextPaint.setTextSize(chart.getTextSize());
		frameTextPaint.setColor(res.getColor(R.color.gray_400));

		//Line values point
		linePaint = new Paint();
		linePaint.setShader(new LinearGradient(0, 0, 0, 100, res.getColor(R.color.redgrad),res.getColor(R.color.bleugrad), Shader.TileMode.MIRROR));

		linePaint.setAntiAlias(true);
		linePaint.setStrokeWidth(res.getDimension(R.dimen.line_width));
//Circle point
		strokePaint = new Paint();
		strokePaint.setStyle(Paint.Style.STROKE);
		strokePaint.setAntiAlias(true);
		strokePaint.setStrokeWidth(res.getDimension(R.dimen.line_width));
		strokePaint.setColor(res.getColor(R.color.bleutransp));
//In circle point
		fillPaint = new Paint();
		fillPaint.setStyle(Paint.Style.FILL);
		fillPaint.setAntiAlias(true);
		fillPaint.setColor(res.getColor(R.color.blue5transp));

		//Numbers
		numberPaint = new Paint();
		numberPaint.setTextSize(23);
		Typeface tf = ResourcesCompat.getFont(context,R.font.smoothfont);
		numberPaint.setTypeface(tf);
		numberPaint.setColor(res.getColor(R.color.trans_white4));

	}
}
