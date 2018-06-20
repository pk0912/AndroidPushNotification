package com.keystone.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class GroupCreation extends AppCompatActivity {

    Button gtype,gcreate,gedit,gdelete;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creation);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        gtype=(Button)findViewById(R.id.bGType);
        gtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), GroupType.class);
                startActivity(i);
            }
        });

        gcreate = (Button) findViewById(R.id.bGCreate);
        gcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), CreateGroup.class);
                startActivity(i);
            }
        });

        gedit=(Button)findViewById(R.id.bGEdit);
        gedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(GroupCreation.this);
                alert.setTitle("Enter group name");
                // alert.setMessage("Email or password is not match");
                final EditText input = new EditText(GroupCreation.this);
                LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(ip);
                alert.setView(input);
                // alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(input.getText().toString());
                        Intent i = new Intent(getApplicationContext(), EditGroup.class);
                        i.putExtra("grpname",input.getText().toString());
                        startActivity(i);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        gdelete=(Button)findViewById(R.id.bGDelete);
        gdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(GroupCreation.this);
                alert.setTitle("Enter group name");
                // alert.setMessage("Email or password is not match");
                final EditText input = new EditText(GroupCreation.this);
                LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(ip);
                alert.setView(input);
                // alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // name = (EditText) findViewById(R.id.eGName);
                        String groupname = input.getText().toString();
                        String url = Config.APP_SERVER_URL + "AdminConfigWebService/deleteGroup" + "/" + groupname;
                        StatusAsync statusasync = new StatusAsync(StatusAsync.GET_TASK,GroupCreation.this,"Delete Group......");
                        String[] data = new String[1];
                        data[0] = url;
                        statusasync.execute(data);


                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });


                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
    }
}
