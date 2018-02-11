package com.codepunks.heartfit;

        import android.app.Fragment;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;

        import com.google.firebase.auth.FirebaseAuth;


public class ThirdFragment extends Fragment{
    Boolean isLoggingOut = false;
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.third_layout, container, false);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                isLoggingOut = true;
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        } ;
        return myView;
    }



}

