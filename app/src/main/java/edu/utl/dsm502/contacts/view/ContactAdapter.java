package edu.utl.dsm502.contacts.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.utl.dsm502.contacts.databinding.CardViewContactBinding;
import edu.utl.dsm502.contacts.model.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>
            implements View.OnClickListener{
    private List<Contact> listContacts;
    private View.OnClickListener listener;

    public void updateContactList(List<Contact> itemsList){
        this.listContacts = itemsList;
    }

    @Override
    public int getItemCount(){
        return listContacts.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardViewContactBinding cardViewContactBinding = CardViewContactBinding.inflate(layoutInflater, parent, false);
        cardViewContactBinding.cardViewContact.setOnClickListener(this);
        return new ViewHolder(cardViewContactBinding);
    }

    @Override
    public void onBindViewHolder(final ContactAdapter.ViewHolder holder, final int position){
        holder.bindData(listContacts.get(position));
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public void setItems(List<Contact> items){
        listContacts = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardViewContactBinding binding;

        ViewHolder(CardViewContactBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindData(final Contact item){
            //binding.txtIdItemCardView.setText(item.getId());
            binding.txtNameCardView.setText(item.getName());
        }
    }
}