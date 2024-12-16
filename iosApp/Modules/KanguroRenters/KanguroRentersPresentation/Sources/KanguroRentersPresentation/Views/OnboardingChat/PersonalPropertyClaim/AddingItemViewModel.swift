import SwiftUI
import PhotosUI

public class AddingItemViewModel: UploadFileViewModel {
    
    @Published var itemName: String = ""
    @Published var itemValue: String = ""
    
    @Published var selectedPhoto: PhotosPickerItem?
    @Published var warrantyFile: PhotosPickerItem?
    @Published var itemFile: PhotosPickerItem?
}
