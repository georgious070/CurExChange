package com.examle.curexchange.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.examle.curexchange.data.database.entities.CurrencyConstants
import com.examle.curexchange.data.database.entities.CurrencyEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface CurrencyDao {

    @Insert(onConflict = REPLACE)
    fun insertAll(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM " + CurrencyConstants.TABLE_NAME + " LIMIT 1")
    fun queryOneLine(): Maybe<CurrencyEntity>

    @Query("SELECT " + CurrencyConstants.COLUMN_CRYPTO_NAME + " FROM " + CurrencyConstants.TABLE_NAME)
    fun queryCryptoNames(): Flowable<MutableList<String>>

    @Query("SELECT " + CurrencyConstants.COLUMN_CODE + ", " + CurrencyConstants.COLUMN_CRYPTO_NAME
            + " FROM " + CurrencyConstants.TABLE_NAME + " WHERE "
            + CurrencyConstants.COLUMN_CRYPTO_NAME + "=:arg0" + " OR "
            + CurrencyConstants.COLUMN_CRYPTO_NAME + "=:arg1")
    fun queryCryptoCodesByCryptoNames(firstCryptoName: String, secondCryptoName: String): Flowable<List<CurrencyEntity>>

}