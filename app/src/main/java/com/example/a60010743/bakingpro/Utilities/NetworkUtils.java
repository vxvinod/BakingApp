package com.example.a60010743.bakingpro.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    // Initialize Variables
    final static String TAG_NAME = "NetworkUtils";
    final static String RECEPIE_BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    final static String PATH = "topher/2017/May/59121517_baking/";
    final static String RESPONSE_FORMAT = "baking.json";

    public static URL buildUrl() {
        Uri buildUrl = Uri.parse(RECEPIE_BASE_URL).buildUpon()
                            .appendEncodedPath(PATH)
                            .appendEncodedPath(RESPONSE_FORMAT)
                            .build();
        URL url = null;
        try {
            url = new URL(buildUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG_NAME, "Url---" + url);
        return url;
    }

    public static String fetchData(URL buildUrl) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) buildUrl.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }


}
