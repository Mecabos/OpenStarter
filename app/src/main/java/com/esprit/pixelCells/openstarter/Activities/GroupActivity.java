package com.esprit.pixelCells.openstarter.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.pixelCells.openstarter.Adapters.CollaborationGroupSpinnerAdapter;
import com.esprit.pixelCells.openstarter.Data.DataSuppliers.CollaborationGroupServer;
import com.esprit.pixelCells.openstarter.Helpers.DialogHelper;
import com.esprit.pixelCells.openstarter.Models.CollaborationGroup;
import com.esprit.pixelCells.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.vlstr.blurdialog.BlurDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    private DialogHelper dialogHelper;
    private BlurDialog blurDialog;

    private boolean isAdminForSelectedGroup=false;

    Button bt_members, bt_editGroup, bt_addGroup;
    TextView tv_name, tv_creationDate, tv_projectCount, tv_countMembers;
    private Spinner collaborationGroupSpinner;
    Long collaborationGroupSelectedId;
    public static GroupActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        dialogHelper = new DialogHelper() ;
        instance = this ;
        blurDialog = findViewById(R.id.blurLoader);
        dialogHelper.blurDialogShow(instance,blurDialog,"Loading group info");

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        collaborationGroupSpinner = findViewById(R.id.groupList);
        collaborationGroupSelectedId = 0L;

        bt_members = findViewById(R.id.bt_members);
        bt_editGroup = findViewById(R.id.bt_editGroup);
        bt_addGroup = findViewById(R.id.bt_addGroup);
        tv_name = findViewById(R.id.groupName);
        tv_projectCount = findViewById(R.id.projectsCount);
        tv_creationDate = findViewById(R.id.creationDate);
        tv_countMembers = findViewById(R.id.countMembers);

        bt_addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog alertDialog = new AlertDialog.Builder(GroupActivity.this).create();
                final EditText groupName = new EditText(GroupActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                groupName.setLayoutParams(lp);
                alertDialog.setView(groupName);
                alertDialog.setTitle("New Collaboration group");
                alertDialog.setMessage("pick a name for your collaboration group, it must be unique !");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE,"DONE",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogHelper.blurDialogShow(instance,blurDialog,"Creating group");
                        CollaborationGroupServer collaborationGroupDs = new CollaborationGroupServer();
                        collaborationGroupDs.addGroup(groupName.getText().toString(), firebaseAuth.getCurrentUser().getEmail(), new CollaborationGroupServer.CallbackAdd() {
                            @Override
                            public void onSuccess() {
                                dialogHelper.blurDialogHide(instance,blurDialog);
                                alertDialog.hide();
                            }

                            @Override
                            public void onFail() {
                                dialogHelper.blurDialogHide(instance,blurDialog);
                                Toast.makeText(GroupActivity.this, "could not load group info", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });

                    }
                });
                alertDialog.show();

            }
        });

        bt_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(GroupActivity.this)
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
                alert.show();*/

                if(isAdminForSelectedGroup){
                    Intent i1 = new Intent(GroupActivity.this, ManageMembersActivity.class);
                    i1.putExtra("groupName",tv_name.getText().toString());
                    startActivity(i1);
                }
                else
                    Toast.makeText(GroupActivity.this,"admin privileges requiered",Toast.LENGTH_LONG).show();

            }
        });

        bt_editGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : refresh
                if(isAdminForSelectedGroup){
                    Intent i2 = new Intent(GroupActivity.this, EditGroupActivity.class);
                    i2.putExtra("groupName",tv_name.getText().toString());
                    startActivity(i2);
                }
                else
                    Toast.makeText(GroupActivity.this,"admin privileges requiered",Toast.LENGTH_LONG).show();

            }
        });


        CollaborationGroupServer collaborationGroupDs = new CollaborationGroupServer();
        collaborationGroupDs.collaborationGroupGetByUser(firebaseAuth.getCurrentUser().getEmail(), new CollaborationGroupServer.CallbackGetByUser() {
            @Override
            public void onSuccessGet(final List<CollaborationGroup> groupsList) {

                dialogHelper.blurDialogHide(instance,blurDialog);

                if (groupsList.size() > 0){


                    CollaborationGroup firstGroup = groupsList.get(0);
                    tv_name.setText(firstGroup.getName());
                    tv_projectCount.setText(Integer.toString(firstGroup.getProjectsCount()));
                    tv_countMembers.setText(Integer.toString(firstGroup.getCountMembers()));
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String creationDate = df.format(firstGroup.getCreationDate());
                    tv_creationDate.setText(creationDate);

                    isAdminForSelectedGroup = firstGroup.isUserAdmin();


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
                            CollaborationGroup selectedGroup = groupsList.get(pos);
                            tv_name.setText(selectedGroup.getName());
                            tv_projectCount.setText(Integer.toString(selectedGroup.getProjectsCount()));
                            tv_countMembers.setText(Integer.toString(selectedGroup.getCountMembers()));
                            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                            String creationDate = df.format(selectedGroup.getCreationDate());
                            tv_creationDate.setText(creationDate);

                            isAdminForSelectedGroup = selectedGroup.isUserAdmin();
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
                finish();
                Toast.makeText(GroupActivity.this, "could not reach server", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
