package com.insurtech.kanguro.ui.scenes.pledgeOfHonor

import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.ui.scenes.pledgeOfHonorBase.PledgeOfHonorBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PledgeOfHonorViewModel @Inject constructor(
    sessionManager: ISessionManager
) : PledgeOfHonorBaseViewModel(sessionManager)
