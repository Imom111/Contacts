package edu.utl.dsm502.contacts.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import edu.utl.dsm502.contacts.R;
import edu.utl.dsm502.contacts.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int tabIndex;
    private Fragment frameContact, frameCall;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        frameContact = new FrameContact();
        frameCall = new FrameCall();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutMain,
                        frameContact)
                .commit();
        tabIndex = binding.tabLayoutMain.getSelectedTabPosition();
        loadFragment(tabIndex);
        binding.tabLayoutMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabIndex = binding.tabLayoutMain.getSelectedTabPosition();
                loadFragment(tabIndex);
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadFragment(int index){
        transaction = getSupportFragmentManager().beginTransaction();
        switch (index){
            case 0:
                transaction.replace(binding.frameLayoutMain.getId(), frameContact);
                break;
            case 1:
                transaction.replace(binding.frameLayoutMain.getId(), frameCall);
                break;
            default:
                Toast.makeText(MainActivity.this,
                        "Intenta nuevamente",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        transaction.commit();
        binding.tabLayoutMain.getTabAt(index).select();;
    }
}
