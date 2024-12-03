package com.insurtech.kanguro.ui.scenes.vetAdvice

import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.setVisibility
import com.insurtech.kanguro.databinding.DoctorCardBinding

class DoctorCardAdapter(private val colorText: String, private val titleText: String) : RecyclerView.Adapter<DoctorCardViewHolder>() {

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCardViewHolder {
        val binding = DoctorCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorCardViewHolder(binding, colorText, titleText)
    }

    override fun onBindViewHolder(holder: DoctorCardViewHolder, position: Int) {
        holder.getColoredText()
        holder.setInstagram()
    }
}

class DoctorCardViewHolder(private val binding: DoctorCardBinding, private val colorText: String, private val titleText: String) : RecyclerView.ViewHolder(binding.root) {
    fun getColoredText() {
        val context = binding.root.context
        val startIndex = titleText.indexOf(colorText)
        val endIndex = startIndex + colorText.length

        val toSpan = SpannableString(titleText)
        toSpan.apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        context,
                        R.color.primary_darkest
                    )
                ),
                startIndex,
                endIndex,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
        binding.vetAdviceLabel.setVisibility(true)
        binding.vetAdviceLabel.text = toSpan
    }
    fun setInstagram() {
        val context = binding.root.context
        binding.instagramButton.setOnClickListener {
            val uri = Uri.parse("https://instagram.com/dr.b.vet?igshid=YmMyMTA2M2Y=")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }
}
