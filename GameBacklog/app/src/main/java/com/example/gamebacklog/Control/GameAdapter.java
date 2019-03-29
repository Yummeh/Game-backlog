package com.example.gamebacklog.Control;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gamebacklog.R;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
	private List<Game> mGames;
	
	public GameAdapter(List<Game> mGames) {
		this.mGames = mGames;
	}
	
	public void swapList(List<Game> newList) {
		mGames = newList;
		if (newList != null) {
			//force recyclerview to refresh
			this.notifyDataSetChanged();
		}
	}
	
	@NonNull
	@Override
	public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
		View view = inflater.inflate(R.layout.gamecard, viewGroup, false);
		
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull GameAdapter.ViewHolder viewHolder, int i) {
		Game game = mGames.get(i);
		
		viewHolder.title.setText(game.getTitle());
		viewHolder.platform.setText(game.getPlatform());
		viewHolder.status.setText(game.getStatus());
		viewHolder.date.setText(game.getDate());
	}
	
	@Override
	public int getItemCount() {
		return mGames.size();
	}
	
	
	public class ViewHolder extends RecyclerView.ViewHolder {
		private TextView title;
		private TextView platform;
		private TextView status;
		private TextView date;
		
		public ViewHolder(View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.title);
			platform = itemView.findViewById(R.id.platform);
			status = itemView.findViewById(R.id.status);
			date = itemView.findViewById(R.id.date);
		}
	}
}
