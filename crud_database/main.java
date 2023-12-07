
import java.sql.*; // koneksi database
import java.io.*; // input output
import javax.swing.*; // mengkombinasi script lebih dari 1 dijadikan 1 form

/**
 * main
 */
public class main { 

    public static void main(String[] args) {
        cobakoneksi kita = new cobakoneksi();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int c;
        String k;
        System.out.println("============Menu Utama============");
        System.out.println("==========1.Lihat Data============"); // select
        System.out.println("==========2.Tambah Data==========="); // insert
        System.out.println("==========3.Ubah Data============="); // update
        System.out.println("==========4.Hapus Data============"); // delete
        System.out.println("==========5.Keluar================"); // keluar

        try {
            System.out.print("Apakah anda ingin menginput lagi:");
            while ((k = br.readLine()).equals("y")) {
                System.out.print("Masukan angka yang anda ingin pilih:");

                c = Integer.parseInt(br.readLine());

                switch (c) {
                    case 1:
                        kita.lihat();
                        break;
                    case 2:
                        kita.tambah();
                        break;
                    case 3:
                        kita.ubah();
                        break;
                    case 4:
                        kita.hapus();
                        break;
                    case 5:
                        kita.keluar();
                        break;

                    default:
                        System.out.println("Salah ketik");
                }

            }
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());

        }

    }

}