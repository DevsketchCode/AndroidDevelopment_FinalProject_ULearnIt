package edu.cvtc.doberlander.ulearnit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.cvtc.doberlander.ulearnit.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "edu.cvtc.doberlander.ulearnit.extra.MESSAGE";
    private String category;


    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToast("Take Test");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_favorites:
                Intent intent = new Intent(this, CategoryActivity.class);
                category = getString(R.string.action_favorites);
                intent.putExtra(EXTRA_MESSAGE, category);
                startActivity(intent);
                return true;
            case R.id.action_contact:
                displayToast(getString(R.string.action_contact_message));
                return true;
            default:
                // Do Nothing
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Launch the Category Activity along with the Greetings text
     */
    public void launchGreetingsCategoryActivity(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("category", getString(R.string.greetings_category_text));
        bundle.putString("category_message", getString(R.string.greetings_category_image));

        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Launch the Category Activity along with the Numbers text
     */
    public void launchNumbersCategoryActivity(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("category", getString(R.string.numbers_category_text));
        bundle.putString("category_message", getString(R.string.numbers_category_image));

        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}