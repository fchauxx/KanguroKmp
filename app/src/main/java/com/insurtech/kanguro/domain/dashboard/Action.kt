package com.insurtech.kanguro.domain.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.insurtech.kanguro.R

enum class Action(@DrawableRes val icon: Int, @StringRes val nameRes: Int) {
    FileClaim(R.drawable.ic_add_square, R.string.file_a_claim),
    TrackClaims(R.drawable.ic_track_claims, R.string.track_claim),
    PaymentSettings(R.drawable.ic_dollar_circle, R.string.payment_settings),
    Settings(R.drawable.ic_settings, R.string.language_settings),
    VetAdvice(R.drawable.ic_message_favorite, R.string.vet_advice),
    FrequentQuestions(R.drawable.ic_message_question, R.string.frequent_asked_questions),
    NewPetParents(R.drawable.ic_hearts, R.string.new_pet_parents),
    GetQuote(R.drawable.ic_paw_outline, R.string.add_a_pet),
    TalkToJavier(R.drawable.ic_chat_javier, R.string.talk_to_javier),
    Profile(R.drawable.ic_user_square, R.string.profile),
    Reminders(R.drawable.ic_notification, R.string.reminders_section),
    Logout(R.drawable.ic_logout, R.string.logout),
    Phone(R.drawable.ic_phone, R.string.call_us),
    ReferAFriend(R.drawable.ic_star, R.string.refer_a_friend_action),
    TermsOfUse(R.drawable.ic_document_favorite, R.string.terms_of_use),
    PrivacyPolicy(R.drawable.ic_document_favorite, R.string.privacy_policy),
    FindVet(R.drawable.ic_maps, R.string.find_vet),
    Blog(R.drawable.ic_blog, R.string.blog),
    SupportCause(R.drawable.ic_heart, R.string.support_a_cause),
    Cloud(R.drawable.ic_cloud, R.string.cloud_menu),
    Communication(R.drawable.ic_message, R.string.communications),
    DirectPayYourVet(R.drawable.ic_dollar_circle_arrow, R.string.direct_pay_vet),
    SMSChat(R.drawable.ic_messages, R.string.chat_sms),
    WhatsAppChat(R.drawable.ic_whatsapp, R.string.chat_whatsapp),
    ContactUs(R.drawable.ic_messages, R.string.contact_us_action),
    LiveVet(R.drawable.ic_live_vet, R.string.action_live_vet);

    fun isHighlighted() = when (this) {
        DirectPayYourVet, SMSChat, WhatsAppChat, LiveVet -> true
        else -> false
    }

    fun highlightedLabel() = when (this) {
        DirectPayYourVet, LiveVet -> R.string.tag_new
        SMSChat, WhatsAppChat -> R.string.tag_faster
        else -> null
    }
}
