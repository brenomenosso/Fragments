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
import com.example.dev22.fragmentsexample.controllers.ProfessorController;
import com.example.dev22.fragmentsexample.model.Professor;
import com.example.dev22.fragmentsexample.parameters.Parameter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfessorFragment extends Fragment {

    public static final String EXTRA_PROFESSOR = "EXTRA_PROFESSOR";

    private AppCompatEditText editNome;
    private AppCompatEditText editCep;
    private AppCompatEditText editEndereco;
    private AppCompatEditText editNumero;
    private AppCompatEditText editCidade;
    private AppCompatEditText editEstado;
    private AppCompatEditText editComplemento;
    private AppCompatEditText editEmail;
    private AppCompatEditText editUsuario;
    private AppCompatEditText editSenha;
    private Professor professor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_professor, container, false);
        instanceMethods(view);
        getActivity().setTitle("Cadastro Professor");
        implementsMethods();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMethods();

    }

    private void instanceMethods(View view) {

        editNome = view.findViewById(R.id.editNomeProfessor);
        editCep = view.findViewById(R.id.editCepProfessor);
        editEndereco = view.findViewById(R.id.editEnderecoProfessor);
        editCidade = view.findViewById(R.id.editCidadeProfessor);
        editEstado = view.findViewById(R.id.editEstadoProfessor);
        editNumero = view.findViewById(R.id.editNumeroProfessor);
        editComplemento = view.findViewById(R.id.editComplementoProfessor);
        editEmail = view.findViewById(R.id.editEmailProfessor);
        editUsuario = view.findViewById(R.id.editUsuarioProfessor);
        editSenha = view.findViewById(R.id.editSenhaProfessor);
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

        if (getArguments() != null && getArguments().containsKey(EXTRA_PROFESSOR)) {
            professor = (Professor) getArguments().getSerializable(EXTRA_PROFESSOR);
            editNome.setText(professor.getNome());
            editCep.setText(professor.getCep());
            editEndereco.setText(professor.getEndereco());
            editCidade.setText(professor.getCidade());
            editEstado.setText(professor.getEstado());
            editNumero.setText(professor.getNumero());
            editComplemento.setText(professor.getComplemento());
            editEmail.setText(professor.getEmail());
            editUsuario.setText(professor.getUsuario());
            editSenha.setText(professor.getSenha());
        } else {
            professor = new Professor();
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
                    professor.setNome(editNome.getText().toString());
                    professor.setCep(editCep.getText().toString());
                    professor.setEndereco(editEndereco.getText().toString());
                    professor.setCidade(editCidade.getText().toString());
                    professor.setEstado(editEstado.getText().toString());
                    professor.setNumero(editNumero.getText().toString());
                    professor.setComplemento(editComplemento.getText().toString());
                    professor.setEmail(editEmail.getText().toString());
                    professor.setUsuario(editUsuario.getText().toString());
                    professor.setSenha(editSenha.getText().toString());

                    if (ProfessorController.getInstance().save(professor)) {
                        Toast.makeText(getActivity(), "Professor salvo com sucesso!", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new ListaProfessorFragment()).commit();
                    }

                } else {
                    Toast.makeText(getActivity(), "Erro ao salvar aluno!", Toast.LENGTH_LONG).show();
                }
                return true;
            case (R.id.item_deletar):
                if (ProfessorController.getInstance().delete(professor)) {
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
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), " Erro de conexão", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }

    }

    public boolean verificaCampos() {

        editNome.setError(null);
        editCep.setError(null);
        editEmail.setError(null);
        editUsuario.setError(null);
        editSenha.setError(null);

        if (TextUtils.isEmpty(editNome.getText())) {
            editNome.setError(("Informe o campo \"Nome\""));
            editNome.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editEndereco.getText())) {
            editEndereco.setError(("Informe o campo \"Endereço\""));
            editEndereco.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editEmail.getText())) {
            editCep.setError(("Informe o campo \"Email\""));
            editCep.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editUsuario.getText())) {
            editCep.setError(("Informe o campo \"Usuario\""));
            editCep.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editSenha.getText())) {
            editEstado.setError(("Informe o campo \"Senha\""));
            editEstado.requestFocus();
            return false;
        }
        return true;
    }

}
