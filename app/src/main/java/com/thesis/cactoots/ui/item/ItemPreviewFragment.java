package com.thesis.cactoots.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.thesis.cactoots.adapters.SliderAdapterExample;
import com.thesis.cactoots.models.SliderItem;
import com.thesis.cactoots.utilities.Generic;
import com.thesis.cactoots.models.Item;
import com.thesis.cactoots.java.R;

import com.thesis.cactoots.java.databinding.FragmentItemPreviewBinding;

import java.util.ArrayList;
import java.util.List;

public class ItemPreviewFragment extends Fragment {

    private FragmentItemPreviewBinding binding;
    private static ItemPreviewFragment instance;
    private Item itemToPreview;
    private SliderAdapterExample sliderAdapter;

    public ItemPreviewFragment() {
        // Required empty public constructor
    }

	public static ItemPreviewFragment getInstance(Item item) {
		if(instance == null) {
			instance = new ItemPreviewFragment();
		}

		Bundle bundle = new Bundle();
		bundle.putSerializable(Generic.ITEM_TO_PREVIEW, item);
		instance.setArguments(bundle);

		return instance;
	}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
				inflater, R.layout.fragment_item_preview, container, false);

        View root = binding.getRoot();

        if (getArguments() != null) {
			itemToPreview = (Item) getArguments().getSerializable(Generic.ITEM_TO_PREVIEW);
		}
		binding.setItem(itemToPreview);
        
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sliderAdapter = new SliderAdapterExample(getActivity());
        binding.imageSlider.setSliderAdapter(sliderAdapter);

        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.startAutoCycle();
        getItemImages();
    }

    private void getItemImages(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child(itemToPreview.getId());


        listRef.listAll()
        .addOnSuccessListener(listResult -> {
            List<SliderItem> images = new ArrayList<>();

            for (StorageReference imageRef :
                    listResult.getItems()) {
                SliderItem item = new SliderItem(imageRef);

                images.add(item);
            }

            sliderAdapter.renewItems(images);
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Uh-oh, an error occurred!
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}