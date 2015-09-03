package com.example.rohan.alcoholmeter;

import android.graphics.Color;
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
    double previousDrink = 0.00;
    DecimalFormat f = new DecimalFormat("##.######");
    DecimalFormat tF = new DecimalFormat("#.##");
    double finalVal;
    double drinkChange;


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
//        ActionBar ab = getActionBar();
//        ab.setDisplayOptions(ab.getDisplayOptions()
//                                | ActionBar.DISPLAY_SHOW_CUSTOM);
//        ImageView imageView = new ImageView(ab.getThemedContext());
//        imageView.setScaleType(ImageView.ScaleType.CENTER);
//        imageView.setImageResource(R.drawable.ic_action_name2);
//        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
//                ActionBar.LayoutParams.WRAP_CONTENT,
//                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
//                | Gravity.CENTER_VERTICAL);
//        layoutParams.rightMargin = 0;
//        imageView.setLayoutParams(layoutParams);
//        ab.setCustomView(imageView);

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
    public void resetAll(View view) {
        etWeight.setText("");
        rgOz.check(findViewById(R.id.radioButtonOneOz).getId());
        tvResult.setText("0.00");
        pbStatus.setProgress(0);
        previousDrink = 0.0;
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

                drinkChange = Double.parseDouble(bacCalculation(Integer.parseInt(etWeight.getText().toString()), gender, drinkSize, alcoholPer));
                finalVal = previousDrink + drinkChange;
                previousDrink = finalVal;
                tvResult.setText(tF.format(finalVal));
                double dval = Math.round((finalVal) * 100);
                int val = (int) dval;
                Log.d("A", "pbVal" + dval + "PV " + previousDrink + " FD " + finalVal);
                pbStatus.setProgress(val);
                displayStatus(tF.format(finalVal));
            } else if (radioId == ((RadioButton) findViewById(R.id.radioButtonfiveOz)).getId()) {
                drinkSize = 5;
                drinkChange = Double.parseDouble(bacCalculation(Integer.parseInt(etWeight.getText().toString()), gender, drinkSize, alcoholPer));
                finalVal = previousDrink + drinkChange;
                previousDrink = finalVal;
                tvResult.setText(tF.format(finalVal));
                double dval = Math.round((finalVal) * 100);
                int val = (int) dval;
                Log.d("A", "pbVal" + dval + "PV " + previousDrink + " FD " + finalVal);
                pbStatus.setProgress(val);
                displayStatus(tF.format(finalVal));
            } else if (radioId == ((RadioButton) findViewById(R.id.radioButtonTwelveOz)).getId()) {
                drinkSize = 12;
                drinkChange = Double.parseDouble(bacCalculation(Integer.parseInt(etWeight.getText().toString()), gender, drinkSize, alcoholPer));
                finalVal = previousDrink + drinkChange;
                previousDrink = finalVal;
                tvResult.setText(tF.format(finalVal));
                double dval = Math.round((finalVal) * 100);
                int val = (int) dval;
                Log.d("A", "pbVal" + dval + "PV " + previousDrink + " FD " + finalVal);
                pbStatus.setProgress(val);
                displayStatus(tF.format(finalVal));
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
 *
*/
            double alcohol = drinkSize * alcoholPer / 10000;
            bac = ((alcohol * 5.1400) / (Weight * 0.7300));
            Log.d("A", "BACMale" + tF.format(bac) + ", DrinkSize " + drinkSize + ", alcoholPer " + alcoholPer + ",Weight " + Weight + ", alcohol" + alcohol);
            return tF.format(bac);


        } else {
            Log.d("A", "Invalid no gender selected:" + gender);
            double alcohol = drinkSize * alcoholPer / 100.0000;
            bac = ((alcohol * 5.1400) / (Weight * 0.6600));
            Log.d("A", "BACFeMale" + tF.format(bac) + ", DrinkSize " + drinkSize + ", alcoholPer " + alcoholPer + ",Weight " + Weight + ", alcohol" + alcohol);
            return tF.format(bac);
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

    /**
     * Final Text bar display
     *
     * @param bacLevel
     */
    public void displayStatus(String bacLevel) {
        Log.d("A", "BACLevel" + bacLevel);
        Double bacVal = Double.parseDouble(bacLevel);
        if (bacVal < 0.08) {
            tvStatus.setText("You're Safe");
            tvStatus.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (bacVal > 0.08 && bacVal < 0.20) {
            tvStatus.setText("Over the Limit!");
            tvStatus.setBackgroundColor(Color.parseColor("#FF9800"));
        } else if (bacVal >= 0.25) {
            tvStatus.setText("No more drinks for you.");
            tvStatus.setBackgroundColor(Color.parseColor("#F44336"));
//            (Button)findViewById(R.id.buttonSave).

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);

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
