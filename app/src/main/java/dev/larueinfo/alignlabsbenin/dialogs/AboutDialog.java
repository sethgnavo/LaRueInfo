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
 * @author Seth-Pharès Gnavo
 */
public class AboutDialog extends DialogFragment {

    public static void show(AppCompatActivity context) {
        AboutDialog dialog = new AboutDialog();
        dialog.show(context.getSupportFragmentManager(), "[ABOUT_DIALOG]");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new MaterialDialog.Builder(getActivity())
                .iconRes(R.drawable.ic_launcher)
                .title("A propos")
                .content(Html.fromHtml(getString(R.string.dialog_about)))
                .positiveText("ok")
                .contentLineSpacing(1.6f)
                .build();
    }
}
