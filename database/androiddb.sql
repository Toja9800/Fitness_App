-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 12 Lut 2023, 21:49
-- Wersja serwera: 10.4.27-MariaDB
-- Wersja PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `androiddb`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `roles`
--

CREATE TABLE `roles` (
  `id` int(20) NOT NULL,
  `role` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `roles`
--

INSERT INTO `roles` (`id`, `role`) VALUES
(1, 'klient'),
(2, 'trener');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(20) NOT NULL,
  `username` varchar(70) NOT NULL,
  `password` varchar(40) NOT NULL,
  `email` varchar(50) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `role_id` int(20) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `created_at`, `updated_at`, `role_id`) VALUES
(1, 'q', '7694f4a66316e53c8cdd9d9954bd611d', 'ania@gmail.com', '2023-02-12 17:43:02', '2023-02-12 17:43:02', 1),
(2, 'trener1', 'e358efa489f58062f10dd7316b65649e', 't@gmail.com', '2023-02-12 17:43:52', '2023-02-12 17:43:52', 2),
(3, 'tomek', 'd0d41f1a3cc3f67dcd74694de9fef1b0', 'tomek@gmail.com', '2023-02-12 20:49:02', '2023-02-12 20:49:02', 1),
(4, 'trener2', 'cc914af11589b0873fd30d64a64e786e', 'trener2@gmail.com', '2023-02-12 20:49:37', '2023-02-12 20:49:37', 1),
(5, 'ania', '5f59ac736640f43e61c6070284bf1c06', 'ania@gmail.com', '2023-02-12 20:51:03', '2023-02-12 20:51:03', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `wybrane_zajecia`
--

CREATE TABLE `wybrane_zajecia` (
  `id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `zajecia_id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `wybrane_zajecia`
--

INSERT INTO `wybrane_zajecia` (`id`, `user_id`, `zajecia_id`) VALUES
(14, 1, 2),
(15, 5, 5),
(16, 5, 2),
(17, 5, 2),
(18, 5, 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `zajecia`
--

CREATE TABLE `zajecia` (
  `id` int(20) NOT NULL,
  `rodzaj` varchar(70) NOT NULL,
  `dzien` varchar(20) NOT NULL,
  `godzina` varchar(10) NOT NULL,
  `sala` int(10) DEFAULT NULL,
  `trener_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `zajecia`
--

INSERT INTO `zajecia` (`id`, `rodzaj`, `dzien`, `godzina`, `sala`, `trener_id`) VALUES
(2, 'pilates', 'TUESDAY', '12:00', 3, 1),
(3, 'joga', 'MONDAY', '16:00', 3, 1),
(4, 'kulki', 'MONDAY', '12:00', 20, 1),
(5, 'boks', 'SATURDAY', '9:00', 2, 2),
(6, 'zumba', 'MONDAY', '17:00', 4, 2),
(7, 'boks', 'FRIDAY', '14:00', 8, 2);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`);

--
-- Indeksy dla tabeli `wybrane_zajecia`
--
ALTER TABLE `wybrane_zajecia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `zajecia_id` (`zajecia_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indeksy dla tabeli `zajecia`
--
ALTER TABLE `zajecia`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `wybrane_zajecia`
--
ALTER TABLE `wybrane_zajecia`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT dla tabeli `zajecia`
--
ALTER TABLE `zajecia`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Ograniczenia dla tabeli `wybrane_zajecia`
--
ALTER TABLE `wybrane_zajecia`
  ADD CONSTRAINT `wybrane_zajecia_ibfk_1` FOREIGN KEY (`zajecia_id`) REFERENCES `zajecia` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `wybrane_zajecia_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
