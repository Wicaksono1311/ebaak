package com.dennyprastiawan.ebaak.desain;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.dennyprastiawan.ebaak.API.APIService;
import com.dennyprastiawan.ebaak.API.NoConnectivityException;
import com.dennyprastiawan.ebaak.MainActivity;
import com.dennyprastiawan.ebaak.R;
import com.dennyprastiawan.ebaak.config.Constants;
import com.dennyprastiawan.ebaak.model.APIError;
import com.dennyprastiawan.ebaak.model.SuratPindahKelasModel;
import com.dennyprastiawan.ebaak.utils.ErrorUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Pindahkelas extends AppCompatActivity {
    private ProgressDialog pDialog;
    private EditText mKelas,mAlasan,mTanggal,mUpload;
    private Button mRegistrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pindahkelas);
        Pindahkelas.this.setTitle("Surat Pindah Kelas");
        tampil_syarat();
        init();
        mRegistrasi.setOnClickListener(v -> simpan());
        /* Menampilkan calendar picker */
        final Calendar myCalendar1 = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, month);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
            mTanggal.setText(sdf.format(myCalendar1.getTime()));
        };
        mTanggal.setOnClickListener(v -> new DatePickerDialog(Pindahkelas.this, date, myCalendar1
                .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                myCalendar1.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void simpan(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");
        String kelas =  mKelas.getText().toString();
        String alasan =  mAlasan.getText().toString();
        String tanggal =  mTanggal.getText().toString();
        String lampiran =  mUpload.getText().toString();
        if(kelas.length() == 0 || alasan.length() == 0 || tanggal.length() == 0){
            displayExceptionMessage("Silahkan lengkapi form !");
        } else {
            showDialog();
            try{
                Call<SuratPindahKelasModel> call = APIService.Factory.create(Pindahkelas.this).daftarPindahKelas(idUser,kelas,alasan,tanggal,lampiran);
                call.enqueue(new Callback<SuratPindahKelasModel>() {
                    @EverythingIsNonNull
                    @Override
                    public void onResponse(Call<SuratPindahKelasModel> call, Response<SuratPindahKelasModel> response) {
                        if(response.isSuccessful()){
                            hideDialog();
                            Intent intent = new Intent(Pindahkelas.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            displayExceptionMessage("Berhasil disimpan");
                        }else {
                            hideDialog();
                            APIError error = ErrorUtils.parseError(response);
                            displayExceptionMessage(error.message());
                        }
                    }
                    @EverythingIsNonNull
                    @Override
                    public void onFailure(Call<SuratPindahKelasModel> call, Throwable t) {
                        if(t instanceof NoConnectivityException) {
                            hideDialog();
                            displayExceptionMessage("Internet Offline!");
                        }
                    }
                });
            }catch (Exception e){
                hideDialog();
                e.printStackTrace();
                displayExceptionMessage(e.getMessage());
            }
        }

    }

    public void init(){
        mKelas= findViewById(R.id.txtKelasSebelum);
        mAlasan = findViewById(R.id.txtAlasan);
        mTanggal = findViewById(R.id.txtTanggal);
        mRegistrasi = findViewById(R.id.btnRegistrasi);
        mUpload = findViewById(R.id.etLinkUpload);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onProfilAction(MenuItem mi) {
        displayExceptionMessage("Selamat Datang!");
    }

    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    public void displayExceptionMessage(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void tampil_syarat()
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(
                Pindahkelas.this);
        alert.setTitle("Syarat :");
        String[] items={
                "1. Surat bekerja bagi mahasiswa yang pindah malam.",
                "2. File dijadikan satu dalam folder zip/rar kemudian diupload ke google drive, dan sertakan linknya pada form."
        };
        alert.setItems(items, (dialogInterface, i) -> {});
        alert.setCancelable(false)
                .setNegativeButton("Siap",
                        (dialog, id) -> dialog.cancel());
        final AlertDialog dialog = alert.create();
        dialog.show();
    }
}