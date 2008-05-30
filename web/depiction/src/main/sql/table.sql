--  Copyright 2008 Safris Technologies Inc.
--
--  Licensed under the Apache License, Version 2.0 (the "License");
--  you may not use this file except in compliance with the License.
--  You may obtain a copy of the License at
--
--    http://www.apache.org/licenses/LICENSE-2.0
--
--  Unless required by applicable law or agreed to in writing, software
--  distributed under the License is distributed on an "AS IS" BASIS,
--  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--  See the License for the specific language governing permissions and
--  limitations under the License.


DROP TABLE IF EXISTS depiction.image_info;

CREATE TABLE IF NOT EXISTS depiction.image_info
(
	id INTEGER NOT NULL,
	path VARCHAR(255) NOT NULL,
	width INTEGER UNSIGNED NOT NULL,
	height INTEGER UNSIGNED NOT NULL,
	thumbnail_size TINYINT UNSIGNED NOT NULL,
	PRIMARY KEY
	(
		id
	)
);
