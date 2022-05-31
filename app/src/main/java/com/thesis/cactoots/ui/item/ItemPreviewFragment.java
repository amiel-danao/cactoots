package com.thesis.cactoots.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.thesis.utilities.Generic;
import com.thesis.cactoots.models.Item;

import com.thesis.cactoots.java.databinding.FragmentItemPreviewBinding;

public class ItemPreviewFragment extends Fragment {

    private FragmentItemPreviewBinding binding;
    private static HeroEditFragment instance;
    private Item itemToPreview;

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

        Generic.fetchImage(itemToPreview, getActivity(), binding.itemImage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}