package com.maxx.oschinanews.ui.status;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.maxx.oschinanews.BR;
import com.maxx.oschinanews.model.User;

public class UserViewModel extends BaseObservable {

    private User user;

    public UserViewModel(){
        this.user = new User();
        user.name = "注册/登录";
    }

    public UserViewModel(User user) {
        this.user = user;
    }

    @Bindable
    public User getUser(){
        return user;
    }

    public void setUser(User me) {
        if (me != null && !(me.id).equals(user.id)) {
            user= me;
            notifyPropertyChanged(BR.user);
        }
    }
}