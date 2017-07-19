package hr.fer.android.hw0036488109.ivanovaaplikacija;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisplayActivity extends AppCompatActivity {

    @BindView(R.id.message) TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ButterKnife.bind(this);

        message.setText(getIntent().getStringExtra("message"));
    }

    @OnClick(R.id.ok)
    public void goBack(){
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.report)
    public void sendReport(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ivan.rezic1@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "0036488109");
        intent.putExtra(Intent.EXTRA_TEXT   , getIntent().getStringExtra("message"));
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
