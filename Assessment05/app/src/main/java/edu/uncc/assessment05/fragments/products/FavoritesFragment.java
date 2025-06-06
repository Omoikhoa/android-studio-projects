package edu.uncc.assessment05.fragments.products;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.assessment05.R;
import edu.uncc.assessment05.databinding.FavoriteListItemBinding;
import edu.uncc.assessment05.databinding.FragmentFavoritesBinding;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private List<Podcast> favoritePodcasts = new ArrayList<>();
    private FavoritesAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Favorites");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoritesAdapter();
        binding.recyclerView.setAdapter(adapter);

        if (user != null) {
            db.collection("users")
                    .document(user.getUid())
                    .collection("favorites")
                    .addSnapshotListener((snapshots, e) -> {
                        if (e != null) {
                            Toast.makeText(getContext(), "Error loading favorites", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (snapshots != null) {
                            favoritePodcasts.clear();
                            snapshots.getDocuments().forEach(doc -> {
                                Podcast podcast = doc.toObject(Podcast.class);
                                favoritePodcasts.add(podcast);
                            });
                            adapter.notifyDataSetChanged();
                        }
                    });
        }

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackClicked();
            }
        });
    }

    private void deleteFavorite(Podcast podcast) {
        if (user != null) {
            DocumentReference docRef = db.collection("users")
                    .document(user.getUid())
                    .collection("favorites")
                    .document(podcast.getCollectionName());
            docRef.delete()
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(getContext(), "Deleted favorite", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(), "Failed to delete favorite", Toast.LENGTH_SHORT).show());
        }
    }

    private class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

        @NonNull
        @Override
        public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            FavoriteListItemBinding itemBinding = FavoriteListItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new FavoriteViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
            Podcast podcast = favoritePodcasts.get(position);
            holder.bind(podcast);
        }

        @Override
        public int getItemCount() {
            return favoritePodcasts.size();
        }

        class FavoriteViewHolder extends RecyclerView.ViewHolder {
            private FavoriteListItemBinding itemBinding;

            public FavoriteViewHolder(FavoriteListItemBinding binding) {
                super(binding.getRoot());
                this.itemBinding = binding;
            }

            public void bind(Podcast podcast) {
                itemBinding.textViewCollectionName.setText(podcast.getCollectionName());
                itemBinding.textViewArtistName.setText(podcast.getArtistName());
                itemBinding.textViewReleaseDate.setText(podcast.getReleaseDate());
                itemBinding.textViewGenres.setText(podcast.getGenres());
                Picasso.get().load(podcast.getArtworkUrl100()).into(itemBinding.imageViewIcon);
                itemBinding.imageViewDelete.setOnClickListener(v -> deleteFavorite(podcast));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    FavoritesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FavoritesListener) {
            mListener = (FavoritesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FavoritesListener");
        }
    }

    public interface FavoritesListener {
        void onBackClicked();
    }


}