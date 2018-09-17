package com.example.dev22.fragmentsexample.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dev22.fragmentsexample.R;
import com.example.dev22.fragmentsexample.controllers.ProfessorController;
import com.example.dev22.fragmentsexample.fragments.ListaAlunoFragment;
import com.example.dev22.fragmentsexample.fragments.ListaMateriaFragment;
import com.example.dev22.fragmentsexample.fragments.ListaNotaFragment;
import com.example.dev22.fragmentsexample.fragments.ListaProfessorFragment;

public class MainActivity extends AppCompatActivity {

    private TextView textNome, textEmail;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instanceMethods();
        implementsMethods();
        initMethods();
    }

    private void instanceMethods() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        textNome = headerLayout.findViewById(R.id.textNome);
        textEmail = headerLayout.findViewById(R.id.textEmail);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void implementsMethods() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_aluno:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new ListaAlunoFragment()).commit();

                        return true;

                    case R.id.nav_professor:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new ListaProfessorFragment()).commit();

                        return true;

                    case R.id.nav_materia:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new ListaMateriaFragment()).commit();

                        return true;

                    case R.id.nav_nota:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new ListaNotaFragment()).commit();
                        return true;

                    case R.id.nav_sair:

                        finish();
                        return true;

                    case R.id.nav_logout:

                        ProfessorController.getInstance().logout();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        return true;

                    default:
                        return false;
                }


            }

        });

    }

    private void initMethods() {
        if (ProfessorController.getInstance().getProfessorLogado() != null) {
            textNome.setText(ProfessorController.getInstance().getProfessorLogado().getNome());
            textEmail.setText(ProfessorController.getInstance().getProfessorLogado().getEmail());
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

}
