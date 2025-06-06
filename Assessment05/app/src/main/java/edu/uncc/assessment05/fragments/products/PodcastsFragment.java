package edu.uncc.assessment05.fragments.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import edu.uncc.assessment05.R;
import edu.uncc.assessment05.databinding.FragmentPodcastsBinding;
import edu.uncc.assessment05.databinding.ProductListItemBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PodcastsFragment extends Fragment {

    public PodcastsFragment() {
        // Required empty public constructor
    }

    FragmentPodcastsBinding binding;
    ArrayList<Podcast> podcasts = new ArrayList<>();
    PodcastAdapter adapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    PodcastsListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPodcastsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Podcasts");

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu);
            }
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.goto_favorites_action) {
                    mListener.gotoFavorites();
                    return true;
                } else if (menuItem.getItemId() == R.id.logout_action) {
                    mListener.logout();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        adapter = new PodcastAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        loadPodcasts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadPodcasts() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://www.theappsdr.com/design.json").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Failed to load podcasts", Toast.LENGTH_SHORT).show());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonStr = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        JSONArray jsonList = jsonObject.getJSONArray("results");

                        podcasts.clear();
                        for (int i = 0; i < jsonList.length(); i++){
                            JSONObject jsonObjects = jsonList.getJSONObject(i);
                            podcasts.add(new Podcast(jsonObjects));
                        }
                        requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void addPodcastToFavorites(Podcast podcast) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users")
                    .document(user.getUid())
                    .collection("favorites")
                    .document(podcast.getCollectionName());
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if(!documentSnapshot.exists()){
                    docRef.set(podcast)
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(getContext(), "Failed to add favorite", Toast.LENGTH_SHORT).show());
                } else {
                    Toast.makeText(getContext(), "Podcast already in favorites", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder> {
        @NonNull
        @Override
        public PodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ProductListItemBinding itemBinding = ProductListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new PodcastViewHolder(itemBinding);
        }
        @Override
        public void onBindViewHolder(@NonNull PodcastViewHolder holder, int position) {
            Podcast podcast = podcasts.get(position);
            holder.bind(podcast);
        }
        @Override
        public int getItemCount() {
            return podcasts.size();
        }
        class PodcastViewHolder extends RecyclerView.ViewHolder {
            ProductListItemBinding binding;
            public PodcastViewHolder(ProductListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
            public void bind(Podcast podcast) {
                binding.textViewCollectionName.setText(podcast.getCollectionName());
                binding.textViewArtistName.setText(podcast.getArtistName());
                binding.textViewReleaseDate.setText(podcast.getReleaseDate());
                binding.textViewGenres.setText(podcast.getGenres());
                binding.imageViewAdd.setOnClickListener(v -> addPodcastToFavorites(podcast));
                Picasso.get().load(podcast.getArtworkUrl100()).into(binding.imageViewIcon);
            }
        }
    }

    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        if (context instanceof PodcastsListener) {
            mListener = (PodcastsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PodcastsListener");
        }
    }

    public interface PodcastsListener {
        void gotoFavorites();
        void logout();
    }
}