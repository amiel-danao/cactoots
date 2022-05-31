package com.thesis.cactoots.java.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.GridLayoutManager;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.thesis.cactoots.java.R;
import com.thesis.cactoots.java.databinding.FragmentShopBinding;
import com.thesis.cactoots.java.models.Item;
import com.thesis.cactoots.java.views.ItemViewHolder;

public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;
    private FirestorePagingAdapter<Item, ItemViewHolder> adapter;

    private void initializeAdapter(){
        // The "base query" is a query with no startAt/endAt/limit clauses that the adapter can use
// to form smaller queries for each page. It should only include where() and orderBy() clauses
        Query baseQuery = FirebaseFirestore.getInstance()
                .collection("items").orderBy("timestamp", Query.Direction.ASCENDING);

// This configuration comes from the Paging 3 Library
// https://developer.android.com/reference/kotlin/androidx/paging/PagingConfig
        PagingConfig config = new PagingConfig(/* page size */ 20, /* prefetchDistance */ 10,
                /* enablePlaceHolders */ false);

// The options for the adapter combine the paging configuration with query information
// and application-specific options for lifecycle, etc.
        FirestorePagingOptions<Item> options = new FirestorePagingOptions.Builder<Item>()
                .setLifecycleOwner(this)
                .setQuery(baseQuery, config, Item.class)
                .build();

        adapter =
        new FirestorePagingAdapter<Item, ItemViewHolder>(options) {
            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create the ItemViewHolder
                // ...
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_item, parent, false);

                return new ItemViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder,
                                            int position,
                                            @NonNull Item model) {
                // Bind the item to the view holder
                // ...
                holder.bindItem(model, getContext());
                
                holder.getItemImage().setTag(model);
                holder.getItemImage().setOnClickListener(viewItemClickListener);
                holder.getItemNameView().setTag(model);
                holder.getItemNameView().setOnClickListener(viewItemClickListener);
            }
        };
    }

    private final View.OnClickListener viewItemClickListener = view -> {
        Item selectedItem = (Item)view.getTag();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment = ItemPreviewFragment.getInstance(selectedItem);
        ft.add(R.id.nav_host_fragment_content_main, fragment);
        ft.addToBackStack(Generic.FRAGMENT_BACK_STACK).commit();
	};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAdapter();
        binding.itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.itemRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}