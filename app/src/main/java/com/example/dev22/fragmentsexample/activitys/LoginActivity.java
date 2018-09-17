package com.example.dev22.fragmentsexample.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.dev22.fragmentsexample.R;
import com.example.dev22.fragmentsexample.controllers.ProfessorController;

public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText editLogin;
    private AppCompatEditText editSenha;
    private AppCompatButton btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        instanceMethods();
        implementsMethods();
    }

    private void instanceMethods() {

        editLogin = findViewById(R.id.editLogin);
        editSenha = findViewById(R.id.editSenha);
        btnLogar = findViewById(R.id.buttonAcessar);

    }

    private void implementsMethods() {

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaLogin()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

        editLogin.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                    hideKeyboard(LoginActivity.this);
            }
        });

    }

    public static void hideKeyboard(LoginActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean verificaLogin() {

        editLogin.setError(null);
        editSenha.setError(null);

        if (TextUtils.isEmpty(editLogin.getText())) {
            editLogin.setError(getString(R.string.erro_campo_obrigatorio, getString(R.string.usuario)));
            editLogin.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editSenha.getText())) {
            editSenha.setError(getString(R.string.erro_campo_obrigatorio, getString(R.string.senha)));
            editSenha.requestFocus();
            return false;
        } else if (!ProfessorController.getInstance().checkLogin(
                editLogin.getText().toString(), editSenha.getText().toString())) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.baseline_warning_24)
                    .setTitle(R.string.titulo_erro)
                    .setMessage(R.string.erro_acesso)
                    .setPositiveButton(R.string.confirmar, null)
                    .show();
            return false;
        }
        return true;
    }

}



