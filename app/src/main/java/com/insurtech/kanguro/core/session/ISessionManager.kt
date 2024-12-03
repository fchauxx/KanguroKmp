package com.insurtech.kanguro.core.session

import com.insurtech.kanguro.domain.model.Login

interface ISessionManager {
    var sessionInfo: Login?
}
