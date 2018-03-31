package com.example.android.electracksih;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.scichart.charting.ClipMode;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.modifiers.AxisDragModifierBase;
import com.scichart.charting.modifiers.ModifierGroup;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.HorizontalAnchorPoint;
import com.scichart.charting.visuals.annotations.TextAnnotation;
import com.scichart.charting.visuals.annotations.VerticalAnchorPoint;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.pointmarkers.EllipsePointMarker;
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries;
import com.scichart.core.annotations.Orientation;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.core.model.DoubleValues;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class graph_frame extends android.support.v4.app.Fragment {
Double valueUpdatedY=0.00;
    Firebase mRef;
    public static ArrayList<SensorData> sensorDataList=new ArrayList<>();
    public void updateValueOfY(Double updatedY){
        Log.d("nsg",updatedY+"");
        valueUpdatedY=updatedY;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.graph_device, container, false);



            // Create a SciChartSurface
           final SciChartSurface surface = new SciChartSurface(this.getActivity());

            // Get a layout declared in "activity_main.xml" by id

            LinearLayout chartLayout = (LinearLayout) view.findViewById(R.id.chart_layout);

            // Add the SciChartSurface to the layout
            chartLayout.addView(surface);

            // Initialize the SciChartBuilder
            SciChartBuilder.init(this.getActivity());

            // Obtain the SciChartBuilder instance
            final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();

            // Create a numeric X axis
            final IAxis xAxis = sciChartBuilder.newNumericAxis()
                    .withAxisTitle("Time")
                    .withVisibleRange(0,60)
                    .build();

            // Create a numeric Y axis
            final IAxis yAxis = sciChartBuilder.newNumericAxis()
                    .withAxisTitle("Power").withVisibleRange(-0.05,0.10).build();

            // Create a TextAnnotation and specify the inscription and position for it
//            TextAnnotation textAnnotation = sciChartBuilder.newTextAnnotation()
//                    .withX1(5.0)
//                    .withY1(55.0)
//                    .withText("")
//                    .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
//                    .withVerticalAnchorPoint(VerticalAnchorPoint.Center)
//                    .withFontStyle(20, ColorUtil.Red)
//                    .build();

            // Create interactivity modifiers
            ModifierGroup chartModifiers = sciChartBuilder.newModifierGroup()
                    .withPinchZoomModifier().withReceiveHandledEvents(true).build()
                    .withZoomPanModifier().withReceiveHandledEvents(true).build()
                    .build();

            // Add the Y axis to the YAxes collection of the surface
            Collections.addAll(surface.getYAxes(), yAxis);

            // Add the X axis to the XAxes collection of the surface
            Collections.addAll(surface.getXAxes(), xAxis);

            // Add the annotation to the Annotations collection of the surface
//            Collections.addAll(surface.getAnnotations(), textAnnotation);

            // Add the interactions to the ChartModifiers collection of the surface
            Collections.addAll(surface.getChartModifiers(), chartModifiers);


        IRenderableSeries lineSeries = sciChartBuilder.newLineSeries()
                .withStrokeStyle(ColorUtil.LightBlue, 2f, true)
                .build();

        // Create an Ellipse PointMarker for the Scatter Series
        EllipsePointMarker pointMarker = sciChartBuilder
                .newPointMarker(new EllipsePointMarker())
                .withFill(ColorUtil.LightBlue)
                .withStroke(ColorUtil.Red, 2f)
                .withSize(10)
                .build();

// Create and configure a scatter series
        IRenderableSeries scatterSeries = sciChartBuilder.newScatterSeries()
                .withPointMarker(pointMarker)
                .build();

//Adding Data
 //  final     XyDataSeries lineData = sciChartBuilder.newXyDataSeries(Integer.class, Double.class).build();
   //  final   XyDataSeries scatterData = sciChartBuilder.newXyDataSeries(Integer.class, Double.class).build();
        final  XyDataSeries  lineData = sciChartBuilder.newXyDataSeries(Integer.class, Double.class).build();
        final  XyDataSeries scatterData = sciChartBuilder.newXyDataSeries(Integer.class, Double.class).build();

       final int dataCount =1000;
        for (int i = 0; i < 1000; i++)
        {
            lineData.append(i, Math.sin(i * 0.1));
            scatterData.append(i, Math.cos(i * 0.1));
        }



        // Create a couple of DataSeries for numeric (Int, Double) data



// Create and configure a line series
        lineSeries = sciChartBuilder.newLineSeries()
                .withDataSeries(lineData)
                .withStrokeStyle(ColorUtil.LightBlue, 2f, true)
                .build();

// Create an Ellipse PointMarker for the Scatter Series
        pointMarker = sciChartBuilder
                .newPointMarker(new EllipsePointMarker())
                .withFill(ColorUtil.LightBlue)
                .withStroke(ColorUtil.Green, 2f)
                .withSize(10)
                .build();

// Create and configure a scatter series
        scatterSeries = sciChartBuilder.newScatterSeries()
                .withDataSeries(scatterData)
                .withPointMarker(pointMarker)
                .build();

// Add a RenderableSeries onto the SciChartSurface
        surface.getRenderableSeries().add(scatterSeries);
        surface.getRenderableSeries().add(lineSeries);


// Add a RenderableSeries onto the SciChartSurface
        surface.getRenderableSeries().add(lineSeries);

        //4
        ModifierGroup additionalModifiers = sciChartBuilder.newModifierGroup()

                .withPinchZoomModifier().build()

                .withZoomPanModifier().withReceiveHandledEvents(true).build()

                .withZoomExtentsModifier().withReceiveHandledEvents(true).build()

                .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Scale).withClipModex(ClipMode.None).build()

                .withYAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).build()

                .build();
//5
        // Create a LegendModifier and configure a chart legend
        ModifierGroup legendModifier = sciChartBuilder.newModifierGroup()
                .withLegendModifier()
                .withOrientation(Orientation.HORIZONTAL)
                .withPosition(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 10)
                .build()
                .build();
        ModifierGroup cursorModifier = sciChartBuilder.newModifierGroup()
                .withCursorModifier().withShowTooltip(true).build()
                .build();

// Add the CursorModifier to the SciChartSurface
        surface.getChartModifiers().add(cursorModifier);
//=-------------------------------------------------------------------------
// Add the LegendModifier to the SciChartSurface
  //      surface.getChartModifiers().add(legendModifier);
// Add the modifiers to the SciChartSurface
    //    surface.getChartModifiers().add(additionalModifiers);
//6

        final DoubleValues lineDoubleData = new DoubleValues(dataCount);
        final DoubleValues scatterDoubleData = new DoubleValues(dataCount);
        lineDoubleData.setSize(dataCount);
        scatterDoubleData.setSize(dataCount);

        TimerTask updateDataTask = new TimerTask() {
            private double _phaseShift = 0.0;
            @Override
            public void run() {
                UpdateSuspender.using(surface, new Runnable() {
                    @Override
                    public void run() {
                        // Fill the DoubleValues collections
                        for (int i = 0; i <dataCount; i++)
                        {
                            lineDoubleData.set(i, valueUpdatedY);
                            scatterDoubleData.set(i, Math.cos(i * 0.1 + _phaseShift));
                        }
                        // Update DataSeries using bunch update
                        lineData.updateRangeYAt(0, lineDoubleData);
                        scatterData.updateRangeYAt(0, scatterDoubleData);
                    }
                });
                _phaseShift += 0.01;
            }
        };
        loadDataFromFirebase();
        Timer timer = new Timer();
        long delay = 0;
        long interval = 10;
        timer.schedule(updateDataTask, delay, interval);

    return  view;
    }
    private void loadDataFromFirebase() {
        mRef = new Firebase("https://not-so-awesome-project-45a2e.firebaseio.com/sensors/");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("changed",dataSnapshot.toString());
                sensorDataList.clear();
                for(DataSnapshot oneDataSnapshot:dataSnapshot.getChildren()){
                    DataSnapshot oneDataSnapshot_data=oneDataSnapshot.child("data");
                    String value=oneDataSnapshot_data.getValue(String.class);
                    String values1[]=value.split(",");
                    int sizeOfValues=values1.length;
                    if(sizeOfValues<6){
                        continue;
                    }
                    String value2=values1[sizeOfValues-1];
                    String values2[]=value2.split("\"");

                    DataSnapshot oneDataSnapshot_time=oneDataSnapshot.child("time");
                    Long timeOfReading=oneDataSnapshot_time.getValue(Long.class);
                    Log.d("haha",sizeOfValues+"");
                    SensorData sensorData=new SensorData(values1[1],values1[2],values1[3],values1[4],values1[5],values2[0],"1",timeOfReading);
                    Log.d("data",oneDataSnapshot.toString());
//                    Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                    sensorDataList.add(sensorData);
                }
                int size=sensorDataList.size();
                updateValueOfY(Double.parseDouble(sensorDataList.get(size-2).Curr1));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
