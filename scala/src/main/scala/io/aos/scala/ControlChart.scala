/**
 * **************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The AOS licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 * **************************************************************
 */

package io.aos.scala.controlchart
/** This package provide a class to compute a Shewhart Control Chart.
  * ==Overview==
  * It embeds all utility functions needed to compute the chart as
  * - A CSV file reader [[io.aos.scala.controlchart.ReadCSV]]
  * - A class to find every csv files in a folder [[io.aos.scala.controlchart.ReadCSVFolderCSV]]
  * - Basic stat function as [[io.aos.scala.controlchart.Stat.computeMean()]],
  *   [[io.aos.scala.controlchart.Stat.computeStdDev()]], [[io.aos.scala.controlchart.Stat.computeVariance()]]
  *   [[io.aos.scala.controlchart.Stat.computeIntegral()]].
  */


import java.io.File

object Stat {
  def computeMean(input: Array[Float]): Float = {
    var sum = 0.0
    for (i <- 0 to (input.length - 1)) {
      sum += input(i)
    }
    val mean = sum / input.length
    return mean.toFloat
  }

  /** Compute empirical variance */
  def computeVariance(input: Array[Float], mean: Float): Float = {
    var variance = 0.0
    for (i <- 0 to (input.length - 1)) {
      variance += Math.pow(input(i) - mean, 2)
    }
    variance = variance / (input.length - 1)

    return variance.toFloat
  }

  /** Compute naively the area under the curve. */
  def computeIntegral(xAxis: Array[Float], yAxis: Array[Float]): Float = {
    var integ = 0.0
    val step = Math.abs(xAxis(1) - xAxis(0))
    for (i <- 0 to (yAxis.length - 1)) {
      integ += yAxis(i) * step
    }
    return integ.toFloat
  }

  /** Compute the deviation from the mean. */
  def computeStdDev(value: Float, mean: Float): Float = {
    return Math.abs(value - mean)
  }
}

/** File reader utility
  * @param filePath the absolute/relative path of the csv.
  * */
 class ReadCSV(filePath: String) {
  def arToFloat(ar: Array[String]): Array[Float] = {
    val newAr = new Array[Float](ar.length)
    for (i <- 0 to (ar.length - 1)) {
      newAr(i) = ar(i).toFloat
    }
    return newAr
  }

  def getColumn(col: Int): Array[Float] = {
    val column = new Array[Float](event.length)

    for (i <- (0 to event.length - 1)) {
      column(i) = event(i)(col)
    }
    return column
  }

  def getLine(col: Int): Array[Float] = {
    val line = event(col)
    return line

  }

  val file = scala.io.Source.fromFile(filePath)
  val parserIt = file.getLines().drop(0).map(_.split(","))

  val colnames = parserIt.next()

  val event = scala.collection.mutable.ArrayBuffer.empty[Array[Float]]
  parserIt.foreach(a => event.append(arToFloat(a)))

  file.close()
}

class ReadCSVFolder(folderPath: String) {
  def listFiles(f: File): Array[File] = {
    return f.listFiles.sorted
  }

  val folder = new File(folderPath)

  val files = listFiles(folder)
  val files_fil = files.filter(_.toString.endsWith(".csv"))

  val data = new Array[ReadCSV](files_fil.length)
  for (f <- 0 to (data.length - 1)) {
    data(f) = new ReadCSV(files_fil(f).toString)
  }
}

/** The Control Chart class perform a basic control chart. It simply oulines
  * data which are 2 times greater than the standard deviation.
  * @param data An array of float representing the data to analyze. */
class ControlChart(data: Array[Float]) {
  val mean = Stat.computeMean(data)
  println(Stat.computeVariance(data, mean))
  val stdDev = Math.sqrt(Stat.computeVariance(data, mean)).toFloat

  val localDev = new Array[Float](data.length)
  for (i <- 0 to (data.length - 1)) {
    localDev(i) = Stat.computeStdDev(data(i), mean)
  }

  val outliers = scala.collection.mutable.ArrayBuffer.empty[Int]

  for (i <- 0 to (data.length - 1)) {
    if (Math.abs(localDev(i)) > 2 * stdDev) {
      outliers.append(i)
    }
  }

  def summary() = {
    println("Mean = " + mean)
    println("Standard Deviation = " + stdDev)
    println("Sample size = " + data.length)
    print("\n")
    println("Outliers:")

    for (i <- 0 to (outliers.length - 1)) {
      print("Data value : " + data(outliers(i)) + " -- " + i + "\n")
    }
  }
}
