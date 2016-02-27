package dev.larueinfo.alignlabsbenin.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.afollestad.materialdialogs.MaterialDialog;

import dev.larueinfo.alignlabsbenin.R;

/**
 * @author Seth-Pharès Gnavo (sethgnavo)
 */
public class HelpDialog extends DialogFragment {

    public static void show(AppCompatActivity context) {
        HelpDialog dialog = new HelpDialog();
        dialog.show(context.getSupportFragmentManager(), "[HELP_DIALOG]");

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new MaterialDialog.Builder(getActivity())
                // .iconRes(R.drawable.ic_launcher)
                .title("Aide")
                .content(Html.fromHtml(getString(R.string.dialog_help)))
                .positiveText("ok")
                .contentLineSpacing(1.6f)
                .build();
    }
}
