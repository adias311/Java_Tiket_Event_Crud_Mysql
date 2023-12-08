-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 09, 2023 at 12:35 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tiket_event`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `nama_kategori` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `nama_kategori`) VALUES
(1, 'Music'),
(2, 'Sport'),
(3, 'Festival'),
(4, 'E-Sport');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `nama`, `email`) VALUES
(1, 'w', 'q'),
(2, 'w', 'e'),
(3, 'reva', 'reva@gmail.com'),
(4, 'q', 'w'),
(5, 'feni', 'feni@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `tiket_event`
--

CREATE TABLE `tiket_event` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `lokasi` varchar(255) NOT NULL,
  `harga` decimal(10,2) NOT NULL,
  `tanggal` date NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `status` enum('available','deprecated') DEFAULT 'available'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tiket_event`
--

INSERT INTO `tiket_event` (`id`, `nama`, `lokasi`, `harga`, `tanggal`, `category_id`, `status`) VALUES
(4, 'JKT48 ', 'Dukuh Bawah', 100000.00, '2023-12-07', 1, 'available'),
(5, 'Final UCL (MU Vs Barca)', 'JIS', 200000.00, '2024-03-20', 2, 'available'),
(6, 'Unindra Summer Fest', 'Unindra Gedong', 300000.00, '2024-01-12', 3, 'deprecated'),
(7, 'Bring Me To Horizon 2024', 'GBK', 400000.00, '2024-01-27', 1, 'available'),
(8, 'MPL ID S13 (Aura vs Rebellion)', 'Mall Taman Anggrek', 500000.00, '2024-03-09', 4, 'available');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `tiket_event_id` int(11) DEFAULT NULL,
  `jumlah` int(11) NOT NULL,
  `total_harga` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `customer_id`, `tiket_event_id`, `jumlah`, `total_harga`) VALUES
(1, 3, 4, 2, 200000.00),
(2, 1, 8, 2, 1000000.00),
(3, 1, 6, 1, 300000.00),
(4, 4, 7, 7, 2800000.00),
(5, 1, 6, 12, 3600000.00),
(6, 4, 8, 2, 1000000.00),
(7, 1, 7, 3, 1200000.00),
(8, 1, 4, 2, 200000.00),
(10, 5, 5, 1, 200000.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tiket_event`
--
ALTER TABLE `tiket_event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `tiket_event_id` (`tiket_event_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tiket_event`
--
ALTER TABLE `tiket_event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tiket_event`
--
ALTER TABLE `tiket_event`
  ADD CONSTRAINT `tiket_event_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`tiket_event_id`) REFERENCES `tiket_event` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
