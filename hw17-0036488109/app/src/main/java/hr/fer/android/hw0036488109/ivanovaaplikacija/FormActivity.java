package hr.fer.android.hw0036488109.ivanovaaplikacija;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.fer.android.hw0036488109.ivanovaaplikacija.models.FormResponse;
import hr.fer.android.hw0036488109.ivanovaaplikacija.networking.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity which provides user path input and fetches JSON data out of it.
 * Each provided property via JSON is displayed vertically.
 */
public class FormActivity extends AppCompatActivity {

    /**
     * The constant BASE_URL.
     */
    public static final String BASE_URL = "http://m.uploadedit.com";
    /**
     * Relative path containing JSON data.
     */
    @BindView(R.id.pathInput)
    EditText path;
    /**
     * The Avatar image.
     */
    @BindView(R.id.avatar)
    ImageView avatar;
    /**
     * The First name.
     */
    @BindView(R.id.firstName)
    TextView firstName;
    /**
     * The Last name.
     */
    @BindView(R.id.lastName)
    TextView lastName;
    /**
     * The Phone number.
     */
    @BindView(R.id.phoneNumber)
    TextView phoneNumber;
    /**
     * The Email.
     */
    @BindView(R.id.email)
    TextView email;
    /**
     * The Spouse.
     */
    @BindView(R.id.spouse)
    TextView spouse;
    /**
     * The Age.
     */
    @BindView(R.id.age)
    TextView age;

    /**
     * Allows http methods simplification.
     */
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    /**
     * Upon clicking on load button, fetches data from provided url and displays it on screen.
     */
    @OnClick(R.id.loadButton)
    public void load() {
        RetrofitService service = retrofit.create(RetrofitService.class);

        service.getData(path.getText().toString()).enqueue(new Callback<FormResponse>() {

            @Override
            public void onResponse(Call<FormResponse> call, Response<FormResponse> response) {
                Gson gson = new Gson();
                FormResponse formResponse = gson.fromJson(gson.toJson(response.body()), FormResponse.class);

                try {
                    if (formResponse.getAvatarUrl() == null) {
                        avatar.setVisibility(View.INVISIBLE);
                    } else {
                        Glide.with(FormActivity.this).load(formResponse.getAvatarUrl()).into(avatar);
                    }

                    firstName.setText(formResponse.getFirstName());
                    lastName.setText(formResponse.getLastName());
                    phoneNumber.setText(formResponse.getPhoneNumber());
                    email.setText(formResponse.getEmail());
                    spouse.setText(formResponse.getSpouse());
                    age.setText(formResponse.getAge());
                } catch (Exception e) {
                    Toast.makeText(FormActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FormResponse> call, Throwable t) {
                Toast.makeText(FormActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        setEmailListener(email);
        setPhoneNumberListener(phoneNumber);
    }

    /**
     * Sets click listener on phone number text if provided and makes  a call upon clicking.
     * @param phoneNumber
     */
    private void setPhoneNumberListener(TextView phoneNumber) {
        if (phoneNumber.getText() != null) {
            phoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.getText().toString()));
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * Sets click listener on email  if provided and makes new mail when clicked.
     * @param email
     */
    private void setEmailListener(TextView email) {
        if (email.getText() != null) {
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Hello!");
                    try {
                        startActivity(Intent.createChooser(intent, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(FormActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
