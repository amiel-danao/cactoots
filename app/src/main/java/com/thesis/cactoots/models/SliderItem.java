package com.thesis.cactoots.models;

import com.google.firebase.storage.StorageReference;

public class SliderItem {
    private String description;
    private String imageUrl;
    private StorageReference storageReference;

    public SliderItem(StorageReference storageReference) {
        this.storageReference = storageReference;
    }

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
