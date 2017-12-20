package com.example.mohamed.openstarter.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.openstarter.Adapters.CollaborationGroupSpinnerAdapter;
import com.example.mohamed.openstarter.Data.DataSuppliers.CollaborationGroupDs;
import com.example.mohamed.openstarter.Models.CollaborationGroup;
import com.example.mohamed.openstarter.R;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class GroupActivity extends AppCompatActivity {

    Button bt_members, bt_editGroup;
    TextView tv_name, tv_creationDate;
    private Spinner collaborationGroupSpinner;
    private Long collaborationGroupSelectedId;
    public static GroupActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        instance = this ;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        collaborationGroupSpinner = findViewById(R.id.groupList);
        collaborationGroupSelectedId = 0L;

        bt_members = findViewById(R.id.bt_members);
        bt_editGroup = findViewById(R.id.bt_editGroup);
        tv_name = findViewById(R.id.groupName);
        tv_creationDate = findViewById(R.id.creationDate);

        bt_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(GroupActivity.this)
                        //.setImageRecourse(R.drawable.ic_cloud_computing)
                        .setTextTitle("MEMBERS")
                        //.setTextSubTitle("username")
                        .setBody("List of members here")
                        //.setNegativeColor(R.color.colorNegative)
                        .setNegativeButtonText("Later")
                        .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButtonText("Continue")
                        //.setPositiveColor(R.color.colorPositive)
                        .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                //Toast.makeText(ProfilActivity.this, "Updating", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setBodyGravity(FancyAlertDialog.TextGravity.LEFT)
                        .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                        .setSubtitleGravity(FancyAlertDialog.TextGravity.RIGHT)
                        .setCancelable(false)
                        .build();
                alert.show();
            }
        });

        bt_editGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : refresh
                Intent i2 = new Intent(GroupActivity.this, EditGroupActivity.class);
                i2.putExtra("groupName",tv_name.getText().toString());
                startActivity(i2);
            }
        });


        CollaborationGroupDs collaborationGroupDs = new CollaborationGroupDs();
        collaborationGroupDs.collaborationGroupGetByUser(firebaseAuth.getCurrentUser().getEmail(), new CollaborationGroupDs.CallbackGetByUser() {
            @Override
            public void onSuccessGet(final List<CollaborationGroup> groupsList) {

                CollaborationGroup firstGroup = groupsList.get(0);
                tv_name.setText(firstGroup.getName());
                //tv_creationDate.setText(firstGroup.getCreationDate().toString());


                if (groupsList.size() > 0){
                    Log.d("spinnerr","spinner loaded");
                    Log.d("spinnerr",groupsList.toString());
                    CollaborationGroup[] dataArray = new CollaborationGroup[groupsList.size()];
                    collaborationGroupSelectedId=groupsList.get(0).getId();
                    for (int i = 0; i < groupsList.size(); i++  ){
                        dataArray[i]=groupsList.get(i);
                    }

                    final ArrayAdapter<CollaborationGroup> groupSpinnerAdapter = new CollaborationGroupSpinnerAdapter(
                            instance, android.R.layout.simple_spinner_item,dataArray);

                    collaborationGroupSpinner.setAdapter(groupSpinnerAdapter);
                    collaborationGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int pos, long id) {

                            CollaborationGroup selectedCollaborationGroup = (CollaborationGroup) parent.getItemAtPosition(pos);
                            collaborationGroupSelectedId = selectedCollaborationGroup.getId();
                            Toast.makeText(GroupActivity.this, "group selected", Toast.LENGTH_LONG).show();
                            CollaborationGroup firstGroup = groupsList.get(pos);
                            tv_name.setText(firstGroup.getName());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else{
                    Log.d("spinnerr","empty spinner");
                    Toast.makeText(GroupActivity.this, "no groups found", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFail() {

            }
        });
    }
}
