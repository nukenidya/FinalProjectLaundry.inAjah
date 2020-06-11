package com.example.laundryin;

import android.content.Intent;
import android.os.Bundle;

import com.example.laundryin.Common.Common;
import com.example.laundryin.Interface.ItemClickListener;
import com.example.laundryin.Model.Category;
import com.example.laundryin.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.service.dreams.DreamService;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference categoty;

    TextView txtFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Katalog");
        setSupportActionBar(toolbar);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        categoty = database.getReference("Category");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carIntent = new Intent(Home.this, Keranjang.class);
                startActivity(carIntent);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(Home.this, "barhasil", Toast.LENGTH_SHORT).show();
           }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawer, toolbar, R.string.OpenNavigatorDrawer, R.string.CloseNavigatorDrawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //mAppBarConfiguration = new AppBarConfiguration.Builder(
         //       R.id.nav_katalog, R.id.nav_keranjang, R.id.nav_pesan, R.id.nav_keluar)
         //       .setDrawerLayout(drawer)
         //       .build();


        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //setupWithNavController(navigationView, navController);

        //Set Name fot User
        View header = navigationView.getHeaderView(0);
        txtFullName = header.findViewById(R.id.txtFullName);
        //Load Menu
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        
        loadMenu();
    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item, MenuViewHolder.class, categoty) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtKatalogName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);

                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent katalogList = new Intent(Home.this, KatalogList.class);
                        katalogList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(katalogList);
                        //Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_katalog) {

        } else if (id == R.id.nav_keranjang) {
            Intent keranjangIntent = new Intent(Home.this, Keranjang.class);
            startActivity(keranjangIntent);

        } else if (id == R.id.nav_pesan) {
            Intent pesanIntent = new Intent(Home.this, PesanStatus.class);
            startActivity(pesanIntent);

        } else if (id == R.id.nav_keluar) {
            Intent SignInIntent = new Intent(Home.this, SignIn.class);
            SignInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(SignInIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
