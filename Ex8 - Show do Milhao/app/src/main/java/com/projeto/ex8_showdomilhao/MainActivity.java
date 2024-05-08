package com.projeto.ex8_showdomilhao;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar meuPb;

    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    TextView titulo;
    TextView pergunta;
    Button botaoOK;
    ConstraintLayout meuLayout;

    Animation some;
    Animation aparece;

    ArrayList<Questao> questoes;
    int rodada = 0;
    int acertos = 0;
    int resposta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String minhaUrl = "http://www.json-generator.com/api/json/get/bPbDxqHBVe?indent=2";

        radioGroup = findViewById(R.id.meuGrupo);

        meuLayout = findViewById(R.id.meuLayout);

        radioButton1 = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        pergunta = findViewById(R.id.txtPergunta);
        titulo = findViewById(R.id.txtTitulo);
        meuPb = findViewById(R.id.meuPb);
        botaoOK = findViewById(R.id.botaoConfirma);
        botaoOK.setEnabled(false);
        questoes = new ArrayList<Questao>();

        some = new AlphaAnimation(1,0);
        aparece = new AlphaAnimation(0,1);

        some.setDuration(1000);
        aparece.setDuration(1000);

        meuLayout.setVisibility(View.GONE);
        meuLayout.startAnimation(aparece);

        aparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                meuLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                meuPb.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        some.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                meuLayout.setVisibility(View.VISIBLE);
                meuPb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(rodada>=questoes.size()){
                    fimDeJogo();
                }else{
                    atualizaView();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });






        new JsonTask().execute(minhaUrl);



        botaoOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicaProxima();
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radioButton){
                    resposta = 1;
                }
                if(i==R.id.radioButton2){
                    resposta = 2;
                }
                if(i==R.id.radioButton3){
                    resposta = 3;
                }
                botaoOK.setEnabled(true);
            }
        });




    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);
                }
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            Log.i("meuLog",""+result);

            try {
                JSONObject listaJson = new JSONObject(result);
                titulo.setText(listaJson.getString("titulo"));
                JSONArray questionario = listaJson.getJSONArray("questionario");

                for(int i=0; i<questionario.length(); i++){
                    JSONObject questao = questionario.getJSONObject(i);

                    String perg = questao.getString("Pergunta");
                    String ra = questao.getString("respA");
                    String rb = questao.getString("respB");
                    String rc = questao.getString("respC");
                    int correta = questao.getInt("correta");

                    Questao minhaQuestao = new Questao(perg,ra,rb,rc,correta);
                    questoes.add(minhaQuestao);

                }

                atualizaView();

            }catch (JSONException e){e.printStackTrace();}

        }
    }
    void atualizaView(){

        radioGroup.clearCheck();
        pergunta.setText(questoes.get(rodada).getPergunta());
        radioButton1.setText(questoes.get(rodada).getRespA());
        radioButton2.setText(questoes.get(rodada).getRespB());
        radioButton3.setText(questoes.get(rodada).getRespC());
        botaoOK.setEnabled(false);
        meuLayout.startAnimation(aparece);
    }
    void clicaProxima(){
        if(resposta == questoes.get(rodada).getCorreta()){
            acertos++;
        }
        rodada++;
        meuLayout.startAnimation(some);
    }

    void fimDeJogo(){
        meuLayout.setVisibility(View.GONE);
        meuPb.setVisibility(View.GONE);
        criaAlerta();
    }

    void criaAlerta(){
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setTitle("Fim de Jogo");
        alerta.setMessage("Você marcou "+acertos+" pontos");
        alerta.setIcon(R.drawable.moeda);
        alerta.setCancelable(false);

        alerta.setPositiveButton("Jogar Novamente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                acertos = 0;
                rodada = 0;
                atualizaView();
            }
        });

        alerta.create();
        alerta.show();
    }
}

class Questao{

    String pergunta;
    String respA;
    String respB;
    String respC;
    int correta;

    public Questao(String pergunta, String respA, String respB, String respC, int correta) {
        this.pergunta = pergunta;
        this.respA = respA;
        this.respB = respB;
        this.respC = respC;
        this.correta = correta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getRespA() {
        return respA;
    }

    public void setRespA(String respA) {
        this.respA = respA;
    }

    public String getRespB() {
        return respB;
    }

    public void setRespB(String respB) {
        this.respB = respB;
    }

    public String getRespC() {
        return respC;
    }

    public void setRespC(String respC) {
        this.respC = respC;
    }

    public int getCorreta() {
        return correta;
    }

    public void setCorreta(int correta) {
        this.correta = correta;
    }
}
