package com.github.polomarcus.main

import com.typesafe.scalalogging.Logger
import com.github.polomarcus.utils.ClimateService

object Main {
  def main(args: Array[String]) {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run` to start the app")

    val sentence = "run sbt test to execute unit test"
    ClimateService.isClimateRelated(sentence)

    val co2Records = ClimateService.getCO2RawDataFromHawaii()
    val parsedCo2Records = ClimateService.parseRawData(co2Records)
    ClimateService.showCO2Data(parsedCo2Records)

    val cleanedData = ClimateService.filterDecemberData(parsedCo2Records)
    // Combine monthly data into yearly averages
    val yearlyData = cleanedData.groupBy(_.year).map { case (year, records) =>
      val avgPpm = records.map(_.ppm).sum / records.size
      (year, avgPpm)
    }.toList.sortBy(_._1)
    val (a, b) = ClimateService.linearRegression(yearlyData)
    // Predict for 2050
    val predictionFor2050 = a * 2050 + b
    logger.info(f"Predicted CO2 levels for 2050: $predictionFor2050%.2f ppm")

    logger.info("Stopping the app")
    System.exit(0)
  }
}

