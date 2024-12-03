package com.insurtech.kanguro.ui.scenes.bankingInformation

import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredBanksRepository
import com.insurtech.kanguro.domain.model.BankInfo
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchBanksViewModel @Inject constructor(
    private val banksRepository: IRefactoredBanksRepository
) : BaseViewModel() {

    val banksList = MutableLiveData<List<BankInfo>>()
    private val localBanksList: ArrayList<BankInfo> = ArrayList()

    init {
        getBanks()
    }

    private fun getBanks() {
        launchLoading(Dispatchers.IO) {
            val result = banksRepository.getBanks()
            if (result is Result.Success) {
                val banks = result.data
                banksList.postValue(banks)
                localBanksList.addAll(banks)
            } else {
                val errorResult = result as Result.Error
                _networkError.postValue(
                    ErrorWithRetry(
                        NetworkResponse.UnknownError<Unit, ErrorDto>(
                            errorResult.exception,
                            null
                        )
                    ) {
                        getBanks()
                    }
                )
            }
        }
    }

    fun updateFilter(bank: String) {
        val plainBankName = bank.trim()
        val listBanks = localBanksList.filter {
            it.name.contains(plainBankName, ignoreCase = true)
        }
        banksList.value = listBanks
    }
}
