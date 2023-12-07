import java.sql.*; // koneksi database
import java.io.*; // input output
import javax.swing.*; // mengkombinasi script lebih dari 1 dijadikan 1 form 

public class cobakoneksi {

  String str;
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public void lihat() {
    try {
      // load database
      Class.forName("org.sqlite.JDBC");
 
      // buat koneksi
      Connection koneksi = DriverManager.getConnection("JDBC:sqlite:belajar.db");
      // buat statement
      Statement stm = koneksi.createStatement();
      ResultSet rs = stm.executeQuery("select * from air");
      // ambil MetaData
      ResultSetMetaData rsmd = rs.getMetaData();
      int kolom = rsmd.getColumnCount();
      // tampilkan judul kolom
      for (int i = 1; i <= kolom; i++) {
        System.out.print(rsmd.getColumnName(i) + " | ");
      }
      System.out.println("\n");
      while (rs.next()) {
        System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3));
      }
    } catch (Exception e) {
      System.err.println("Eror" + e.getMessage());
    }

  }

  public void tambah() {
    try {
      Class.forName("org.sqlite.JDBC");
      Connection koneksi = DriverManager.getConnection("JDBC:sqlite:belajar.db");

      Statement stm = koneksi.createStatement();

      System.out.print("Masukan Nama anda:");
      String a = br.readLine();
      System.out.print("Masukan Alamat anda:");
      String b = br.readLine();
      System.out.print("Masukan Npm anda:");
      int c = Integer.parseInt(br.readLine());

      stm.executeUpdate("insert into air (Nama,Alamat,Npm) values('" + a + "','" + b + "','" + c + "');");

      stm.close();
      koneksi.close();

    } catch (Exception e) {
      System.err.println("error" + e.getMessage());
    }
  }

  public void ubah() {
    try {
      Class.forName("org.sqlite.JDBC");
      Connection koneksi = DriverManager.getConnection("JDBC:sqlite:belajar.db");
      Statement stm = koneksi.createStatement();

      System.out.print("Masukan Nama baru:");
      String a = br.readLine();
      System.out.print("Masukan Alamat baru:");
      String b = br.readLine();
      System.out.print("Masukan Npm lama:");
      int c = Integer.parseInt(br.readLine());
      stm.executeUpdate("update air set Nama='" + a + "',Alamat='" + b + "',Npm='" + c + "' where Npm='" + c + "';");

      stm.close();
      koneksi.close();
    } catch (Exception e) {
      System.err.println("eror" + e.getMessage());
    }
  }

  public void hapus() {
    try {
      Class.forName("org.sqlite.JDBC");
      Connection koneksi = DriverManager.getConnection("JDBC:sqlite:belajar.db");
      Statement stm = koneksi.createStatement();

      System.out.print("Masukan Nama yang ingin anda hapus:");
      String a = br.readLine();
      System.out.print("Masukan Npm yang ingin anda hapus:");
      int c = Integer.parseInt(br.readLine());

      stm.executeUpdate("delete from air where Npm='" + c + "';");

      stm.close();
      koneksi.close();
    } catch (Exception e) {
      System.err.println("error" + e.getMessage());
    }
  }

  public void keluar() {
    try {

      System.exit(0);

    } catch (Exception e) {

      System.err.print("error" + e.getMessage());
      
    }
  }

}