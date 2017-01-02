package goudard.david.qcm.tools;

import android.content.Context;

import goudard.david.qcm.R;

/**
 * Created by david on 01/01/17.
 */

/**
 * ElapsedTime class
 *
 * @author David GOUDARD
 * @version 1
 * @since 01/01/2017
 */
public class ElapsedTime {

    /**
     * Start time
     */
    private long startTime = 0;

    /**
     * Stop time
     */
    private long stopTime = 0;

    /**
     * True if in progress
     */
    private boolean running = false;

    /**
     * Context who use this class
     */
    private Context mContext;

    /**
     * Constructor
     *
     * @param context who use this class
     */
    public ElapsedTime(Context context) {
        mContext = context;
    }

    /**
     * Formatted string of elapsed time
     *
     * @param diff     the elapsed time
     * @param mContext to get string resources
     * @return formatted elapsed time
     */
    public static String getStringElapsedTime(long diff, Context mContext) {
        long diffSeconds;
        long diffMinutes;
        long diffHours;

        if (diff > 0) {
            diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000);
        } else {
            long diffpos = (24 * ((60 * 60 * 1000))) + diff;
            diffSeconds = diffpos / 1000 % 60;
            diffMinutes = diffpos / (60 * 1000) % 60;
            diffHours = (diffpos / (60 * 60 * 1000));
        }

        String response = "";

        if (mContext != null) {

            if (diffHours > 0) {
                response += Long.toString(diffHours) + " " + (diffHours > 1 ? (mContext.getString(R.string.hours)) : R.string.hour) + " ";
            }
            if (diffMinutes > 0) {
                response += Long.toString(diffMinutes) + " " + (diffMinutes > 1 ? R.string.minutes : R.string.minute) + " ";
            }
            response += Long.toString(diffSeconds) + " " + (diffSeconds > 1 ? R.string.seconds : R.string.second) + " ";
        } else {

            if (diffHours > 0) {
                response += Long.toString(diffHours) + " h";
            }
            if (diffMinutes > 0) {
                response += Long.toString(diffMinutes) + "m ";
            }
            response += Long.toString(diffSeconds) + "s";
        }
        return response;
    }

    /**
     * Launch the counting
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    /**
     * Stop the counting
     */
    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    /**
     * Elapsed time getter
     *
     * @return the elapsed time
     */
    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    /**
     * Elapsed time in second getter
     *
     * @return the elapsed time in second(s)
     */
    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        } else {
            elapsed = ((stopTime - startTime) / 1000);
        }
        return elapsed;
    }

    /**
     * Get expanded format of elapsed time
     *
     * @return elapsed time
     */
    public String toString() {
        return ElapsedTime.getStringElapsedTime(getElapsedTime(), mContext);
    }
}