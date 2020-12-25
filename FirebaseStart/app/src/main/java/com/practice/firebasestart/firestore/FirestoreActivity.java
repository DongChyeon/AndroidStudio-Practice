package com.practice.firebasestart.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.practice.firebasestart.R;

import java.util.HashMap;
import java.util.Map;

public class FirestoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);

        Button adddatabtn = findViewById(R.id.button);
        adddatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        Button setdatabtn = findViewById(R.id.button2);
        setdatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });

        Button deletedocbtn = findViewById(R.id.button3);
        deletedocbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDoc();
            }
        });

        Button deletefieldbtn = findViewById(R.id.button4);
        deletefieldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteField();
           }
        });

        Button selectdocbtn = findViewById(R.id.button5);
        selectdocbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               selectDoc();
           }
        });

        Button selectwheredocbtn = findViewById(R.id.button6);
        selectwheredocbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectWhereDoc();
            }
        });

        Button listenerdocbtn = findViewById(R.id.button7);
        listenerdocbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listenerDoc();
           }
        });

        Button listenerquerybtn = findViewById(R.id.button8);
        listenerquerybtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listenerQueryDoc();
           }
        });
    }

    private void addData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> member = new HashMap<>();
        member.put("name", "홍길동");
        member.put("address", "수원시");
        member.put("age", 25);
        member.put("id", "hong");
        member.put("pw", "hello!");

        db.collection("users")
                .add(member)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("kimdonghyeon", "Document ID = " + documentReference.get());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("kimdonghyeon", "Document Error!!");
                    }
                });
    }

    private void setData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> member = new HashMap<>();
        member.put("name", "나야나");
        member.put("address", "경기도");
        member.put("age", 25);
        member.put("id", "my");
        member.put("pw", "hello!");

        db.collection("users")
                .document("userinfo")
                .set(member)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("kimdonghyeon", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("kimdonghyeon", "Document Error!!");
                    }
                });
    }

    private void deleteDoc() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document("userinfo")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("kimdonghyeon", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("kimdonghyeon", "Error deleting document", e);
                    }
                });
    }

    private void deleteField() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("users").document("userinfo");

        Map<String, Object> updates = new HashMap<>();
        updates.put("address", FieldValue.delete());

        docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("kimdonghyeon", "DocumentSnapshot successfully deleted!");
            }
        });
    }

    private void selectDoc() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document("userinfo");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("kimdonghyeon", "DocumentSnapshot data: " + document.getData());

                        UserInfo userInfo = document.toObject(UserInfo.class);
                        Log.d("kimdonghyeon", "name = " + userInfo.getName());
                        Log.d("kimdonghyeon", "address = " + userInfo.getAddress());
                        Log.d("kimdonghyeon", "id = " + userInfo.getId());
                        Log.d("kimdonghyeon", "pw = " + userInfo.getPw());
                        Log.d("kimdonghyeon", "age = " + userInfo.getAge());
                    }
                    else {
                        Log.d("kimdonghyeon", "No such document");
                    }
                }
                else {
                    Log.d("kimdonghyeon", "get failed with ", task.getException());
                }
            }
        });
    }

    private void selectWhereDoc() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("age", 25)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("kimdonghyeon", document.getId() + " => " + document.getData());

                                UserInfo userInfo = document.toObject(UserInfo.class);
                                Log.d("kimdonghyeon", "name = " + userInfo.getName());
                                Log.d("kimdonghyeon", "address = " + userInfo.getAddress());
                                Log.d("kimdonghyeon", "id = " + userInfo.getId());
                                Log.d("kimdonghyeon", "pw = " + userInfo.getPw());
                                Log.d("kimdonghyeon", "age = " + userInfo.getAge());
                            }
                        }
                        else {
                            Log.d("kimdonghyeon", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void listenerDoc() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("users").document("userinfo");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("kimdonghyeon", "Listen failed.", error);
                    return;
                }

                if (value != null && value.exists()) {
                    Log.d("kimdonghyeon", "Current data: " + value.getData());
                }
                else {
                    Log.d("kimdonghyeon", "Current data: null");
                }
            }
        });
    }

    private void listenerQueryDoc() {
        Log.d("kimdonghyeon", "listenerQueryDoc in");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("id", "hong")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Log.d("kimdonghyeon", "listenerQueryDoc in 1");

                        if (error != null) {
                            Log.w("kimdonghyeon", "listen:error", error);
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            Log.d("kimdonghyeon", "listenerQueryDoc dc.getType() = " + dc.getType());
                            switch(dc.getType()) {
                                case ADDED:
                                    Log.d("kimdonghyeon", "New city : " + dc.getDocument().getData());
                                    break;
                                case MODIFIED:
                                    Log.d("kimdonghyeon", "Modified city : " +  dc.getDocument().getData());
                                    break;
                                case REMOVED:
                                    Log.d("kimdonghyeon", "Removed city : " +  dc.getDocument().getData());
                                    break;
                            }
                        }
                    }
                });
    }
}
