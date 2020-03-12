package com.sky.casper.skywalker_new_app.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.sky.casper.skywalker_new_app.R;

public class WarningDialog {
    String message;
    boolean yesOrNo;
    Context context;
    AsyncDialogAnswer response;

    public interface AsyncDialogAnswer{
        void warningResponse(String answ);
    }


    public WarningDialog(Context ctx,String msg,boolean yn,AsyncDialogAnswer r){
        message=msg;
        yesOrNo=yn;
        context=ctx;
        response=r;
    }

    public void showDialog(){
        if(yesOrNo){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String answer="";
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            dialog.dismiss();
                            answer = context.getResources().getString(R.string.yes);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            answer = context.getResources().getString(R.string.no);
                            break;
                    }
                    if(response!=null){
                        response.warningResponse(answer);
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message).setPositiveButton(context.getResources().getString(R.string.yes), dialogClickListener)
                    .setNegativeButton(context.getResources().getString(R.string.no), dialogClickListener).show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(this.context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
