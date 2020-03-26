-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 19, 2020 at 09:32 PM
-- Server version: 5.7.29-0ubuntu0.16.04.1
-- PHP Version: 7.0.33-0ubuntu0.16.04.12
DROP DATABASE IF EXISTS `json_to_kotlin`;
CREATE DATABASE `json_to_kotlin`;
USE `json_to_kotlin`;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `json_to_kotlin`
--

-- --------------------------------------------------------

--
-- Table structure for table `hits`
--

CREATE TABLE `hits` (
  `id` int(11) NOT NULL,
  `client` enum('PLUG_IN','WEB','API') NOT NULL,
  `class_name` text NOT NULL,
  `annotation_lib` enum('NONE','NONE_CC','GSON','FAST_JSON','JACKSON','MOSHI','LOGAN_SQUARE','MOSHI_CODE_GEN','SERIALIZABLE','CUSTOM') NOT NULL,
  `class_suffix` text DEFAULT NULL,
  `default_value_strategy` enum('AVOID_NULL','ALLOW_NULL','NONE') NOT NULL,
  `indent` int(11) NOT NULL,
  `is_comments_enabled` tinyint(1) NOT NULL,
  `is_create_annotation_only_when_needed_enabled` tinyint(1) NOT NULL,
  `is_enable_var_properties` tinyint(1) NOT NULL,
  `is_force_init_default_value_with_origin_json_value_enabled` tinyint(1) NOT NULL,
  `is_force_primitive_type_non_nullable_enabled` tinyint(1) NOT NULL,
  `is_inner_class_model_enabled` tinyint(1) NOT NULL,
  `is_keep_annotation_on_class_androidx_enabled` tinyint(1) NOT NULL,
  `is_keep_annotation_on_class_enabled` tinyint(1) NOT NULL,
  `is_map_type_enabled` tinyint(1) NOT NULL,
  `is_order_by_alphabetic_enabled` tinyint(1) NOT NULL,
  `is_parcelable_support_enabled` tinyint(1) NOT NULL,
  `is_property_and_annotation_in_same_line_enabled` tinyint(1) NOT NULL,
  `package_name` text,
  `parent_class_template` text,
  `property_prefix` text,
  `property_suffix` text,
  `property_type_strategy` enum('NOT_NULLABLE','NULLABLE','AUTO_DETERMINE_NULLABLE_OR_NOT') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hits`
--
ALTER TABLE `hits`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hits`
--
ALTER TABLE `hits`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
