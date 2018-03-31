package com.example.android.electracksih;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 30-03-2018.
 */

public class ChatBotListAdapter extends RecyclerView.Adapter<ChatBotListAdapter.ViewHolder> {
ArrayList<String> myList=new ArrayList<>();

    public ChatBotListAdapter(ArrayList<String> myList) {
        this.myList = myList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View incomingMessageView = inflater.inflate(R.layout.chat_bot_incoming, parent, false);
        View outgoingMesssageView = inflater.inflate(R.layout.chat_bot_outgoing, parent, false);
        if(viewType==1){
            return new ChatBotListAdapter.ViewHolder(outgoingMesssageView);
        }
        else{
            return new ChatBotListAdapter.ViewHolder(incomingMessageView);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text=myList.get(position);
        holder.message.setText(text);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        public ViewHolder(View view) {
            super(view);
            message=itemView.findViewById(R.id.BotTextView);
        }
    }
}
