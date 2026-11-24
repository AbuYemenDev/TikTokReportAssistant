
package com.example.tiktokreportassistant;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.*;
import android.graphics.Typeface;

public class MainActivity extends Activity {

    EditText urlInput, detailsInput;
    Spinner typeSpinner;
    TextView reportOutput;

    String[] types = {
        "انتحال شخصية",
        "تنمر أو مضايقة",
        "محتوى مسيء",
        "احتيال أو نصب",
        "انتهاك الخصوصية",
        "بلاغ آخر"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 30, 30, 30);

        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);

        TextView title = new TextView(this);
        title.setText("مساعد بلاغات تيك توك");
        title.setTextSize(24);
        title.setTypeface(null, Typeface.BOLD);
        layout.addView(title);

        TextView label = new TextView(this);
        label.setText("اختر نوع البلاغ:");
        layout.addView(label);

        typeSpinner = new Spinner(this);
        typeSpinner.setAdapter(new ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            types
        ));
        layout.addView(typeSpinner);

        urlInput = new EditText(this);
        urlInput.setHint("رابط الحساب أو الفيديو");
        urlInput.setSingleLine(true);
        layout.addView(urlInput);

        detailsInput = new EditText(this);
        detailsInput.setHint("اشرح سبب البلاغ بالتفصيل");
        detailsInput.setMinLines(4);
        detailsInput.setGravity(48);
        layout.addView(detailsInput);

        Button generate = new Button(this);
        generate.setText("إنشاء نص البلاغ");
        layout.addView(generate);

        reportOutput = new TextView(this);
        reportOutput.setTextSize(16);
        reportOutput.setPadding(10, 20, 10, 20);
        layout.addView(reportOutput);

        Button copy = new Button(this);
        copy.setText("نسخ نص البلاغ");
        layout.addView(copy);

        Button share = new Button(this);
        share.setText("مشاركة نص البلاغ");
        layout.addView(share);

        Button help = new Button(this);
        help.setText("فتح مركز مساعدة تيك توك");
        layout.addView(help);

        generate.setOnClickListener(v -> {
            String type = typeSpinner.getSelectedItem().toString();
            String url = urlInput.getText().toString().trim();
            String details = detailsInput.getText().toString().trim();

            String report = "نوع البلاغ: " + type +
                "\n\nالرابط: " + url +
                "\n\nالتفاصيل: " + details +
                "\n\nأرجو مراجعة هذا المحتوى واتخاذ الإجراء المناسب إذا ثبتت مخالفته لإرشادات مجتمع تيك توك.";

            reportOutput.setText(report);
        });

        copy.setOnClickListener(v -> {
            String text = reportOutput.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(this, "أنشئ البلاغ أولاً", Toast.LENGTH_SHORT).show();
                return;
            }
            ClipboardManager clipboard =
                (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText("Report", text));
            Toast.makeText(this, "تم نسخ البلاغ", Toast.LENGTH_SHORT).show();
        });

        share.setOnClickListener(v -> {
            String text = reportOutput.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(this, "أنشئ البلاغ أولاً", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(intent, "مشاركة البلاغ"));
        });

        help.setOnClickListener(v -> {
            Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://support.tiktok.com/")
            );
            startActivity(intent);
        });

        setContentView(scroll);
    }
}
