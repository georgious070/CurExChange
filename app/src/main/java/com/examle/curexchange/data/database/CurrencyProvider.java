//package com.examle.curexchange.data.database;
//
//import android.content.ContentProvider;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//public class CurrencyProvider extends ContentProvider {
//
//    private DbHelper dbHelper;
//    public SQLiteDatabase sqLiteDatabase;
//    private static final int CURRENCY_MATCHER = 100;
//    private static final int HISTORY_MATCHER = 101;
//    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//
//    static {
//        uriMatcher.addURI(CurrencyContract.CONTENT_AUTHORITY,
//                CurrencyContract.PATH_CURRENCY,
//                CURRENCY_MATCHER);
//        uriMatcher.addURI(HistoryContract.CONTENT_AUTHORITY,
//                HistoryContract.PATH_HISTORY,
//                HISTORY_MATCHER);
//    }
//
//    @Override
//    public boolean onCreate() {
//        dbHelper = new DbHelper(getContext());
//        sqLiteDatabase = dbHelper.getWritableDatabase();
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(@NonNull Uri uri,
//                        @Nullable String[] projection,
//                        @Nullable String selection,
//                        @Nullable String[] selectionArgs,
//                        @Nullable String sortOrder) {
//        Cursor cursor;
//        switch (uriMatcher.match(uri)) {
//            case CURRENCY_MATCHER:
//                cursor = sqLiteDatabase.query(CurrencyContract.CurrencyEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder);
//                break;
//            default:
//                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
//        }
//        cursor.setNotificationUri(getContext().getContentResolver(), uri);
//        return cursor;
//    }
//
//    @Nullable
//    @Override
//    public String getType(@NonNull Uri uri) {
//        switch (uriMatcher.match(uri)) {
//            case CURRENCY_MATCHER:
//                return CurrencyContract.CurrencyEntry.CONTENT_LIST_TYPE;
//            case HISTORY_MATCHER:
//                return HistoryContract.HistoryEntry.CONTENT_LIST_TYPE;
//            default:
//                throw new IllegalStateException("Unknown URI " + uri + " with match");
//        }
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(@NonNull Uri uri,
//                      @Nullable ContentValues values) {
//        long rawId;
//        switch (uriMatcher.match(uri)) {
//            case CURRENCY_MATCHER:
//                rawId = sqLiteDatabase.insert(CurrencyContract.CurrencyEntry.TABLE_NAME, null, values);
//                break;
//            case HISTORY_MATCHER:
//                rawId = sqLiteDatabase.insert(HistoryContract.HistoryEntry.TABLE_NAME, null, values);
//                break;
//            default:
//                throw new IllegalArgumentException("Insertion is not supported for " + uri);
//        }
//        Uri resultUri = ContentUris.withAppendedId(uri, rawId);
//        getContext().getContentResolver().notifyChange(resultUri, null);
//        return resultUri;
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri,
//                      @Nullable String selection,
//                      @Nullable String[] selectionArgs) {
//        //no-op
//        return 0;
//    }
//
//    @Override
//    public int update(@NonNull Uri uri,
//                      @Nullable ContentValues values,
//                      @Nullable String selection,
//                      @Nullable String[] selectionArgs) {
//        //no-op
//        return 0;
//    }
//}
