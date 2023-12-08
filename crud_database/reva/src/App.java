import java.io.*;
import utils.*;

public class App {
    
    public static void main(String[] args) {
        try {
            Tiket_Event tiket = new Tiket_Event();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            String next = br != null ? "Production" : "Development";
            
            do {
                
                Utils.clrsrc();
                
                System.out.println("============Menu Utama============");
                System.out.println("==========1.Cetak Tiket==========="); // insert
                System.out.println("==========2.Ubah Data Tiket======="); // update
                System.out.println("==========3.Lihat Transaksi======="); // select
                System.out.println("==========4.Hapus Tiket Event====="); // delete
                System.out.println("==========5.Keluar================"); // keluar
                
                System.out.print("\nMasukan angka yang anda ingin pilih : ");
                int c = Integer.parseInt(br.readLine());

                switch (c) {
                    case 1: tiket.store(); break;
                    case 2: tiket.update(); break;
                    case 3: tiket.show(); break;
                    case 4: tiket.destroy(); break;
                    case 5: tiket.exit(); break;
                    default:
                    next = "y";
                    continue; 
                }
                
                System.out.print("Apakah Anda ingin kembali ke menu utama ? (y/n) : ");
                next = br.readLine();
                
            } while (next.equals("y"));
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
