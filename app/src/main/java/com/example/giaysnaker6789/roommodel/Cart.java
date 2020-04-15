package com.example.giaysnaker6789.roommodel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cart_table")
public class Cart implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int idsanpham;
    String tensp;
    int iduser;
    String tenuser;
    String xuatsu;
    int giadagiam;
    int soluong;
    int thanhtien;
    String hinhanh;
    String voucher;


    public Cart() {
    }

    public Cart(int id, int idsanpham, String tensp, int iduser, String tenuser, String xuatsu, int giadagiam, int soluong, int thanhtien, String hinhanh, String voucher) {
        this.id = id;
        this.idsanpham = idsanpham;
        this.tensp = tensp;
        this.iduser = iduser;
        this.tenuser = tenuser;
        this.xuatsu = xuatsu;
        this.giadagiam = giadagiam;
        this.soluong = soluong;
        this.thanhtien = thanhtien;
        this.hinhanh = hinhanh;
        this.voucher = voucher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getTenuser() {
        return tenuser;
    }

    public void setTenuser(String tenuser) {
        this.tenuser = tenuser;
    }

    public String getXuatsu() {
        return xuatsu;
    }

    public void setXuatsu(String xuatsu) {
        this.xuatsu = xuatsu;
    }

    public int getGiadagiam() {
        return giadagiam;
    }

    public void setGiadagiam(int giadagiam) {
        this.giadagiam = giadagiam;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(int thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
}
