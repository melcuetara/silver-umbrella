package com.example.rentaland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentaland.databinding.ActivitySearchBinding;
import com.example.rentaland.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;

public class SearchActivity extends AppCompatActivity {

    public final String TAG = "SEARCH";
    private ActivitySearchBinding binding;

    private ArrayList<CheckBox> checkBoxes;
    private ArrayList<TextInputEditText> editTexts;
    public String error = "Filter is Empty";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkBoxes = new ArrayList<CheckBox>() {
            {
                add(binding.cbArea);
                add(binding.cbBudget);
            }
        };
        editTexts = new ArrayList<TextInputEditText>() {
            {
                add(binding.etAreaMin);
                add(binding.etBudgetMin);
                add(binding.etAreaMax);
                add(binding.etBudgetMax);
            }
        };

        checkBoxes.get(0).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.etAreaMinLayout.setEnabled(true);
                    binding.etAreaMaxLayout.setEnabled(true);
                    return;
                }
                binding.etAreaMinLayout.setEnabled(false);
                binding.etAreaMaxLayout.setEnabled(false);
            }
        });
        checkBoxes.get(1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.etBudgetMinLayout.setEnabled(true);
                    binding.etBudgetMaxLayout.setEnabled(true);
                    return;
                }
                binding.etBudgetMinLayout.setEnabled(false);
                binding.etBudgetMaxLayout.setEnabled(false);
            }
        });


        binding.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                for (int i = 0; i < checkBoxes.size(); i++) {
                    if (checkBoxes.get(i).isChecked()) {
                        if (hasValue(editTexts.get(i)) && hasValue(editTexts.get(i + 2))) {
                            double min = Double.parseDouble(editTexts.get(i).getText().toString());
                            double max = Double.parseDouble(editTexts.get(i + 2).getText().toString());
                            if (max > min) {
                                if (i == 0) {
                                    intent.putExtra("areaMin", min);
                                    intent.putExtra("areaMax", max);
                                } else if (i == 1) {
                                    intent.putExtra("budgetMin", min);
                                    intent.putExtra("budgetMax", max);
                                }
                            } else {
                                error = "Invalid Range";
                                break;
                            }
                        }
                    }
                }
                if (binding.etKeyword.getText().toString().trim().length() != 0) {
                    intent.putExtra("keyword", binding.etKeyword.getText().toString());
                }
                if (intent.getExtras() != null) {
                    setResult(1, intent);
                    finish();
                    return;
                }
                Toast.makeText(SearchActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean hasValue(TextInputEditText et) {
        return et.getText().toString().trim().length() != 0;
    }

}