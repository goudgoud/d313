package goudard.david.qcm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import goudard.david.qcm.R;
import goudard.david.qcm.tools.Internet;
import goudard.david.qcm.tools.QcmJsonParser;

/**
 * The application splashscreen
 *
 * @author David Goudard
 * @version 1
 * @since 31/12/2016
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
