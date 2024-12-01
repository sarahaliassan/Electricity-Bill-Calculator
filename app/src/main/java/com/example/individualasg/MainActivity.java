package com.example.individualasg;

import android.content.Intent;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCalc, btnClear, btnAboutMe;
    EditText etElecUnit, etRebatePctg;
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Toolbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Link UI elements to code
        btnCalc = findViewById(R.id.btnCalc);
        btnClear = findViewById(R.id.btnClear);
        btnAboutMe = findViewById(R.id.btnAboutMe); // Link About Me button
        etElecUnit = findViewById(R.id.etElecUnit);
        etRebatePctg = findViewById(R.id.etRebatePctg);
        tvOutput = findViewById(R.id.tvOutput);

        // Set onClick listeners for buttons
        btnCalc.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnAboutMe.setOnClickListener(this); // Set listener for About Me
    }

    @Override
    public void onClick(View view) {
        if (view == btnCalc) {
            Log.d("MainActivity", "Calculate button clicked!");
            calculateBill(); // Perform calculation
        } else if (view == btnClear) {
            Log.d("MainActivity", "Clear button clicked!");
            clearInputs(); // Clear input fields and output
        } else if (view == btnAboutMe) {
            Log.d("MainActivity", "About Me button clicked!");
            Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(intent); // Navigate to About Me Activity
        }
    }

    private void calculateBill() {
        try {
            // Get input values
            String electUnitStr = etElecUnit.getText().toString();
            String rebatePctgStr = etRebatePctg.getText().toString();

            // Validate inputs
            if (electUnitStr.isEmpty() || rebatePctgStr.isEmpty()) {
                Toast.makeText(this, "Please enter all inputs.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse inputs
            double electUnits = Double.parseDouble(electUnitStr);
            double rebatePctg = Double.parseDouble(rebatePctgStr);

            // Check rebate percentage range
            if (rebatePctg < 0 || rebatePctg > 5) {
                Toast.makeText(this, "Rebate percentage must be between 0% and 5%.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate total charges and final cost
            double totalCharges = calculateCharges(electUnits);
            double finalCost = totalCharges - (totalCharges * (rebatePctg / 100));

            // Show the dialog with results
            showSummaryDialog(totalCharges, finalCost);

        } catch (NumberFormatException e) {
            // Handle invalid numeric inputs
            Toast.makeText(this, "Invalid input. Please enter numeric values.", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateCharges(double units) {
        double total = 0;

        if (units > 600) {
            total += (units - 600) * 0.546;
            units = 600;
        }
        if (units > 300) {
            total += (units - 300) * 0.516;
            units = 300;
        }
        if (units > 200) {
            total += (units - 200) * 0.334;
            units = 200;
        }
        if (units > 0) {
            total += units * 0.218;
        }

        return total;
    }

    private void clearInputs() {
        etElecUnit.setText("");
        etRebatePctg.setText("");
        tvOutput.setText("");
        Toast.makeText(this, "Inputs cleared!", Toast.LENGTH_SHORT).show();
    }

    private void showSummaryDialog(double totalCharges, double finalCost) {
        // Create a dialog instance
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_summary);

        // Get references to dialog views
        TextView tvTotalCharges = dialog.findViewById(R.id.tvTotalCharges);
        TextView tvFinalCost = dialog.findViewById(R.id.tvFinalCost);
        Button btnClose = dialog.findViewById(R.id.btnClose);

        // Set the calculated values
        tvTotalCharges.setText(String.format("Total Charges: RM %.2f", totalCharges));
        tvFinalCost.setText(String.format("Final Cost (after rebate): RM %.2f", finalCost));

        // Set button click listener to close the dialog
        btnClose.setOnClickListener(v -> dialog.dismiss());

        // Show the dialog
        dialog.show();
    }
}
