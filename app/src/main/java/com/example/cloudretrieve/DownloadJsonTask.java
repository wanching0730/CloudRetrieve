package com.example.cloudretrieve;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by James Ooi on 9/8/2017.
 */

public class DownloadJsonTask extends AsyncTask<Void, Void, ArrayList<Person>> {
    private static final String JSON_URL = "https://labs.jamesooi.com/android-get.php";

    private Activity activity;

    public DownloadJsonTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected ArrayList<Person> doInBackground(Void... v) {
        ArrayList<Person> persons = null;

        try {
            persons = downloadJson();
        }
        catch (IOException ex) {
            Log.e("IO_EXCEPTION", ex.toString());
        }

        return persons;
    }

    @Override
    protected void onPostExecute(ArrayList<Person> persons) {
        ListView listView = (ListView)(activity.findViewById(R.id.list_view));
        PersonAdapter adapter = new PersonAdapter(persons, activity);

        listView.setAdapter(adapter);
    }

    private ArrayList<Person> downloadJson() throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(JSON_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode == 200 || responseCode == 304) {
                is = conn.getInputStream();

                // Convert the InputStream into ArrayList<Person>
                ArrayList<Person> persons = readInputStream(is);
                return persons;
            }
            else {
                Log.e("HTTP_ERROR", Integer.toString(responseCode));
                return null;
            }
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private ArrayList<Person> readInputStream(InputStream is)
            throws IOException, UnsupportedEncodingException {

        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return readPersonsArray(reader);
        } finally {
            reader.close();
        }
    }

    private ArrayList<Person> readPersonsArray(JsonReader reader) throws IOException {
        ArrayList<Person> persons = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            persons.add(readPerson(reader));
        }
        reader.endArray();
        return persons;
    }

    private Person readPerson(JsonReader reader) throws IOException {
        Person person = new Person();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();    // name variable refers to the name (key) of JSON object
            if (name.equals("name")) {
                person.setName(reader.nextString());
            } else if (name.equals("city")) {
                person.setCity(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return person;
    }
}
