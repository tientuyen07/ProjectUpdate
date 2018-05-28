package com.example.tient.spa.Util.api;

import com.example.tient.spa.Model.DatLich;
import com.example.tient.spa.Model.DichVu;
import com.example.tient.spa.Model.KhungGio;
import com.example.tient.spa.Model.MyPham;
import com.example.tient.spa.Model.NhanVien;
import com.example.tient.spa.Model.Phong;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {
    @FormUrlEncoded // Mã hóa trước khi post
    @POST("login.php")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded // Mã hóa trước khi post
    @POST("register.php")
    Call<ResponseBody> registerRequest(@Field("username") String username,
                                       @Field("password") String password);

    @FormUrlEncoded // Mã hóa trước khi post
    @POST("DoiMatKhau.php")
    Call<ResponseBody> DoiMatKhau(@Field("username") String username,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("getDsPhong.php")
    Call<List<Phong>> getDsPhong(@Field("tbl_dichvu_ma_dichvu") int tbl_dichvu_ma_dichvu);

    @FormUrlEncoded
    @POST("GioTheoNV.php")
    Call<List<KhungGio>> GioTheoNV(@Field("ngayhen") String ngayhen,
                                   @Field("tbl_nhanvien_id_nhanvien") int tbl_nhanvien_id_nhanvien);

    @FormUrlEncoded
    @POST("GioTheoPhong.php")
    Call<List<KhungGio>> GioTheoPhong(@Field("ngayhen") String ngayhen,
                                      @Field("tbl_dichvu_has_tbl_phong_tbl_phong_maphong") String tbl_dichvu_has_tbl_phong_tbl_phong_maphong);

    @FormUrlEncoded
    @POST("postDatLich.php")
    Call<ResponseBody> DatLich(@Field("ngayhen") String ngayhen,
                               @Field("khunggio") String khunggio,
                               @Field("sdt_datlich") String sdt_datlich,
                               @Field("hoten_datlich") String hoten_datlich,
                               @Field("tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu") int tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu,
                               @Field("tbl_nhanvien_id_nhanvien") int tbl_nhanvien_id_nhanvien,
                               @Field("tbl_dichvu_has_tbl_phong_tbl_phong_maphong") String tbl_dichvu_has_tbl_phong_tbl_phong_maphong,
                               @Field("xacnhan") int xacnhan);

    @FormUrlEncoded
    @POST("UpdateLich.php")
    Call<List<DatLich>> UpdateLich(@Field("id_lichhen") int id_lichhen,
                                   @Field("ngayhen") String ngayhen,
                                   @Field("khunggio") String khunggio,
                                   @Field("sdt_datlich") String sdt_datlich,
                                   @Field("hoten_datlich") String hoten_datlich,
                                   @Field("tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu") int tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu,
                                   @Field("tbl_nhanvien_id_nhanvien") int tbl_nhanvien_id_nhanvien,
                                   @Field("tbl_dichvu_has_tbl_phong_tbl_phong_maphong") String tbl_dichvu_has_tbl_phong_tbl_phong_maphong,
                                   @Field("xacnhan") int xacnhan);

    @FormUrlEncoded
    @POST("CapNhatThongTin.php")
    Call<ResponseBody> CapNhatThongTin(@Field("hoten") String hoten,
                                       @Field("ngaysinh") String ngaysinh,
                                       @Field("gioitinh") String gioitinh,
                                       @Field("sdt") String sdt,
                                       @Field("diachi") String diachi,
                                       @Field("tbl_taikhoan_username") String tbl_taikhoan_username);

    @FormUrlEncoded
    @POST("LichSuDatLich.php")
    Call<List<DatLich>> LichSuDatLich(@Field("sdt_datlich") String sdt_datlich);

    @FormUrlEncoded
    @POST("DeleteLichHen.php")
    Call<ResponseBody> DeleteDatLich(@Field("id_lichhen") int id_lichhen,
                                     @Field("khunggio") String khunggio);


    @GET("getMyPham.php")
    Call<List<MyPham>> getMyPham();

    @GET("getDichVu.php")
    Call<List<DichVu>> getDichVu();

    @GET("getPhong.php")
    Call<List<Phong>> getPhong();

    @GET("getNhanVien.php")
    Call<List<NhanVien>> getNhanVien();


}
