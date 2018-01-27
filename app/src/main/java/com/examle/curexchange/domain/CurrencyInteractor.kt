package com.examle.curexchange.domain

import com.examle.curexchange.data.repository.currency.CurrencyRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class CurrencyInteractor @Inject constructor(private val currencyRepository: CurrencyRepository) {

    fun loadData(): Flowable<MutableList<String>> =
            currencyRepository.names.observeOn(AndroidSchedulers.mainThread())
}