package com.insurtech.kanguro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.insurtech.kanguro.databinding.ActivityComponentTestBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ComponentTestActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityComponentTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityComponentTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            while (true) {
                delay(2000)
                binding.textField.error = "Test error"
                delay(2000)
                binding.textField.error = null
            }
        }
    }
}
