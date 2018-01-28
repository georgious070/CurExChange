package com.examle.curexchange.domain

import com.examle.curexchange.data.repository.exchange.ExchangeRepository
import io.reactivex.Flowable
import javax.inject.Inject

class ExchangeInteractor @Inject constructor(private val exchangeRepository: ExchangeRepository) {

    fun getResult(firstCurrencyName: String,
                  secondCurrencyName: String,
                  value: Int): Flowable<Float> =
            exchangeRepository.getResult(firstCurrencyName, secondCurrencyName, value)
}