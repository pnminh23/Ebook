package com.example.ebook_pnminh.Filter;

import android.widget.Filter;

import com.example.ebook_pnminh.Adapter.BookAdapter;
import com.example.ebook_pnminh.Adapter.RowBookAdapter;
import com.example.ebook_pnminh.model.Books;

import java.util.ArrayList;
import java.util.List;

public class FilterBook extends Filter {
    List<Books> filterList;
    RowBookAdapter rowBookAdapter;


    public FilterBook(List<Books> filtreList, RowBookAdapter rowBookAdapter) {
        this.filterList = filtreList;
        this.rowBookAdapter = rowBookAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length() >0){
            // chuyển hết chuỗi thành lowercase
            constraint = constraint.toString().toLowerCase();
            List<Books> filterdBook = new ArrayList<>();
            for (int i  = 0 ; i < filterList.size();i++){
                if(filterList.get(i).getName().toLowerCase().contains(constraint)||filterList.get(i).getAuthor().toLowerCase().contains(constraint)){
                    filterdBook.add(filterList.get(i));
                }
            }
            results.count = filterdBook.size();
            results.values = filterdBook;


        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        rowBookAdapter.listBook = (List<Books>)results.values;

    }
}
