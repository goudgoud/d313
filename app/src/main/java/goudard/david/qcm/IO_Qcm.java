package goudard.david.qcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.request.GsonRequest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by david on 21/12/16.
 */

public class IO_Qcm {

    // Constant with a file name
    public static String fileName = "qcm.ser";
    private Context context;
    private TextView tv;

    public IO_Qcm(Context context, TextView tv) {
        this.context = context;
    }

    // Creates an object by reading it from a file
    public static Qcm readFromFile(Context context, TextView tv) {
        Qcm qcm = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            qcm = (Qcm) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //tv.setText(e.toString());
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        } finally {

        }
        return qcm;
    }

    // Serializes an object and saves it to a file
    public void saveToFile(Qcm qcm) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(qcm);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            //tv.setText(e.toString());
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
