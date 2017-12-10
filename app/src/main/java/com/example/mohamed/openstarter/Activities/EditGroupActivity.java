package com.example.mohamed.openstarter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohamed.openstarter.Data.DataSuppliers.GroupDs;
import com.example.mohamed.openstarter.Helpers.GradientBackgroundPainter;
import com.example.mohamed.openstarter.R;
import com.vlstr.blurdialog.BlurDialog;

public class EditGroupActivity extends AppCompatActivity {

    private GradientBackgroundPainter gradientBackgroundPainter;
    Button update;
    EditText groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        final BlurDialog blurDialog = findViewById(R.id.blurLoader);
        blurDialog.create(getWindow().getDecorView(), 6);
        blurDialog.setTitle("Please wait");

        groupName = findViewById(R.id.groupName);
        update = findViewById(R.id.bt_update_group);

        //background set
        View backgroundImage = findViewById(R.id.bg_view);
        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;

        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (groupName.getText().toString().equals("")) {
                    Toast.makeText(EditGroupActivity.this, "group name can't be empty ", Toast.LENGTH_LONG).show();
                } else {

                    blurDialog.show();
                    GroupDs ds = new GroupDs();
                    String oldGroupName="Legacy team";
                    ds.updateGroup(oldGroupName,groupName.getText().toString(), new GroupDs.CallbackUpdate() {

                        @Override
                        public void onSuccess() {
                            blurDialog.hide();
                            Toast.makeText(EditGroupActivity.this, "Group name updated", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onError() {
                            blurDialog.hide();
                            Toast.makeText(EditGroupActivity.this, "could not reach the server", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gradientBackgroundPainter.stop();
    }

}
