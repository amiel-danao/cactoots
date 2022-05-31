package com.thesis.cactoots.utilities;

import com.thesis.cactoots.models.Item;
import android.widget.ImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.Glide;

public class Generic {
    public static final String TAG = "logTag";
    public static final String ITEM_TO_PREVIEW = "ITEM_TO_PREVIEW";


    public static void fetchImage(Item item, Context context, ImageView image){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child(item.getId());

        listRef.listAll()
            .addOnSuccessListener(listResult -> Glide.with(context)
                    .load(listResult.getItems().get(0))
                    .into(image))
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Uh-oh, an error occurred!
                }
            });
    }
}
