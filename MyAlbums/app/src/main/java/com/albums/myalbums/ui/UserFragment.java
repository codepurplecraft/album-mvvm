package com.albums.myalbums.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albums.myalbums.R;
import com.albums.myalbums.persistence.model.User;
import com.albums.myalbums.viewmodel.AlbumViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class UserFragment extends Fragment implements UserViewAdapter.ItemClickListener {

    public static final String TAG = UserFragment.class.getSimpleName();
    private AlbumViewModel viewModel;
    private UserViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private View progressView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        viewModel.getUsers().observe(this, (Observer<? super List<User>>) users -> {
            // Update UI.
            recyclerViewAdapter.addItems(users);
            progressView.setVisibility(View.GONE);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        progressView = view.findViewById(R.id.loading_progress);

        // Set the adapter
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerViewAdapter = new UserViewAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onItemClicked(RecyclerView.ViewHolder vh, Object item, int pos) {
        User user = (User)item;
        AlbumFragment fragment = AlbumFragment.newInstance(user.getId(),user.getName() );
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, AlbumFragment.TAG).commit();
    }

}

