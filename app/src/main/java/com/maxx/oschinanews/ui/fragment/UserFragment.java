package com.maxx.oschinanews.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.maxx.oschinanews.R;
import com.maxx.oschinanews.api.RetrofitClient;
import com.maxx.oschinanews.databinding.FragmentUserBinding;
import com.maxx.oschinanews.model.User;
import com.maxx.oschinanews.ui.status.UserViewModel;
import com.maxx.oschinanews.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    public UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user, container, false);
        binding = FragmentUserBinding.bind(root);

        String token = NetworkUtil.getToken(getActivity());

        final User[] user = new User[1];

        RetrofitClient.getInstance()
                .getApi()
                .getUser(token)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user[0] = response.body();
                        android.util.Log.e("fangjian","user[0]="+ user[0]);
                        userViewModel.setUser(user[0]);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

        userViewModel = new UserViewModel();
        binding.setUserViewModel(userViewModel);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
