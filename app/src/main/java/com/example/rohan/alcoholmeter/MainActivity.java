package com.example.rohan.alcoholmeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    int pounds;
    int percentage;
    String gender = "F";
    EditText etWeight;
    Switch sGender;
    RadioGroup rgOz;
    TextView tvPercentage;
    TextView tvResult;
    TextView tvStatus;
    ProgressBar pbStatus;
    SeekBar sbalcohol;
    int stepSize = 5;
    int maxValue = 25;
    final int minValue = 5;
    int radioId = 0;
    DecimalFormat f = new DecimalFormat("##.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * All Componenets
         */
        etWeight = ((EditText) findViewById(R.id.editText_weight));
        sGender = ((Switch) findViewById(R.id.switchGender));
        rgOz = ((RadioGroup) findViewById(R.id.alcoholLevel));
        tvPercentage = ((TextView) findViewById(R.id.textViewPerAlcohol));
        tvStatus = ((TextView) findViewById(R.id.finalStatus));
        tvResult = ((TextView) findViewById(R.id.resultBAC));
        pbStatus = ((ProgressBar) findViewById(R.id.progressBar));
        sbalcohol = ((SeekBar) findViewById(R.id.percentageAlcohol));
        sbalcohol.setProgress(0);
        sbalcohol.setMax((maxValue - minValue) / stepSize);
        /**
         * Intialization
         */
        tvPercentage.setText("5");
        sGender.setChecked(false);

        //TODO: isSelected() male else female

        sGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sGender.isChecked()) {
                    gender = "M";
                    Log.d("A", gender);
                } else {
                    gender = "F";
                    Log.d("A", gender);
                }
            }
        });

        sbalcohol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = minValue + (progress * stepSize);
                tvPercentage.setText(progress + "");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    /**
     * Reset Button
     */
    public void resetAll(View view) {
        etWeight.setText("");
        rgOz.check(findViewById(R.id.radioButtonOneOz).getId());
        //TODO: Check BAC field and Status field
    }

    /**
     * addDrink
     */
    public void addDrink(View view) {
        int alcoholPer = Integer.parseInt(((TextView) findViewById(R.id.textViewPerAlcohol)).getText().toString());
        int drinkSize = 0;
        Log.d("A", radioId + "");
        radioId = rgOz.getCheckedRadioButtonId();
        Log.d("A", "Chrrrr" + radioId + "");
        Log.d("A", "Progress " + alcoholPer + "" + "Weight " + etWeight.getText().toString() + "Gender " + gender);
        pbStatus.setMax(25);
        if (checkNull()) {
            if (radioId == ((RadioButton) findViewById(R.id.radioButtonOneOz)).getId()) {
                drinkSize = 1;
                tvResult.setText(bacCalculation(Integer.parseInt(etWeight.getText().toString()), gender, drinkSize, alcoholPer));
                double dval = Math.round(Double.parseDouble(bacCalculation(Integer.parseInt(etWeight.getText().toString()), gender, drinkSize, alcoholPer))*100);
                int val = (int)dval;
                Log.d("A","pbVal"+dval);
                pbStatus.setProgress(val);
            } else if (radioId == ((RadioButton) findViewById(R.id.radioButtonfiveOz)).getId()) {
                drinkSize = 5;
                tvResult.setText(bacCalculation(Integer.parseInt(etWeight.getText().toString()), gender, drinkSize, alcoholPer));
                double dval = Math.round(Double.parseDouble(bacCalculation(Integer.parseInt(etWeight.getText().toString()), gender, drinkSize, alcoholPer))*100);
                int val = (int)dval;
                Log.d("A","pbVal"+dval);
                pbStatus.setProgress(val);
            } else if (radioId == ((RadioButton) findViewById(R.id.radioButtonTwelveOz)).getId()) {
                drinkSize = 12;
                tvResult.setText(bacCalculation(Integer.parseInt(etWeight.getText().toString()), gender, drinkSize, alcoholPer));
                double dval = Math.round(Double.parseDouble(bacCalculation(Integer.parseInt(etWeight.getText().toString()), gender, drinkSize, alcoholPer))*100);
                int val = (int)dval;
                Log.d("A","pbVal"+dval);
                pbStatus.setProgress(val);
            } else {
                Log.d("A", "Error in radio selection");
            }
        } else {
            toastMessage();
        }
    }

    /**
     * BAC Calculation
     */
    public String bacCalculation(int Weight, String gender, int drinkSize, int alcoholPer) {
        Log.d("A", "Progress " + alcoholPer + "" + "Weight " + Weight + "Gender " + gender + "alcoholSi" + drinkSize);
        double bac;
        if (gender.equals("M")) {
/*
 *           % BAC = (A x 5.14 / W x r). [Here
 *           we are ignoring the passage of time in the formula.] See Figure 2(a).
 *                   a. A = liquid ounces of alcohol consumed = ounces * alcohol percentage (i.e. 5 x .15)
 *           b. W = a person’s weight in pounds
 *           c. r = a gender constant of alcohol distribution (.73 for men and .66 for women)
*/
            bac = (drinkSize * alcoholPer / 100 * (5.14 / Weight) * 0.73);
            Log.d("A","BACMale"+bac);
            return f.format(bac);


        } else {
            Log.d("A", "Invalid no gender selected:" + gender);
            bac = (drinkSize * alcoholPer / 100 * (5.14 / Weight) * 0.66);
            Log.d("A","BACFeMale"+bac);
            return f.format(bac);
        }
    }

    /**
     * Save Button
     *
     * @param view
     */
    public void saveAll(View view) {
        if (checkNull()) {
            pounds = Integer.parseInt(etWeight.getText().toString());
            Log.d("A", "weight" + pounds + "Gender " + gender);
        } else
            toastMessage();
    }
    /*
     * Toast Message On Empty
     */

    public void toastMessage() {
        Toast.makeText(getApplicationContext(), "Enter a valid input", Toast.LENGTH_SHORT).show();
    }

    public boolean checkNull() {
        if (etWeight.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
