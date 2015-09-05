package com.example.rohan.alcoholmeter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    int newPounds;
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
    double previousDrink = 0.0000;
    DecimalFormat f = new DecimalFormat("##.######");
    DecimalFormat tF = new DecimalFormat("#.##");
    double finalVal;
    double drinkChange;
    Button bAddDrink;
    Button bsave;
    Button bclearAllContent;
    int drinkSize = 0;
    int alcoholPer = 5;
    double previousAlcoholVal = 0.00000;
    double currentValue = 0.0000;
    boolean isClicked = false;
    Double bacVal;


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
        bAddDrink = ((Button) findViewById(R.id.addDrink));
        bsave = ((Button) findViewById(R.id.buttonSave));
        bclearAllContent = ((Button) findViewById(R.id.buttonReset));

        //Adding the custom icon on the Action Bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_launcher);
        actionBar.setTitle(R.string.app_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void resetAll(View view) {
        etWeight.setText("");
        rgOz.check(findViewById(R.id.radioButtonOneOz).getId());
        tvResult.setText("0.00");
        pbStatus.setProgress(0);
        previousDrink = 0.0;
        bAddDrink.setEnabled(true);
        bsave.setEnabled(true);
        previousAlcoholVal = 0.00000;
        currentValue = 0.0000;
        tvStatus.setText("You're safe.");
        isClicked = false;
        tvStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.textviewlayout));
        //TODO: Check BAC field and Status field
    }

    /**
     * addDrink
     */
    public void addDrink(View view) {

        alcoholPer = Integer.parseInt(((TextView) findViewById(R.id.textViewPerAlcohol)).getText().toString());
        Log.d("A", radioId + "");
        radioId = rgOz.getCheckedRadioButtonId();
        Log.d("A", "Chrrrr" + radioId + "");
        Log.d("A", "Progress " + alcoholPer + "" + "Weight " + newPounds + "Gender " + gender);
        pbStatus.setMax(25);
        if (checkNull()) {
            if (isClicked == true) {
                Log.d("A", "button_save true");

                if (radioId == ((RadioButton) findViewById(R.id.radioButtonOneOz)).getId()) {
                    drinkSize = 1;
                    bacLayoutUpdater(drinkSize, alcoholPer);
//                alcoholConcentration(drinkSize,alcoholPer,gender);
                } else if (radioId == ((RadioButton) findViewById(R.id.radioButtonfiveOz)).getId()) {
                    drinkSize = 5;
                    bacLayoutUpdater(drinkSize, alcoholPer);
//                alcoholConcentration(drinkSize, alcoholPer,gender);
                } else if (radioId == ((RadioButton) findViewById(R.id.radioButtonTwelveOz)).getId()) {
                    drinkSize = 12;
                    bacLayoutUpdater(drinkSize, alcoholPer);
//                alcoholConcentration(drinkSize, alcoholPer,gender);

                } else {
                    Log.d("A", "Error in radio selection");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Save the Weight to Proceed", Toast.LENGTH_LONG).show();
            }
        } else {

            setErrorMessage();
        }
    }

    /**
     * BAC Layout Updater
     */

    public void bacLayoutUpdater(int drinkSi, int alcoholPe) {
//        alcoholConcentration(drinkSi,alcoholPe,gender);
        drinkChange = Double.parseDouble(alcoholConcentration(drinkSi, alcoholPe, gender));
//        drinkChange = Double.parseDouble(bacCalculation(gender,alcoholPe));
//        finalVal = previousDrink + drinkChange;
//        previousDrink = finalVal;
        finalVal = drinkChange;
        tvResult.setText(tF.format(finalVal));
        double dval = Math.round((finalVal) * 100);
        int val = (int) dval;
        Log.d("A", "pbVal" + dval + "PV " + previousDrink + " FD " + finalVal);
        pbStatus.setProgress(val);
        displayStatus(tF.format(finalVal));
    }

    /**
     * Alcohol Calculation
     */

    public String alcoholConcentration(int drinkSize, int alcoholPer, String gender) {
//        double previousAlcoholVal = ;

        currentValue = drinkSize * alcoholPer / 100.0000 + previousAlcoholVal;
        previousAlcoholVal = currentValue;
        Log.d("A", "Current Val " + f.format(currentValue) + ", Previos" + f.format(previousAlcoholVal));
        return bacCalculation(gender, currentValue);

    }

    /**
     * BAC Calculation
     */
    public String bacCalculation(String gender, double alcoholConc) {

        double bac;
//        weight = Integer.parseInt(etWeight.getText().toString());
        Log.d("A", "Progress " + alcoholPer + "" + "Weight " + newPounds + "Gender " + gender + "alcoholSi" + drinkSize);
        if (gender.equals("M")) {
/*
 *           % BAC = (A x 5.14 / W x r). [Here
 *           we are ignoring the passage of time in the formula.] See Figure 2(a).
 *                   a. A = liquid ounces of alcohol consumed = ounces * alcohol percentage (i.e. 5 x .15)
 *           b. W = a person’s weight in pounds
 *           c. r = a gender constant of alcohol distribution (.73 for men and .66 for women)
 *
*/
//            double alcohol = drinkSize * alcoholPer / 100.0000;
            bac = ((alcoholConc * 5.1400) / (newPounds * 0.7300));
            Log.d("A", "BACMale" + tF.format(bac) + ",weight " + newPounds + " alcoholConc" + alcoholConc);
            return tF.format(bac);


        } else {
            Log.d("A", "Invalid no gender selected:" + gender);
            double alcohol = drinkSize * alcoholPer / 100.0000;
            bac = ((alcoholConc * 5.1400) / (newPounds * 0.6600));
            Log.d("A", "BACFeMale" + tF.format(bac) + ",weight " + newPounds + " alcoholConc" + alcoholConc);
            return tF.format(bac);
        }
    }


    /*
     * Toast Message On Empty
     */

    public void setErrorMessage() {
//        Toast.makeText(getApplicationContext(), "Enter the weight in lbs.", Toast.LENGTH_SHORT).show();
        etWeight.setError("Enter the weight in lbs.");

    }

    public boolean checkNull() {
        if (etWeight.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    /**
     * Final Text bar display
     *
     * @param bacLevel
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void displayStatus(String bacLevel) {
        Log.d("A", "BACLevel" + bacLevel);
        bacVal = Double.parseDouble(bacLevel);
        if (bacVal < 0.08) {
            Log.d("A", "Green");
            tvStatus.setText("You're safe.");
            tvStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.textviewlayout));
        } else if (bacVal > 0.08 && bacVal < 0.20) {
            Log.d("A", "Be careful...");
            tvStatus.setText("Be careful...");
            tvStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.textviewlayoutamber));
        } else if (bacVal >= 0.20 && bacVal < 0.25) {
            Log.d("A", "Disable, Red");
            tvStatus.setText("Over the Limit!");
            tvStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.textviewred));

        } else if (bacVal >= 0.25) {
            Toast.makeText(getApplicationContext(), "No more drinks for you", Toast.LENGTH_LONG).show();
            bAddDrink.setEnabled(false);
            bsave.setEnabled(false);
        }

    }

    /**
     * Save Button
     *
     * @param view
     */

    /**
     * For the weight and gender to be used the user should press the SAVE button ..
     * If they SAVE button was never pressed and the user clicks the ADD button print
     * a toast indicating that the weight and gender should be entered and registered
     * using the SAVE button before drinks can be added.
     *
     * @param view
     */
    public void saveAll(View view) {
        double bacNew = 0.0;
        if (checkNull()) {
            isClicked = true;
            newPounds = Integer.parseInt(etWeight.getText().toString());


//            drinkChange = Double.parseDouble(alcoholConcentration(drinkSize, alcoholPer, gender));
            if (gender.equals("M")) {
                bacNew = (((currentValue) * 5.1400) / (newPounds * 0.73));
                Log.d("A", "BACSaMale" + tF.format(bacNew) + ",weight " + newPounds + ", CurrentVal" + currentValue + ", Preval" + previousAlcoholVal);

            } else {
                bacNew = (((currentValue) * 5.1400) / (newPounds * 0.66));
                Log.d("A", "BACSaFeMale" + tF.format(bacNew) + ",weight " + newPounds + ", CurrentVal" + currentValue + ", Preval" + previousAlcoholVal);

            }

            tvResult.setText(tF.format(bacNew));
            double dval = Math.round((bacNew) * 100);
            int val = (int) dval;
            pbStatus.setProgress(val);
            displayStatus(tF.format(bacNew));
        } else
            setErrorMessage();
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
