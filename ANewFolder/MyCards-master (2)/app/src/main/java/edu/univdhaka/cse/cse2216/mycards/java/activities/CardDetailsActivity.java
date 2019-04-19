package edu.univdhaka.cse.cse2216.mycards.java.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import edu.univdhaka.cse.cse2216.mycards.R;
import edu.univdhaka.cse.cse2216.mycards.java.domain.Card;
import edu.univdhaka.cse.cse2216.mycards.java.service.CardService;

public class CardDetailsActivity extends Activity {

    private Card card;

    private TextView cardTypeLabel;
    private TextView bankNameLabel;
    private TextView cardNumberLabel;
    private TextView cardholderNameLabel;
    private TextView expiryDateLabel;
    private TextView cvvLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.card_details);

        setContentView(R.layout.activity_card_details);

        bindWidgets();

        if (getIntent().getExtras() != null) {
            Object card = getIntent().getExtras().get("card");
            if (card != null) {
                this.card = (Card) card;
            }
        }

        bindValuesToLabels();
    }

    private void bindWidgets() {
        cardTypeLabel = findViewById(R.id.card_type_label);
        bankNameLabel = findViewById(R.id.bank_name_label);
        cardNumberLabel = findViewById(R.id.card_number_label);
        cardholderNameLabel = findViewById(R.id.cardholder_name_label);
        expiryDateLabel = findViewById(R.id.expiry_date_label);
        cvvLabel = findViewById(R.id.cvv_label);
    }

    @SuppressLint("SetTextI18n")
    private void bindValuesToLabels() {
        cardTypeLabel.setText(card.getType());
        bankNameLabel.setText(card.getBankName());
        cardNumberLabel.setText(card.getNumber());
        cardholderNameLabel.setText(card.getCardholderName());
        expiryDateLabel.setText(card.getExpiryDate());
        cvvLabel.setText(Integer.toString(card.getCvv()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                onEdit();
                return true;
            case R.id.action_delete:
                onDelete();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onEdit() {
        startActivity(new Intent(this, AddEditCardActivity.class).putExtra("card", card));
    }

    private void onDelete() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_card_confirmation_title)
                .setMessage(R.string.delete_card_confirmation_message)
                .setPositiveButton(R.string.delete,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCard();
                            }
                        })
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    @SuppressWarnings("DebugLoggingFunction")
    private void deleteCard() {
        Log.d(getString(R.string.app_name), "Delete card: " + card.toString());

        CardService cardService = new CardService(this);
        cardService.deleteCard(card);

        Toast.makeText(this, R.string.card_delete_success, Toast.LENGTH_SHORT).show();

        finish();
    }
}
