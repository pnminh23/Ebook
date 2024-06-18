//package com.example.ebook_pnminh.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.example.ebook_pnminh.R;
//import com.example.ebook_pnminh.model.Books;
//import com.makeramen.roundedimageview.RoundedImageView;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
//    private Context context;
//    private List<Books> booksList;
//
//    public SliderAdapter(Context context, List<Books> booksList) {
//        this.context = context;
//        this.booksList = booksList;
//    }
//
//    @NonNull
//    @Override
//    public SliderAdapter.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_book_item,parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SliderAdapter.SliderViewHolder holder, int position) {
//            holder.setImageView(booksList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return booksList.size();
//    }
//
//    public static class SliderViewHolder extends RecyclerView.ViewHolder {
//        private RoundedImageView imageView;
//        public SliderViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imageSlide);
//        }
//        void setImageView(Books books){
//            Picasso.get().load(books.getImg()).into(imageView);
//        }
//    }
//}
