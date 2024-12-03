package com.insurtech.kanguro.ui.scenes.cloud

import android.view.LayoutInflater
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentCloudFilesBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment

class CloudFilesBottomSheet : KanguroBottomSheetFragment<FragmentCloudFilesBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.CloudFiles

    override fun onCreateBinding(inflater: LayoutInflater) = FragmentCloudFilesBinding.inflate(inflater)
}
