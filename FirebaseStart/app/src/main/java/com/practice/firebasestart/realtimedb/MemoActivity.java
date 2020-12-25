package com.practice.firebasestart.realtimedb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.firebasestart.R;

import java.util.ArrayList;
import java.util.Random;

public class MemoActivity extends AppCompatActivity implements MemoViewListener {
    private ArrayList<MemoItem> memoItems = null;
    private MemoAdapter memoAdapter = null;
    private String username = null;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        init();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        addChildEvent();
        addValueEventListener();
    }

    @Override
    public void onItemClick(int position, View view) {

    }

    private void init() {
        memoItems = new ArrayList<>();

        username = "user_" + new Random().nextInt(1000);
    }

    private void initView() {
        Button regbtn = findViewById(R.id.button);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regMemo();
            }
        });

        Button userbtn = findViewById(R.id.button2);
        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        memoAdapter = new MemoAdapter(memoItems, this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memoAdapter);
    }

    private void regMemo() {
        EditText titleEdit = findViewById(R.id.editText);
        EditText contentsEdit = findViewById(R.id.editText2);

        if (titleEdit.getText().toString().length() == 0 || contentsEdit.getText().toString().length() == 0) {
            Toast.makeText(this, "메모 제목 또는 메모 내용이 입력되지 않았습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        MemoItem item = new MemoItem();
        item.setUser(this.username);
        item.setMemotitle(titleEdit.getText().toString());
        item.setMemocontents(contentsEdit.getText().toString());

        databaseReference.child("memo").push().setValue(item);
    }

    private void writeNewUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            Log.d("kimdonghyun", "name = " + name);
            Log.d("kimdonghyun", "email = " + email);
            Log.d("kimdonghyun", "uid = " + uid);

            UserInfo userInfo = new UserInfo();
            userInfo.setUserpassword("1234");
            userInfo.setUsername(name);
            userInfo.setEmailaddress(email);

            databaseReference.child("users").child(uid).setValue(userInfo);
        }
        else {
            Log.d("kimdonghyun", "user null");
        }
    }

    private void addChildEvent() {
        databaseReference.child("memo").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("kimdonghyun", "addChildEvent in");
                MemoItem item = snapshot.getValue(MemoItem.class);

                memoItems.add(item);
                memoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String string) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void addValueEventListener() {
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("kimdonghyun", "Value = " + snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
