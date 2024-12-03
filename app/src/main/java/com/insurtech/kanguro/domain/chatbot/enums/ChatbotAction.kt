package com.insurtech.kanguro.domain.chatbot.enums

enum class ChatbotAction {
    Yes,
    No,
    Skip,
    Submit,
    Finish,
    Signature,
    UploadFile,
    UploadImage,
    UserCustomInput,
    FinishAndRedirect,
    Reimbursement,

    /***
     * Used when we must create an offline action that has to be intercepted.
     */
    LocalAction,
    StopClaim,
    StopDuplicatedClaim, // Local action. Not mapped in backend
    FinishPetInformation,
    FinishFilesUpload, // Local action. Not mapped in backend
    VaccinesAndExamsSelect,
    OtpValidation,
    EditLastStep, // Local action. Not mapped in backend
    SendLastStep // Local action. Not mapped in backend
}
