package com.ats.pos_app.mpchart;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.ats.pos_app.R;
import com.ats.pos_app.model.FrGraph;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class XYMarkerViewFr extends MarkerView {

    private final TextView tvContent;
    private final ValueFormatter xAxisValueFormatter;

    public ArrayList<FrGraph> frGraph;


    public int type=0;


    private final DecimalFormat format;

    public XYMarkerViewFr(Context context, ValueFormatter xAxisValueFormatter) {
        super(context, R.layout.custom_marker_view);

        this.xAxisValueFormatter = xAxisValueFormatter;
       // this.frDashboards = frDashboards;
        tvContent = findViewById(R.id.tvContent);
        format = new DecimalFormat("###.0");
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {


        Log.e("Pos1111111111111","----------------------------------"+format.format(e.getX()));

        String pos= format.format(e.getX());

        Log.e("Pos","----------------------------------"+pos);

        FrGraph strFr = frGraph.get((int) e.getX());

            Log.e("Pos", "---------------FR List-------------------" + strFr);

            //tvContent.setText(String.format("x: %s, y: %s", xAxisValueFormatter.getFormattedValue(e.getX()), format.format(e.getY())));
            tvContent.setText(String.format("Fr Name: %s, ₹: %s", strFr.getSellDate(), format.format(e.getY())));

            Log.e("getDate", "-------------------------------------------------" + e.getData());
            Log.e("X", "-------------------------------------------------" + e.getX());
            Log.e("Y", "-------------------------------------------------" + e.getY());

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
