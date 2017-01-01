package goudard.david.qcm.tools;

import android.content.Context;

import goudard.david.qcm.R;

/**
 * Created by david on 01/01/17.
 */

public class ElapsedTime {

    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;
    private Context mContext;

    public ElapsedTime() {

    }

    public ElapsedTime(Context context) {
        mContext = context;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }


    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }


    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        } else {
            elapsed = ((stopTime - startTime) / 1000);
        }
        return elapsed;
    }

    public String toString() {
        return ElapsedTime.getStringElapsedTime(getElapsedTimeSecs(), mContext);
    }

    public static String getStringElapsedTime(long diff, Context mContext) {
        long diffSeconds;
        long diffMinutes;
        long diffHours;

        if (diff > 0) {
            diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000);
        }
        else{
            long diffpos = (24*((60 * 60 * 1000))) + diff;
            diffSeconds = diffpos / 1000 % 60;
            diffMinutes = diffpos / (60 * 1000) % 60;
            diffHours = (diffpos / (60 * 60 * 1000));
        }

        String response = "";

        if (mContext != null) {

            if (diffHours>0) {
                response += Long.toString(diffHours) + " " + (diffHours > 1 ? (mContext.getString(R.string.hours)) : R.string.hour) + " ";
            }
            if (diffMinutes>0) {
                response += Long.toString(diffMinutes) + " " + (diffMinutes > 1 ? R.string.minutes : R.string.minute) + " ";
            }
            response += Long.toString(diffSeconds) + " " + (diffSeconds > 1 ? R.string.seconds : R.string.second) + " ";
        }
        else {

            if (diffHours>0) {
                response += Long.toString(diffHours) + " h";
            }
            if (diffMinutes>0) {
                response += Long.toString(diffMinutes) + "m ";
            }
            response += Long.toString(diffSeconds) + "s";
        }
        return response;
    }
}