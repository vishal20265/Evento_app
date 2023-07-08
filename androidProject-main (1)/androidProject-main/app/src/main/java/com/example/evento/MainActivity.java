package com.example.evento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInOptions gso;
    private GoogleSignInClient client;
    private ImageButton log;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass>userList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log = findViewById(R.id.logoutButton);
        initData();
        initRecyclerView();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        client = GoogleSignIn.getClient(this, gso);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });
    }

    private void LogOut() {
        client.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
            }
        });
    }

    private void initData() {
        userList = new ArrayList<>();
        userList.add(new ModelClass(R.drawable.listitem1,"Hemant","9:45 Pm"));
        userList.add(new ModelClass(R.drawable.listitem1,"Ramesh","9:45 Pm"));
        userList.add(new ModelClass(R.drawable.listitem1,"Suresh","9:45 Pm"));
        userList.add(new ModelClass(R.drawable.listitem1,"Samresh","9:45 Pm"));
        userList.add(new ModelClass(R.drawable.listitem1,"Kamlesh","9:45 Pm"));
        userList.add(new ModelClass(R.drawable.listitem1,"Mahesh","9:45 Pm"));

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}