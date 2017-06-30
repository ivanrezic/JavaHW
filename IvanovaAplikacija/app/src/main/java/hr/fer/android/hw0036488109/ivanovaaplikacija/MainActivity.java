package hr.fer.android.hw0036488109.ivanovaaplikacija;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity which provides link to app functionalities.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Go to calculus activity.
     */
    @OnClick(R.id.mathematics)
    public void goToCalculusActivity(){
        startActivity(new Intent(this,CalculusActivity.class));
    }

    /**
     * Go to form activity.
     */
    @OnClick(R.id.statistics)
    public void goToFormActivity(){
        startActivity(new Intent(this, FormActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
