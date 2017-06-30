package hr.fer.android.hw0036488109.ivanovaaplikacija;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity which provides simple arithmetic operations. Result is forwarded to DisplayActivity.
 */
public class CalculusActivity extends AppCompatActivity {

    /**
     * The constant REQUEST_CODE.
     */
    public static final int REQUEST_CODE = 69;

    /**
     * The First input.
     */
    @BindView(R.id.firstInput)
    EditText firstInput;
    /**
     * The Second input.
     */
    @BindView(R.id.secondInput)
    EditText secondInput;
    /**
     * The Radio group containing operations provided which can be selected one at a time.
     */
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    /**
     * Operation used.
     */
    private String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);
        ButterKnife.bind(this);
    }

    /**
     * Method which calculates wanted arithmetic operation upon clicking on result button.
     */
    @OnClick(R.id.result)
    public void calculate() {
        Intent intent = new Intent(this, DisplayActivity.class);

        try {
            double firstNumber = Double.parseDouble(firstInput.getText().toString());
            double secondNumber = Double.parseDouble(secondInput.getText().toString());

            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please select arithmetic operation.", Toast.LENGTH_SHORT).show();
                return;
            }

            double result = 0;
            switch (checkedRadioButtonId) {
                case R.id.add:
                    result = firstNumber + secondNumber;
                    operation = "Addition";
                    break;
                case R.id.sub:
                    result = firstNumber - secondNumber;
                    operation = "Subtraction";
                    break;
                case R.id.mul:
                    result = firstNumber * secondNumber;
                    operation = "Multiplication";
                    break;
                case R.id.div:
                    operation = "Division";
                    if (secondNumber == 0) {
                        throw new ArithmeticException("Division with zero not possible");
                    }
                    result = firstNumber * 1.0 / secondNumber;
                    break;
            }
            intent.putExtra("message", String.format("%s result is: %s.", operation, String.valueOf(result)));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please provide both values.", Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception e) {
            intent.putExtra("message", String.format("%s operation with values %s and %s, issued an exception: %s."
                    , operation, firstInput.getText().toString()
                    , secondInput.getText().toString()
                    , e.getMessage()));
        }

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            firstInput.getText().clear();
            secondInput.getText().clear();
            radioGroup.clearCheck();
        }
    }
}
