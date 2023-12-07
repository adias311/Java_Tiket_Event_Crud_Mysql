import java.io.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

import reva.utils.Utils;

public class Tiket_Event {

  String jdbcUrl = "jdbc:mysql://localhost:3306/tiket_event";
  String username = "root";
  String password = "";

  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public void index() {
    System.out.println("Lihat Pembelian");
  }

  public void store() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
      Statement stmt = connection.createStatement();

      String query = "SELECT tiket.*, c.nama_kategori FROM tiket_event tiket INNER JOIN category c ON tiket.category_id = c.id;";
      ResultSet rs = stmt.executeQuery(query);

      ResultSetMetaData rsmd = rs.getMetaData();
      int kolom = rsmd.getColumnCount();

      // Tampilkan judul kolom
      System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n",
          rsmd.getColumnName(1),
          rsmd.getColumnName(2),
          rsmd.getColumnName(3),
          rsmd.getColumnName(4),
          rsmd.getColumnName(5),
          rsmd.getColumnName(6),
          "nama_kategori");

      // Tampilkan data tiket_event
      while (rs.next()) {
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n",
            rs.getInt(1),
            rs.getString(2),
            rs.getString(3),
            rs.getInt(4),
            rs.getDate(5),
            rs.getInt(6),
            rs.getString("nama_kategori"));
      }

      // Input pembelian tiket
      System.out.print("Masukan Jumlah tiket yang ingin dibeli :");
      int jumlahTiket = Integer.parseInt(br.readLine());
      System.out.print("Pilih tiket yang ingin dibeli (masukan no didaftar tiket):");
      int idTiket = Integer.parseInt(br.readLine());

      // Lakukan proses pembelian
      // Input informasi pelanggan
      System.out.print("Masukan Nama anda :");
      String nama = br.readLine();
      System.out.print("Masukan Email anda :");
      String email = br.readLine();

      // Cek apakah email sudah ada dalam tabel customer
      PreparedStatement checkEmailStatement = connection
          .prepareStatement("SELECT id, nama FROM customer WHERE email = ?");
      checkEmailStatement.setString(1, email);

      ResultSet emailResult = checkEmailStatement.executeQuery();

      int customerID;

      if (emailResult.next()) {
        // Email sudah ada dalam tabel customer
        int existingCustomerId = emailResult.getInt("id");
        String existingCustomerName = emailResult.getString("nama");

        if (existingCustomerName.equals(nama)) {
          // Nama dan email sudah sama, ambil ID customer
          System.out.println("ID Customer yang sudah ada: " + existingCustomerId);
          customerID = existingCustomerId;
        } else {
          // Nama berbeda, beri pesan bahwa email sudah digunakan
          System.out.println("Email sudah digunakan");
          return; // Hentikan proses karena email sudah digunakan dengan nama yang berbeda
        }
      } else {
        // Email belum ada, tambahkan pelanggan baru
        PreparedStatement addCustomerStatement = connection.prepareStatement(
            "INSERT INTO customer (nama, email) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        addCustomerStatement.setString(1, nama);
        addCustomerStatement.setString(2, email);
        addCustomerStatement.executeUpdate();

        ResultSet generatedKeys = addCustomerStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
          // Ambil ID customer yang baru ditambahkan
          customerID = generatedKeys.getInt(1);
          System.out.println("Pelanggan baru ditambahkan dengan ID: " + customerID);
        } else {
          System.out.println("Gagal menambahkan pelanggan baru");
          return; // Hentikan proses jika gagal menambahkan pelanggan baru
        }

        addCustomerStatement.close();
      }

      emailResult.close();
      checkEmailStatement.close();

      // Ambil harga tiket yang dipilih
      PreparedStatement hargaTiketStatement = connection.prepareStatement(
          "SELECT harga FROM tiket_event WHERE id = ?");
      hargaTiketStatement.setInt(1, idTiket);

      ResultSet hargaTiketResult = hargaTiketStatement.executeQuery();

      int hargaTiket = 0;

      if (hargaTiketResult.next()) {
        hargaTiket = hargaTiketResult.getInt("harga");
      } else {
        System.out.println("Tiket dengan ID " + idTiket + " tidak ditemukan");
        return; // Hentikan proses jika tiket tidak ditemukan
      }

      hargaTiketResult.close();
      hargaTiketStatement.close();

      // Hitung total_harga
      int totalHarga = hargaTiket * jumlahTiket;

      // Masukkan informasi pembelian tiket ke dalam tabel pembelian
      PreparedStatement addPembelianStatement = connection.prepareStatement(
          "INSERT INTO pembelian (customer_id, tiket_event_id, jumlah, total_harga) VALUES (?, ?, ?, ?)");
      addPembelianStatement.setInt(1, customerID);
      addPembelianStatement.setInt(2, idTiket);
      addPembelianStatement.setInt(3, jumlahTiket);
      addPembelianStatement.setInt(4, totalHarga);
      addPembelianStatement.executeUpdate();

      Utils.clrsrc();
      
      // Fetch details of the purchased ticket
      PreparedStatement fetchTicketDetailsStatement = connection.prepareStatement(
          "SELECT te.*, c.nama_kategori FROM tiket_event te INNER JOIN category c ON te.category_id = c.id WHERE te.id = ?");
      fetchTicketDetailsStatement.setInt(1, idTiket);

      ResultSet ticketDetailsResult = fetchTicketDetailsStatement.executeQuery();

      if (ticketDetailsResult.next()) {
        // Display additional information about the purchased ticket
        String namaEvent = ticketDetailsResult.getString("nama_event");
        String tanggalEvent = ticketDetailsResult.getString("tanggal_event");
        String lokasiEvent = ticketDetailsResult.getString("lokasi_event");
        hargaTiket = ticketDetailsResult.getInt("harga");

        System.out.println("\u001B[32m==================|| Tiket Berhasil Di Cetak ||==================\u001B[0m");
        System.out.println();
        System.out.println(" ID Tiket     : \u001B[34m" + idTiket + "\u001B[0m");
        System.out.println(" Nama Event   : " + namaEvent);
        System.out.println(" Nama Pembeli : " + nama);
        System.out.println(" Jumlah Tiket : " + jumlahTiket + " Tiket");
        System.out.println(" Tanggal      : " + tanggalEvent);
        System.out.println(" Lokasi       : " + lokasiEvent);
        System.out
            .println(" Harga Tiket  : " + NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(hargaTiket));
        System.out
            .println(" Total Harga  : " + NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(totalHarga));
        System.out.println();
        System.out.println("\u001B[32m=================================================================\u001B[0m");
      } else {
        System.out.println("Tiket dengan ID " + idTiket + " tidak ditemukan");
      }
      System.out.print("Apakah Anda ingin melakukan pembelian tiket lagi? (y/n): ");
      String lagi = br.readLine();

      ticketDetailsResult.close();
      fetchTicketDetailsStatement.close();
      addPembelianStatement.close();
      stmt.close();
      connection.close();

      if (lagi.equalsIgnoreCase("y")) {
        store(); // Rekursif untuk melakukan pembelian tiket lagi
      }

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  
  public void update() {
    System.out.println("Update daftar tiket");
  }

  public void destroy() {
    System.out.println("Hapus daftar tiket");
  }

  public void exit() {
    try {
      System.exit(0);
    } catch (Exception e) {
      System.err.print("Error: " + e.getMessage());
    }
  }
}
