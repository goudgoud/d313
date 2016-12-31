package goudard.david.qcm.activity;

/**
 * Created by david on 31/12/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import goudard.david.qcm.R;
import goudard.david.qcm.tools.Internet;
import goudard.david.qcm.tools.QcmJsonParser;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
