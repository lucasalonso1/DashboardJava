package br.edu.ifsp.dashboardAndroidJava.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ifsp.dashboardAndroidJava.R;
import br.edu.ifsp.dashboardAndroidJava.model.Live;


public class MainActivity extends AppCompatActivity {

    TextView tvTemp, tvVelocidade;
    GraphView graph;
    LineGraphSeries<DataPoint> series;
    Date data;
    private static final String TAG = "TESTE";
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference liveRef = dbRef.child("live");

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Live live = new Live();
            live = dataSnapshot.getValue(Live.class);
            Log.i(TAG, "teste temperatura" + live.getTemperatura());
            Log.i(TAG, "teste velocidade" + live.getVelocidade());
            exibirApiResultados(live);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d("TAG", databaseError.getMessage()); //Don't ignore errors!
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTemp = findViewById(R.id.tvTemp);
        tvVelocidade = findViewById(R.id.tvVelocidade);
        graph = (GraphView) findViewById(R.id.graph);
        liveRef.addValueEventListener(valueEventListener);
        series = new LineGraphSeries<DataPoint>();
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat("mm:ss:SS");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });
    }

    public void exibirApiResultados (Live live) {
        if(live != null) {
            tvTemp.setText(live.getTemperatura()+"c");
            double localTemp = Double.parseDouble(live.getTemperatura());
            data = new Date();
            tvVelocidade.setText(live.getVelocidade());
            series.appendData(new DataPoint(data, localTemp), true, 1000);
            graph.addSeries(series);
        }else {
            Toast.makeText(this, "null" , Toast.LENGTH_LONG).show();
        }
    }
}