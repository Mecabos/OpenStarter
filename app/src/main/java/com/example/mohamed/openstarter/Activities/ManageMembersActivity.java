package com.example.mohamed.openstarter.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mohamed.openstarter.Data.DataSuppliers.MembershipDs;
import com.example.mohamed.openstarter.Helpers.DialogHelper;
import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.R;
import com.vlstr.blurdialog.BlurDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageMembersActivity extends AppCompatActivity {

    ListView lv;
    Button bt_addMember;
    private DialogHelper dialogHelper;
    BlurDialog blurDialog;
    public static ManageMembersActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_members);

        blurDialog = findViewById(R.id.blurLoader);
        bt_addMember = findViewById(R.id.bt_addMember);
        dialogHelper = new DialogHelper();
        instance = this;
        final MembershipDs membershipDs = new MembershipDs();
        final String groupName = getIntent().getStringExtra("groupName");

        membershipDs.userGetByGroupName(groupName, new MembershipDs.CallbackGetByName() {
            @Override
            public void onSuccessGet(final List<User> result) {

                List<HashMap<String, String>> liste = new ArrayList<>();


                HashMap<String, String> hash;
                for (User u : result) {
                    hash = new HashMap<>();
                    hash.put("fullname", u.getFirstName() + " " + u.getLastName());
                    //hash.put("id",Long.toString(u.getId()));
                    liste.add(hash);
                }

                SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), liste, R.layout.useritem, new String[]{"fullname"}, new int[]{R.id.name});

                lv = findViewById(R.id.listMembers);
                lv.setAdapter(simpleAdapter);
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                   final int pos, final long id) {

                        Log.v("long clicked", "pos: " + pos);
                        AlertDialog.Builder builder;

                        builder = new AlertDialog.Builder(ManageMembersActivity.this, android.R.style.Theme_Material_Dialog_Alert);

                        builder.setTitle("Delete member")
                                .setMessage("are you sure ?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        membershipDs.deleteMembership(groupName, (int) id, new MembershipDs.CallbackDelete() {
                                            @Override
                                            public void onSuccess() {
                                                Toast.makeText(ManageMembersActivity.this, "member deleted", Toast.LENGTH_LONG).show();

                                            }

                                            @Override
                                            public void onFail() {
                                                Toast.makeText(ManageMembersActivity.this, "failed", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                        return true;
                    }
                });

            }

            @Override
            public void onFail() {
                Log.d("userlist", "failed");
            }
        });

        bt_addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(ManageMembersActivity.this).create();
                final EditText email = new EditText(ManageMembersActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                email.setLayoutParams(lp);
                alertDialog.setView(email);
                alertDialog.setTitle("New Member");
                alertDialog.setMessage("add a new member to the group");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "DONE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogHelper.blurDialogShow(instance, blurDialog, "Adding member");
                        membershipDs.addMembership(groupName, email.getText().toString(),  0, new MembershipDs.CallbackAdd() {
                            @Override
                            public void onSuccess() {
                                dialogHelper.blurDialogHide(instance, blurDialog);
                                alertDialog.hide();
                            }

                            @Override
                            public void onFail() {
                                Log.d("memberadd",groupName);
                                Log.d("memberadd",email.getText().toString());
                                dialogHelper.blurDialogHide(instance, blurDialog);
                                Toast.makeText(ManageMembersActivity.this, "failed", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
                alertDialog.show();
            }
        });


    }
}
