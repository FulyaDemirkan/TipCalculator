package sheridan.demirkaf.demirkaf_assignment2;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    // UI elements
    private EditText mBillAmount;
    private Spinner mTipPercentage;
    private CheckBox mRoundTip;
    private TextView mCalculatedTip;
    private TextView mCalculatedTotal;

    // related classes
    private TipCalculator tipCalculator;
    private NumberFormat currencyFormat;

    //sharedPreferences
    private SharedPreferences mSharedPreferences;
    private static final String SAVED_GUI_STATE = "SavedGuiState";
    private static final String BILL_AMOUNT = "BillAmount";
    private static final String MINIMAL_TIP = "MinimalTip";
    private static final String IS_ROUNDED = "IsRounded";
    private static final String CALCULATED_TIP = "CalculatedTip";
    private static final String CALCULATED_TOTAL = "CalculatedTotal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBillAmount = findViewById(R.id.txt_amount);
        mTipPercentage = findViewById(R.id.spinner_minimal_tip);
        mRoundTip = findViewById(R.id.chk_round);
        mCalculatedTip = findViewById(R.id.txt_calculatedTip);
        mCalculatedTotal = findViewById(R.id.txt_calculatedTotal);

        tipCalculator = new TipCalculator();
        currencyFormat = NumberFormat.getCurrencyInstance();

        mSharedPreferences = getSharedPreferences(SAVED_GUI_STATE, MODE_PRIVATE);
    }

    public void calculateTip(View view)
    {
        try
        {
            tipCalculator.setBillAmount(Float.parseFloat(mBillAmount.getText().toString()));
        }
        catch(NumberFormatException ex)
        {
            Toast toast = Toast.makeText(this, "Please enter a valid Bill Amount!", Toast.LENGTH_LONG);
            toast.show();
        }

        tipCalculator.setTipPercentage(Float.parseFloat(mTipPercentage.getSelectedItem().toString().substring(0,2)) / 100);

        tipCalculator.setRounded(mRoundTip.isChecked());

        mCalculatedTip.setText(currencyFormat.format(tipCalculator.calculateTipAmount()));
        mCalculatedTotal.setText(currencyFormat.format(tipCalculator.calculateTotalAmount()));
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(BILL_AMOUNT, mBillAmount.getText().toString());
        editor.putInt(MINIMAL_TIP, mTipPercentage.getSelectedItemPosition());
        editor.putBoolean(IS_ROUNDED, mRoundTip.isChecked());
        editor.putString(CALCULATED_TIP, mCalculatedTip.getText().toString());
        editor.putString(CALCULATED_TOTAL, mCalculatedTotal.getText().toString());

        editor.commit();

        super.onPause();
    }

    @Override
    protected void onPostResume() {
        mBillAmount.setText(mSharedPreferences.getString(BILL_AMOUNT, ""));
        mTipPercentage.setSelection(mSharedPreferences.getInt(MINIMAL_TIP, 0));
        mRoundTip.setChecked(mSharedPreferences.getBoolean(IS_ROUNDED, false));
        mCalculatedTip.setText(mSharedPreferences.getString(CALCULATED_TIP, ""));
        mCalculatedTotal.setText(mSharedPreferences.getString(CALCULATED_TOTAL, ""));

        super.onPostResume();
    }
}
