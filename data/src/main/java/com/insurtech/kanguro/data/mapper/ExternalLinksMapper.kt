package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ExternalLinks
import com.insurtech.kanguro.networking.dto.ExternalLinksDto

object ExternalLinksMapper {
    fun ExternalLinksDto.toExternalLink(): ExternalLinks =
        ExternalLinks(this.redirectTo)
}
