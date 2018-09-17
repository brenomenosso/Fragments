package com.example.dev22.fragmentsexample.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev22.fragmentsexample.R;
import com.example.dev22.fragmentsexample.controllers.AlunoController;
import com.example.dev22.fragmentsexample.model.Aluno;
import com.example.dev22.fragmentsexample.parameters.Parameter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

public class AlunoFragment extends Fragment {

    public static final String EXTRA_ALUNO = "EXTRA_ALUNO";

    private AppCompatEditText editNome;
    private AppCompatEditText editEndereco;
    private AppCompatEditText editNumero;
    private AppCompatEditText editCep;
    private AppCompatEditText editCidade;
    private AppCompatEditText editEstado;
    private AppCompatEditText editComplemento;
    private Aluno aluno;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_aluno, container, false);
        getActivity().setTitle("Cadastro Alunos");
        instanceMethods(view);
        implementsMethods();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMethods();
    }

    private void instanceMethods(View view) {

        editNome = view.findViewById(R.id.editNomeAluno);
        editEndereco = view.findViewById(R.id.editEnderecoAluno);
        editNumero = view.findViewById(R.id.editNumeroAluno);
        editCep = view.findViewById(R.id.editCepAluno);
        editCidade = view.findViewById(R.id.editCidadeAluno);
        editEstado = view.findViewById(R.id.editEstadoAluno);
        editComplemento = view.findViewById(R.id.editComplementoAluno);

    }

    private void implementsMethods() {

        editCep.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (!TextUtils.isEmpty(editCep.getText()) && editCep.getText().toString().length() == 8) {
                        new AsyncCep().execute(editCep.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });
     }

    private void initMethods() {

        if (getArguments() != null && getArguments().containsKey(EXTRA_ALUNO)) {
            aluno = (Aluno) getArguments().getSerializable(EXTRA_ALUNO);
            editNome.setText(aluno.getNome());
            editEndereco.setText(aluno.getEndereco());
            editNumero.setText(aluno.getNumero());
            editCep.setText(aluno.getCep());
            editCidade.setText(aluno.getCidade());
            editEstado.setText(aluno.getEstado());
            editComplemento.setText(aluno.getComplemento());
        } else {
            aluno = new Aluno();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cadastro, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.item_salvar):
                if (verificaCampos()) {
                    aluno.setNome(editNome.getText().toString());
                    aluno.setCep(editCep.getText().toString());
                    aluno.setEndereco(editEndereco.getText().toString());
                    aluno.setCidade(editCidade.getText().toString());
                    aluno.setEstado(editEstado.getText().toString());
                    aluno.setNumero(editNumero.getText().toString());
                    aluno.setComplemento(editComplemento.getText().toString());

                    if (AlunoController.getInstance().save(aluno)) {
                        Toast.makeText(getActivity(), "Aluno salvo com sucesso!", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new ListaAlunoFragment()).commit();
                    }

                } else {
                    Toast.makeText(getActivity(), "Erro ao salvar aluno!", Toast.LENGTH_LONG).show();
                }
                return true;
            case (R.id.item_deletar):
                if (AlunoController.getInstance().delete(aluno)) {
                    Toast.makeText(getActivity(), "Aluno deletado com sucesso!", Toast.LENGTH_LONG).show();
                    ListaAlunoFragment lista = new ListaAlunoFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, lista).commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class AsyncCep extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpURLConnection conn = (HttpURLConnection)
                        new URL(String.format(Parameter.URL_CEP, strings[0])).openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    editEndereco.setText(jsonObject.getString(Parameter.PARAM_ENDERECO));
                    editCidade.setText(jsonObject.getString(Parameter.PARAM_CIDADE));
                    editEstado.setText(jsonObject.getString(Parameter.PARAM_ESTADO));
                    editComplemento.setText(jsonObject.getString(Parameter.PARAM_COMPLEMENTO));
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Cep inválido ou não existente" , Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Erro de conexão", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }

    public boolean verificaCampos() {

        editNome.setError(null);
        editEndereco.setError(null);
        editCep.setError(null);
        editCidade.setError(null);
        editEstado.setError(null);

        if (TextUtils.isEmpty(editNome.getText())) {
            editNome.setError(("Informe o campo \"Nome\""));
            editNome.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editEndereco.getText())) {
            editEndereco.setError(("Informe o campo \"Endereço\""));
            editEndereco.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editCep.getText())) {
            editCep.setError(("Informe o campo \"Cep\""));
            editCep.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editCidade.getText())) {
            editCep.setError(("Informe o campo \"Cidade\""));
            editCep.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editEstado.getText())) {
            editEstado.setError(("Informe o campo \"Estado\""));
            editEstado.requestFocus();
            return false;
        }
        return true;
    }

}

