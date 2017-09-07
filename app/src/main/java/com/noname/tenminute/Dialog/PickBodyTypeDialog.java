package com.noname.tenminute.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.noname.tenminute.Activity.SignupActivity;
import com.noname.tenminute.Adapter.DialogSelectAdapter;
import com.noname.tenminute.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PJC on 2017-09-07.
 */

public class PickBodyTypeDialog extends BaseDialog {

    PickBodyTypeInterface pickBodyTypeInterface;

    @BindView(R.id.lv_list)
    ListView listView;

    DialogSelectAdapter dialogSelectAdapter;

    public interface PickBodyTypeInterface {
        void selectItem(String item);
    }

    public void setPickBodyTypeInterface(PickBodyTypeInterface pickBodyTypeInterface) {
        this.pickBodyTypeInterface = pickBodyTypeInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_pick_bodytype, container, false);
        ButterKnife.bind(this, rootView);

        dialogSelectAdapter = new DialogSelectAdapter(getActivity());
        dialogSelectAdapter.addItem("마름");
        dialogSelectAdapter.addItem("슬림탄탄");
        dialogSelectAdapter.addItem("보통");
        dialogSelectAdapter.addItem("근육질");
        dialogSelectAdapter.addItem("통통한");
        listView.setAdapter(dialogSelectAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pickBodyTypeInterface.selectItem(dialogSelectAdapter.mList.get(i));
                ((SignupActivity)getActivity()).getRegisterObject().bodyType = i;
                dismiss();
            }
        });

        return rootView;
    }
}
