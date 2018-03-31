package com.example.android.electracksih;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    RecyclerView.Adapter mAdapter;
    RecyclerView.Adapter mAdapter2;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager2;
    ChatBotFragment chatBotFragment;

    Firebase mRef,IOTRef;
    ArrayList<SensorData> sensorDataList=new ArrayList<>();
    ArrayList<OneDevice> deviceList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ImageView iv = (ImageView) findViewById(R.id.dotview);
//        Blurry.with(MainActivity.this)
//                .radius(10)
//                .sampling(8)
//                .color(Color.argb(66, 255, 255, 0))
//                .async()
//                .animate(500)
//                .onto(iv);

        mRecyclerView = (RecyclerView) findViewById(R.id.device_recycler_view);
        mRecyclerView2 = (RecyclerView) findViewById(R.id.temp_humid_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView2.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter2 = new MyAdapter2();
        mRecyclerView2.setAdapter(mAdapter2);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetDialogFragment();
                chatBotFragment = new ChatBotFragment();
                chatBotFragment.show(getSupportFragmentManager(), chatBotFragment.getTag());
                loadDataFromFirebase();

            }
        });
    }

    private void loadDataFromFirebase() {
        mRef = new Firebase("https://not-so-awesome-project-45a2e.firebaseio.com/sensors/");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot oneDataSnapshot:dataSnapshot.getChildren()){
                    DataSnapshot oneDataSnapshot_data=oneDataSnapshot.child("data");
                    String value=oneDataSnapshot_data.getValue(String.class);
                    String values1[]=value.split(",");
                    int sizeOfValues=values1.length;
                    String value2=values1[sizeOfValues-1];
                    String values2[]=value2.split("\"");

                    DataSnapshot oneDataSnapshot_time=oneDataSnapshot.child("time");
                    Long timeOfReading=oneDataSnapshot_time.getValue(Long.class);
                    Log.d("haha",sizeOfValues+"");
                    SensorData sensorData=new SensorData(values1[5],values1[0],values1[1],values1[2],values1[3],values2[4],"1",timeOfReading);
                    Log.d("data",oneDataSnapshot.toString());
//                    Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
                    sensorDataList.add(sensorData);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        IOTRef=new Firebase("https://not-so-awesome-project-45a2e.firebaseio.com/IOTdata/");
        IOTRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot oneDeviceData:dataSnapshot.getChildren()){
                    DataSnapshot applianceIDSnapshot=oneDeviceData.child("applianceid");

//                    OneDevice oneDevice=oneDeviceData.getValue(OneDevice.class);
//                    deviceList.add(oneDevice);
                    Log.d("device",oneDeviceData.toString());
//                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(MainActivity.this, "failed2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
