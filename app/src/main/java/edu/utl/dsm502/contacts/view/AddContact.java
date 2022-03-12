package edu.utl.dsm502.contacts.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.BitmapCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utl.dsm502.contacts.databinding.ActivityAddContactBinding;
import edu.utl.dsm502.contacts.databinding.ActivityMainBinding;
import edu.utl.dsm502.contacts.model.Contact;


public class AddContact extends AppCompatActivity {

    private ActivityAddContactBinding binding;
    int idContact;
    FirebaseFirestore db;
    String idLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Contact c = (Contact) getIntent().getSerializableExtra("contact");
        int sizeListC = (int) getIntent().getSerializableExtra("sizeListC");
        if (c.getId() == 0){
            idContact = sizeListC + 1;
        }else{
            idContact = c.getId();
        }

        db = FirebaseFirestore.getInstance();
        loadData();
        binding.btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idContact == (sizeListC + 1)) {
                    add();
                }else{
                    update();
                }
            }
        });

        binding.btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.collection("contacts").document(idLoad).delete();
                    goList();
                } catch (Exception e) {
                    Toast.makeText(getApplication(), "Error al eliminar contacto", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    public void add() {
        Map<String, Object> contacts = new HashMap<>();
        contacts.put("id", idContact);
        contacts.put("name", binding.txtNameAddContact.getText().toString());
        contacts.put("number", binding.txtNumberAddContact.getText().toString());
        contacts.put("eMail", binding.txtEmailAddContact.getText().toString());
        contacts.put("address", binding.txtAddressAddContact.getText().toString());
        contacts.put("photography", "null");

        db.collection("contacts")
                .add(contacts)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplication(), "Se ha guadrdado con exito", Toast.LENGTH_SHORT).show();
                        goList();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplication(), "Error al agregar", Toast.LENGTH_SHORT).show();
                        System.out.println("Error: " + e);
                    }
                });
    }

    public void loadData(){
        db.collection("contacts")
                .whereEqualTo("id", idContact)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String objData = String.valueOf(document.getData());
                                Gson gson = new Gson();
                                Contact objContactGson = gson.fromJson(objData, Contact.class);
                                binding.txtNameAddContact.setText(objContactGson.getName());
                                binding.txtNumberAddContact.setText(objContactGson.getNumber());
                                binding.txtEmailAddContact.setText(objContactGson.geteMail());
                                binding.txtAddressAddContact.setText(objContactGson.getAddress());
                                idLoad = document.getId();
                            }
                        } else {
                            Toast.makeText(getApplication(), "Error al cargar contacto", Toast.LENGTH_SHORT).show();
                            System.out.println(task.getException());
                        }
                    }
                });
    }

    public void update(){
        Map<String, Object> contacts = new HashMap<>();
        contacts.put("id", idContact);
        contacts.put("name", binding.txtNameAddContact.getText().toString());
        contacts.put("number", binding.txtNumberAddContact.getText().toString());
        contacts.put("eMail", binding.txtEmailAddContact.getText().toString());
        contacts.put("address", binding.txtAddressAddContact.getText().toString());
        contacts.put("photography", "null");

        try {
            db.collection("contacts")
                    .document(idLoad)
                    .update(contacts);
            Toast.makeText(getApplication(), "Se ha actualizado correctamente contacto", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error al actualizar contacto", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void goList(){
        binding.txtNameAddContact.setText("");
        binding.txtNumberAddContact.setText("");
        binding.txtEmailAddContact.setText("");
        binding.txtAddressAddContact.setText("");
        Intent intentList = new Intent(AddContact.this, MainActivity.class);
        startActivity(intentList);
    }
}