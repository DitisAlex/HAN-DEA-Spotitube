-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 17 mrt 2021 om 17:13
-- Serverversie: 10.4.17-MariaDB
-- PHP-versie: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spotitube`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `playlist`
--

CREATE TABLE `playlist` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `playlist_tracks`
--

CREATE TABLE `playlist_tracks` (
  `id` int(11) NOT NULL,
  `playlistID` int(11) NOT NULL,
  `trackID` int(11) DEFAULT NULL,
  `offlineAvailable` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `tracks`
--

CREATE TABLE `tracks` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `performer` varchar(255) NOT NULL,
  `duration` int(11) NOT NULL,
  `album` varchar(255) DEFAULT NULL,
  `playcount` int(255) DEFAULT NULL,
  `publicationDate` date DEFAULT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `tracks`
--

INSERT INTO `tracks` (`id`, `title`, `performer`, `duration`, `album`, `playcount`, `publicationDate`, `description`) VALUES
(1, 'Conversations', 'Juice Wrld', 180, 'Legends Never Die', NULL, '2021-03-16', 'Banger song'),
(2, 'Life\'s A Mess', 'Juice Wrld', 186, 'Legends Never Die', NULL, '2021-03-16', 'aaa'),
(3, 'Man of the Year', 'Juice Wrld', 140, 'Legends Never Die', 2, '2021-03-15', NULL),
(4, 'Dandelion', 'Galantis', 220, NULL, 5, NULL, 'picked up a dandelion..'),
(5, 'Lifestyle (feat. Adam Levine)', 'Jason Derulo', 151, 'LIFESTYLE', 5, '2021-03-07', NULL),
(6, '24', 'Arizona Zervas', 195, NULL, 5, '2021-03-02', '24 hours 24 hours'),
(7, 'Safe And Sound', 'Capital Cities', 192, 'In A Tidal Wave Of Mystery', 112, '2020-12-08', 'WE SAFE AND SOUNDDDD'),
(8, 'Am I Wrong', 'Nico & Vinz', 120, 'Black Star Elephant', 5, NULL, NULL),
(9, 'Stolen Dance', 'Milky Chance', 301, 'Sadnecessary', NULL, NULL, 'never dance like this before'),
(10, 'Riptide', 'Vance Joy', 201, 'Dream Your Life Away', NULL, '2021-03-06', NULL);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `users`
--

INSERT INTO `users` (`userID`, `username`, `password`, `token`) VALUES
(1, 'Alex', 'Cheng', '88b32f70-eea7-42cc-b08f-145945f5f866'),
(2, 'test', 'test', 'db657ba1-692f-4d25-a55c-abace4e03cf4');

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `playlist`
--
ALTER TABLE `playlist`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `playlist_tracks`
--
ALTER TABLE `playlist_tracks`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `tracks`
--
ALTER TABLE `tracks`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `playlist`
--
ALTER TABLE `playlist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT voor een tabel `playlist_tracks`
--
ALTER TABLE `playlist_tracks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT voor een tabel `tracks`
--
ALTER TABLE `tracks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT voor een tabel `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
