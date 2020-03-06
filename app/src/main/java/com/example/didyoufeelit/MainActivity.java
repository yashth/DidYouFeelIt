package com.example.didyoufeelit;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;
import android.util.Log;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity","calling Asyctask");
        EarthAsynTask task = new EarthAsynTask();
        // Update the information displayed to the user.
        task.execute(USGS_REQUEST_URL);

    }

    private void updateUi(Event earthquake) {
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        TextView tsunamiTextView = (TextView) findViewById(R.id.number_of_people);
        tsunamiTextView.setText(getString(R.string.num_people_felt_it, earthquake.numOfPeople));

        TextView magnitudeTextView = (TextView) findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(earthquake.perceivedStrength);
    }

    private class EarthAsynTask extends AsyncTask<String,Void,Event>{

        @Override
        protected Event doInBackground(String... url) {
            if(url.length<1||url[0]==null){
                return null;
            }
            Log.d("MainActivity","doInBackground url = "+url[0]);
            Event earthquake = Utils.fetchEarthquakeData(url[0]);
            return earthquake;
        }
        @Override
        protected void onPostExecute(Event earthquake){
            if(earthquake==null){
                return;
            }
            updateUi(earthquake);

        }
    }
}
