package com.example.listadetarefas;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listadetarefas.db.TaskContract;
import com.example.listadetarefas.db.TaskDBHelper;


public class MainActivity extends AppCompatActivity {

    private TaskDBHelper helper;

    private Button btAdd, btRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUI();

        btAdd = findViewById(R.id.button);


        btAdd.setOnClickListener(v -> {
            adicionarItem();
        });
    }


    private void updateUI() {
        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TAREFA},
                null,null,null,null,null);

        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.card,
                cursor,
                new String[] { TaskContract.Columns.TAREFA},
                new int[] { R.id.text_card},
                0
        );
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdapter);
    }





    public void adicionarItem(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicione uma Tarefa");
        builder.setMessage("O que você precisa fazer?");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);


        builder.setPositiveButton("Adicionar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tarefa = inputField.getText().toString();
                        Log.d("MainActivity", tarefa);

                        helper = new com.example.listadetarefas.db.TaskDBHelper(MainActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.clear();
                        values.put(com.example.listadetarefas.db.TaskContract.Columns.TAREFA, tarefa);

                        db.insertWithOnConflict(com.example.listadetarefas.db.TaskContract.TABLE, null, values,
                                SQLiteDatabase.CONFLICT_IGNORE);

                        updateUI();

                    }
                });

        builder.setNegativeButton("Cancelar",null);

        builder.create().show();
    }

    public void apagarItem(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.text_card);
        String tarefa = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE,
                TaskContract.Columns.TAREFA,
                tarefa);


        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

}