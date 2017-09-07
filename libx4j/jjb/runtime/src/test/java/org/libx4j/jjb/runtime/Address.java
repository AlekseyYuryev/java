/* Copyright (c) 2017 lib4j
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.libx4j.jjb.runtime;

@JsonxObject
public class Address {
  @NumberProperty(form=Form.INTEGER, min="0", nullable=false)
  public Integer number;

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, nullable=false)
  public String street;

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, required=false, nullable=false)
  public String premise;

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, nullable=false)
  public String city;

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, nullable=false)
  public String postalCode;

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, nullable=false)
  public String locality;

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, nullable=false)
  public String country;
}