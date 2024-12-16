import Foundation

public typealias SimpleClosure = () -> Void
public typealias StringClosure = (String) -> Void
public typealias StringOptionalClosure = (String?) -> Void
public typealias IntClosure = (Int) -> Void
public typealias AnyClosure = (Any) -> Void
public typealias BoolClosure = (Bool) -> Void
public typealias LanguageClosure = (Language) -> Void
public typealias DataClosure = (Data) -> Void
public typealias PolicyDocumentDataClosure = (PolicyDocumentData) -> Void
public typealias UserDonationTypeClosure = (DonationType, [DonationCause])-> Void
public typealias BoolResponseClosure = (Result<Bool, RequestError>) -> Void
public typealias TwoStringClosure = (String, String) -> Void
public typealias AttachmentClosure = (Attachment) -> Void
public typealias PolicyAttachmentClosure = (PolicyAttachment) -> Void
