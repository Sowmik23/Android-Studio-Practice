package edu.univdhaka.cse.cse2216.mycards.java.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.univdhaka.cse.cse2216.mycards.java.domain.Card;
import edu.univdhaka.cse.cse2216.mycards.java.utils.DbHelper;

public class DatabaseCardRepository implements CardRepository {

    private Context context;

    public DatabaseCardRepository(Context context) {
        this.context = context;
    }

    @Override
    public List<Card> listCards() {
        List<Card> cards = new ArrayList<>();

        try (SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
             Cursor cursor = db.query("cards", null, null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                cards.add(
                        new Card(
                                cursor.getString(cursor.getColumnIndexOrThrow("type")),
                                cursor.getString(cursor.getColumnIndexOrThrow("bank_name")),
                                cursor.getString(cursor.getColumnIndexOrThrow("number")),
                                cursor.getString(cursor.getColumnIndexOrThrow("cardholder_name")),
                                cursor.getString(cursor.getColumnIndexOrThrow("expiry_date")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("cvv")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
                        )
                );
            }
        }

        return cards;
    }

    @Override
    public void addCard(Card card) {
        ContentValues values = getContentValues(card);

        try (SQLiteDatabase db = new DbHelper(context).getWritableDatabase()) {
            db.insertOrThrow("cards", null, values);
        }
    }

    @Override
    public void updateCard(Card card) {
        ContentValues values = getContentValues(card);

        try (SQLiteDatabase db = new DbHelper(context).getWritableDatabase()) {
            db.update("cards", values, "_id = ?", new String[]{Integer.toString(card.getId())});
        }
    }

    @Override
    public void deleteCard(Card card) {
        try (SQLiteDatabase db = new DbHelper(context).getWritableDatabase()) {
            db.delete("cards", "_id = ?", new String[]{Integer.toString(card.getId())});
        }
    }

    @NonNull
    private ContentValues getContentValues(Card card) {
        ContentValues values = new ContentValues();
        values.put("number", card.getNumber());
        values.put("type", card.getType());
        values.put("bank_name", card.getBankName());
        values.put("cardholder_name", card.getCardholderName());
        values.put("expiry_date", card.getExpiryDate());
        values.put("cvv", card.getCvv());

        return values;
    }
}
