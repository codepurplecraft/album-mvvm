package com.albums.myalbums.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albums.myalbums.R;
import com.albums.myalbums.persistence.model.Album;
import com.albums.myalbums.viewmodel.AlbumViewModel;

import java.util.ArrayList;
import java.util.Collections;
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
public class AlbumFragment extends Fragment {

    public static final String TAG = AlbumFragment.class.getSimpleName();
    private AlbumViewModel viewModel;
    private AlbumViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private View progressView;
    private int userId;
    private static final String USER_ID_ARGUMENT = "userId";
    private static final String USER_NAME_ARGUMENT = "name";
    private String name;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlbumFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AlbumFragment newInstance(int userId, String name) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(USER_ID_ARGUMENT, userId);
        bundle.putString(USER_NAME_ARGUMENT, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userId = getArguments().getInt(USER_ID_ARGUMENT);
        name = getArguments().getString(USER_NAME_ARGUMENT);
        getActivity().setTitle(String.format(getString(R.string.albums_of),name));
        viewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        viewModel.getAlbums(userId).observe(this, (Observer<? super List<Album>>) albums -> {
            // Update UI.
            Collections.sort(albums);
            recyclerViewAdapter.addItems(albums);
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
        recyclerViewAdapter = new AlbumViewAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

}
