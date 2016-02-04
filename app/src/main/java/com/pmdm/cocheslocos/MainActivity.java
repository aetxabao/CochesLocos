package com.pmdm.cocheslocos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

    GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        gv = new GameView(this);
        setContentView(gv);
    }

    public void onPause() {
        super.onPause();
        gv.stopGame();
    }

    public void onResume() {
        super.onResume();
        gv.startGame();
    }
}
