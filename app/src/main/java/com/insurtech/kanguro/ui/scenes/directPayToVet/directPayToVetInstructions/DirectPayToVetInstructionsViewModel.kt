package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetInstructions

import androidx.lifecycle.SavedStateHandle
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.domain.model.ClaimDirectPayment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DirectPayToVetInstructionsViewModel(
    private val args: DirectPayToVetInstructionsFragmentArgs
) : BaseViewModel() {

    @Inject
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        DirectPayToVetInstructionsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    )

    fun getSharedFlow(): ClaimDirectPayment {
        return args.sharedFlow
    }
}
