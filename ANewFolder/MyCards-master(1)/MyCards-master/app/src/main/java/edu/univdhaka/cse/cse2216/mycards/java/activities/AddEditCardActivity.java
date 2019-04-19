package edu.univdhaka.cse.cse2216.mycards.java.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.univdhaka.cse.cse2216.mycards.R;
import edu.univdhaka.cse.cse2216.mycards.java.domain.Card;
import edu.univdhaka.cse.cse2216.mycards.java.service.CardService;

public class AddEditCardActivity extends Activity {

    private Card card;

    private Spinner cardTypeInput;
    private EditText bankNameInput;
    private EditText cardNumberInput;
    private EditText cardholderNameInput;
    private EditText expiryDateInput;
    private EditText cvvInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_add_edit);

        bindWidgets();

        if (getIntent().getExtras() != null) {
            Object card = getIntent().getExtras().get("card");
            if (card != null) {
                this.card = (Card) card;
            }
        }

        bindValuesToInputs();
        setTitle(card == null ? R.string.add_card : R.string.edit_card);
    }

    private void bindWidgets() {
        cardTypeInput = findViewById(R.id.card_type_input);
        bankNameInput = findViewById(R.id.bank_name_input);
        cardNumberInput = findViewById(R.id.card_number_input);
        cardholderNameInput = findViewById(R.id.cardholder_name_input);
        expiryDateInput = findViewById(R.id.expiry_date_input);
        cvvInput = findViewById(R.id.cvv_input);
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("unchecked")
    private void bindValuesToInputs() {
        if (card != null) {
            cardTypeInput.setSelection(((ArrayAdapter<String>) cardTypeInput.getAdapter()).getPosition(card.getType()));
            bankNameInput.setText(card.getBankName());
            cardNumberInput.setText(card.getNumber());
            cardholderNameInput.setText(card.getCardholderName());
            expiryDateInput.setText(card.getExpiryDate());
            cvvInput.setText(Integer.toString(card.getCvv()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card_add_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                onSave();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSave() {
        if (validateInput()) {
            saveCard(getCardInstanceFromInput());
        }
    }

    private boolean validateInput() {
        boolean allInputsValid = true;

        for(EditText input
                : new EditText[]{bankNameInput, cardNumberInput, cardholderNameInput, expiryDateInput, cvvInput}) {
            if (input.getText().toString().isEmpty()) {
                showError(input, R.string.required);
                allInputsValid = false;
            }
        }

        return allInputsValid;
    }

    private void showError(EditText field, int messageRes) {
        field.setError(getString(messageRes));
    }

    private Card getCardInstanceFromInput() {
        return new Card(
                cardTypeInput.getSelectedItem().toString(),
                bankNameInput.getText().toString(),
                cardNumberInput.getText().toString(),
                cardholderNameInput.getText().toString(),
                expiryDateInput.getText().toString(),
                Integer.parseInt(cvvInput.getText().toString())
        );
    }

    @SuppressWarnings("DebugLoggingFunction")
    private void saveCard(Card card) {
        Log.d(getString(R.string.app_name), "New card added: " + card.toString());

        CardService cardService = new CardService(this);
        if (this.card == null) {
            cardService.addCard(card);
        } else {
            // replace old values of card fields with new values
            this.card.setType(card.getType());
            this.card.setBankName(card.getBankName());
            this.card.setNumber(card.getNumber());
            this.card.setCardholderName(card.getCardholderName());
            this.card.setExpiryDate(card.getExpiryDate());
            this.card.setCvv(card.getCvv());

            cardService.updateCard(this.card);
        }

        Toast.makeText(this, R.string.card_save_success, Toast.LENGTH_SHORT).show();

        finish();
    }
}
