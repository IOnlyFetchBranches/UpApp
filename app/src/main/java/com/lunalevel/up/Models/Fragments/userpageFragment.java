package com.lunalevel.up.Models.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.lunalevel.up.Activities.MainLoginActivity;
import com.lunalevel.up.R;


public class userpageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup userpage=(ViewGroup) inflater.inflate(R.layout.fragment_userpage,container,false);

        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null)
            ((TextView) userpage.findViewById(R.id.fragment_userpage_name_view)).setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        else
            ((TextView) userpage.findViewById(R.id.fragment_userpage_name_view)).setText(R.string.erro_nouname);

        ((ImageView) userpage.findViewById(R.id.fragement_userpage_avatar_display)).setImageURI(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());



        userpage.findViewById(R.id.fragment_userpage_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                Toast.makeText(userpageFragment.this.getContext(),"Current user-> "+ auth.getCurrentUser().getUid(),Toast.LENGTH_LONG).show();
                auth.signOut();
                Intent resetApp=new Intent(userpageFragment.this.getContext(), MainLoginActivity.class);
                startActivity(resetApp);

                userpageFragment.this.getActivity().finish();

            }
        });

        return userpage;
    }








    public userpageFragment(){
        super();
    }
}
