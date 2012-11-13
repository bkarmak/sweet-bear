package com.wanhu.android.shelves.controls;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.business.BusinessNote;
import com.wanhu.android.shelves.model.ModelNote;

public class ShowNotesPopupWindow extends PopupWindowBetter implements
		android.view.View.OnClickListener {

	private String mBookId;
	private Context mContext;
	private BusinessNote mBusinessNote;
	private ModelNote mModelNote;

	private Button btnOk;
	private Button btnCancel;
	private ViewGroup mRoot;
	private EditText etNote;

	public ShowNotesPopupWindow(View anchor, String pBookId) {
		super(anchor);
		mBookId = pBookId;

		initVariable();
		initView();
		initListeners();
	}

	@Override
	protected void onCreate() {

		mContext = this.anchor.getContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRoot = (ViewGroup) inflater.inflate(R.layout.popup_notes, null);
		this.setContentView(mRoot);

	}

	private void initVariable() {
		mBusinessNote = new BusinessNote(mContext);
		mModelNote = mBusinessNote.getModelNoteByBookID(mBookId);
	}

	private void initView() {
		btnOk = (Button) mRoot.findViewById(R.id.btnOk);
		btnCancel = (Button) mRoot.findViewById(R.id.btnCancel);
		etNote = (EditText) mRoot.findViewById(R.id.etNote);
		if (mModelNote != null) {
			etNote.setText(mModelNote.getContentFr());
		}
	}

	private void initListeners() {
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOk:
			if (mModelNote == null) {
				String _content = etNote.getText().toString();
				if (!TextUtils.isEmpty(_content)) {
					mModelNote = new ModelNote();
					mModelNote.setBookID(mBookId);
					mModelNote.setContentFr(_content);

					mBusinessNote.insertNote(mModelNote);

					Toast.makeText(mContext, R.string.add_note_successfully,
							Toast.LENGTH_SHORT).show();
				}

			} else {
				mBusinessNote.updateUserByBookID(mModelNote);
				Toast.makeText(mContext, R.string.update_note_successfully,
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnCancel:
			break;

		default:
			break;
		}

		dismiss();

	}
}
