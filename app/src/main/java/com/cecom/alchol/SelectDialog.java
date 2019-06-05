package com.cecom.alchol;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cecom.alchol.model.DrinkList;
public class SelectDialog {

    private Context context;
    LinearLayout mainLayout;
    Button okButton;
    Button cancelButton;
    CheckBox[] checkBoxes;
    EditText[] editTexts;
    String sourceInput = "";
    Dialog dlg;
    SelectDialogListener listener;
    public SelectDialog(Context context) {
        this.context = context;
        dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_user_register_select);
        dlg.setTitle("Choose an element");
        mainLayout = dlg.findViewById(R.id.mainLinearLayout);
        mainLayout.setGravity(Gravity.CENTER);

        LinearLayout buttonLayout = new LinearLayout(dlg.getContext());
        okButton = new Button(dlg.getContext());
        okButton.setText("ok");
        cancelButton = new Button(dlg.getContext());
        cancelButton.setText("cancle");

        buttonLayout.addView(cancelButton);
        buttonLayout.addView(okButton);
        TextView textView = new TextView(dlg.getContext());
        textView.setText("Choose Elements");
        textView.setPadding(20,30,20,30);
        textView.setTextSize(30);
        checkBoxes = new CheckBox[DrinkList.data.length];
        editTexts = new EditText[DrinkList.data.length];

        mainLayout.addView(textView);
        for(int i = 0; i < checkBoxes.length; i++){
            LinearLayout tempLayout = new LinearLayout(dlg.getContext());
            tempLayout.setOrientation(LinearLayout.HORIZONTAL);
            editTexts[i] = new EditText(dlg.getContext());
            editTexts[i].setHint("비율을 입력하세요");
            editTexts[i].setPadding(20,20,20,80);
            checkBoxes[i] = new CheckBox(dlg.getContext());
            checkBoxes[i].setText(DrinkList.data[i]);
            checkBoxes[i].setPadding(20,20,20,80);
            tempLayout.addView(checkBoxes[i]);
            tempLayout.addView(editTexts[i]);
            mainLayout.addView(tempLayout);
        }
        mainLayout.setPadding(50,50,50,50);
        mainLayout.addView(buttonLayout);
    }

    public void setListener(SelectDialogListener listener){
        this.listener = listener;
    }

    public boolean[] showDialog() {

        final boolean[] returnBooleans = new boolean[DrinkList.data.length];
        final int[] ratio = new int[DrinkList.data.length];
        dlg.show();

        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                for (int i =0; i < checkBoxes.length; i++){
                    if(checkBoxes[i].isChecked()){
                        returnBooleans[i] = true;
                        ratio[i] = Integer.parseInt(editTexts[i].getText().toString());
                        Log.d("AVC",String.valueOf(i));
                    }
                    else{
                        returnBooleans[i] = false;
                        ratio[i] = 0;
                    }
                }
                listener.onPositiveClicked(returnBooleans, ratio);
                dlg.dismiss();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });

        return returnBooleans;
    }
}
