package com.example.swlab.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Leisure_Article_Activity  extends AppCompatActivity{
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(Leisure_Article_Activity.this, Leisure_Activity.class);
        startActivity(intent);
        finish();
    }
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leisure_article);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseReference databaseRef=FirebaseDatabase.getInstance().getReference("server/article");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot pisSnapshot : dataSnapshot.getChildren()) {
                    DB_Leisure_Article lerisure=pisSnapshot.getValue(DB_Leisure_Article.class);
                    Log.i("Photo's Title:", lerisure.getTitle());
                    Log.i("Photo's Content:", lerisure.getContent());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Photo", "failed: " + databaseError.getMessage());
            }
        });
        FirebaseRecyclerAdapter<DB_Leisure_Article,LeisureViewHolder> adapter=
                new FirebaseRecyclerAdapter<DB_Leisure_Article, LeisureViewHolder>(DB_Leisure_Article.class,R.layout.leisure_article_list,LeisureViewHolder.class,databaseRef) {
                    @Override
                    protected void populateViewHolder(LeisureViewHolder viewHolder, DB_Leisure_Article model, int position) {
                        viewHolder.setPhoto(model);
                    }
                };
        recyclerView.setAdapter(adapter);
    }
    public static class LeisureViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView content;

        public LeisureViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.txt_title);
            content = (TextView) itemView.findViewById(R.id.txt_content);
        }

        public void setPhoto(DB_Leisure_Article lerisure) {
            title.setText(lerisure.getTitle());
            /* content.setText(lerisure.getContent());*/
           /*測試*/
            content.setText(Html.fromHtml("<a href="+lerisure.getContent()+">點我閱讀</a> "));
            content.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

}
