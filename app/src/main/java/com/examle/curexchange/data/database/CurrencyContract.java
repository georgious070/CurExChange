package com.examle.curexchange.data.database;

import android.content.ContentResolver;
import android.net.Uri;

public class CurrencyContract {

    public static final String CONTENT_AUTHORITY = "com.example.currency_db";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CURRENCY = "currency";

    public static class CurrencyEntry {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CURRENCY);
        public static final String TABLE_NAME = "crypto_codes";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_CRYPTO_NAME = "name";
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_CURRENCY;
    }
}
