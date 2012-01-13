-- Copyright Safris Software 2006
--
-- This code is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with this program.  If not, see <http://www.gnu.org/licenses/>.

DROP TABLE IF EXISTS depiction.image_info;

CREATE TABLE IF NOT EXISTS depiction.image_info (
  id INTEGER NOT NULL,
  path VARCHAR(255) NOT NULL,
  width INTEGER UNSIGNED NOT NULL,
  height INTEGER UNSIGNED NOT NULL,
  thumbnail_size TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (
    id
  )
);
