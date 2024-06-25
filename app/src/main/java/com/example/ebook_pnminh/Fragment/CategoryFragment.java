package com.example.ebook_pnminh.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ebook_pnminh.CategoryActivity;
import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.SearchActivity;


public class CategoryFragment extends Fragment {
    private View categoryView;
    private TextView search;
    private ImageView tinhcamlangman,hoikydanhnha,lichsu,vanhocnuocngoai,kynangmem,trinhtham,nuoidaycon,kinhte,khoahoc,tuoihoctro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        categoryView = inflater.inflate(R.layout.fragment_category,container,false);
        tinhcamlangman = categoryView.findViewById(R.id.img_tinhcamlangman);
        hoikydanhnha = categoryView.findViewById(R.id.img_hoikidanhnhan);
        lichsu = categoryView.findViewById(R.id.img_lichsuchinhtri);
        vanhocnuocngoai = categoryView.findViewById(R.id.img_vanhocnuocngoai);
        kynangmem = categoryView.findViewById(R.id.img_kynangmem);
        trinhtham = categoryView.findViewById(R.id.img_trinhthamkinhdi);
        nuoidaycon = categoryView.findViewById(R.id.img_nuoidaycon);
        kinhte = categoryView.findViewById(R.id.img_kinhtetaichinh);
        khoahoc = categoryView.findViewById(R.id.img_khoahocvientuong);
        tuoihoctro = categoryView.findViewById(R.id.img_tuoihoctro);
        search = categoryView.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        tinhcamlangman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Tình cảm lãng mạn");
                startActivity(intent);
            }
        });
        hoikydanhnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Hồi ký - Danh nhân");
                startActivity(intent);
            }
        });
        lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Lịch sử - Chính trị");
                startActivity(intent);
            }
        });
        vanhocnuocngoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Văn học nước ngoài");
                startActivity(intent);
            }
        });

        kynangmem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Kỹ năng mềm");
                startActivity(intent);
            }
        });
        trinhtham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Trinh thám - Kinh dị");
                startActivity(intent);
            }
        });
        nuoidaycon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Nuôi dạy con");
                startActivity(intent);
            }
        });
        kinhte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Kinh tế - tài chính");
                startActivity(intent);
            }
        });
        khoahoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Khoa học viễn tưởng");
                startActivity(intent);
            }
        });
        tuoihoctro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("category","Tuổi học trò");
                startActivity(intent);
            }
        });


        return categoryView;
    }
}