package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetAlmostDone

import androidx.lifecycle.SavedStateHandle
import com.insurtech.kanguro.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DirectPayToVetAlmostDoneViewModel(
    private val args: DirectPayToVetAlmostDoneFragmentArgs
) : BaseViewModel() {

    @Inject
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        DirectPayToVetAlmostDoneFragmentArgs.fromSavedStateHandle(savedStateHandle)
    )

    fun getClaimId() = args.claimId
}
