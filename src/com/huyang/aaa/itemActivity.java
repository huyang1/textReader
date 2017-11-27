package com.huyang.aaa;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class itemActivity extends Activity {
    private EditText edit;
    private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item);
		intent=getIntent();
        String title=intent.getStringExtra("title");
        String content=intent.getStringExtra("content");
        intent.getExtras().clear();
        intent.putExtra("message", content);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(title);
        edit=(EditText) findViewById(R.id.edit);
        edit.setText(content);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() { 
		    @Override
		    public void onClick(View v) {
			     // TODO Auto-generated method stub
			     String message=edit.getText().toString();
			     intent.putExtra("message", message);
			     itemActivity.this.setResult(8,intent);
			     itemActivity.this.finish();
		    }
	    });
	}

}
