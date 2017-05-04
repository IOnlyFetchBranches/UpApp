package com.lunalevel.up.Models.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lunalevel.up.Activities.MainLoginActivity;
import com.lunalevel.up.R;


public class userpageFragment extends Fragment {


    FirebaseStorage data=FirebaseStorage.getInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup userpage=(ViewGroup) inflater.inflate(R.layout.fragment_userpage,container,false);

        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null)
            ((TextView) userpage.findViewById(R.id.fragment_userpage_name_view)).setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        else
            ((TextView) userpage.findViewById(R.id.fragment_userpage_name_view)).setText(R.string.error_nouname);

        //retrieve picture if it exists if not, retrieve default;
        StorageReference userData=data.getReference("Service/Users/Profiles/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

        System.out.println(userData.getPath()  + " <-- path " + userData.getParent());


        //((ImageView) userpage.findViewById(R.id.fragement_userpage_avatar_display)).setImageURI();



        userpage.findViewById(R.id.fragment_userpage_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth=FirebaseAuth.getInstance();

                Toast userToast=Toast.makeText(userpageFragment.this.getContext(),"Current user-> "+ auth.getCurrentUser().getUid(),Toast.LENGTH_LONG);
                View userToastView=userToast.getView();
                userToastView.setBackground(ContextCompat.getDrawable(userpageFragment.this.getContext(),R.drawable.rounded_corner));
                userToastView.findViewById(android.R.id.message).setBackgroundColor(Color.BLACK);
                userToast.show();



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
