package com.hrap.app.da_agile.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hrap.app.da_agile.DTO.SanPhamDTO;
import com.hrap.app.da_agile.DTO.Top10;
import com.hrap.app.da_agile.DbHelper.MyDbhelper;

import java.util.ArrayList;

public class ThongKeDAO {
    SQLiteDatabase db;
    Context context;
public ThongKeDAO(Context context){
this.context=context;
    MyDbhelper mydb=new MyDbhelper(context);
db=mydb.getWritableDatabase();
}
    @SuppressLint("Range")
    public ArrayList<Top10> getTopNhap(){
        String sqlTopNhap="select maSP,soLuong from HoaDon where nhap_xuat=0 group by maSP order by soLuong desc limit 10";
        ArrayList<Top10> list=new ArrayList<Top10>();
        SanPhamDAO spdao=new SanPhamDAO(context);
        Cursor c=db.rawQuery(sqlTopNhap,null);
        while(c.moveToNext()){
            Top10 top=new Top10();
            @SuppressLint("Range") SanPhamDTO sp=spdao.getID(String.valueOf(c.getInt(c.getColumnIndex("maSP"))));
            top.tenSP=sp.ten_SP;
            top.soLuongTOP=Integer.parseInt(c.getString(c.getColumnIndex("soLuong")));
            list.add(top);
        }
        return list;
    }
    @SuppressLint("Range")
    public ArrayList<Top10> getTopXuat(){
        String sqlTopXuat="select maSP,soLuong from HoaDon where nhap_xuat=1 group by maSP order by soLuong desc limit 10";
        ArrayList<Top10> list=new ArrayList<Top10>();
        SanPhamDAO spdao=new SanPhamDAO(context);
        Cursor c=db.rawQuery(sqlTopXuat,null);
        while(c.moveToNext()){
            Top10 top=new Top10();
            @SuppressLint("Range") SanPhamDTO sp=spdao.getID(String.valueOf(c.getInt(c.getColumnIndex("maSP"))));
            top.tenSP=sp.ten_SP;
            top.soLuongTOP=Integer.parseInt(c.getString(c.getColumnIndex("soLuong")));
            list.add(top);
        }
        return list;
    }
//    @SuppressLint("Range")
//    public int getDoanhthu(String tuNgay, String denNgay){
//        String sqlDoanhthu="select sum(donGia * soLuong) as doanhThu from HoaDon where trangThai=1 AND nhap_xuat=1 AND ngayXuat between ? and ?";
//        ArrayList<Integer> list=new ArrayList<>();
//        Cursor c=db.rawQuery(sqlDoanhthu,new String[]{tuNgay,denNgay});
//        while(c.moveToNext()){
//            try{
//                list.add(Integer.parseInt(String.valueOf(c.getInt(c.getColumnIndex("doanhThu")))));
//
//            }catch (Exception e){
//                list.add(0);
//            }
//
//        }
//        return list.get(0);
//    }
    @SuppressLint("Range")
    public int getDoanhthu(String tuNgay, String denNgay) {
        int doanhThu = 0;
        String sqlDoanhthu = "SELECT SUM(donGia * soLuong) AS doanhThu FROM HoaDon WHERE trangThai = 1 AND nhap_xuat = 1 AND (ngayXuat BETWEEN ? AND ?)";
        Cursor c = db.rawQuery(sqlDoanhthu, new String[]{tuNgay, denNgay});
        if (c.moveToFirst()) {
            doanhThu = c.getInt(c.getColumnIndex("doanhThu"));
        }
        c.close();
        return doanhThu;
    }

}
