package com.example.android.electracksih;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 30-03-2018.
 */

public class ChatBotFragment extends BottomSheetDialogFragment {
RecyclerView chatRecyclerView;
EditText currentInput;
ChatBotListAdapter chatBotListAdapter;
public static ArrayList<String> messagesList=new ArrayList<>();

    @Override
    public void setupDialog(Dialog dialog, int style) {
//        super.setupDialog(dialog, style);
        messagesList.add("hii");
        messagesList.add("hello");
        messagesList.add("bi bi");
        messagesList.add("tc");
        View contentView = View.inflate(getContext(), R.layout.chat_bot_fragment, null);
        dialog.setContentView(contentView);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chatRecyclerView=view.findViewById(R.id.chatBotRecyclerView);
        currentInput=view.findViewById(R.id.BotCurrentInput);


        chatBotListAdapter=new ChatBotListAdapter(messagesList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        chatRecyclerView.setLayoutManager(mLayoutManager);

        chatRecyclerView.setAdapter(chatBotListAdapter);
        chatBotListAdapter.notifyDataSetChanged();



    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);


    }
}
