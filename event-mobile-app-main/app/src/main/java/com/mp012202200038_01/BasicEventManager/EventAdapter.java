/**
 * Adapter for displaying a list of events within a RecyclerView. This adapter is responsible for
 * binding event data to views defined in the event card layout. It utilizes Picasso for image loading
 * to display event posters and handles click events on each item to facilitate interaction.
 */


package com.mp012202200038_01.BasicEventManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The type Event adapter.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    /**
     * The Context.
     */
    Context context;
    /**
     * The Array list.
     */
    ArrayList<Event> arrayList;
    /**
     * The On item click listener.
     */
    OnItemClickListener onItemClickListener;

    /**
     * Instantiates a new Event adapter.
     *
     * @param context   the context
     * @param arrayList the array list
     */
    public EventAdapter(Context context, ArrayList<Event> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = arrayList.get(position);
        holder.title.setText(arrayList.get(position).getTitle());
        holder.subtitle.setText(arrayList.get(position).getDesc());
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));
        holder.checkedInIndicator.setVisibility(event.isCheckedIn() ? View.VISIBLE : View.GONE);

        // Use Picasso to load the image from the URL into the ImageView
        if (event.getPosterUrl() != null && !event.getPosterUrl().isEmpty()) {
            Picasso.get().load(event.getPosterUrl()).into(holder.image);
            Picasso.get()
                    .load(event.getPosterUrl())
                    .placeholder(R.drawable.placeholder) // add placeholder image resource
                    .error(R.drawable.image) // add error image resource
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /**
     * The type View holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Title.
         */
        TextView title, /**
         * The Subtitle.
         */
        subtitle;
        /**
         * The Image.
         */
        ImageView image;
        /**
         * The Checked in indicator.
         */
        ImageView checkedInIndicator;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.event_card_title);
            subtitle = itemView.findViewById(R.id.event_card_subtitle);
            image = itemView.findViewById(R.id.event_card_image);
            checkedInIndicator = itemView.findViewById(R.id.event_checked_in_indicator);
        }
    }


    /**
     * Sets on item click listener.
     *
     * @param onItemClickListener the on item click listener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * The interface On item click listener.
     */
    public interface OnItemClickListener {
        /**
         * On click.
         *
         * @param Event the event
         */
        void onClick(Event Event);
    }
}
