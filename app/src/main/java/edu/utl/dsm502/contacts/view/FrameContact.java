package edu.utl.dsm502.contacts.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.utl.dsm502.contacts.databinding.FragmentFrameContactBinding;
import edu.utl.dsm502.contacts.model.Contact;

public class FrameContact extends Fragment {

    private FragmentFrameContactBinding binding;
    ContactAdapter contactAdapter;
    Contact cGeneral;
    List<Contact> listContacts = new ArrayList<>();
    FirebaseFirestore db;

    public FrameContact() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrameContactBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        db = FirebaseFirestore.getInstance();

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd = new Intent(getActivity(), AddContact.class);
                cGeneral = new Contact();
                cGeneral.setId(0);
                intentAdd.putExtra("contact", (Serializable) cGeneral);
                intentAdd.putExtra("sizeListC", listContacts.size());
                startActivity(intentAdd);
            }
        });
        extractData();
        loadContacts();
        binding.editTextTextPersonName.setSelection(0);
        return view;
    }

    public void extractData(){
        db.collection("contacts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String objData = String.valueOf(document.getData());
                                Gson gson = new Gson();
                                Contact objContactGson = gson.fromJson(objData, Contact.class);
                                listContacts.add(objContactGson);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error al cargar documentos", Toast.LENGTH_SHORT).show();
                            System.out.println(task.getException());
                        }
                    }
                });

    }

    private void loadContacts(){
        contactAdapter = new ContactAdapter();
        contactAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = listContacts.get(binding.recyclerViewContacts.getChildAdapterPosition(v)).getId();
                Intent intentAdd = new Intent(getActivity(), AddContact.class);
                cGeneral = new Contact();
                cGeneral.setId(id);
                intentAdd.putExtra("contact", (Serializable) cGeneral);
                intentAdd.putExtra("sizeListC", listContacts.size());
                startActivity(intentAdd);
            }
        });

        contactAdapter.updateContactList(listContacts);
        binding.recyclerViewContacts.setAdapter(contactAdapter);
        binding.recyclerViewContacts.setHasFixedSize(true);
        binding.recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewContacts.setAdapter(contactAdapter);

    }
}
