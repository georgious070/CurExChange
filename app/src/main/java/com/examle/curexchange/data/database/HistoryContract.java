package com.examle.curexchange.data.database;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class HistoryContract {

    public static final String CONTENT_AUTHORITY = "com.example.currency_db";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_HISTORY = "history";

    public static class HistoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HISTORY);
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_FIRST_CURRENCY = "first";
        public static final String COLUMN_SECOND_CURRENCY = "second";
        public static final String COLUMN_RESULT = "result";
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_HISTORY;
    }
}
