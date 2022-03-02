-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 02, 2022 at 11:46 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sas`
--

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `order_id` varchar(10) NOT NULL,
  `prod_id` varchar(4) NOT NULL,
  `qty` int(4) NOT NULL,
  `amt` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`order_id`, `prod_id`, `qty`, `amt`) VALUES
('BN000001', 'S001', 2, 10),
('BN000001', 'B001', 1, 50),
('BN000002', 'S001', 2, 10),
('BN000002', 'C001', 2, 100),
('BN000003', 'S003', 4, 300),
('BN000003', 'B002', 3, 150),
('BN000004', 'G004', 4, 200),
('BN000004', 'G005', 3, 150),
('BN000005', 'B002', 3, 150),
('BN000005', 'B004', 3, 150),
('BN000006', 'C002', 3, 150),
('BN000009', 'C002', 5, 250),
('BN000010', 'G004', 200, 10000),
('BN000011', 'D001', 3, 150),
('BN000011', 'C004', 2, 150),
('BN000012', 'D001', 5, 250),
('BN000012', 'C003', 4, 200),
('BN000013', 'D001', 3, 150),
('BN000013', 'C003', 5, 250),
('BN000013', 'D001', 3, 150),
('BN000013', 'C003', 5, 250);

--
-- Triggers `bill`
--
DELIMITER $$
CREATE TRIGGER `CUT_STOCK` AFTER INSERT ON `bill` FOR EACH ROW BEGIN
UPDATE stock set stock.stock_qty = stock_qty - NEW.qty 
where prod_id = NEW.prod_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `orderprod`
--

CREATE TABLE `orderprod` (
  `order_id` varchar(10) NOT NULL,
  `order_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orderprod`
--

INSERT INTO `orderprod` (`order_id`, `order_date`) VALUES
('BN000000', '2021-04-04'),
('BN000001', '2021-04-04'),
('BN000002', '2021-04-04'),
('BN000003', '2021-04-04'),
('BN000004', '2021-04-04'),
('BN000005', '2021-04-04'),
('BN000006', '2021-04-04'),
('BN000007', '2021-04-04'),
('BN000008', '2021-04-04'),
('BN000009', '2021-04-07'),
('BN000010', '2021-04-07'),
('BN000011', '2021-04-07'),
('BN000012', '2021-04-07'),
('BN000013', '2021-04-07'),
('BN000013', '2021-04-07');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `prod_id` varchar(5) NOT NULL,
  `prod_name` varchar(20) NOT NULL,
  `unit_amt` int(4) NOT NULL,
  `stock_qty` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`prod_id`, `prod_name`, `unit_amt`, `stock_qty`) VALUES
('S002', 'pencil', 5, 50),
('G001', 'Masala Powder', 50, 50),
('G002', '3 Roses ', 50, 500),
('G003', 'Bru', 50, 150),
('G004', 'Green tea', 50, 96),
('S001', 'Pen', 5, 290),
('S003', 'crayons', 75, 146),
('S004', 'sketchers', 50, 150),
('S005', 'brush set', 75, 300),
('G005', 'Chilli powder', 50, 297),
('G006', 'cloves', 75, 500),
('D001', 'Coco Cola', 50, 75),
('D002', 'Sliee', 75, 50),
('D003', 'Pepsi', 50, 50),
('B001', 'Chick shampoo', 50, 149),
('B002', 'Sandalwood soap', 50, 294),
('C001', 'Exo bar', 50, 300),
('C002', 'Detol', 50, -3),
('B003', 'Himalayas soap', 75, 150),
('B004', 'Sunsilk shampoo', 50, 147),
('C003', 'Vim liquid', 50, 286),
('C004', 'lisol liquid', 75, 148);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(5) NOT NULL,
  `fname` text NOT NULL,
  `lname` text DEFAULT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `auth` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fname`, `lname`, `username`, `password`, `auth`) VALUES
(0, 'admin', '', 'admin', 'admin', 'admin'),
(1, 'Bhavika', 'Arage', 'BhavikaArage', 'qwerty', 'M'),
(5, 'Dev', 'Dharshani', 'DevDharshani', 'dd62', 'S'),
(6, 'Keerthana', 'T', 'KeerthanaT', 'keerthana78', 'C');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
